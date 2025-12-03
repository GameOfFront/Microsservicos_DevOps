# ============================================
# SCRIPT DE SETUP COMPLETO - MICROSERVIÇOS ASSESSMENT
# Autor: Luiz
# Descrição: Recria o cluster Minikube e sobe os microsserviços do projeto.
# ============================================

Write-Host "1) Limpando ambiente antigo..." -ForegroundColor Yellow
minikube stop
minikube delete --all

# ============================================
Write-Host "2) Criando novo cluster Minikube..." -ForegroundColor Yellow
minikube start --driver=docker --kubernetes-version=v1.30.0 --cpus=2 --memory=4096

# ============================================
Write-Host "3) Conectando Docker ao ambiente interno do Minikube..." -ForegroundColor Yellow
minikube -p minikube docker-env | Invoke-Expression

# ============================================
Write-Host "4) Gerando os .jar dos microsserviços..." -ForegroundColor Yellow

# Compila cada projeto individualmente (agora dentro da pasta k8s)
cd ./service-api
mvn clean package -DskipTests

cd ../service-cliente
mvn clean package -DskipTests

cd ../service-consumer
mvn clean package -DskipTests

cd ..

# ============================================
Write-Host "5) Construindo imagens Docker dentro do Minikube..." -ForegroundColor Yellow
docker build -t service-api:latest ./service-api
docker build -t service-cliente:latest ./service-cliente
docker build -t service-consumer:latest ./service-consumer

Write-Host "Imagens criadas:" -ForegroundColor Green
docker images | findstr "service-"

# ============================================
Write-Host "6) Aplicando namespace..." -ForegroundColor Yellow
kubectl apply -f ./namespace.yaml

# ============================================
Write-Host "7) Subindo bancos de dados (Postgres API e Cliente)..." -ForegroundColor Yellow
kubectl apply -f ./postgres-api/ -n microservices-assessment
kubectl apply -f ./postgres-cliente/ -n microservices-assessment

Write-Host "Aguardando pods do Postgres ficarem prontos..." -ForegroundColor Yellow
kubectl wait --for=condition=ready pod -l app=postgres-api -n microservices-assessment --timeout=120s
kubectl wait --for=condition=ready pod -l app=postgres-cliente -n microservices-assessment --timeout=120s

# ============================================
Write-Host "8) Subindo microsserviços (API, Cliente e Consumer)..." -ForegroundColor Yellow
kubectl apply -f ./service-api/ -n microservices-assessment
kubectl apply -f ./service-cliente/ -n microservices-assessment
kubectl apply -f ./service-consumer/ -n microservices-assessment

Write-Host "Aguardando todos os pods ficarem prontos..." -ForegroundColor Yellow
kubectl wait --for=condition=ready pod -l app=service-api -n microservices-assessment --timeout=180s
kubectl wait --for=condition=ready pod -l app=service-cliente -n microservices-assessment --timeout=180s
kubectl wait --for=condition=ready pod -l app=service-consumer -n microservices-assessment --timeout=180s

# ============================================
Write-Host "9) Expondo URL pública do service-consumer..." -ForegroundColor Yellow
$url = minikube service service-consumer -n microservices-assessment --url

Write-Host "==============================================" -ForegroundColor Yellow
Write-Host "Ambiente rodando com sucesso! Use esta URL no Postman:" -ForegroundColor Green
Write-Host ""
Write-Host "URL: $url" -ForegroundColor Cyan
Write-Host ""
Write-Host "Exemplo de endpoint: $url/consumer/produtos"
Write-Host ""
Write-Host "Setup completo com sucesso!" -ForegroundColor Green
# ============================================
