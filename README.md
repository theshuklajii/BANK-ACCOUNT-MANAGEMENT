# ShopEase - Java eCommerce Web Application

A full-stack Java-based eCommerce web application built with JSP, Servlets, and MySQL. ShopEase provides a complete online shopping experience with user authentication, product catalog, shopping cart, order management, and admin functionality.

## 🚀 Features

### Customer Features
- **User Authentication**: Secure signup and login system
- **Product Catalog**: Browse products with search and category filtering
- **Shopping Cart**: Add, update, and remove items from cart
- **Order Management**: Place orders and track order history
- **Responsive Design**: Modern UI with Bootstrap 5

### Admin Features
- **Admin Dashboard**: Overview of orders, products, and users
- **Product Management**: Add, edit, and delete products
- **Order Management**: View and update order status
- **Role-based Access Control**: Secure admin-only areas

## 🛠 Technology Stack

- **Frontend**: JSP, HTML5, CSS3, JavaScript, Bootstrap 5, Font Awesome
- **Backend**: Java Servlets, JDBC
- **Database**: MySQL 8.0
- **Build Tool**: Maven
- **Application Server**: Apache Tomcat 9.0+ or Jetty
- **Architecture**: MVC (Model-View-Controller)

## 📋 Prerequisites

- Java 11 or higher
- Apache Maven 3.6+
- MySQL 8.0+
- Apache Tomcat 9.0+ or Jetty 9.4+

## 🔧 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/shopease.git
cd shopease
```

### 2. Database Setup
1. Install and start MySQL server
2. Create the database and tables:
```sql
-- Run the database/schema.sql file
mysql -u root -p < database/schema.sql

-- Insert sample data
mysql -u root -p < database/sample_data.sql
```

### 3. Configure Database Connection
Update the database connection settings in:
- `src/main/java/com/shopease/util/DatabaseConnection.java`
- `src/main/webapp/WEB-INF/web.xml`

Default settings:
- URL: `jdbc:mysql://localhost:3306/shopease_db`
- Username: `root`
- Password: `password`

### 4. Build and Deploy

#### Option A: Using Maven + Tomcat
```bash
# Build the project
mvn clean compile

# Deploy to Tomcat (copy the WAR file)
mvn clean package
cp target/shopease.war $TOMCAT_HOME/webapps/

# Start Tomcat
$TOMCAT_HOME/bin/startup.sh
```

#### Option B: Using Maven + Jetty (Development)
```bash
# Run with Jetty plugin
mvn clean jetty:run
```

### 5. Access the Application
- **Application URL**: http://localhost:8080/shopease
- **Context Path**: `/shopease`

## 👥 Demo Accounts

### Admin Account
- **Username**: `admin`
- **Password**: `admin123`

### Customer Accounts
- **Username**: `john_doe` | **Password**: `password123`
- **Username**: `jane_smith` | **Password**: `password123`

## 📁 Project Structure

```
shopease/
├── src/main/java/com/shopease/
│   ├── controllers/          # Servlet controllers
│   ├── dao/                  # Data Access Objects
│   ├── models/               # Entity/Model classes
│   └── util/                 # Utility classes
├── src/main/webapp/
│   ├── views/                # JSP pages
│   │   ├── common/           # Header/Footer
│   │   └── admin/            # Admin pages
│   └── WEB-INF/
│       └── web.xml           # Deployment descriptor
├── database/                 # SQL scripts
│   ├── schema.sql            # Database schema
│   └── sample_data.sql       # Sample data
├── pom.xml                   # Maven configuration
└── README.md
```

## 🗃 Database Schema

### Tables
- **users**: User accounts (customers and admins)
- **products**: Product catalog
- **cart**: Shopping cart items
- **orders**: Customer orders
- **order_items**: Individual order items

### Relationships
- Users → Cart (1:N)
- Users → Orders (1:N)
- Products → Cart Items (1:N)
- Products → Order Items (1:N)
- Orders → Order Items (1:N)

## 🌐 API Endpoints

### Public Endpoints
- `GET /products` - Product catalog page
- `GET /login` - Login page
- `GET /signup` - Registration page
- `POST /login` - User authentication
- `POST /signup` - User registration
- `GET /logout` - User logout

### Customer Endpoints (Authenticated)
- `GET /cart` - Shopping cart
- `POST /cart` - Cart operations (add/update/remove)
- `GET /checkout` - Checkout page
- `POST /checkout` - Place order
- `GET /orders` - Order history
- `GET /order-confirmation` - Order confirmation

### Admin Endpoints (Admin Role)
- `GET /admin/dashboard` - Admin dashboard
- `GET /admin/products` - Product management
- `POST /admin/products` - Add/update products
- `GET /admin/orders` - Order management
- `POST /admin/orders` - Update order status

## 🔒 Security Features

- **Session Management**: Secure HTTP sessions
- **Role-based Access**: Admin/Customer role separation
- **Input Validation**: Server-side validation for all forms
- **SQL Injection Prevention**: Prepared statements
- **XSS Protection**: Input sanitization

## 🚀 Development

### Running in Development Mode
```bash
# Using Jetty (recommended for development)
mvn jetty:run

# Using Tomcat plugin
mvn tomcat7:run
```

### Building for Production
```bash
# Create WAR file
mvn clean package

# The WAR file will be in target/shopease.war
```

## 📝 Features Roadmap

- [ ] Payment gateway integration
- [ ] Email notifications
- [ ] Product reviews and ratings
- [ ] Wishlist functionality
- [ ] Advanced search filters
- [ ] Order tracking
- [ ] Inventory management
- [ ] Reporting and analytics

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For support and questions:
- Create an issue on GitHub
- Email: support@shopease.com
- Documentation: [Wiki](https://github.com/yourusername/shopease/wiki)

## 🙏 Acknowledgments

- Bootstrap team for the excellent CSS framework
- Font Awesome for the icons
- MySQL team for the reliable database
- Apache Foundation for Tomcat and Maven

---

**Built with ❤️ using Java, JSP, and MySQL**