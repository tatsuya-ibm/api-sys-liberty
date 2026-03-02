-- ============================================
-- テストデータ投入
-- ============================================

-- 口座データ
INSERT INTO account (branch_code, account_number, account_name, balance, created_at, updated_at)
VALUES 
    ('001', '1234567', '山田太郎', 1000000.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('001', '2345678', '佐藤花子', 500000.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('002', '3456789', '鈴木一郎', 2000000.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('002', '4567890', '田中美咲', 750000.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('003', '5678901', '高橋健太', 1500000.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 入出金履歴データ（サンプル）
INSERT INTO transaction_history (branch_code, account_number, transaction_type, amount, balance_after, transaction_at)
VALUES 
    ('001', '1234567', 'DEPOSIT', 100000.00, 1000000.00, CURRENT_TIMESTAMP - INTERVAL '1 day'),
    ('001', '2345678', 'DEPOSIT', 500000.00, 500000.00, CURRENT_TIMESTAMP - INTERVAL '2 days'),
    ('002', '3456789', 'WITHDRAW', 50000.00, 2000000.00, CURRENT_TIMESTAMP - INTERVAL '3 days');

-- Made with Bob
