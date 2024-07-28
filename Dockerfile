# Usa una imagen base con Java
FROM eclipse-temurin:17-jre-alpine

# Etiqueta del autor del Dockerfile
LABEL autor="Juan Carlos Arguello"

# Establece el directorio de trabajo
WORKDIR /app 

# Define una variable de la ruta del archivo jar
ARG JAR_FILE=target/sgse-backend-0.0.1-SNAPSHOT.jar

# Copia el archivo JAR de tu aplicación a la imagen Docker
COPY ${JAR_FILE} /app/sgse-backend.jar

# Expone el puerto en el que se ejecuta tu aplicación Spring
EXPOSE 8080

# Comando para ejecutar la aplicación Spring cuando se inicie el contenedor
ENTRYPOINT [ "java", "-jar", "sgse-backend.jar" ]


# DOCKERFILE EN DESARROLLO
#FROM eclipse-temurin:17-jdk-alpine
#WORKDIR /sgse-backend
#COPY . .
#RUN ./mvnw clean package
#EXPOSE 8080
#ENTRYPOINT [ "java", "-jar", "target/sgse-backend-0.0.1-SNAPSHOT.jar" ]