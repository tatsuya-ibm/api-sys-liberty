#!/bin/bash

# ============================================
# 銀行口座管理API 動作確認スクリプト (Linux/Mac)
# ============================================

BASE_URL="http://localhost:9080/api-proc"

echo "=========================================="
echo "銀行口座管理API 動作確認"
echo "=========================================="
echo ""

# 色付き出力用
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 1. 口座情報取得
echo -e "${YELLOW}[1] 口座情報取得${NC}"
echo "GET /accounts?branchCode=001&accountNumber=1234567"
curl -s -X GET "${BASE_URL}/accounts?branchCode=001&accountNumber=1234567" | jq .
echo ""
echo ""

# 2. 入金
echo -e "${YELLOW}[2] 入金処理${NC}"
echo "POST /accounts/deposit"
curl -s -X POST "${BASE_URL}/accounts/deposit" \
  -H "Content-Type: application/json" \
  -d '{
    "branchCode": "001",
    "accountNumber": "1234567",
    "amount": 50000.00
  }' | jq .
echo ""
echo ""

# 3. 入金後の残高確認
echo -e "${YELLOW}[3] 入金後の残高確認${NC}"
echo "GET /accounts?branchCode=001&accountNumber=1234567"
curl -s -X GET "${BASE_URL}/accounts?branchCode=001&accountNumber=1234567" | jq .
echo ""
echo ""

# 4. 出金
echo -e "${YELLOW}[4] 出金処理${NC}"
echo "POST /accounts/withdraw"
curl -s -X POST "${BASE_URL}/accounts/withdraw" \
  -H "Content-Type: application/json" \
  -d '{
    "branchCode": "001",
    "accountNumber": "1234567",
    "amount": 30000.00
  }' | jq .
echo ""
echo ""

# 5. 出金後の残高確認
echo -e "${YELLOW}[5] 出金後の残高確認${NC}"
echo "GET /accounts?branchCode=001&accountNumber=1234567"
curl -s -X GET "${BASE_URL}/accounts?branchCode=001&accountNumber=1234567" | jq .
echo ""
echo ""

# 6. 振込トランザクション作成
echo -e "${YELLOW}[6] 振込トランザクション作成${NC}"
echo "POST /transfers"
curl -s -X POST "${BASE_URL}/transfers" \
  -H "Content-Type: application/json" \
  -d '{
    "destBankCode": "0001",
    "destBranchCode": "002",
    "destAccountNumber": "7654321",
    "amount": 100000.00
  }' | jq .
echo ""
echo ""

# 7. エラーケース: 存在しない口座
echo -e "${YELLOW}[7] エラーケース: 存在しない口座${NC}"
echo "GET /accounts?branchCode=999&accountNumber=9999999"
curl -s -X GET "${BASE_URL}/accounts?branchCode=999&accountNumber=9999999" | jq .
echo ""
echo ""

# 8. エラーケース: 残高不足
echo -e "${YELLOW}[8] エラーケース: 残高不足${NC}"
echo "POST /accounts/withdraw (残高を超える出金)"
curl -s -X POST "${BASE_URL}/accounts/withdraw" \
  -H "Content-Type: application/json" \
  -d '{
    "branchCode": "001",
    "accountNumber": "2345678",
    "amount": 9999999.00
  }' | jq .
echo ""
echo ""

# 9. エラーケース: 不正な金額（負の値）
echo -e "${YELLOW}[9] エラーケース: 不正な金額（負の値）${NC}"
echo "POST /accounts/deposit (負の金額)"
curl -s -X POST "${BASE_URL}/accounts/deposit" \
  -H "Content-Type: application/json" \
  -d '{
    "branchCode": "001",
    "accountNumber": "1234567",
    "amount": -1000.00
  }' | jq .
echo ""
echo ""

echo -e "${GREEN}=========================================="
echo "動作確認完了"
echo "==========================================${NC}"

# Made with Bob
