FROM openjdk:21-ea-24-oracle

WORKDIR /app
COPY target/usuarios-1.0-SNAPSHOT.jar app.jar
COPY Wallet_K8OTOIK423MJWKAX /app/oracle_wallet
EXPOSE 8080

CMD [ "java", "-jar", "app.jar" ]