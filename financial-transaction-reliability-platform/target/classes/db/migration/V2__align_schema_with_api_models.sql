-- Align the schema with the in-memory API model before a persistent adapter is wired.
-- Keep this as a forward migration in case V1 has already been applied locally.

ALTER TABLE transfers ADD COLUMN owner_id UUID;

UPDATE transfers AS transfer
SET owner_id = account.user_id
FROM accounts AS account
WHERE account.id = transfer.source_account_id;

DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM transfers WHERE owner_id IS NULL) THEN
        RAISE EXCEPTION 'Cannot infer owner_id for every existing transfer';
    END IF;
END $$;

ALTER TABLE transfers ALTER COLUMN owner_id SET NOT NULL;
ALTER TABLE transfers
    ADD CONSTRAINT fk_transfers_owner FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE RESTRICT;

-- A transfer's API owner is the owner of its source account.
ALTER TABLE accounts ADD CONSTRAINT uq_accounts_id_user UNIQUE (id, user_id);
ALTER TABLE transfers
    ADD CONSTRAINT fk_transfers_source_owner
    FOREIGN KEY (source_account_id, owner_id) REFERENCES accounts (id, user_id) ON DELETE RESTRICT;

ALTER TABLE transfers DROP CONSTRAINT uq_transfers_idempotency_key;
ALTER TABLE transfers
    ADD CONSTRAINT uq_transfers_owner_idempotency_key UNIQUE (owner_id, idempotency_key);

CREATE INDEX ix_transfers_owner_created ON transfers (owner_id, created_at DESC);

-- The current API creates INR-only accounts and transfers.
ALTER TABLE accounts
    ADD CONSTRAINT ck_accounts_currency_inr CHECK (currency = 'INR');
ALTER TABLE transfers
    ADD CONSTRAINT ck_transfers_currency_inr CHECK (currency = 'INR');
ALTER TABLE ledger_entries
    ADD CONSTRAINT ck_ledger_currency_inr CHECK (currency = 'INR');
