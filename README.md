# ShopEase

ShopEase is a mini e-commerce web application built with Java Servlets, JSP, and MySQL following the MVC design pattern.

## Features
1. User authentication with role-based access (Customer / Admin)
2. Product catalogue with search & category filter
3. Session-based shopping cart
4. Order placement & tracking
5. Admin dashboard for product & order management

## Project Structure
```
ShopEase/
 ├── pom.xml               # Maven build file
 ├── sql/
 │    └── schema.sql        # Database schema & sample data
 └── src/
      └── main/
           ├── java/
           │    └── com/shopease/
           │         ├── models/      # POJO domain models
           │         ├── dao/         # Database access objects
           │         └── controllers/ # Servlet controllers (MVC)
           └── webapp/
                ├── views/            # JSP views
                ├── css/             # Static resources
                └── WEB-INF/
                     └── web.xml      # Servlet configuration
```

## Prerequisites
* JDK 8+
* Maven 3+
* MySQL 5.7 or higher

## Setup
1. Create the database and sample data:
   ```bash
   mysql -u root -p < sql/schema.sql
   ```
2. Adjust database credentials in `src/main/java/com/shopease/dao/DBConnection.java` if necessary.
3. Build the WAR:
   ```bash
   mvn clean package
   ```
4. Deploy `target/shopease.war` to a servlet container (Tomcat 9+).

## Default Accounts
* **Admin**  →  `admin@example.com` / `admin123`
* **Customer** → `user@example.com` / `user123`

Happy hacking! 🎉