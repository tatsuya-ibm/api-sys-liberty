
FROM icr.io/appcafe/open-liberty:kernel-slim-java21-openj9-ubi-minimal

# PostgreSQL JDBCドライバのコピー
COPY --chown=1001:0 ./lib/postgresql-42.7.1.jar /opt/ol/wlp/usr/shared/resources/postgresql/

# Liberty設定ファイルのコピー
COPY --chown=1001:0 /src/main/liberty/config /config

# フィーチャーのインストール
RUN features.sh

# アプリケーションWARファイルのコピー
COPY --chown=1001:0 target/*.war /config/apps

# サーバー設定の適用
RUN configure.sh
