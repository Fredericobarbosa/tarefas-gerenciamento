# 📝 Sistema de Gerenciamento de Tarefas

## 📌 Descrição

Aplicação backend desenvolvida com **Spring Boot** para gerenciamento de tarefas do dia a dia, permitindo operações completas de **CRUD (Create, Read, Update, Delete)**.

O sistema foi projetado com boas práticas de arquitetura, incluindo separação em camadas, tratamento global de exceções e testes automatizados.

---

## 🚀 Funcionalidades

* ✅ Criar uma nova tarefa
* ✅ Atualizar uma tarefa existente
* ✅ Deletar uma tarefa
* ✅ Buscar tarefa por ID
* ✅ Listar todas as tarefas
* ✅ Filtrar tarefas por status

---

## 🧠 Regras de Negócio

* O nome da tarefa é obrigatório
* O nome da tarefa deve ter no máximo 150 caracteres
* Não existe controle de usuários ou autenticação

---

## 🛠 Tecnologias Utilizadas

* Java
* Spring Boot
* PostgreSQL

---

## 🗂 Estrutura do Projeto

* `api.resource` → Controllers (REST API)
* `service` → Regras de negócio
* `repository` → Acesso ao banco de dados
* `model.entity` → Entidades
* `exception` → Tratamento de erros

---

## 🔌 Endpoints da API

### 📍 Criar tarefa

POST `/api/tarefas`

### 📍 Atualizar tarefa

PUT `/api/tarefas/{id}`

### 📍 Deletar tarefa

DELETE `/api/tarefas/{id}`

### 📍 Buscar por ID

GET `/api/tarefas/{id}`

### 📍 Listar tarefas

GET `/api/tarefas`

### 📍 Filtrar por status

GET `/api/tarefas?status=PENDENTE`

---

## ⚠️ Tratamento de Erros

A aplicação possui tratamento global de exceções com respostas padronizadas:

Exemplo:

```json
{
  "status": 404,
  "erro": "Tarefa não encontrada",
  "mensagem": "Tarefa não encontrada com o id: 1",
  "timestamp": "2026-04-26T20:00:00"
}
```

---

## 🧪 Testes

### ✔️ Testes Unitários

* Implementados na camada de serviço
* Utilização de **Mockito**
* Validação de regras de negócio e exceções

### ✔️ Testes de Integração

* Utilização de **@SpringBootTest**
* Testes reais com o banco de dados
* Validação de operações do repositório

---

## ▶️ Como Executar o Projeto

1. Clonar o repositório:

```
git clone <URL_DO_REPOSITORIO>
```

2. Configurar o banco PostgreSQL

3. Executar a aplicação:

```
./mvnw spring-boot:run
```

---

## 👨‍💻 Autor

* Frederico Pessoa Barbosa
* Jorge Luiz Patrocínio dos Santos
