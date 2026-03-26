# ビルドステージ
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY lib ./lib
RUN mvn clean package -DskipTests

# 実行ステージ
FROM icr.io/appcafe/open-liberty@sha256:4f5455d383544fe7b2515edd733cd97315b118139593c322520415556e2d0ab5
COPY --chown=1001:0 ./lib/postgresql-42.7.1.jar /opt/ol/wlp/usr/shared/resources/postgresql/
COPY --chown=1001:0 src/main/liberty/config /config
RUN features.sh
COPY --chown=1001:0 --from=builder /app/target/*.war /config/apps
RUN configure.sh

#FROM icr.io/appcafe/open-liberty:kernel-slim-java21-openj9-ubi-minimal

# PostgreSQL JDBCドライバのコピー
#COPY --chown=1001:0 ./lib/postgresql-42.7.1.jar /opt/ol/wlp/usr/shared/resources/postgresql/

# Liberty設定ファイルのコピー
#COPY --chown=1001:0 /src/main/liberty/config /config

# フィーチャーのインストール
#RUN features.sh

# アプリケーションWARファイルのコピー
#COPY --chown=1001:0 target/*.war /config/apps

# サーバー設定の適用
#RUN configure.sh
