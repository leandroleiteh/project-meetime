# Project ERP - Backend

## Descrição

**Project ERP - Backend** é um sistema backend desenvolvido com **Spring Boot 3.4.2** para suportar um sistema de ERP (Enterprise Resource Planning). O projeto utiliza uma arquitetura moderna, seguindo boas práticas de desenvolvimento, e conta com uma documentação de API baseada em **Swagger/OpenAPI**.

---

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.4.2**
- **Maven**
- **Swagger/OpenAPI**
- **Spring Security** (JWT Auth)
- **JPA/Hibernate**
- **Banco de Dados H2/MySQL**
- **Lombok**
- **MapStruct**
- **Apache Commons**
- **Apache POI**

---

## Estrutura do Projeto

O projeto segue uma estrutura modularizada para facilitar a manutenção e expansão.

```
project-erp/
├── src/
│ ├── main/
│ │ ├── java/br/com/project/
│ │ │ ├── config/ # Configurações do sistema
│ │ │ ├── security/ # Implementação de segurança com JWT
│ │ │ ├── constant/ # Constantes do sistema
│ │ │ ├── controller/ # Controllers da API
│ │ │ ├── entity/ # Entidades do banco de dados
│ │ │ ├── enums/ # Enumerações utilizadas no sistema
│ │ │ ├── exception/ # Tratamento de exceções
│ │ │ ├── helper/ # Classes auxiliares
│ │ │ ├── mapper/ # Mapeamento de entidades e DTOs
│ │ │ ├── repository/ # Persistência de dados
│ │ │ ├── service/ # Regras de negócio
│ │ │ ├── util/ # Utilitários e helpers
│ │ ├── resources/
│ │ │ ├── application.properties # Configuração da aplicação
│ │ │ ├── swagger/ # Definição dos endpoints
└── pom.xml # Configuração do Maven
```

---

## Dependências do Projeto

O projeto possui diversas dependências configuradas no arquivo `pom.xml`, listadas abaixo:

### **Spring Boot**
- `spring-boot-starter-data-jpa` → Suporte ao Hibernate/JPA
- `spring-boot-starter-security` → Segurança com Spring Security
- `spring-boot-starter-validation` → Validação de dados
- `spring-boot-starter-web` → Desenvolvimento de APIs REST

### **Banco de Dados**
- `h2` → Banco de dados em memória para testes
- `mysql-connector-j` → Driver para conexão com MySQL

### **Ferramentas de Desenvolvimento**
- `lombok` → Redução de boilerplate code
- `spring-boot-devtools` → Hot reload para desenvolvimento

### **Testes**
- `spring-boot-starter-test` → Frameworks de testes integrados
- `spring-security-test` → Testes para Spring Security

### **Documentação (Swagger/OpenAPI)**
- `swagger-core`
- `swagger-annotations`
- `jackson-databind-nullable`
- `swagger-codegen-maven-plugin`

### **Utilidades**
- `commons-lang` → Utilitários para manipulação de Strings, Numbers, etc.
- `commons-collections` → Manipulação de coleções Java
- `commons-codec` → Codificação e decodificação de dados
- `apache-poi` → Manipulação de arquivos Excel

### **Mapeamento de Objetos (DTOs)**
- `mapstruct`
- `mapstruct-processor`

---

## Plugins do Maven

- `swagger-codegen-maven-plugin` → Geração automática da API baseada no YAML do Swagger
- `build-helper-maven-plugin` → Adiciona diretórios gerados automaticamente ao classpath
- `spring-boot-maven-plugin` → Empacotamento da aplicação como um JAR executável

---

## Configuração e Execução

### **Requisitos**
- **Java 21**
- **Maven**

### **Compilar e Rodar**

```sh
# Compilar o projeto
mvn clean package

# Executar a aplicação
mvn spring-boot:run
```

### **Executar Testes**

```sh
mvn test
```

### **Swagger UI**
A documentação da API pode ser acessada após a inicialização da aplicação:

```
http://localhost:8000/swagger-ui.html
```

### **Configuração do Banco de Dados**

Por padrão, a aplicação utiliza o **H2 Database** para testes locais. Para usar o **MySQL**, configure o `application.properties`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/project_erp
    username: root
    password: senha
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database-platform: org.hibernate.dialect.MySQL8Dialect
```
---

## Autenticação e Segurança

A API utiliza **JWT (JSON Web Token)** para autenticação e autorização. Para acessar endpoints protegidos, é necessário enviar um token no header:

```
Authorization: Bearer {token}
```

Endpoints de autenticação:

- **`POST /auth/login`** → Gera um token JWT
- **`GET /users/me`** → Retorna informações do usuário autenticado

---


## Licença

Este projeto está sob a licença **???**. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

**Desenvolvido por:** [???] 🚀

