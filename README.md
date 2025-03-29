# Demo de uma API com Spring Boot e com Autenticação Azure Directory

## Funcionalidades

Este projeto é uma API desenvolvida com Spring Boot que oferece as seguintes funcionalidades:

### [Swagger UI](http://localhost:8080/swagger-ui.html) 

## Requisitos
- **Java 21**
- **Banco de Dados PostgreSQL** ou **Docker Compose** para executar o banco de dados em um contêiner.

## Script de Inicialização
O script data.sql localizado em src/main/resources/data.sql é executado automaticamente no início da aplicação, criando a estrutura do banco de dados e registros de teste.

## Executando o Projeto

### Com PostgreSQL

1. Certifique-se de ter o PostgreSQL instalado e em execução.
2. Configure as credenciais do banco de dados no arquivo `application.yml`.
3. Execute a aplicação pela sua IDE de desenvolvimento ou compile e execute a aplicação com o Maven:
   ```sh
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

### Com Docker Compose
1. Certifique-se de ter o Docker e o Docker Compose instalados.
2. Execute o comando abaixo para iniciar os serviços:
   ```sh
   docker-compose up
   ```