# -----------------------------
# 1️⃣ Etapa de build (compilação)
# -----------------------------
FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /app

# Instala o Maven dentro do container para compilar o projeto
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Copia os arquivos necessários do Maven
COPY pom.xml .
COPY src ./src

# Compila o projeto sem rodar testes
RUN mvn clean package -DskipTests

# -----------------------------
# 2️⃣ Etapa final (execução)
# -----------------------------
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copia o JAR gerado na etapa anterior
COPY --from=builder /app/target/service-cliente-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta usada pelo microsserviço
EXPOSE 8082

# Define o comando para iniciar o Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
