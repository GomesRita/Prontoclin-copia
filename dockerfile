# Etapa 1: Construir o JAR usando uma imagem Maven
FROM maven:3.8-openjdk-17-slim AS build
# Defina o diretório de trabalho no contêiner
WORKDIR /app

# Copie o arquivo pom.xml e o diretório src para dentro do contêiner
COPY ../SpringProntoClin/pom.xml /app/
COPY ../SpringProntoClin/src /app/src/

# Baixar as dependências e compilar o projeto
RUN mvn clean package -DskipTests

# Etapa 2: Criar a imagem final com JRE e o JAR
FROM openjdk:17-slim

# Defina o diretório de trabalho no contêiner
WORKDIR /app

# Copiar o arquivo JAR gerado da etapa de build para o contêiner
COPY --from=build /app/target/SpringProntoClin-0.0.1-SNAPSHOT.jar /app/app.jar

# Expor a porta que o Spring Boot vai rodar
EXPOSE 8080

# Definir o comando de execução do Spring Boot
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
