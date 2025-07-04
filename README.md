# msclientes-devsu

Compila el proyecto de microservicio de clientes

1) tener instalado maven en su computadora
2) Ir consola de cmd
3) Ubicar en la raiz del proyecto
4) Ejecutar el comando "mvn clean compile"
5) Luego dentro del ide de intelij click en el boton ejecutar

Ejecutar script de mysql proporcionado (Dockerfile proporcionado trabaja con bd local)

Ejecutar dockerfile:

1) ir a esta url https://hub.docker.com/r/nraudales2/msclientes
2) ejecutar docker pull nraudales2/msclientes:latest
3) docker run -p 8080:8080 nraudales2/msclientes:latest