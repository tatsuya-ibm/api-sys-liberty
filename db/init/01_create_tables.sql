-- ============================================
-- 銀行口座管理システム DDL
-- ============================================

-- 口座テーブル
CREATE TABLE account (
    branch_code VARCHAR(3) NOT NULL,
    account_number VARCHAR(7) NOT NULL,
    account_name VARCHAR(100) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_account PRIMARY KEY (branch_code, account_number),
    CONSTRAINT chk_balance CHECK (balance >= 0)
);

COMMENT ON TABLE account IS '口座テーブル';
COMMENT ON COLUMN account.branch_code IS '支店番号';
COMMENT ON COLUMN account.account_number IS '口座番号';
COMMENT ON COLUMN account.account_name IS '口座名義';
COMMENT ON COLUMN account.balance IS '残高';
COMMENT ON COLUMN account.created_at IS '作成日時';
COMMENT ON COLUMN account.updated_at IS '更新日時';

-- 入出金履歴テーブル
CREATE TABLE transaction_history (
    id BIGSERIAL NOT NULL,
    branch_code VARCHAR(3) NOT NULL,
    account_number VARCHAR(7) NOT NULL,
    transaction_type VARCHAR(10) NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    balance_after DECIMAL(15, 2) NOT NULL,
    transaction_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_transaction_history PRIMARY KEY (id),
    CONSTRAINT fk_transaction_account FOREIGN KEY (branch_code, account_number) 
        REFERENCES account(branch_code, account_number),
    CONSTRAINT chk_transaction_type CHECK (transaction_type IN ('DEPOSIT', 'WITHDRAW'))
);

COMMENT ON TABLE transaction_history IS '入出金履歴テーブル';
COMMENT ON COLUMN transaction_history.id IS '履歴ID';
COMMENT ON COLUMN transaction_history.branch_code IS '支店番号';
COMMENT ON COLUMN transaction_history.account_number IS '口座番号';
COMMENT ON COLUMN transaction_history.transaction_type IS '取引種別(DEPOSIT:入金/WITHDRAW:出金)';
COMMENT ON COLUMN transaction_history.amount IS '取引金額';
COMMENT ON COLUMN transaction_history.balance_after IS '取引後残高';
COMMENT ON COLUMN transaction_history.transaction_at IS '取引日時';

-- インデックス作成
CREATE INDEX idx_transaction_history_account ON transaction_history(branch_code, account_number);
CREATE INDEX idx_transaction_history_date ON transaction_history(transaction_at);

-- 振込トランザクションテーブル
CREATE TABLE transfer_transaction (
    id BIGSERIAL NOT NULL,
    dest_bank_code VARCHAR(4) NOT NULL,
    dest_branch_code VARCHAR(3) NOT NULL,
    dest_account_number VARCHAR(7) NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_transfer_transaction PRIMARY KEY (id),
    CONSTRAINT chk_transfer_amount CHECK (amount > 0)
);

COMMENT ON TABLE transfer_transaction IS '振込トランザクションテーブル';
COMMENT ON COLUMN transfer_transaction.id IS 'トランザクションID';
COMMENT ON COLUMN transfer_transaction.dest_bank_code IS '宛先銀行コード';
COMMENT ON COLUMN transfer_transaction.dest_branch_code IS '宛先支店番号';
COMMENT ON COLUMN transfer_transaction.dest_account_number IS '宛先口座番号';
COMMENT ON COLUMN transfer_transaction.amount IS '振込金額';
COMMENT ON COLUMN transfer_transaction.created_at IS '作成日時';

-- インデックス作成
CREATE INDEX idx_transfer_transaction_date ON transfer_transaction(created_at);

-- Made with Bob
