StockDemo

A java based backend application that uses ReST. It contains following endpoints :
        GET /api/stocks : To list all the stocks
        GET /api/stocks/5 : Retrieve a stock with id=5
        POST /api/stocks : To create/add/save a new stock
        PUT /api/stocks/5 : To update the price of stock with id=5

A stock entity has following fields :
        id(number),name(String),currentPrice(Amount) and lastUpdate(Timestamp)

Technologies :
        Jdk 8
        Spring Boot 2.1.3
        Apache Maven
        Embedded Tomcat
        H2 in-memory database

Opensource frameworks used :
        Log4J - logging
        Jackson - ser & deser
        Swagger2 - REST documentation
        Actuator - To see if app is up and running
        Junit,Mockito - for testing

Tools :
        Postman - for testing
