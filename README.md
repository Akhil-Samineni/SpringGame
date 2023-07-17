# SpringGame
docker build -f Dockerfile -t dockerhubusername/appname:latest .
docker push dockerhubusername/appname:latest
kubectl apply -f deployment.yaml   
kubectl get services 
kubectl get pods
kubectl logs -f podname
kubectl delete service kubernetes (but his keeps on recreating we need to delete deployment itself)
kubectl get deployments
kubectl delete deployment deploymnetname