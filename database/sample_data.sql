USE shopease_db;

-- Insert sample users
INSERT INTO users (username, email, password, full_name, role, address, phone) VALUES
('admin', 'admin@shopease.com', 'admin123', 'System Administrator', 'Admin', '123 Admin Street, Admin City', '+1-555-0001'),
('john_doe', 'john.doe@email.com', 'password123', 'John Doe', 'Customer', '456 Customer Ave, Customer City', '+1-555-0002'),
('jane_smith', 'jane.smith@email.com', 'password123', 'Jane Smith', 'Customer', '789 Buyer Blvd, Buyer Town', '+1-555-0003'),
('mike_wilson', 'mike.wilson@email.com', 'password123', 'Mike Wilson', 'Customer', '321 Shopper St, Shopper City', '+1-555-0004'),
('sarah_brown', 'sarah.brown@email.com', 'password123', 'Sarah Brown', 'Customer', '654 Consumer Ct, Consumer City', '+1-555-0005');

-- Insert sample products
INSERT INTO products (name, description, price, category, stock_quantity, image_url) VALUES
-- Electronics
('iPhone 15 Pro', 'Latest Apple iPhone with advanced camera system and A17 Pro chip', 999.99, 'Electronics', 50, 'https://via.placeholder.com/300x300?text=iPhone+15+Pro'),
('Samsung Galaxy S24', 'Premium Android smartphone with excellent display and camera', 899.99, 'Electronics', 45, 'https://via.placeholder.com/300x300?text=Galaxy+S24'),
('MacBook Air M3', '13-inch laptop with M3 chip, perfect for work and creativity', 1299.99, 'Electronics', 30, 'https://via.placeholder.com/300x300?text=MacBook+Air'),
('Dell XPS 13', 'Ultra-portable Windows laptop with stunning InfinityEdge display', 1099.99, 'Electronics', 25, 'https://via.placeholder.com/300x300?text=Dell+XPS+13'),
('AirPods Pro', 'Wireless earbuds with active noise cancellation', 249.99, 'Electronics', 100, 'https://via.placeholder.com/300x300?text=AirPods+Pro'),
('Sony WH-1000XM5', 'Premium noise-canceling over-ear headphones', 399.99, 'Electronics', 40, 'https://via.placeholder.com/300x300?text=Sony+Headphones'),
('iPad Air', '10.9-inch tablet perfect for work and entertainment', 599.99, 'Electronics', 60, 'https://via.placeholder.com/300x300?text=iPad+Air'),
('Nintendo Switch', 'Popular gaming console for home and portable gaming', 299.99, 'Electronics', 35, 'https://via.placeholder.com/300x300?text=Nintendo+Switch'),

-- Clothing
('Levi\'s 501 Jeans', 'Classic straight-fit jeans, timeless style', 89.99, 'Clothing', 80, 'https://via.placeholder.com/300x300?text=Levis+Jeans'),
('Nike Air Force 1', 'Iconic basketball-inspired sneakers', 110.99, 'Clothing', 75, 'https://via.placeholder.com/300x300?text=Nike+Air+Force+1'),
('Adidas Ultraboost 22', 'High-performance running shoes with boost technology', 180.99, 'Clothing', 60, 'https://via.placeholder.com/300x300?text=Adidas+Ultraboost'),
('Calvin Klein T-Shirt', 'Premium cotton t-shirt with modern fit', 29.99, 'Clothing', 120, 'https://via.placeholder.com/300x300?text=Calvin+Klein+Tee'),
('North Face Jacket', 'Waterproof outdoor jacket for all weather conditions', 199.99, 'Clothing', 40, 'https://via.placeholder.com/300x300?text=North+Face+Jacket'),
('Ralph Lauren Polo', 'Classic polo shirt in various colors', 79.99, 'Clothing', 90, 'https://via.placeholder.com/300x300?text=Ralph+Lauren+Polo'),
('Lululemon Leggings', 'High-quality athletic leggings for yoga and fitness', 128.99, 'Clothing', 70, 'https://via.placeholder.com/300x300?text=Lululemon+Leggings'),

