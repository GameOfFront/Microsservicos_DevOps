# =========================
# Etapa 1 - Build do projeto com Maven
# =========================
FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app

# Instala o Maven (necessário porque a imagem base só tem o JDK)
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Copia os arquivos Maven
COPY pom.xml .
COPY src ./src

# Compila o projeto (sem rodar testes)
RUN mvn clean package -DskipTests

# =========================
# Etapa 2 - Runtime (imagem leve)
# =========================
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Copia o JAR gerado na etapa anterior
COPY --from=builder /app/target/service-api-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta do serviço
EXPOSE 8081

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
