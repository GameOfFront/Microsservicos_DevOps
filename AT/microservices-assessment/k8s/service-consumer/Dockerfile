# ===========================
# Etapa 1 — Build da aplicação
# ===========================
FROM eclipse-temurin:21-jdk-jammy AS builder

# Diretório de trabalho
WORKDIR /app

# Instala o Maven (necessário para compilar o projeto com base no seu pom.xml)
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Copia o arquivo pom.xml e baixa dependências antes de copiar o código-fonte
# Isso otimiza o cache do Docker
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte para dentro do container
COPY src ./src

# Compila e empacota a aplicação (sem rodar testes)
RUN mvn clean package -DskipTests

# ===========================
# Etapa 2 — Runtime otimizado
# ===========================
FROM eclipse-temurin:21-jdk-jammy

# Diretório de trabalho no container final
WORKDIR /app

# Copia apenas o JAR final da etapa anterior
COPY --from=builder /app/target/service-consumer-0.0.1-SNAPSHOT.jar app.jar

# Exponha a porta 8083 (porta definida no application.properties)
EXPOSE 8083

# Define o ponto de entrada da aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
