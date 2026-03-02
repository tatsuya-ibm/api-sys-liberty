@echo off
chcp 65001 > nul
REM ============================================
REM 銀行口座管理API 動作確認スクリプト (Windows)
REM ============================================

set BASE_URL=http://localhost:9080/api-sys/api

echo ==========================================
echo 銀行口座管理API 動作確認
echo ==========================================
echo.

REM 1. 口座情報取得（入出金履歴リスト含む）
echo [1] 口座情報取得（入出金履歴リスト含む）
echo GET /accounts?branchCode=001^&accountNumber=1234567
echo レスポンスには branchCode, accountNumber, accountName, balance, transactionHistories が含まれます
curl -s -X GET "%BASE_URL%/accounts?branchCode=001&accountNumber=1234567"
echo.
echo.

REM 2. 入金
echo [2] 入金処理
echo POST /accounts/deposit
curl -s -X POST "%BASE_URL%/accounts/deposit" ^
  -H "Content-Type: application/json" ^
  -d "{\"branchCode\":\"001\",\"accountNumber\":\"1234567\",\"amount\":50000.00}"
echo.
echo.

REM 3. 入金後の残高・履歴確認
echo [3] 入金後の残高・履歴確認
echo GET /accounts?branchCode=001^&accountNumber=1234567
echo transactionHistories に入金履歴が追加されていることを確認
curl -s -X GET "%BASE_URL%/accounts?branchCode=001&accountNumber=1234567"
echo.
echo.

REM 4. 出金
echo [4] 出金処理
echo POST /accounts/withdraw
curl -s -X POST "%BASE_URL%/accounts/withdraw" ^
  -H "Content-Type: application/json" ^
  -d "{\"branchCode\":\"001\",\"accountNumber\":\"1234567\",\"amount\":30000.00}"
echo.
echo.

REM 5. 出金後の残高・履歴確認
echo [5] 出金後の残高・履歴確認
echo GET /accounts?branchCode=001^&accountNumber=1234567
echo transactionHistories に出金履歴が追加されていることを確認
curl -s -X GET "%BASE_URL%/accounts?branchCode=001&accountNumber=1234567"
echo.
echo.

REM 6. 振込トランザクション作成
echo [6] 振込トランザクション作成
echo POST /transfers
curl -s -X POST "%BASE_URL%/transfers" ^
  -H "Content-Type: application/json" ^
  -d "{\"destBankCode\":\"0001\",\"destBranchCode\":\"002\",\"destAccountNumber\":\"7654321\",\"amount\":100000.00}"
echo.
echo.

REM 7. エラーケース: 存在しない口座
echo [7] エラーケース: 存在しない口座
echo GET /accounts?branchCode=999^&accountNumber=9999999
curl -s -X GET "%BASE_URL%/accounts?branchCode=999&accountNumber=9999999"
echo.
echo.

REM 8. エラーケース: 残高不足
echo [8] エラーケース: 残高不足
echo POST /accounts/withdraw (残高を超える出金)
curl -s -X POST "%BASE_URL%/accounts/withdraw" ^
  -H "Content-Type: application/json" ^
  -d "{\"branchCode\":\"001\",\"accountNumber\":\"2345678\",\"amount\":9999999.00}"
echo.
echo.

REM 9. エラーケース: 不正な金額（負の値）
echo [9] エラーケース: 不正な金額（負の値）
echo POST /accounts/deposit (負の金額)
curl -s -X POST "%BASE_URL%/accounts/deposit" ^
  -H "Content-Type: application/json" ^
  -d "{\"branchCode\":\"001\",\"accountNumber\":\"1234567\",\"amount\":-1000.00}"
echo.
echo.

echo ==========================================
echo 動作確認完了
echo ==========================================
pause

@REM Made with Bob
