# Test Automation of simple Spring Boot registration application

The simple spring boot application registers users that come as RESTful HTTP requests
into the database. If everything with the request is OK, then the app returns
a successful response. Otherwise, the app will return error messages to the user
inside a response body. Test automation will ensure that the applications handle 
valid and invalid data correctly and everything works fine.

## Request and Response

Request with valid data

```JSON
{
    "nick-name": "isfzade",
    "password": "123456",
    "email": "info@isfan.az"
}
```
Response to valid request

```JSON
{
    "errors": null,
    "response": {
        "id": 1,
        "nick-name": "isfzade"
    },
    "is-successful": true
}
```

Request with invalid data

```JSON
{
    "nick-name": "isfzade",
    "password": "",
    "email": ""
}
```

Response to invalid request

```JSON
{
    "errors": [
        {
            "code": 1006,
            "type": "PASSWORD_EMPTY",
            "message": "Password cannot be empty"
        },
        {
            "code": 1002,
            "type": "EMAIL_EMPTY",
            "message": "Email cannot be empty"
        }
    ],
    "response": null,
    "is-successful": false
}
```

## Packages

Here are the short explanations uf used packages:

- spring-boot-starter-web: This is needed to make HTTP requests like get and post.
- spring-boot-starter-data-jpa: JPA is a known pack to handle database relations.
- lombok: A useful package to prevent writing constructors, getters, and setters in code for nice and readable code.
- spring-boot-starter-validation: This package is used to validate request data. i.e. is a valid email.
- spring-boot-starter-test: This is needed to implement basic JUnit tests.
- h2: This is used for mocking the database. It simulates a database by using RAM and prevents implementing tests on real databases

## Article

You can read the article by clicking
<a href="https://isfan.az/2024/06/10/spring-boot-native-test-automation-of-simple-registration-application/" target="_blank">here</a>
where I explained the test automation for the simple registration application.
