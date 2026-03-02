# 銀行口座管理APIアプリケーション

銀行口座の入出金管理と他行への振込トランザクション作成を行うREST APIアプリケーションです。

## 技術スタック

- **Java**: 21
- **フレームワーク**: Jakarta EE 10.0 (Open Liberty)
- **データベース**: PostgreSQL 16
- **ビルドツール**: Maven
- **コンテナ**: Docker / Docker Compose

## プロジェクト構成

```
api-sys/
├── src/
│   ├── main/
│   │   ├── java/jp/sample/
│   │   │   ├── entity/          # エンティティクラス
│   │   │   ├── dao/              # データアクセスオブジェクト
│   │   │   ├── service/          # ビジネスロジック
│   │   │   ├── resource/         # RESTエンドポイント
│   │   │   ├── dto/              # リクエスト/レスポンスDTO
│   │   │   └── exception/        # 例外クラス
│   │   ├── resources/
│   │   │   └── META-INF/
│   │   │       └── persistence.xml
│   │   └── liberty/config/
│   │       └── server.xml
├── db/init/                      # データベース初期化スクリプト
│   ├── 01_create_tables.sql
│   └── 02_insert_data.sql
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── .env
```

## 前提条件

### ローカル開発環境
- Java 21
- Maven 3.9+
- PostgreSQL 16（Dockerで起動可能）

### Docker環境
- Docker 20.10+
- Docker Compose 2.0+

## セットアップ手順

### 1. PostgreSQL JDBCドライバの準備

PostgreSQL JDBCドライバをダウンロードして配置します。

```bash
# libディレクトリを作成
mkdir -p lib

# PostgreSQL JDBCドライバをダウンロード（バージョン42.7.1）
# Windows (PowerShell)
Invoke-WebRequest -Uri "https://jdbc.postgresql.org/download/postgresql-42.7.1.jar" -OutFile "lib/postgresql-42.7.1.jar"

# Linux/Mac
curl -o lib/postgresql-42.7.1.jar https://jdbc.postgresql.org/download/postgresql-42.7.1.jar
```

### 2. PostgreSQLの起動（Docker）

```bash
# PostgreSQLコンテナのみ起動
docker-compose up -d postgres

# 起動確認
docker-compose ps

# ログ確認
docker-compose logs postgres
```

データベースが起動すると、`db/init/`配下のSQLスクリプトが自動実行され、テーブルとテストデータが作成されます。

### 3. アプリケーションのビルド

```bash
# Mavenでビルド
mvnw clean package

# または
./mvnw clean package  # Windows(Powershell)
```

ビルドが成功すると、`target/api-proc.war`が生成されます。

## 実行方法

### 方法1: ローカル開発環境で実行

#### 1. 環境変数の設定

`.env`ファイルを確認・編集します（デフォルト値で問題なければそのまま使用可能）。

```properties
DB_HOST=localhost
DB_PORT=5432
DB_NAME=bankdb
DB_USER=bankuser
DB_PASSWORD=bankpass
```

#### 2. Open Libertyサーバーの起動

```bash
# 開発モードで起動
mvnw liberty:dev

# または
./mvnw liberty:dev  # Windows(Powershell)
```

サーバーが起動したら、以下のURLでアクセス可能です：
- **API Base URL**: http://localhost:9080/api-proc

#### 3. 停止

開発モードの場合、コンソールで`Ctrl+C`を押すか、`q`を入力してEnterで停止します。

### 方法2: Docker Composeで実行

#### 1. すべてのサービスを起動

```bash
# ビルドして起動
docker-compose up --build

# バックグラウンドで起動
docker-compose up -d --build
```

#### 2. 起動確認

```bash
# コンテナの状態確認
docker-compose ps

# アプリケーションログ確認
docker-compose logs -f app
```

#### 3. 停止

```bash
# 停止
docker-compose down

# データも削除する場合
docker-compose down -v
```

## API仕様

### ベースURL
```
http://localhost:9080/api-proc
```

### エンドポイント一覧

#### 1. 口座情報取得
```http
GET /accounts?branchCode={支店番号}&accountNumber={口座番号}
```

**レスポンス例**:
```json
{
  "branchCode": "001",
  "accountNumber": "1234567",
  "accountName": "山田太郎",
  "balance": 1000000.00
}
```

