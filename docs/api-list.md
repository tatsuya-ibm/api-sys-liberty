# 公開APIリスト

## 概要

- **ベースURL**: `http://<host>:9080/api-sys/api`
- **コンテンツタイプ**: `application/json`

---

## 口座API (`/accounts`)

### 1. 口座情報取得

| 項目 | 内容 |
|------|------|
| **メソッド** | GET |
| **パス** | `/api-sys/api/accounts` |
| **説明** | 支店番号と口座番号を指定して口座情報を取得する |

#### リクエストパラメータ（クエリパラメータ）

| パラメータ名 | 型 | 必須 | 説明 |
|---|---|---|---|
| `branchCode` | string | ✓ | 支店番号 |
| `accountNumber` | string | ✓ | 口座番号 |

#### レスポンス（成功時 200 OK）

```json
{
  "branchCode": "001",
  "accountNumber": "1234567",
  "accountName": "山田 太郎",
  "balance": 100000,
  "transactionHistories": [
    {
      "transactionDate": "2026-03-01",
      "amount": 10000,
      "comment": "入金"
    },
    {
      "transactionDate": "2026-02-28",
      "amount": -5000,
      "comment": "出金"
    }
  ]
}
```

| フィールド名 | 型 | 説明 |
|---|---|---|
| `branchCode` | string | 支店番号 |
| `accountNumber` | string | 口座番号 |
| `accountName` | string | 口座名義 |
| `balance` | number | 残高 |
| `transactionHistories` | array | 入出金履歴リスト |

**transactionHistories 要素**

| フィールド名 | 型 | 説明 |
|---|---|---|
| `transactionDate` | string | 取引日付（YYYY-MM-DD形式） |
| `amount` | number | 入出金額（入金はプラス、出金はマイナス） |
| `comment` | string | コメント（取引種別: 入金 / 出金） |

---

### 2. 入金

| 項目 | 内容 |
|------|------|
| **メソッド** | POST |
| **パス** | `/api-sys/api/accounts/deposit` |
| **説明** | 指定口座に入金する |

#### リクエストボディ

```json
{
  "branchCode": "001",
  "accountNumber": "1234567",
  "amount": 10000
}
```

| フィールド名 | 型 | 必須 | 説明 |
|---|---|---|---|
| `branchCode` | string | ✓ | 支店番号 |
| `accountNumber` | string | ✓ | 口座番号 |
| `amount` | number | ✓ | 入金金額 |

#### レスポンス（成功時 200 OK）

```json
{
  "status": 0
}
```

| フィールド名 | 型 | 説明 |
|---|---|---|
| `status` | number | 処理結果ステータス（0: 正常） |

---

### 3. 出金

| 項目 | 内容 |
|------|------|
| **メソッド** | POST |
| **パス** | `/api-sys/api/accounts/withdraw` |
| **説明** | 指定口座から出金する |

#### リクエストボディ

```json
{
  "branchCode": "001",
  "accountNumber": "1234567",
  "amount": 5000
}
```

| フィールド名 | 型 | 必須 | 説明 |
|---|---|---|---|
| `branchCode` | string | ✓ | 支店番号 |
| `accountNumber` | string | ✓ | 口座番号 |
| `amount` | number | ✓ | 出金金額 |

#### レスポンス（成功時 200 OK）

```json
{
  "status": 0
}
```

| フィールド名 | 型 | 説明 |
|---|---|---|
| `status` | number | 処理結果ステータス（0: 正常） |

---

## 振込API (`/transfers`)

### 4. 振込

| 項目 | 内容 |
|------|------|
| **メソッド** | POST |
| **パス** | `/api-sys/api/transfers` |
| **説明** | 振込トランザクションを作成する |

#### リクエストボディ

```json
{
  "destBankCode": "0001",
  "destBranchCode": "002",
  "destAccountNumber": "7654321",
  "amount": 30000
}
```

| フィールド名 | 型 | 必須 | 説明 |
|---|---|---|---|
| `destBankCode` | string | ✓ | 振込先銀行コード |
| `destBranchCode` | string | ✓ | 振込先支店番号 |
| `destAccountNumber` | string | ✓ | 振込先口座番号 |
| `amount` | number | ✓ | 振込金額 |

#### レスポンス（成功時 200 OK）

```json
{
  "status": 0
}
```

| フィールド名 | 型 | 説明 |
|---|---|---|
| `status` | number | 処理結果ステータス（0: 正常） |

---

## エラーレスポンス

全APIで共通のエラーレスポンス形式を返す。

```json
{
  "errorCode": "0001",
  "errorMessage": "口座が見つかりません"
}
```

| フィールド名 | 型 | 説明 |
|---|---|---|
| `errorCode` | string | エラーコード |
| `errorMessage` | string | エラーメッセージ |

### HTTPステータスコードとエラーコード一覧

| HTTPステータス | エラーコード | 説明 |
|---|---|---|
| 400 Bad Request | `0005` | 必須パラメータが不足している |
| 400 Bad Request | その他 | 業務エラー（残高不足など） |
| 404 Not Found | `0001` | 口座が見つからない |
| 500 Internal Server Error | `9999` | システムエラー |

---

## API一覧サマリ

| No | メソッド | パス | 説明 |
|---|---|---|---|
| 1 | GET | `/api-sys/api/accounts` | 口座情報取得 |
| 2 | POST | `/api-sys/api/accounts/deposit` | 入金 |
| 3 | POST | `/api-sys/api/accounts/withdraw` | 出金 |
| 4 | POST | `/api-sys/api/transfers` | 振込 |