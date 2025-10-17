DROP VIEW IF EXISTS monthly_sales_report;
DROP TABLE IF EXISTS payments, sale_items, sales, customers, products, categories, users;
DROP TYPE IF EXISTS user_role, paymentmethod;

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    sku VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    cost_price NUMERIC(10, 2) NOT NULL,
    selling_price NUMERIC(10, 2) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    category_id BIGINT REFERENCES categories(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    phone VARCHAR(20) UNIQUE,
    email VARCHAR(100) UNIQUE,
    address TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE sales (
    id BIGSERIAL PRIMARY KEY,
    invoice_number VARCHAR(50) UNIQUE NOT NULL,
    sale_date TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    total_amount NUMERIC(12, 2) NOT NULL,
    discount NUMERIC(10, 2) DEFAULT 0.00,
    net_amount NUMERIC(12, 2) NOT NULL,
    customer_id BIGINT REFERENCES customers(id),
    user_id BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE sale_items (
    id BIGSERIAL PRIMARY KEY,
    sale_id BIGINT NOT NULL REFERENCES sales(id) ON DELETE CASCADE,
    product_id BIGINT NOT NULL REFERENCES products(id),
    quantity INT NOT NULL,
    unit_price NUMERIC(10, 2) NOT NULL,
    total_price NUMERIC(12, 2) NOT NULL
);

CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    sale_id BIGINT NOT NULL REFERENCES sales(id) ON DELETE CASCADE,
    payment_date TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    amount NUMERIC(12, 2) NOT NULL,
    method VARCHAR(255) NOT NULL
);

CREATE OR REPLACE VIEW monthly_sales_report AS
SELECT
    TO_CHAR(s.sale_date, 'YYYY-MM') AS month,
    EXTRACT(YEAR FROM s.sale_date)::INT AS year,
    EXTRACT(MONTH FROM s.sale_date)::INT AS month_of_year,
    COUNT(s.id) AS total_sales_count,
    SUM(si.quantity) AS total_items_sold,
    SUM(s.discount) AS total_discount,
    SUM(s.total_amount) AS total_revenue,
    SUM(s.net_amount) AS net_revenue
FROM sales s
JOIN sale_items si ON s.id = si.sale_id
GROUP BY TO_CHAR(s.sale_date, 'YYYY-MM'), EXTRACT(YEAR FROM s.sale_date), EXTRACT(MONTH FROM s.sale_date)
ORDER BY month DESC;
