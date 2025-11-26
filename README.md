# Banking API Challenge

## Requirements
- Java 21
- Gradle 8.14.3

## Running the Application

### Using Gradle
```bash
./gradlew bootRun
```

### Running Tests
```bash
./gradlew test
```

#### Note: You should have a Postgres database running on localhost:5432 (username and password are postgres, and the database is banking).

### Using Docker
```bash
docker compose up
```

## API Endpoints

### Create Account
```bash
POST /customers/{customerId}/accounts
Content-Type: application/json

{
"balance": 1000.00,
"customerId": 1
}
```

### Transfer Funds
```bash
POST /transfers/accounts/{accountId}
Content-Type: application/json

{
"fromAccountId": 1,
"toAccountId": 2,
"amount": 100.00
}
```

### Get Balance
```bash
GET /accounts/{accountId}/balance
```

### Get Transfer History
```bash
GET /transfers/accounts/{accountId}/sent  # get only sent transfers from this account
GET /transfers/accounts/{accountId}/received  # get only received transfers to this account
GET /transfers/accounts/{accountId}  # get all transfers for this account
```

## Pre-populated Customers
The application comes with 4 pre-populated customers:
- ID 1: Arisha Barron
- ID 2: Branden Gibson
- ID 3: Rhonda Church
- ID 4: Georgina Hazel

And they are given the next account balances:
- ID 1: 1000.00 for Customer 1
- ID 2: 500.00 for Customer 2
- ID 3: 0.00 for Customer 3
- ID 4: 20000.00 for Customer 4
- ID 5: 600000.00 for Customer 1

## Notes
- I have designed the application to be RESTful.
- The application uses a Postgres database on a docker container.
- The application is fully containerized (You can run it using Docker Compose).
- I deliberately did not implement any security measures because this is just a demo and the time is short.
- I deliberately implemented just few tests to demonstrate testing approach due to time constraints.
- I have not used any caching mechanism.
- I have not used any asynchronous programming.

Thank you.

Best regards,

Mr. Fathi Abdelmalek, a software engineer and PhD student in the AI field.
