# Usa un'immagine base di Java
FROM openjdk:17-jdk-alpine

# Imposta la variabile d'ambiente
ENV JAVA_OPTS=""

# Copia il file JAR generato nella cartella del container
COPY target/todoapi-1.0.0.jar /todo-api.jar

# Espone la porta 8081 per il container
EXPOSE 8081

# Definisce il comando di avvio dell'applicazione
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /todo-api.jar"]
