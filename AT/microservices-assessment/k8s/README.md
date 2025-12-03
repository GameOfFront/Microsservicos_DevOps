# 🧩 Microservices Assessment

Arquitetura de microsserviços desenvolvida com **Spring Boot 3.3.5** e **Java 21**, composta por três aplicações independentes que se comunicam entre si via **REST**. O projeto utiliza **Docker**, **Kubernetes (Minikube)** e **PostgreSQL** para persistência e orquestração.

## Microsserviços

| Serviço         | Função                                         | Porta Interna | Tipo de Serviço |
|----------------|-------------------------------------------------|---------------|-----------------|
| service-api     | Gerenciamento de produtos                       | 8081          | ClusterIP       |
| service-cliente | Gerenciamento de clientes                       | 8082          | ClusterIP       |
| service-consumer| Consome e agrega dados dos outros dois serviços | 8083          | NodePort        |

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3.3.5
- Spring WebFlux
- Spring Data R2DBC
- PostgreSQL
- Maven
- Docker
- Kubernetes (kubectl + Minikube)
- PowerShell (para automação com o script `setup-k8s.ps1`)

## Estrutura de Diretórios

```text
k8s/
├── postgres-api/
│   ├── deployment.yaml
│   ├── pvc.yaml
│   └── service.yaml
├── postgres-cliente/
│   ├── deployment.yaml
│   ├── pvc.yaml
│   └── service.yaml
├── service-api/
│   ├── deployment.yaml
│   ├── service.yaml
│   └── configmap.yaml
├── service-cliente/
│   ├── deployment.yaml
│   ├── service.yaml
│   └── configmap.yaml
└── service-consumer/
    ├── deployment.yaml
    └── service.yaml

