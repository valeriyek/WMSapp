# Use Maven to build the application
FROM maven:3.8.1-openjdk-11 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Use a minimal Java runtime image
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/WarehouseMain.jar /app/WarehouseMain.jar

# Download JMX Exporter
ADD https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/0.15.0/jmx_prometheus_javaagent-0.15.0.jar /app/jmx_prometheus_javaagent.jar
COPY jmx_exporter_config.yml /app/jmx_exporter_config.yml

EXPOSE 8080
ENTRYPOINT ["java", "-javaagent:/app/jmx_prometheus_javaagent.jar=12345:/app/jmx_exporter_config.yml", "-jar", "/app/DemoWMS.jar"]