-- Home & Garden
('KitchenAid Stand Mixer', 'Professional-grade stand mixer for baking enthusiasts', 379.99, 'Home & Garden', 25, 'https://via.placeholder.com/300x300?text=KitchenAid+Mixer'),
('Dyson V15 Vacuum', 'Cordless vacuum cleaner with laser dust detection', 749.99, 'Home & Garden', 20, 'https://via.placeholder.com/300x300?text=Dyson+Vacuum'),
('Instant Pot Duo', '7-in-1 electric pressure cooker for quick and easy meals', 99.99, 'Home & Garden', 50, 'https://via.placeholder.com/300x300?text=Instant+Pot'),
('Philips Hue Smart Bulbs', 'Color-changing smart LED bulbs (4-pack)', 199.99, 'Home & Garden', 80, 'https://via.placeholder.com/300x300?text=Philips+Hue'),
('Weber Genesis Grill', 'Premium gas grill for outdoor cooking', 899.99, 'Home & Garden', 15, 'https://via.placeholder.com/300x300?text=Weber+Grill'),
('Roomba i7+', 'Self-emptying robot vacuum with smart mapping', 599.99, 'Home & Garden', 30, 'https://via.placeholder.com/300x300?text=Roomba+i7'),

-- Books
('The Psychology of Money', 'Timeless lessons on wealth, greed, and happiness', 16.99, 'Books', 100, 'https://via.placeholder.com/300x300?text=Psychology+of+Money'),
('Atomic Habits', 'An easy and proven way to build good habits', 18.99, 'Books', 150, 'https://via.placeholder.com/300x300?text=Atomic+Habits'),
('The 7 Habits of Highly Effective People', 'Powerful lessons in personal change', 15.99, 'Books', 120, 'https://via.placeholder.com/300x300?text=7+Habits'),
('Think and Grow Rich', 'Classic book on success and wealth building', 14.99, 'Books', 80, 'https://via.placeholder.com/300x300?text=Think+Grow+Rich'),
('The Lean Startup', 'How today\'s entrepreneurs use continuous innovation', 17.99, 'Books', 90, 'https://via.placeholder.com/300x300?text=Lean+Startup'),

-- Sports & Outdoors
('Yeti Rambler Tumbler', 'Insulated stainless steel tumbler', 39.99, 'Sports & Outdoors', 200, 'https://via.placeholder.com/300x300?text=Yeti+Tumbler'),
('Patagonia Backpack', 'Durable hiking backpack for outdoor adventures', 149.99, 'Sports & Outdoors', 45, 'https://via.placeholder.com/300x300?text=Patagonia+Backpack'),
('Wilson Tennis Racket', 'Professional-grade tennis racket', 199.99, 'Sports & Outdoors', 30, 'https://via.placeholder.com/300x300?text=Wilson+Racket'),
('Coleman Camping Tent', '4-person dome tent for camping trips', 129.99, 'Sports & Outdoors', 25, 'https://via.placeholder.com/300x300?text=Coleman+Tent'),
('Fitbit Charge 5', 'Advanced fitness tracker with GPS', 179.99, 'Sports & Outdoors', 60, 'https://via.placeholder.com/300x300?text=Fitbit+Charge+5');

-- Insert sample orders (for demonstration)
INSERT INTO orders (user_id, total_amount, status, shipping_address, customer_name, customer_phone) VALUES
(2, 1199.98, 'Shipped', '456 Customer Ave, Customer City', 'John Doe', '+1-555-0002'),
(3, 249.99, 'Delivered', '789 Buyer Blvd, Buyer Town', 'Jane Smith', '+1-555-0003'),
(4, 89.99, 'Pending', '321 Shopper St, Shopper City', 'Mike Wilson', '+1-555-0004');

-- Insert sample order items
INSERT INTO order_items (order_id, product_id, quantity, price) VALUES
(1, 1, 1, 999.99),  -- iPhone 15 Pro
(1, 8, 1, 199.99),  -- Nintendo Switch
(2, 5, 1, 249.99),  -- AirPods Pro
(3, 9, 1, 89.99);   -- Levi's Jeans

-- Insert sample cart items
INSERT INTO cart (user_id, product_id, quantity) VALUES
(2, 3, 1),  -- John has MacBook Air in cart
(2, 6, 1),  -- John has Sony headphones in cart
(3, 10, 2), -- Jane has 2 Nike shoes in cart
(4, 18, 1), -- Mike has KitchenAid mixer in cart
(5, 22, 3); -- Sarah has 3 books in cart