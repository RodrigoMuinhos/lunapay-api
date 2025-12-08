# LunaPay API

API de integração de pagamentos do ecossistema Luna, responsável por gerenciar pagamentos através de múltiplos gateways (Asaas, C6, etc).

## 🚀 Tecnologias

- Java 17
- Spring Boot 3.2.0
- Spring Security (JWT)
- Spring Data JPA
- PostgreSQL
- Lombok
- Maven

## 🔐 Segurança

A API utiliza JWT para autenticação, integrada com o LunaCore. Apenas usuários com o módulo **LUNAPAY** habilitado podem acessar os endpoints.

## 🏗️ Estrutura do Projeto

```
com.luna.pay
├── config          // Configurações (SecurityConfig, etc)
├── security        // JwtUtil, JwtAuthenticationFilter, UserContext
├── payment         // Entidades, repos, services e controllers de pagamentos
├── webhook         // Controllers para receber webhooks dos gateways
└── common          // Exceptions, utils compartilhados
```

## ⚙️ Configuração

### Banco de Dados

Crie o banco PostgreSQL:

```sql
CREATE DATABASE lunapay;
CREATE USER lunapay WITH PASSWORD 'secret';
GRANT ALL PRIVILEGES ON DATABASE lunapay TO lunapay;
```

### Variáveis de Ambiente

```bash
LUNACORE_JWT_SECRET=seu-secret-jwt-compartilhado-com-core
```

## 🔧 Como Executar

```bash
# Compilar
mvn clean install

# Executar
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8082`

## 🧪 Testando

### Health Check (sem autenticação)
```bash
curl http://localhost:8082/actuator/health
```

### Endpoint de Teste (com autenticação)
```bash
curl -H "Authorization: Bearer SEU_TOKEN_DO_LUNACORE" \
     http://localhost:8082/payments/ping
```

Resposta esperada:
```
LunaPay OK para tenant {tenantId} (user: {userId})
```

## 📦 Dependências Principais

- `spring-boot-starter-web` - REST API
- `spring-boot-starter-data-jpa` - Persistência
- `spring-boot-starter-security` - Segurança
- `spring-boot-starter-validation` - Validações
- `postgresql` - Driver PostgreSQL
- `jjwt` - JWT (validação de tokens)
- `lombok` - Redução de boilerplate

## 🎯 Próximos Passos

1. ✅ Estrutura base do projeto
2. ✅ Segurança e multi-tenant
3. ✅ Modelo de Payment
4. ⏳ Endpoint POST /payments/create
5. ⏳ Integração com Asaas
6. ⏳ Integração com C6
7. ⏳ Webhooks de confirmação
8. ⏳ Gestão de status de pagamentos

## 📝 Licença

Proprietary - LunaPay © 2024
