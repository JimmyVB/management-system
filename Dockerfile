FROM eclipse-temurin:17-jdk

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo pom.xml y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar la aplicación
RUN mvn package -DskipTests

# Exponer el puerto en el que corre la aplicación
EXPOSE 8080

# Ejecutar la aplicación
CMD ["java", "-jar", "target/management-system-0.0.1-SNAPSHOT.jar"]
