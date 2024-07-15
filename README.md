# Produtos API

Este projeto é uma API simples desenvolvida com Java Spring para fins acadêmicos, abordando disciplinas de frameworks
backend e serviços web.  
Futuramente, pretende-se usar esta aplicação como integração para as matérias de Mobile 2 e frameworks de frontend.

## Overview

A API foi construída utilizando as seguintes tecnologias principais:

- Java Spring Boot: Framework principal para a criação da API.
- H2 Database: Banco de dados em memória para testes.
- Spring Security: Para autenticação e autorização.
- JWT (JSON Web Token): Para a geração e validação de tokens de autenticação.
- Swagger: Para documentação e teste da API.
# Endpoints
- [Registrar Usuario](#registrar-usuario)

## Registrar Usuario
/auth/register
### Método: POST

**Descrição**: Registra um novo usuário e retorna uma mensagem de sucesso.

#### Request:

````Json
{
  "email": "string",
  "password": "string",
  "role": "USER | ADMIN"
}
````

#### Response de Sucesso:

````json
{
  "message": "Usuario registrado com sucesso"
}
````

## /auth/login

Login de Usuário

### Método: POST

**Descrição:** Realiza o login do usuário e retorna um token JWT.

#### Request:

````json
{
  "email": "string",
  "password": "string"
}
````

#### Response de Sucesso:

````json
{
  "token": "Bearer JWT-TOKEN"
}
````

## /product

### Método: POST

**Descrição**: Cria um novo produto com as informações fornecidas.

#### Request:

```json
{
  "name": "Nome do Produto",
  "price": 99.99
}
```

#### Response de Sucesso

````json
{
  "id": 1,
  "name": "Nome do produto",
  "price": 9.99
}
````

### Método: GET

**Descrição**: Retorna todos os produtos cadastrados.

#### Response de Sucesso:

```json
{
  "qttd": 2,
  "products": [
    {
      "id": 1,
      "name": "Nome do produto",
      "price": 9.99
    },
    {
      "id": 2,
      "name": "Nome do produto",
      "price": 9.99
    }
  ]
}
```

## /products/:id

#### Parâmetros de Path:

- `id` (integer): ID do produto a ser recuperado.

### Método: GET

**Descrição**: Retorna um produto específico baseado no ID fornecido.

#### Response de Sucesso:

```json
{
  "id": 1,
  "name": "Nome do Produto",
  "price": 99.99
}
```
### Método: PUT  
**Descrição**: Atualiza as informações de um produto existente com base no ID fornecido.

#### Request:
```json
{
  "name": "Novo Nome do Produto",
  "price": 129.99
}
````

#### Método: DELETE  
**Descrição**: Remove um produto existente com base no ID fornecido.

#### Response de Sucesso:
```json
{
  "message": "DELETED. Product with id: 1"
}
```



