# B2C DEMO Application

This project is a **B2C (Business-to-Consumer) web application** built with **Spring Boot**.  
It demonstrates how to develop a scalable and maintainable e-commerce platform using modern Java technologies.

Key features:
- **Spring Boot** as the core framework for rapid development
- **Thymeleaf** for server-side rendering and dynamic UI
- **JPA/Hibernate** for database interactions
- Designed with a **modular architecture** to support common B2C scenarios

This project can serve as a foundation for learning Spring Boot, or as a starting point for building a real-world e-commerce application.

# Architecture Overview

This is a Spring Boot e-commerce application with a multi-layered architecture:

- **Entities**: JPA entities with inheritance patterns (`BaseEntity` -> `Container` -> `Basket/Order`, `LineItem` -> `BasketLineItem/OrderLineItem`)
- **Models**: DTOs for view layer (`ContainerModel`, `OrderModel`, `LineItemModel`) with nested classes for organization
- **Frontend**: Thymeleaf templates + Bootstrap 5 + custom JavaScript (Webpack bundled)
- **Payment**: Stripe integration via custom JavaScript modules

# Key Patterns & Conventions

### Entity Design
- Use `String` IDs for business entities (Product, Category) with kebab-case enforcement in setters
- Use `Long` IDs for system entities (Customer, Order, Basket) 
- Field naming: `{entityName}_id` for primary keys (e.g., `customer_id`, `product_id`)
- Extend `BaseEntity` for audit fields (`creationDate`, `lastModified`)

### Model Layer
- Models mirror entities but add presentation logic (totals calculation, item counts)
- Use nested classes for related data (`Total`, `Shipping`, `Billing` in `ContainerModel`)
- Always provide no-arg constructors and entity-based constructors

### Frontend Structure
- Templates in `src/main/resources/templates/` with fragment-based composition
- JavaScript in `client/js/` built via Webpack to `static/dist/`
- Bootstrap 5 classes only, semantic HTML structure (`<header>`, `<main>`, `<footer>`)
- Thymeleaf fragments use `th:fragment` and `th:replace` patterns

# Critical Workflows

### Build & Development
```bash
# Build frontend assets separately
npm run build
```

### Database Relationships
- `Basket/Order` -> `BasketLineItem/OrderLineItem` (One-to-Many)
- `Product` relationships via separate assignment entities (`CategoryAssignment`, `PriceBook`, `Inventory`)
- Use `@ManyToOne` for foreign keys, avoid bidirectional mappings unless needed

### Frontend Integration Points
- AJAX endpoints expect JSON request/response
- Cart operations update minicart via `.cart-count` selector
- Stripe payment flow: create intent -> submit form -> confirm payment
- Use `data-*` attributes for JavaScript configuration

# Repository & Service Patterns
- Repositories extend `CrudRepository<Entity, IdType>`
- ID types: `String` for business entities, `Long` for system entities
- Service methods should handle entity-to-model conversion

# Important Files
- `webpack.config.js`: Multi-target build configuration
- `global/header.html`, `global/footer.html`: Shared layout fragments  
- `client/js/stripe/stripe.main.js`: Payment processing logic
- Entity base classes: `BaseEntity`, `Container`, `LineItem`

# Template Conventions
- Use Bootstrap card components with `shadow-sm` and `rounded-3`
- Mobile-first responsive design (`col-12 col-md-*`)
- Consistent spacing: `py-4`, `px-3`, `mb-4` for sections
- Icons via Bootstrap Icons (`bi-*` classes)