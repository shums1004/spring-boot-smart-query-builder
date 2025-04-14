# SmartQueryBuilder Spring Boot Starter
> A dynamic, reusable, type-safe query builder built on top of the JPA Criteria API.

![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)

SmartQueryBuilder is a lightweight Spring Boot starter that simplifies building dynamic queries using the JPA Criteria API.  
It supports filtering, sorting, full-text search, and pagination — all through a fluent and chainable interface.

---


## 📦 Installation

Add to your `pom.xml`:

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

🚀 Usage Example
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
```


🧩 Available Methods
1. filterIfPresent(String fieldName, String compare, Object value)

      Filters records based on field and comparison operator.
    
      Supported operators: "=", ">", "<", ">=", "<=".
    
      Ignores filter if the value is null.

2. searchIfPresent(String fieldName, String keyword, boolean enableFullTextSearch)

      Performs case-insensitive search on the specified field.
    
      If enableFullTextSearch is true, performs a LIKE query.
    
      Use with caution on large datasets due to potential performance impact.

3. sort(String fieldName, String direction)

      Sorts the result by a specific column.
    
      direction: "asc" or "desc" (default: "asc").

4. paginate(int pageNo, int pageSize)

      Simple pagination method.
    
      pageNo starts from 0.
  


🤝 Contributing

Have ideas for improvement? Found a bug?
Feel free to open an issue or submit a pull request!



📄 License

This project is licensed under the MIT License.


