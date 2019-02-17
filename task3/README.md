# Task 2

## Tool 
- java
- maven
- junit 
- rest-assured

## How to run tests

Precondition :
- run application for test [Instruction how to run ](https://github.com/BestBuy/api-playground) 

1. clone repo 
2. cd task3 
3. mvn clean test


## List of proposed test cases
  
  |#|Endpoint |Expected result|
  |---|----|----|
  |1|GET /healthcheck|Service is healthy and running|
  |2|GET /products|Return default amount of products |
  |3|GET /products?$limit={lim} | Return proper {lim} amount of products. Value (0, 5, 60000)|
  |4|GET /products?$skip={skip} | Will skip {skip} amount of products. Value (0, 5, 60000)|
  |5|GET /products?$limit={lim}&$skip={skip}|Check that both parameter will be applied|
  |6|POST /products (with missed mandatory fields)|Will return error message.|
  |7|POST /products (with wrong field name)| Will return error message|
  |8|POST /products (with correct data)|Will create product |
  |9|DELETE /products (not existed productId)|Will return error message|
  |10|DELETE /products {existed product)|Successfully delete product. |
  |11|GET /products/{productId} (not existed productId)| Will return error message. |
  |12|GET /products/{productId} (existed productId)| Will return correct product. |
  |13|GET /products/{productId} (existed productId)| Will return proper json schema. |
  
  I have create test only for products. 
  All other endpoints should be covered with similar test also.
  
## Short explanation of the provided solution 

- I have choose java as it my common language for work.
- Rest-assured most common library for api tests. Easy to read and maintain. With JSON, Hamcrest support. 
- Junit library this is enough for this implementation. (Now I see that TestNg will suite better due it is much easy to handle parametrized test)
  