#### 2. 入金
```http
POST /accounts/deposit
Content-Type: application/json

{
  "branchCode": "001",
  "accountNumber": "1234567",
  "amount": 50000.00
}
```

**レスポンス例**:
```json
{
  "status": 0
}
```

#### 3. 出金
```http
POST /accounts/withdraw
Content-Type: application/json

{
  "branchCode": "001",
  "accountNumber": "1234567",
  "amount": 30000.00
}
```

**レスポンス例**:
```json
{
  "status": 0
}
```

#### 4. 振込トランザクション作成
```http
POST /transfers
Content-Type: application/json

{
  "destBankCode": "0001",
  "destBranchCode": "002",
  "destAccountNumber": "7654321",
  "amount": 100000.00
}
```

**レスポンス例**:
```json
{
  "status": 0
}
```

### エラーレスポンス

```json
{
  "errorCode": "0001",
  "errorMessage": "指定された口座が見つかりません"
}
```

## API動作確認

### curlコマンドでの確認

#### 口座情報取得
```bash
curl -X GET "http://localhost:9080/api-proc/accounts?branchCode=001&accountNumber=1234567"
```

#### 入金
```bash
curl -X POST http://localhost:9080/api-proc/accounts/deposit \
  -H "Content-Type: application/json" \
  -d '{
    "branchCode": "001",
    "accountNumber": "1234567",
    "amount": 50000.00
  }'
```

#### 出金
```bash
curl -X POST http://localhost:9080/api-proc/accounts/withdraw \
  -H "Content-Type: application/json" \
  -d '{
    "branchCode": "001",
    "accountNumber": "1234567",
    "amount": 30000.00
  }'
```

#### 振込トランザクション作成
```bash
curl -X POST http://localhost:9080/api-proc/transfers \
  -H "Content-Type: application/json" \
  -d '{
    "destBankCode": "0001",
    "destBranchCode": "002",
    "destAccountNumber": "7654321",
    "amount": 100000.00
  }'
```

## テストデータ

初期データとして以下の口座が登録されています：

| 支店番号 | 口座番号 | 口座名義 | 残高 |
|---------|---------|---------|------|
| 001 | 1234567 | 山田太郎 | 1,000,000円 |
| 001 | 2345678 | 佐藤花子 | 500,000円 |
| 002 | 3456789 | 鈴木一郎 | 2,000,000円 |
| 002 | 4567890 | 田中美咲 | 750,000円 |
| 003 | 5678901 | 高橋健太 | 1,500,000円 |

## トラブルシューティング

### データベース接続エラー

**症状**: アプリケーション起動時に接続エラーが発生

**対処法**:
1. PostgreSQLが起動しているか確認
   ```bash
   docker-compose ps postgres
   ```
2. 環境変数が正しく設定されているか確認
3. ポート5432が他のプロセスで使用されていないか確認

### JDBCドライバが見つからない

**症状**: `ClassNotFoundException: org.postgresql.Driver`

**対処法**:
1. `lib/postgresql-42.7.1.jar`が存在するか確認
2. Dockerfileで正しくコピーされているか確認
3. server.xmlのライブラリ設定を確認

### ポート競合

**症状**: ポート9080または5432が既に使用されている

**対処法**:
1. 使用中のポートを確認
   ```bash
   # Windows
   netstat -ano | findstr :9080
   netstat -ano | findstr :5432
   
   # Linux/Mac
   lsof -i :9080
   lsof -i :5432
   ```
2. docker-compose.ymlでポート番号を変更

## 開発情報

### ログ確認

#### ローカル開発
```bash
# Liberty開発モードのコンソールに出力されます
```

#### Docker環境
```bash
# アプリケーションログ
docker-compose logs -f app

# PostgreSQLログ
docker-compose logs -f postgres
```

### データベース直接接続

```bash
# Docker環境のPostgreSQLに接続
docker exec -it bank-postgres psql -U bankuser -d bankdb

# よく使うSQLコマンド
\dt              # テーブル一覧
\d account       # テーブル定義確認
SELECT * FROM account;
```

## ライセンス

このプロジェクトはサンプルアプリケーションです。

## 参考資料

- [Open Liberty Documentation](https://openliberty.io/docs/)
- [Jakarta EE 10 Specification](https://jakarta.ee/specifications/platform/10/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)