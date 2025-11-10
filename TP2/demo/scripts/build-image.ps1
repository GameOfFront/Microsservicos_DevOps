Write-Host "🔨 Iniciando build da imagem Docker..."
docker build -t ardovino/springboot-api:1.0 ..
docker push ardovino/springboot-api:1.0
Write-Host "✅ Imagem enviada para o Docker Hub com sucesso!"
