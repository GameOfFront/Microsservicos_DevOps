Write-Host "🚀 Executando aplicação Spring Boot localmente via Docker..."
docker run -d -p 8080:8080 --name springboot-api ardovino/springboot-api:1.0
Write-Host "✅ Aplicação disponível em http://localhost:8080/api/produtos/hello"
