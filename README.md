# 🥗 Nutri-Express API

API REST desenvolvida em **Spring Boot (Java 17)** com persistência em banco de dados relacional **PostgreSQL** (via Docker), gerenciamento com **Spring Data JPA**, validação com **Bean Validation**, **Records (DTOs)** e tratamento global de exceções.

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
* **Java 17** ou superior instalado.
* **Docker** instalado e em execução (Docker Desktop).

---

### 1. Iniciar o Banco de Dados (PostgreSQL no Docker)

Abra o terminal e execute o comando abaixo para iniciar o container do PostgreSQL com persistência de dados:

```bash
docker run --name postgres-nutri -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=nutridb -p 5432:5432 -v postgres_dados:/var/lib/postgresql/data -d postgres:16
```

> **Nota:** Se você pausar o computador ou reiniciar, basta religar o container com:
> ```bash
> docker start postgres-nutri
> ```

---

### 2. Executar a Aplicação Spring Boot

No terminal, dentro da pasta do projeto, execute:

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`.

---

## 📋 Endpoints Implementados (`/pratos`)

| Verbo | Rota | Descrição | Status de Sucesso | Status de Erro |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/pratos` | Lista todos os pratos cadastrados | `200 OK` | - |
| **GET** | `/pratos/{id}` | Busca os detalhes de um prato pelo ID | `200 OK` | `404 Not Found` |
| **GET** | `/pratos?categoria=Sobremesa` | Filtra pratos pelo nome ou ID da categoria | `200 OK` | - |
| **GET** | `/pratos/calorias?max=500` | Filtra pratos com até X calorias | `200 OK` | - |
| **POST** | `/pratos` | Cadastra um novo prato no cardápio | `201 Created` | `400 Bad Request` |
| **PUT** | `/pratos/{id}` | Atualiza todas as informações de um prato | `200 OK` | `404 Not Found` / `400 Bad Request` |
| **PATCH** | `/pratos/{id}/valor` | Atualiza **apenas o valor/preço** do prato | `200 OK` | `404 Not Found` / `400 Bad Request` |
| **DELETE**| `/pratos/{id}` | Remove um prato do cardápio | `204 No Content`| `404 Not Found` |

---

## 📦 Exemplos de Requisição e Resposta

### 1. Criar um Novo Prato (`POST /pratos`)
**Request Body (JSON):**
```json
{
  "nome": "Salada Caesar com Frango",
  "descricao": "Alface americana, tiras de frango grelhado, croutons e molho caesar light",
  "preco": 32.50,
  "calorias": 380,
  "disponivel": true,
  "categoriaId": 1
}
```
**Response (201 Created):**
```json
{
  "id": 1,
  "nome": "Salada Caesar com Frango",
  "descricao": "Alface americana, tiras de frango grelhado, croutons e molho caesar light",
  "preco": 32.50,
  "calorias": 380,
  "disponivel": true,
  "categoriaId": 1
}
```

---

### 2. Atualizar Apenas o Valor (`PATCH /pratos/{id}/valor`)
**Request Body (JSON):**
```json
{
  "valor": 35.00
}
```
**Response (200 OK):**
```json
{
  "id": 1,
  "nome": "Salada Caesar com Frango",
  "descricao": "Alface americana, tiras de frango grelhado, croutons e molho caesar light",
  "preco": 35.00,
  "calorias": 380,
  "disponivel": true,
  "categoriaId": 1
}
```

---

## 🛡️ Regras de Negócio e Tratamento de Erros

1. **Unicidade de Prato por Categoria (`existsByNomeAndCategoriaId`):**
   * Não é permitido cadastrar dois pratos com o mesmo nome na mesma categoria, evitando duplicações no cardápio.
2. **Validação da Categoria:**
   * O sistema valida se a `categoriaId` informada realmente existe no banco de dados antes de cadastrar ou alterar um prato.
3. **Tratamento Global de Erros (`GlobalExceptionHandler` com `@ControllerAdvice`):**
   * **`400 Bad Request`**: Captura erros de validação (`@Valid`) e retorna um mapa detalhando exatamente quais campos falharam na validação (ex: preço negativo, nome em branco).
   * **`404 Not Found`**: Captura a exceção personalizada `PratoNaoEncontradoException` e retorna uma mensagem clara quando o ID do prato não existe no banco.

---

## 🧪 Testes e Coleção Postman
Na raiz do projeto está disponível o arquivo **`Nutri-Express.postman_collection.json`**.
Basta abrir o Postman, clicar em **Import** e selecionar o arquivo para ter todos os testes prontos e configurados!
