# 📦 SmartStock API

API REST para gestão de estoque desenvolvida com Java e Spring Boot. O sistema permite o cadastro e controle de produtos, fornecedores, categorias e movimentações de entrada e saída de estoque.

---

## ✅ Funcionalidades

- Cadastro e listagem de **produtos**
- Cadastro de **categorias** de produto
- Cadastro de **fornecedores**
- Registro de **movimentações de estoque** (entrada e saída)
- Histórico completo das movimentações

---

## 💠 Tecnologias Utilizadas

- Java 17
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Maven
- Lombok
- ModelMapper

---

## ⚙️ Como executar

1. **Clone o projeto:**

```bash
git clone https://github.com/Nathangc77/smartstock.git
```

2. **Configure o perfil de desenvolvimento:**

No arquivo `application-dev.properties`, configure sua conexão com PostgreSQL:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/stockdb
spring.datasource.username=postgres
spring.datasource.password=admin
spring.profiles.active=dev
```

3. **Execute o projeto:**

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```
