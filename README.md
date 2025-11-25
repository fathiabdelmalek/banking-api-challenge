# Banking API Challenge

## Requirements
- Java 21
- Gradle 8.14.3

## Running the Application

### Using Gradle
\`\`\`bash
./gradlew bootRun
\`\`\`

### Running Tests
\`\`\`bash
./gradlew test
\`\`\`

## API Endpoints

### Create Account
\`\`\`bash
POST /customers/{customerId}/accounts
Content-Type: application/json

{
"balance": 1000.00,
"customerId": 1
}
\`\`\`

### Transfer Funds
\`\`\`bash
POST /transfers/accounts/{accountId}
Content-Type: application/json

{
"fromAccountId": 1,
"toAccountId": 2,
"amount": 100.00
}
\`\`\`

### Get Balance
\`\`\`bash
GET /accounts/{accountId}/balance
\`\`\`

### Get Transfer History
\`\`\`bash
GET /transfers/accounts/{accountId}
\`\`\`

## Pre-populated Customers
The application comes with 4 pre-populated customers:
- ID 1: Arisha Barron
- ID 2: Branden Gibson
- ID 3: Rhonda Church
- ID 4: Georgina Hazel

## H2 Console
Access the H2 console at: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:bankdb
- Username: sa
- Password: (leave empty)
  \`\`\`