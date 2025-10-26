## Step 1: Authentication (`/api/auth`)

First, you need to get a JSON Web Token (JWT) to authenticate your requests. The system is pre-seeded with an `OWNER` user.

### 1.1. Login as Owner

Send a `POST` request to `/api/auth/login` to get a token for the default owner.

**Endpoint:** `POST /api/auth/login`
**Request Body:** (`LoginRequest.java`)

```json
{
    "username": "owner",
    "password": "password123"
}
```

*Note: The `ShopManagementApplication.java` and `data.sql` also define a user "pyaepyae" with password "password123". You can use "owner" as defined in `data.sql`.*

**Response:** You will receive a JSON response containing the token.

```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvd25...",
    "username": "owner",
    "role": "OWNER"
}
```

**➡️ Action:** Copy this `token`. For all subsequent requests, you must include it in the HTTP headers:
`Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvd25...`

### 1.2. Register a New User (as Owner)

Using the **Owner's token** from Step 1.1, you can register new users. Let's register a `CASHIER`.

**Endpoint:** `POST /api/auth/register`
**Request Body:** (`RegisterRequest.java`)

```json
{
    "username": "new_cashier",
    "password": "cashier_pass_123",
    "email": "cashier@shop.com",
    "role": "CASHIER"
}
```

-----

## Step 2: Manage Categories (`/api/categories`)

These endpoints require an **`OWNER`** or **`ADMIN`** token. Use the token from Step 1.1.

### 2.1. Create a New Category

**Endpoint:** `POST /api/categories`
**Request Body:** (`CategoryDto.java`)

```json
{
    "name": "Apparel",
    "description": "Clothing and accessories"
}
```

### 2.2. Update a Category

Let's update the "Books" category (which has `id: 2` from `data.sql`).

**Endpoint:** `PUT /api/categories/2`
**Request Body:** (`CategoryDto.java`)

```json
{
    "id": 2,
    "name": "Books & Stationery",
    "description": "All books and office supplies"
}
```

### 2.3. Get & Delete Categories (No JSON Body)

* **`GET /api/categories`**: Fetches all categories.
* **`GET /api/categories/{id}`**: Fetches a single category by its ID.
* **`DELETE /api/categories/{id}`**: Deletes a category.

-----

## Step 3: Manage Products (`/api/products`)

These endpoints also require an **`OWNER`** or **`ADMIN`** token.

### 3.1. Create a New Product

Let's add a new product to the "Electronics" category (`id: 1` from `data.sql`).

**Endpoint:** `POST /api/products`
**Request Body:** (`ProductDto.java`)

```json
{
    "name": "Bluetooth Headphones",
    "sku": "ELEC-BTH-001",
    "description": "Over-ear wireless headphones",
    "costPrice": 45.00,
    "sellingPrice": 89.99,
    "quantity": 50,
    "categoryId": 1
}
```

### 3.2. Update a Product

Let's update the "Wireless Mouse" (`id: 1` from `data.sql`).

**Endpoint:** `PUT /api/products/1`
**Request Body:** (`ProductDto.java`)

```json
{
    "id": 1,
    "name": "Wireless Mouse 2.0",
    "sku": "ELEC-WM-001",
    "description": "A reliable wireless mouse, new version",
    "costPrice": 16.00,
    "sellingPrice": 29.99,
    "quantity": 120,
    "categoryId": 1
}
```

### 3.3. Get & Delete Products (No JSON Body)

* **`GET /api/products`**: Fetches all products.
* **`GET /api/products/{id}`**: Fetches a single product by its ID.
* **`DELETE /api/products/{id}`**: Deletes a product.

-----

## Step 4: Create a Sale (`/api/sales`)

This endpoint can be used by **`OWNER`**, **`ADMIN`**, or **`CASHIER`**. You can use the `OWNER` token from Step 1.1 or log in as the `CASHIER` you created in Step 1.2.

### 4.1. Create a New Sale

This request will sell 1 "Wireless Mouse" (`id: 1`) and 2 "The Great Gatsby" books (`id: 2`).

**Endpoint:** `POST /api/sales`
**Request Body:** (`SaleRequestDto.java`)

```json
{
    "customerId": null,
    "discount": 5.00,
    "items": [
        {
            "productId": 1,
            "quantity": 1
        },
        {
            "productId": 2,
            "quantity": 2
        }
    ],
    "payments": [
        {
            "amount": 50.00,
            "method": "CASH"
        }
    ]
}
```

*Note: `customerId` is optional. Payment methods are `CASH`, `CARD`, or `TRANSFER`. The total payment amount must be greater than or equal to the `netAmount` (Total - Discount).*

### 4.2. Get Sales (No JSON Body)

* **`GET /api/sales`**: Fetches all sales (Owner/Admin only).
* **`GET /api/sales/{id}`**: Fetches a single sale by ID (Owner/Admin only).

-----

## Step 5: View Reports (`/api/reports`)

These `GET` endpoints require an **`OWNER`** or **`ADMIN`** token and use query parameters, not JSON bodies.

* **Monthly Report:**
  `GET /api/reports/monthly?year=2025`

* **Daily Report (for a specific month):**
  `GET /api/reports/daily?month=2025-10-01`

* **Category Report (for a date range):**
  `GET /api/reports/category?startDate=2025-01-01&endDate=2025-12-31`

* **Customer Report (for a date range):**
  `GET /api/reports/customer?startDate=2025-01-01&endDate=2025-12-31`