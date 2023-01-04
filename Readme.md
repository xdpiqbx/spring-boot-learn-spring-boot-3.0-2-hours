# Amigoscode [Spring Boot - Learn Spring Boot 3 (2 Hours)](https://youtu.be/-mwpoE0x0JQ)

---

```java
@SpringBootApplication // Now it is a Spring Boot Application
public class Main {
    public static void main(String[] args) {
      SpringApplication.run(Main.class, args); // Now it is a Spring Boot Application
      System.out.println("Hello");
    }
}
```

---

### [Configure embedded web server](https://youtu.be/-mwpoE0x0JQ?t=1571)

[3. Embedded Web Servers](https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.webserver)

In `src\main\resources\application.yml`
```yml
server:
  port: 3000
```

It will disable web server:
```yml
spring:
  main:
    web-application-type: none # default is - servlet
```

---

### [First API](https://www.youtube.com/watch?v=-mwpoE0x0JQ&t=1868s)

```java
@SpringBootApplication
@RestController // it means that every method in this class which has annotation @...Mapping will be exposed as REST endpoints
public class Main {
  // ...
  @GetMapping("/") // will be exposed as REST endpoints
  public String greet(){
    return "Hello";
  }
}
```

---

### @SpringBootApplication

**@SpringBootApplication** - without this annotation app will not start

we can replace **@SpringBootApplication** with:
- `@ComponentScan(basePackages = "com.amigoscode")`
- `@EnableAutoConfiguration`
- `@Configuration`

--- 

### Spring Web MVC

- **@Controller** - marks the class as a web controller
- **@RestController** - convenience for **@Controller** and @ResponseBody together. All methods will return JSON.

#### @RequestMapping(...) || @GetMapping(...)  || @PostMapping(...) || ...

- `@RequestMapping(method = RequestMethod.GET, value = "/path")`
- `@GetMapping(value = "/path")`

---

```java
  @GetMapping("/greet")
  public GreetResponse greet(){
    GreetResponse response = new GreetResponse(
      "Hello",
      List.of("Java, Golang", "Javascritp"),
      new Person("Alex", 24, 900_000)
    );
    return response;
  }
```

```java
record Person(String name, int age, double savings){}

record GreetResponse(String greet, List<String> favProgrammingLanguages, Person person){}
```

the same as:

```java

  class GreetResponse{
    private final String greet;
    public GreetResponse(String greet) {
      this.greet = greet;
    }
    public String getGreet() {
      return greet;
    }
    @Override
    public String toString() {
      return "GreetResponse{" +
        "greet='" + greet + '\'' +
        '}';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      GreetResponse that = (GreetResponse) o;
      return Objects.equals(greet, that.greet);
    }

    @Override
    public int hashCode() {
      return Objects.hash(greet);
    }
  }
```

---

### Add postgres docker container

```yaml
services:
  db:
    container_name: postgres
    image: postgres
    environment:
      POSTGRES_USER: amigoscode
      POSTGRES_PASSWORD: password
      PGDATA: /data/postgres
    volumes:
      - db:/data/postgres
    ports:
      - "5332:5432"
    networks:
      - db
    restart: unless-stopped

networks:
  db:
    driver: bridge

volumes:
  db:
```
```code
docker compose up -d // use if you to lazy to install plugin to idea

docker compose ps

docker logs postgres -f
```

---

### Add PostgreSQL JDBC Driver

[main page PostgreSQL JDBC Driver](https://jdbc.postgresql.org/)

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
    <version>42.5.1</version>
</dependency>
```

[Spring Data JPA](https://spring.io/projects/spring-data-jpa)

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

In `src\main\resources\application.yml`

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:postgresql://localhost:5332/customer
    username: amigoscode
    password: password
  jpa:
    hibernate:
      ddl-auto: create-drop
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
    show-sql: true
  main:
    web-application-type: servlet
```
---

Create database customer

```
docker ps
docker exec -it postgres bash
psql -U amigoscode
\l
CREATE DATABASE customer;
Ctrl+d
Ctrl+d
```

- `docker ps` - to see started containers
- `docker exec -it postgres bash` - starts bash in container
- `psql -U amigoscode` - chose postgres user
- `\l` - list of databases
- `Ctrl+d` - to exit psql shell
- `Ctrl+d` - to exit docker shell

---

### Make @Entity from Customer

---

```cmd
amigoscode=# \c customer
You are now connected to database "customer" as user "amigoscode".
customer=#
```

```cmd
customer=# \dt
customer=# \d
customer=# select * from customer;
customer=# \d customer
```

- `amigoscode=# \c customer` - connect to customer
- `customer=# \dt` - to see database tables
- `customer=# \d` - to see all relations
- `customer=# \d customer` - to see table structure
---

### CRUD

```java
public interface CustomerRepository extends JpaRepository {
  // JpaRepository<@Entity, Type of @Id>
}
```

```SQL
INSERT INTO customer (id, name, email, age)
VALUES
    (nextval('customer_id_sequence'), 'Alex', 'alex@gmail.com', 30),
    (nextval('customer_id_sequence'), 'Jamila', 'jami@gmail.com', 22),
    (nextval('customer_id_sequence'), 'John', 'john@gmail.com', 27);
```