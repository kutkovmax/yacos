# yacos - Yet Another CRUD On Spring

Очередной сервис учета товаров на Spring Boot 4 и PostgreSQL. Мир буквально задыхался без еще одного бэкенда, умеющего читать и писать сущности в реляционную базу, поэтому я исправил эту вселенскую несправедливость.

## Демо

**Swagger UI:**
[https://kutkovmax.ru/yacos/swagger-ui/index.html](https://kutkovmax.ru/yacos/swagger-ui/index.html)

**OpenAPI:**
[https://kutkovmax.ru/yacos/api-docs](https://kutkovmax.ru/yacos/api-docs)

## Стек

* Java 21
* Spring Boot 4
* PostgreSQL 17
* Flyway
* SpringDoc / Swagger UI
* Docker Compose

## Конфигурация

Скопируй `.env.example`:

```bash
cp .env.example .env
```

И задай свои значения:

```dotenv
POSTGRES_DB=yacos
POSTGRES_USER=yacos
POSTGRES_PASSWORD=change_this_or_get_hacked
POSTGRES_PORT=127.0.0.1:3333

APP_PORT=8080
SERVER_SERVLET_CONTEXT_PATH=/yacos
```

## Запуск

```bash
docker compose up -d
```

## API

Получить список товаров:

```bash
curl https://kutkovmax.ru/yacos/api/v1/items
```

Создать товар:

```bash
curl -X POST https://kutkovmax.ru/yacos/api/v1/items \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "ITEM-1337",
    "name": "Еще один товар",
    "quantity": 42,
    "price": 100500
  }'
```

Получить товар:

```bash
curl https://kutkovmax.ru/yacos/api/v1/items/1
```

Обновить товар:

```bash
curl -X PUT https://kutkovmax.ru/yacos/api/v1/items/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Еще один товар, но уже обновленный",
    "quantity": 50,
    "price": 110000,
    "version": 0
  }'
```

Удалить товар:

```bash
curl -X DELETE https://kutkovmax.ru/yacos/api/v1/items/1
```
