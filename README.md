# PostgresCRUD
This is a simple CRUD api project which utilizes postgres database to set up a simple store database.
The database contains customer,product and order tables.
The customer and order tables have a one to many relationship whilst the order and product tables have a many to many relationship.
The order_product table is a join table to handle the many to many relationship between the order and product tables.
We have simple CRUD operations that can be performed with the customers and products table.The order controller gives placeOrder and getTotalBill api which
can place an order for a set of products,upon placing the order the stock of the products in the order will reduce,before placing any order a check on whether the necessary stock is there to meet the order demands will be performed.
The total bill api will calculate the total bill for a particular customers order.
All APIs are protected using keycloak identity and access management system.
All necessary services which includes the postgresDB,pgAdmin to monitor the db,keycloak and the crud app itself can be brought up and running using the docker-compose.
