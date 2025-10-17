INSERT INTO users (username, password, email, role) VALUES
('owner', '$2a$10$w4.l.gW5VzYJ8dJ0p7sK..pGqylqGg2PjG/P0vH2nPuqg.iTUtM.m', 'owner@shop.com', 'OWNER')
ON CONFLICT (username) DO NOTHING;

INSERT INTO categories (name, description) VALUES
('Electronics', 'Gadgets and electronic devices'),
('Books', 'Fiction and non-fiction books'),
('Groceries', 'Daily essentials and food items')
ON CONFLICT (name) DO NOTHING;

INSERT INTO products (name, sku, description, cost_price, selling_price, quantity, category_id) VALUES
('Wireless Mouse', 'ELEC-WM-001', 'A reliable wireless mouse', 15.00, 25.00, 100, 1),
('The Great Gatsby', 'BOOK-TGG-001', 'A classic novel by F. Scott Fitzgerald', 5.50, 12.00, 50, 2),
('Organic Apples', 'GROC-APP-001', 'A pack of 6 organic apples', 2.00, 4.50, 200, 3)
ON CONFLICT (sku) DO NOTHING;
