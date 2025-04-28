# Luizalabs Favorite Products API

API RESTful para gerenciamento de clientes e seus produtos favoritos.

Projeto desenvolvido com:
- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Security (Basic Auth)
- Springdoc OpenAPI (Swagger)
- Banco de dados H2 (em memória para desenvolvimento)

---

## Funcionalidades

- Cadastro, atualização e remoção de **clientes**.
- Adição e remoção de **produtos favoritos** para cada cliente.
- Integração com API pública de produtos (https://fakestoreapi.com/).
- Autenticação e autorização usando **Basic Authentication**.
- Banco de dados H2 inicializado automaticamente com dados.

---

## Requisitos

- Java 17+
- Maven 3.8+
- (Opcional) Postman, Insomnia ou outra ferramenta de testes de API.

---

## Como executar o projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/MuriloGustavo/luizalabs.git
   cd luizalabs

2. Build do projeto:
    ```bash
    mvn clean install

3. Execute a aplicação:
    ```bash
    mvn spring-boot:run

4. Acesse a aplicação em:
   ```bash
   http://localhost:8080

---

## Documentação da API (Swagger)

- Acesse a documentação:
   ```bash
   http://localhost:8080/api/swagger-ui/index.html
- Clique em "Authorize" (ícone de cadeado).
- Informe o username e password para autenticar.

---

## Usuários padrão para teste

| Username | Password | Role  |
|----------|----------|-------|
|   admin  | admin123 | ADMIN |
|   user   | user123  | USER  |

---

## Segurança

- Todas as rotas da API são protegidas.
- Autenticação obrigatória via **Basic Authentication**.
- Autorização baseada em perfis:
    - **ADMIN**: acesso total a todos os endpoints de clientes e produtos.
    - **USER**: acesso restrito aos produtos favoritos relacionados a si.

- Exemplo de chamada autenticada usando curl:
    ```bash
    curl -u admin:admin123 http://localhost:8080/api/clients

---

## Banco de dados

- Banco H2 em memória durante o desenvolvimento.
- Console disponível em:
    ```bash
        http://localhost:8080/api/h2-console
- Configurações:
    - JDBC URL: **jdbc:h2:mem:luizalabsdb**
    - User: **sa**
    - Password: **(em branco)**
- A base de dados é criada automaticamente através dos arquivos:
    - **schema.sql**: estrutura das tabelas
    - **data.sql**: carga inicial de clientes

---

## Rotas principais

Clientes (**/clients**)

| Método | Rota              | Descrição                       |
|--------|-------------------|---------------------------------|
| GET    | /api/clients      | Listar todos os clientes        |
| GET    | /api/clients/{id} | Buscar cliente por ID           |
| POST   | /api/clients      | Criar um novo cliente           |
| PUT    | /api/clients/{id} | Atualizar um cliente existente  |
| DELETE | /api/clients/{id} | Deletar um cliente              |

Produtos Favoritos (**/clients/{clientId}/products**)

| Método | Rota                                         | Descrição                           |
|--------|----------------------------------------------|-------------------------------------|
| GET    | /api/clients/{clientId}/products             | Listar produtos favoritos do client |
| POST   | /api/clients/{clientId}/products/{productId} | Adicionar produto favorito          |
| DELETE | /api/clients/{clientId}/products/{productId} | Remover produto favorito            |

---

## Tecnologias utilizadas

- **Spring Boot**: Framework principal do projeto.
- **Spring Data JPA**: Persistência e manipulação de dados.
- **Spring Security**: Controle de autenticação e autorização.
- **Springdoc OpenAPI**: Documentação da API.
- **H2 Database**: Banco de dados em memória.
- **Lombok**: Anotações para reduzir o código repetitivo

---

Desenvolvido por **Murilo Gustavo**.