# Fórum Hub API

A API do Fórum Hub é uma aplicação backend para gerenciar usuários, tópicos e cursos. Ela está configurada para usar o Spring Boot com PostgreSQL como banco de dados, e está pronta para ser testada com o Postman.
## Configuração do Projeto

Para rodar este projeto localmente, siga os passos abaixo.

### 1. Clone o Repositório

Clone o repositório usando o comando abaixo:

```bash
git clone https://github.com/usuario/forumhub-api.git
```

### 2. Configuração do Banco de Dados

Edite o arquivo `application.properties` para configurar o acesso ao seu banco de dados PostgreSQL:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/forumhub  
spring.datasource.username=postgres  
spring.datasource.password=sua-senha  
spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true  
spring.datasource.driver-class-name=org.postgresql.Driver  
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect  
server.port=8080
```
### 3. Executando o Projeto

Para rodar o projeto, basta usar o Maven:

```bash
mvn spring-boot:run
```
## 4. Testando a API com o Postman

Você pode importar a coleção do Postman para testar a API.

1. Acesse a coleção [aqui](https://github.com/likannp/forumhub-api/blob/main/forumhub.postman_collection.json).
2. Importe a coleção no Postman.
3. Configure as variáveis de ambiente para o URL da sua API (`localhost:8080`).

## Dependências

Este projeto utiliza as seguintes dependências:

- Spring Boot Web
- Spring Data JPA
- PostgreSQL Driver
- Spring Security
- Jackson (para serialização/deserialização JSON)
- JWT (para autenticação)

Aqui está o `pom.xml` com as dependências necessárias:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <version>2.7.6</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
        <version>2.7.6</version>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.5.0</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
        <version>2.7.6</version>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.13.3</version>
    </dependency>
</dependencies>
```
