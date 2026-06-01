# store-api(https://github.com/leonardtataru/store-api)

API REST pentru management de magazin: CRUD pe produse, autentificare cu JWT și acces pe endpoint-uri bazat pe roluri (ADMIN / USER).

## Stack

- Java 17
- Spring Boot (Web, Security, Data JPA, Validation)
- H2 (bază de date in-memory)
- JJWT
- Lombok
- Maven

## Run

Aplicația pornește pe `http://localhost:8080`.

### Consola H2

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: *(gol)*

## Date inițiale

La pornire (vezi `schema.sql` și `Initializer`) se creaza automat un ADMIN:

- Roluri: `ADMIN` (id=1), `USER` (id=2)
- Utilizator admin:
  - username: `admin`
  - parolă: `admin`

Utilizatorii noi creați prin `/authentication/register` primesc implicit rolul `USER`.

## Endpoint-uri

| Metodă | Path                       | Rol necesar  | Descriere                   |
|--------|----------------------------|--------------|-----------------------------|
| POST   | `/authentication/register` | public       | Creează cont nou (rol USER) |
| POST   | `/authentication/login`    | public       | Returnează un JWT           |
| GET    | `/product`                 | USER / ADMIN | Listează toate produsele    |
| GET    | `/product/search?name=...` | USER / ADMIN | Caută produs după nume      |
| GET    | `/product/search/{id}`     | USER / ADMIN | Caută produs după id        |
| POST   | `/product/create`          | ADMIN        | Adaugă un produs            |
| PUT    | `/product/changePrice`     | ADMIN        | Modifică prețul unui produs |
| DELETE | `/product/{id}`            | ADMIN        | Șterge un produs            |
| DELETE | `/product/soft/{id}`       | ADMIN        | Șterge un produs soft       |

## Autentificare

După login, trimite token-ul în header la fiecare request protejat:

```
Authorization: Bearer <jwt>
```

Token-ul are durată de viață **10 minute**
