# SpringGame
To start the app and deploy to kubernetes 
first login to azure from cli using az login
once logged in
az aks get-credentials --name your-aks-cluster-name --resource-group your-aks-resource-group
And then run startup.sh file with arguments of docker username and password
.\startup.sh dockerUserName dockerPassword

docker build -f Dockerfile -t dockerhubusername/appname:latest .
docker push dockerhubusername/appname:latest
kubectl apply -f deployment.yaml
kubectl get services
kubectl get pods
kubectl logs -f podname
kubectl delete service kubernetes (but his keeps on recreating we need to delete deployment itself)
kubectl get deployments
kubectl delete deployment deploymnetname

Once deployed into cluster:

ERROR: Caused by: com.azure.core.exception.HttpResponseException: Status code 403, "{"error":{"code":"Forbidden","message":"Caller is not authorized to perform actio
n on resource.

For this we need to go to Virtual machine scale sets and then click on the pool where your service is running
and enable managed identity and then click on azure role assignments and give Key Vault Secrets User role
Doing this reflects on key vault side as well
I need to delete deployment and then restart it 


ERROR WHILE CONNECTING TO MONGO DB

2023-07-18 02:16:00.151 [scheduling-1] INFO  org.mongodb.driver.cluster [UserIdInMDC] - Cluster description not yet available. Waiting for 30000 ms before timing out
2023-07-18 02:16:07.562 [cluster-ClusterId{value='64b5f5ddc6400057cc7da0ea', description='null'}-c.akhilmongodbcluster.mongocluster.cosmos.azure.com:10260] INFO  org.mongodb.driver.cluster [] - Exception in monitor thread while connecting to server c.akhilmongodbcluster.mongocluster.cosmos.azure.com:10260
com.mongodb.MongoSocketOpenException: Exception opening socket
at com.mongodb.internal.connection.SocketStream.open(SocketStream.java:73)
at com.mongodb.internal.connection.InternalStreamConnection.open(InternalStreamConnection.java:183)
at com.mongodb.internal.connection.DefaultServerMonitor$ServerMonitorRunnable.lookupServerDescription(DefaultServerMonitor.java:198)
at com.mongodb.internal.connection.DefaultServerMonitor$ServerMonitorRunnable.run(DefaultServerMonitor.java:158)
at java.base/java.lang.Thread.run(Thread.java:833)
Caused by: java.net.SocketTimeoutException: Connect timed out
at java.base/sun.nio.ch.NioSocketImpl.timedFinishConnect(NioSocketImpl.java:546)
at java.base/sun.nio.ch.NioSocketImpl.connect(NioSocketImpl.java:597)
at java.base/java.net.SocksSocketImpl.connect(SocksSocketImpl.java:327)
at java.base/java.net.Socket.connect(Socket.java:633)
at java.base/sun.security.ssl.SSLSocketImpl.connect(SSLSocketImpl.java:299)
at com.mongodb.internal.connection.SocketStreamHelper.initialize(SocketStreamHelper.java:107)
at com.mongodb.internal.connection.SocketStream.initializeSocket(SocketStream.java:82)
at com.mongodb.internal.connection.SocketStream.open(SocketStream.java:68)
... 4 common frames omitted


To fix this we need to allow public access from azure services under mongo cosmos db cluster and networking
