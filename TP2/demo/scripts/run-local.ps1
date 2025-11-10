Write-Host "☸️ Aplicando manifests no Kubernetes..."
kubectl apply -f ../kubernetes/springboot-deployment.yaml
kubectl apply -f ../kubernetes/springboot-service.yaml
Write-Host "✅ Deploy realizado com sucesso!"
kubectl get pods
kubectl get services
