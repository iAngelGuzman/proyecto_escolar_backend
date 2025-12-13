# para usar java 17
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# copiamos todo el código fuente
COPY . .

# dar permisos de ejecución al instalador de Maven
RUN chmod +x mvnw

# construir el archivo .jar para ir rápido
RUN ./mvnw clean package -DskipTests

# comando para arrancar la app usando el puerto dinámico de Render
CMD ["java", "-jar", "-Dserver.port=${PORT}", "target/gestion-0.0.1-SNAPSHOT.jar"]