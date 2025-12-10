# 1. Usar Java 17
FROM eclipse-temurin:17-jdk-alpine

# 2. Definir carpeta de trabajo
WORKDIR /app

# 3. Copiar todos los archivos del proyecto al contenedor
COPY . .

# 4. Dar permisos de ejecución al instalador de Maven
RUN chmod +x mvnw

# 5. Construir el archivo .jar (Saltando los tests para ir rápido)
RUN ./mvnw clean package -DskipTests

# 6. Comando para arrancar la app
# Render asigna el puerto automáticamente en la variable $PORT
CMD ["java", "-jar", "-Dserver.port=${PORT}", "target/gestion-0.0.1-SNAPSHOT.jar"]