# BulSU eCash

**Bulacan State University — Campus Cashless Payment System**

A Java console-based application developed as an academic prototype for Object-Oriented Programming (OOP) and Data Structures and Algorithms.

## Project Description

BulSU eCash is a prototype cashless payment system designed for transactions within Bulacan State University.

Students have a digital wallet connected to their student account and card. They can add funds through a cashier and use their card to pay participating campus vendors.

The system records payments, top-ups, and cash-outs, allowing users to review their transaction history.

## User Roles and Features

### 1. Student
- Register an account using an authorized Student Number.
- Log in using an Account ID and PIN.
- View personal information, wallet balance, and card status.
- View payment and top-up transaction history.
- Report a card as lost.

### 2. Vendor
- Log in to an authorized vendor account.
- View store information and wallet balance.
- Process student payments using a Card UID.
- View payment and cash-out history.

### 3. Cashier
- Process student wallet top-ups.
- Process vendor cash-outs.
- View top-up and cash-out records.
- Search top-up transactions by Student Number.
- View transaction summaries.
- Replace reported lost student cards.

## Main System Features

- Account registration and login
- Role-based console menus
- Student and store wallets
- Card-based cashless payments
- Student wallet top-up
- Vendor cash-out
- Transaction confirmation
- Transaction history and reports
- Card loss reporting and replacement
- Basic input validation

## OOP Concepts Applied

**Encapsulation:** Classes use private attributes with public methods to access or manage their data.

**Inheritance:** `Student`, `Vendor`, and `Cashier` inherit from the abstract `User` class.

**Abstraction:** The `User`, `Wallet`, and `TransactionRecord` classes provide common structures for related objects.

**Polymorphism:** Different transaction types override `getTransactionType()` to return their respective transaction names.

**Upcasting and Downcasting:** The application uses the `User` type and `instanceof` checks when directing logged-in users to their appropriate menus.

## Technologies Used

- Java
- Object-Oriented Programming
- ArrayList for temporary data storage
- Console-based user interface
- Git and GitHub for version control

## How to Run

**Requirements:**
- Java JDK 11 or newer
- Terminal or Git Bash

**1. Clone the repository**

`git clone https://github.com/Nandoi/bulsu-ecash.git`

**2. Open the project folder**

`cd bulsu-ecash`

**3. Compile the Java files (Git Bash)**

`javac -encoding UTF-8 -d out $(find src -name "*.java")`

**4. Run the application**

`java -cp out Main`

## Important Notes

- This application is an academic prototype, not a production payment system.
- Data is currently stored in memory using `ArrayList`.
- Sample records are initialized through `TempData.java`.
- Transactions and new registrations are not permanently saved. They reset when the application is restarted.
- A database integration may be added in future development.

## Project Developers

- Fernando III Vidal
- Joshua Del Carmen
- Jullian Manarang
- Michelle Jumaquio

**Bulacan State University**
Bachelor of Science in Information Technology