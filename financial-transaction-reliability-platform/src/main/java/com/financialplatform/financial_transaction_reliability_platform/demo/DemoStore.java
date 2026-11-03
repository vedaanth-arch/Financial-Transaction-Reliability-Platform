package com.financialplatform.financial_transaction_reliability_platform.demo;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.*;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.springframework.stereotype.Service;

/** Temporary API-phase store. All state disappears when the process stops. */
@Service
public class DemoStore {
    public record User(UUID id, String email, String passwordHash) {}
    public record Account(UUID id, UUID ownerId, BigDecimal balance, String currency) {}
    public record Transfer(UUID id, UUID ownerId, UUID sourceAccountId, UUID destinationAccountId,
                          BigDecimal amount, String currency, String status, String idempotencyKey,
                          Date createdAt) {}
    public record LedgerEntry(UUID id, UUID transferId, UUID accountId, String entryType,
                              BigDecimal amount, String currency, Date createdAt) {}

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, User> tokens = new HashMap<>();
    private final Map<UUID, Account> accounts = new LinkedHashMap<>();
    private final Map<UUID, Transfer> transfers = new LinkedHashMap<>();
    private final List<LedgerEntry> ledgerEntries = new ArrayList<>();
    private final Map<String, Transfer> idempotentTransfers = new HashMap<>();
    private final SecureRandom random = new SecureRandom();

    public synchronized User register(String email, String password) {
        String key = email.toLowerCase(Locale.ROOT);
        if (users.containsKey(key)) throw new ApiException(409, "EMAIL_ALREADY_REGISTERED", "Email is already registered");
        User user = new User(UUID.randomUUID(), email, hash(password));
        users.put(key, user);
        return user;
    }

    public synchronized String login(String email, String password) {
        User user = users.get(email.toLowerCase(Locale.ROOT));
        if (user == null || !verify(password, user.passwordHash())) throw new ApiException(401, "INVALID_CREDENTIALS", "Email or password is incorrect");
        byte[] bytes = new byte[32]; random.nextBytes(bytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        tokens.put(token, user);
        return token;
    }
    public synchronized User registeredUser(String email) {
        User user = users.get(email.toLowerCase(Locale.ROOT));
        if (user == null) throw new ApiException(401, "INVALID_CREDENTIALS", "Email or password is incorrect");
        return user;
    }

    public synchronized User authenticate(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) throw new ApiException(401, "AUTHENTICATION_REQUIRED", "Use Authorization: Bearer <token>");
        User user = tokens.get(authorization.substring(7));
        if (user == null) throw new ApiException(401, "INVALID_TOKEN", "Bearer token is invalid or no longer available");
        return user;
    }

    public synchronized Account createAccount(User owner, BigDecimal initialBalance) {
        Account account = new Account(UUID.randomUUID(), owner.id(), initialBalance, "INR");
        accounts.put(account.id(), account); return account;
    }
    public synchronized Account account(User user, UUID id) {
        Account account = accounts.get(id);
        if (account == null) throw new ApiException(404, "ACCOUNT_NOT_FOUND", "Account was not found");
        if (!account.ownerId().equals(user.id())) throw new ApiException(403, "ACCOUNT_FORBIDDEN", "Account belongs to another user");
        return account;
    }

    public synchronized Transfer transfer(User user, UUID sourceId, UUID destinationId, BigDecimal amount, String key) {
        if (key == null || key.isBlank() || key.length() > 128) throw new ApiException(400, "INVALID_IDEMPOTENCY_KEY", "Idempotency-Key header is required (1 to 128 characters)");
        String scope = user.id() + ":" + key;
        Transfer previous = idempotentTransfers.get(scope);
        if (previous != null) {
            if (!previous.sourceAccountId().equals(sourceId) || !previous.destinationAccountId().equals(destinationId) || previous.amount().compareTo(amount) != 0)
                throw new ApiException(409, "IDEMPOTENCY_KEY_REUSED", "Idempotency key was already used with a different request");
            return previous;
        }
        Account source = account(user, sourceId);
        Account destination = accounts.get(destinationId);
        if (destination == null) throw new ApiException(404, "ACCOUNT_NOT_FOUND", "Destination account was not found");
        if (sourceId.equals(destinationId)) throw new ApiException(400, "SAME_ACCOUNT", "Source and destination must differ");
        if (!"INR".equals(destination.currency())) throw new ApiException(400, "CURRENCY_MISMATCH", "Both accounts must use INR");
        if (source.balance().compareTo(amount) < 0) throw new ApiException(409, "INSUFFICIENT_FUNDS", "Source account balance is insufficient");
        accounts.put(sourceId, new Account(source.id(), source.ownerId(), source.balance().subtract(amount), source.currency()));
        accounts.put(destinationId, new Account(destination.id(), destination.ownerId(), destination.balance().add(amount), destination.currency()));
        Transfer transfer = new Transfer(UUID.randomUUID(), user.id(), sourceId, destinationId, amount, "INR", "COMPLETED", key, new Date());
        transfers.put(transfer.id(), transfer);
        Date timestamp = transfer.createdAt();
        ledgerEntries.add(new LedgerEntry(UUID.randomUUID(),transfer.id(),sourceId,"DEBIT",amount,"INR",timestamp));
        ledgerEntries.add(new LedgerEntry(UUID.randomUUID(),transfer.id(),destinationId,"CREDIT",amount,"INR",timestamp));
        idempotentTransfers.put(scope, transfer); return transfer;
    }

    public synchronized Transfer transfer(User user, UUID id) {
        Transfer transfer = transfers.get(id);
        if (transfer == null) throw new ApiException(404, "TRANSFER_NOT_FOUND", "Transfer was not found");
        account(user, transfer.sourceAccountId()); return transfer;
    }
    public synchronized List<Transfer> history(User user, UUID accountId) {
        account(user, accountId);
        return transfers.values().stream().filter(t -> t.sourceAccountId().equals(accountId) || t.destinationAccountId().equals(accountId))
                .sorted(Comparator.comparing(Transfer::createdAt).reversed()).toList();
    }
    private String hash(String password) {
        byte[] salt = new byte[16]; random.nextBytes(salt);
        byte[] digest = derive(password, salt);
        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(digest);
    }
    private boolean verify(String password, String stored) {
        try { String[] parts = stored.split(":"); return MessageDigest.isEqual(derive(password, Base64.getDecoder().decode(parts[0])), Base64.getDecoder().decode(parts[1])); }
        catch (RuntimeException e) { return false; }
    }
    private byte[] derive(String password, byte[] salt) {
        try { PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 120_000, 256); try { return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded(); } finally { spec.clearPassword(); } }
        catch (Exception e) { throw new IllegalStateException("Password hashing is unavailable", e); }
    }
}
