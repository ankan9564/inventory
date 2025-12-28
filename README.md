# Inventory Management System with Basic  Automation

A Spring Boot–based Inventory Management System for small–to–mid sized businesses.  
The system manages products, stock movement, automation alerts, smart reorder logic, 
pagination, basic authentication, and a minimal UI dashboard.

This project demonstrates:
• Programming fundamentals  
• API & database design  
• Logical automation  
• AI-style rule-based logic  
• Clean code & validation  
• Working API outputs

---

## 🚀 Tech Stack

Backend  
• Java 21  
• Spring Boot 3.x  
• Spring Data JPA  
• PostgreSQL  
• Spring Mail (Email Alerts)  
• Spring Security (Basic Auth)

Frontend (Basic UI)  
• Thymeleaf  
• Bootstrap

Build & Testing  
• Maven  
• JUnit 5  

---

## ⚙ Setup Instructions

1) Start PostgreSQL  
2) Create database: `inventory_db`  
3) Configure `application.properties`  
4) Run application:

API Server Port  http://localhost:8080/

UI Dashboard  http://localhost:8080/

---

## 🔐 Basic Authentication (Bonus Feature)

admin / admin123 → Full Access
user / user123 → View + Stock Update

Authentication uses Spring Security (Basic Auth).

---

## 🧠 Automation / AI Features

### Option-A — Low Stock Alert
• Each product has a minimum stock level  
• When stock drops below threshold:
  - Alert appears in API response  
  - Console log prints alert  
  - Email notification is sent (bonus)

### Option-B — Smart Reorder Suggestion
• Calculates reorder quantity using:
  - Average daily usage (OUT transactions)
  - Fixed lead time (default 7 days)

### Option-C — AI-Based Product Categorization (Rule-Based NLP)
• Category auto-assigned from product name  
• Example: `"Cotton Shirt Blue"` → `"Clothing"`  
• Implemented with logical rules (no ML)

---

## 🗄 Database Model

Product Fields  
• Name  
• SKU / Code  
• Category  
• Price  
• Stock Quantity  
• Minimum Stock Level  

Stock History Tracks  
• IN transactions  
• OUT transactions  
• Prevents negative stock  

---
## 🧪 API Testing & Validation (Postman Evidence)

The APIs were fully tested using Postman to validate:
• Request/response flow  
• Stock validation rules  
• Automation triggers  
• Error handling behavior  

**Postman Collection Link**
👉 https://your-postman-link-here
---
## 🖥 UI Dashboard Evidence (Working Screenshot)
A basic UI dashboard was implemented using Thymeleaf + Bootstrap.
The UI provides:
• Product list view  
• Stock visibility  
• Low-stock visual alerts (highlighted in red)
## 📸 Additional Evidence Screenshots

**Low Stock Email Notification**
![Low Stock Email Alert](screenshots/low-stock-email.png)

**Stock Update API Trigger**
![Stock Update API](screenshots/stock-update-test.png)


## 🧾 API Demonstration (Working Outputs)

### Add Product
POST `/api/products`

Request
```json
{
  "sku": "GKU-103",
  "name": "ghee",
  "category": "Grocery",
  "price": 199,
  "stockQty": 29,
  "minStockLevel": 1
}
Response

{
  "success": true,
  "message": "Product created successfully",
  "data": {
    "id": 6,
    "sku": "GKU-103",
    "name": "ghee",
    "category": "Grocery",
    "price": 199,
    "stockQty": 29,
    "minStockLevel": 1
  }
}
Fetch All Products

GET /api/products

Returns full product list.
Fetch Single Product

GET /api/products/SKU-102
data reccvied:
{
    "success": true,
    "message": "Product fetched",
    "data": {
        "id": 2,
        "sku": "SKU-102",
        "name": "Wireless keyboard",
        "category": "Electronics",
        "price": 1599.00,
        "stockQty": 7,
        "minStockLevel": 5
    }
}
Stock IN

POST /api/products/stock

{
  "sku": "SKU-101",
  "quantity": 5,
  "type": "IN"
}
Stock OUT
{
  "sku": "SKU-101",
  "quantity": 3,
  "type": "OUT"
}
Prevent Stock from Going Below Zero
{
  "sku": "GKU-103",
  "quantity": 50,
  "type": "OUT"
}
Response

{
  "success": false,
  "message": "Stock cannot go below zero for SKU: GKU-103"
}
Low Stock Alert Triggered
{
  "sku": "GKU-101",
  "quantity": 23,
  "type": "OUT"
}
Response

{
  "success": true,
  "message": "Stock updated. LOW STOCK ALERT triggered",
  "data": {
    "sku": "GKU-101",
    "stockQty": 1,
    "minStockLevel": 2,
    "lowStock": true
  }
}
Email Example

LOW STOCK ALERT - SKU: GKU-101
Action Required: Please reorder immediately.
Smart Reorder Suggestion:

GET /api/products/{sku}/reorder-suggestion?leadDays=7
respone
{
    "success": true,
    "message": "Reorder suggestion generated",
    "data": 917
}
AI Auto Categorization

When category is blank → assigned automatically
send:
{
  "sku": "CKU-310",
  "name": "Cotton Shirt Blue",
  "category": "",
  "price": 1199,
  "stockQty": 10,
  "minStockLevel": 3
}
reccived:
{
    "success": true,
    "message": "Product created successfully",
    "data": {
        "id": 10,
        "sku": "CKU-310",
        "name": "Cotton Shirt Blue",
        "category": "Clothing",
        "price": 1199,
        "stockQty": 10,
        "minStockLevel": 3,
        "lowStock": null
    }
}
📄 Pagination Support

GET /api/products/paged?page=0&size=5
Returns page metadata & elements.

🖥 Basic UI Dashboard
Runs on:
http://localhost:8080/
Displays:
• SKU
• Product Name
• Category
• Stock
• Minimum Level

Low stock rows show in red
🧷 Input Validation

• Required field validation
• Prevents invalid values
• Graceful error messages

⚠ Error Handling

Standard response format:

success = true/false
message = description
data = payload


Example

{
  "success": false,
  "message": "Product not found: SKU-999"
}

🧪 Unit Tests

Covers:
• Negative stock prevention
• Low stock alert logic
• Auto category assignment

Note
Price is NOT NULL — tests assign price accordingly.
📦 Sample Data (JSON)
[
  {"sku":"SKU-101","name":"Wireless Mouse","category":"Electronics","price":799,"stockQty":5,"minStockLevel":3},
  {"sku":"SKU-102","name":"Wireless keyboard","category":"Electronics","price":1599,"stockQty":7,"minStockLevel":5},
  {"sku":"GKU-101","name":"Rice","category":"Grocery","price":299,"stockQty":40,"minStockLevel":2}
]
✔ Project Status

This project successfully meets assignment requirements:

• Product CRUD
• Stock management
• Automation features
• Low stock alert + email
• Smart reorder suggestion
• AI-style category logic
• Pagination support
• Basic UI dashboard
• Input validation
• Error handling
• Basic authentication
• Unit tests
## 📌 API Demonstration Summary

| Feature | API | Tested |
|--------|-----|-------|
| Add Product | POST /api/products | ✔ Successful |
| Get Product List | GET /api/products | ✔ Working |
| Stock IN | POST /api/products/stock | ✔ Validated |
| Prevent Negative Stock | OUT transaction | ✔ Blocked |
| Low Stock Alert | Stock threshold case | ✔ Triggered |
| Email Notification | Low stock event | ✔ Sent |
| Reorder Suggestion | GET /reorder-suggestion | ✔ Generated |

