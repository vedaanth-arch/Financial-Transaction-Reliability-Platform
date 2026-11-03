package com.financialplatform.financial_transaction_reliability_platform.api.accounts;

import com.financialplatform.financial_transaction_reliability_platform.demo.DemoStore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/accounts")
public class AccountsController {
    private final DemoStore store;
    public AccountsController(DemoStore store) { this.store = store; }
    public record CreateAccount(@DecimalMin(value="0.00") @Digits(integer=17,fraction=2) BigDecimal initialBalance) {}
    public record AccountView(UUID id, String currency, BigDecimal balance) {}
    public record BalanceView(UUID accountId, String currency, BigDecimal balance) {}
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public AccountView create(@RequestHeader(value="Authorization",required=false) String authorization, @Valid @RequestBody(required=false) CreateAccount request) {
        var user=store.authenticate(authorization); BigDecimal balance=request==null||request.initialBalance()==null?BigDecimal.ZERO:request.initialBalance();
        var account=store.createAccount(user,balance); return new AccountView(account.id(),account.currency(),account.balance());
    }
    @GetMapping("/{accountId}")
    public AccountView get(@RequestHeader(value="Authorization",required=false) String authorization,@PathVariable UUID accountId) {
        var account=store.account(store.authenticate(authorization),accountId); return new AccountView(account.id(),account.currency(),account.balance());
    }
    @GetMapping("/{accountId}/balance")
    public BalanceView balance(@RequestHeader(value="Authorization",required=false) String authorization,@PathVariable UUID accountId) {
        var account=store.account(store.authenticate(authorization),accountId); return new BalanceView(account.id(),account.currency(),account.balance());
    }
}
