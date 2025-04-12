# SmartQueryBuilder Spring Boot Starter

A dynamic, reusable, type-safe query builder built with JPA Criteria API.  
Supports filtering, sorting, searching, and pagination with a fluent interface.

---

## 📦 Installation

Add this to your `pom.xml`:

```xml
<repositories>
  <repository>
    <id>github</id>
    <url>https://maven.pkg.github.com/shums1004/spring-boot-smart-query-builder</url>
  </repository>
</repositories>

<dependency>
  <groupId>com.querybuilder</groupId>
  <artifactId>querybuilder-spring-boot-starter</artifactId>
  <version>1.0.0</version>
</dependency>
```

How to use it in your project:
```java
@Autowired
SmartQueryBuilderFactory smartQueryBuilderFactory;

public List<User> getUsers() {
    return smartQueryBuilderFactory.of(User.class)
        .filterIfPresent("age", ">", 18)
        .searchIfPresent("name", "john", true)
        .sort("createdDate", "desc")
        .paginate(0, 20)
        .build();
}
