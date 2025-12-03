# =========================
# Etapa 1 - Build do projeto
# =========================
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o pom.xml e baixa dependências para cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte e empacota
COPY src ./src
RUN mvn clean package -DskipTests

# =========================
# Etapa 2 - Imagem final (Runtime)
# =========================
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Copia o JAR gerado da etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Define o perfil ativo (para usar application-docker.properties)
ENV SPRING_PROFILES_ACTIVE=docker

# Expõe a porta padrão da aplicação
EXPOSE 8081

# Define o comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
