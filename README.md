# Fitness Studio
Final project for IT Academy.
It is a microservices-based web application for managing a healthy fitness diet. 
App is built using Spring Boot (MVC, Security, Mail, Data JPA, AOP, Validation), PostgreSQL, Maven. The application is deployed in Docker. 
API documentation is available in the Swagger UI. PgAdmin is used to communicate with the DB.
____

## Microservices
At the moment, 4 modules have been implemented, each module has its own DB, communication between modules is implemented via the HTTP protocol. 

### User-service
Service realises CRUD operations with users, performs authentication and authorization, generates JWT tokens used in other microservices to check access rights.

#### Endpoints
##### ***POST /api/v1/users*** 

Create new inner user. Access only for admin. The JWT token is required.

No parameters. Request body required in json.

Example json:

![image](https://user-images.githubusercontent.com/110600085/229316268-94746cb3-5161-47a8-aa93-6e6e4e04c614.png)

Responses: 201, 400, 401, 403, 500.

##### ***GET /api/v1/users***

Get users page. Access only for admin. The JWT token is required.

Parameters in query:
* page (integer)  - Number of page for view. Default value - 0;

* size (integer) - Amount elements on one page. Default value - 20.

Responses: 200, 400, 401, 403, 500.

##### ***GET /api/v1/users/{uuid}***

Get information about user by uuid. Access only for admin. The JWT token is required.

Parameters in path required:
* uuid (string($uuid)) - User ID.

Responses: 200, 400, 401, 403, 500.

##### ***PUT /api/v1/users/{uuid}/dt_update/{dt_update}***

Edit user information. Access only for admin. The JWT token is required.

Parameters in path required:
* uuid (string($uuid)) - User ID;
* dt_update (long) - Date the record was last updated.

Request body required in json.

Example json:

![image](https://user-images.githubusercontent.com/110600085/229316268-94746cb3-5161-47a8-aa93-6e6e4e04c614.png)

Responses: 200, 400, 401, 403, 500.

##### ***POST /api/v1/users/registration***

Self-registration of the user. Access for all.

No parameters. Request body required in json.

Example json:

![image](https://user-images.githubusercontent.com/110600085/229319326-18fdaaba-4084-478c-bffc-dc65be7ef960.png)

Responses: 201, 400, 500.

##### ***GET /api/v1/users/verification***

Verification of a self-registered user. Access for all.

Parameters in path required:
* code (string) - Verification code confirming access to the postal address;
* mail (string) - Email for which the verification code was issued.

Responses: 200, 400, 500.

##### ***POST /api/v1/users/login***

Log in. Upon successful login, a token is issued. Access for all.

No parameters. Request body required in json.

Example json:

![image](https://user-images.githubusercontent.com/110600085/229319676-805a5cf5-f92d-4987-97d9-9b181a12d326.png)

Responses: 200, 400, 500.

##### ***GET /api/v1/users/me***

The user can get information about himself. Access only for user. The JWT token is required.

No parameters. Data to provide information about the user is read from the token.

Responses: 200, 400, 401, 403, 500.

----

### Product-service
Service realises CRUD operations with products and recipes. It calculates the nutritional value of each recipe.

#### Endpoints
##### ***POST /api/v1/product*** 

Create new product. Access only for admin. The JWT token is required.

No parameters. Request body required in json.

Example json:

![image](https://user-images.githubusercontent.com/110600085/229618162-c4408fb0-2d81-4136-a1f1-0c314c6d885d.png)

Responses: 201, 400, 401, 403, 500.

##### ***GET /api/v1/product***

Get products page. Access for all. The JWT token is not required.

Parameters in query:
* page (integer)  - Number of page for view. Default value - 0;

* size (integer) - Amount elements on one page. Default value - 20.

Responses: 200, 400, 401, 403, 500.

##### ***PUT /api/v1/product/{uuid}/dt_update/{dt_update}***

Edit product information. Access only for admin. The JWT token is required.

Parameters in path required:
* uuid (string($uuid)) - Product ID;
* dt_update (long) - Date the record was last updated.

Request body required in json.

Example json:

![image](https://user-images.githubusercontent.com/110600085/229618162-c4408fb0-2d81-4136-a1f1-0c314c6d885d.png)

Responses: 200, 400, 401, 403, 500.

##### ***POST /api/v1/recipe*** 

Create new recipe. Access only for admin. The JWT token is required.

No parameters. Request body required in json.

Example json:

![image](https://user-images.githubusercontent.com/110600085/229620445-e34585c1-7d5b-43ec-9d43-8885496a3498.png)

Responses: 201, 400, 401, 403, 500.

##### ***GET /api/v1/recipe***

Get recipes page. Shows weight and the nutritional value each product in the recipe, and sum  nutritional value recipe.
Access for all. The JWT token is not required.

Parameters in query:
* page (integer)  - Number of page for view. Default value - 0;

* size (integer) - Amount elements on one page. Default value - 20.

Responses: 200, 400, 401, 403, 500.

##### ***PUT /api/v1/recipe/{uuid}/dt_update/{dt_update}***

Edit recipe information. Access only for admin. The JWT token is required.

Parameters in path required:
* uuid (string($uuid)) - Recipe ID;
* dt_update (long) - Date the record was last updated.

Request body required in json.

Example json:

![image](https://user-images.githubusercontent.com/110600085/229620445-e34585c1-7d5b-43ec-9d43-8885496a3498.png)

Responses: 200, 400, 401, 403, 500.

----

### Mail-service
An internal microservice, not connected to the outside world. In the current version, the mail-service is used for:

* receiving a letter with a verification code from user-service (via HTTP, json);
* saving the letter in the DB;
* selection of unsent messages from the DB according to the schedule (once an hour; at the first start, the selection is delayed by 2 minutes);
* sending mail to users (asynchronously, the core number of threads - 10, the maximum allowed number of threads - 15, the queue capacity - 100).

SMTP server - gmail.com is used to send mail.

----
