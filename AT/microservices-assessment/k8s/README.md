# Microservices Assessment

## Descrição
Arquitetura de microsserviços desenvolvida com Spring Boot 3.3.5 e Java 21, composta por três aplicações independentes que se comunicam entre si via REST. O projeto utiliza Docker, Docker Compose e Kubernetes para orquestração, além de testes automatizados com JUnit, Mockito e JaCoCo para garantir qualidade e cobertura de código.

---

## Microsserviços

| Serviço | Função | Porta |
|----------|--------|-------|
| service-api | Gerenciamento de produtos | 8081 |
| service-cliente | Gerenciamento de clientes | 8082 |
| service-consumer | Consumo e agregação de dados dos outros serviços | 8083 |

---

## Tecnologias

**Linguagem e Frameworks**
- Java 21  
- Spring Boot 3.3.5  
- Spring Data JPA  
- Spring Web  
- Spring Boot Actuator  

**Banco de Dados**
- PostgreSQL (produção)  
- H2 Database (testes)

**Build e Testes**
- Maven  
- JUnit 5  
- Mockito  
- JaCoCo  

**DevOps**
- Docker  
- Docker Compose  
- Kubernetes  
- Spring Boot Maven Plugin  

**Outros**
- Lombok  
- Jackson Databind  

---

## Requisitos
- Java 21  
- Maven 3.9+  
- Docker e Docker Compose  
- Kubernetes (kubectl e minikube ou Docker Desktop com K8s habilitado)

---

## Execução Local

### 1. Compilar os serviços
```bash
cd service-api && mvn clean package -DskipTests
cd ../service-cliente && mvn clean package -DskipTests
cd ../service-consumer && mvn clean package -DskipTests

docker-compose up --build
