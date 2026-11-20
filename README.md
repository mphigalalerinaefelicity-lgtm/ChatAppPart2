
# QuickChat – Java Messaging Application

## PROG5121 – Programming Portfolio of Evidence

### Project Overview

QuickChat is a Java-based messaging application developed as part of the PROG5121 Programming Portfolio of Evidence.

The application allows users to register, log in, send messages, disregard messages, store messages and manage message records. It also provides message searching, reporting and JSON-based data persistence.

The project was developed using Java and Maven in NetBeans.

## Features

### 1. User Registration
- Capture first name and surname.
- Validate usernames.
- Validate passwords according to the required security rules.
- Validate South African cellphone numbers.
- Provide feedback when registration information is invalid.

### 2. User Login
- Authenticate registered users.
- Reject incorrect login credentials.
- Display a personalised welcome message after successful login.

### 3. Message Management
Users can:
- Send messages.
- Disregard messages.
- Store messages.
- Generate unique message IDs.
- Generate message hashes.
- Validate recipient numbers and message content.

### 4. Sent Messages Report
The application provides a report of sent messages, including available message details such as:
- Message ID.
- Message hash.
- Recipient.
- Message content.
- Sender.
- Message number.
- Message status.

### 5. Stored Messages Management

The Stored Messages menu provides eight operations:

1. Display sender and recipient details.
2. Display the longest stored message.
3. Search for a message using its ID.
4. Search stored messages by recipient.
5. Delete a stored message using its hash.
6. Display the full stored messages report.
7. Search sent and stored messages by recipient.
8. Return to the main menu.

### 6. Data Persistence
- Gson is used to process JSON data.
- Stored messages are saved in `messages.json`.
- Message history is maintained using the application's history storage.
- Stored records can be loaded when the application starts again.
- Deletion updates the stored message archive.

## Technologies Used

- Java 17 or later
- Apache Maven
- NetBeans IDE
- Gson
- JUnit 5
- Git and GitHub
- GitHub Actions

## Project Structure

```text
ChatAppPart2/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/mycompany/chatapppart2/
│   │           ├── Main.java
│   │           ├── Message.java
│   │           ├── MessageManager.java
│   │           ├── MessageHistory.java
│   │           ├── MessageArchive.java
│   │           ├── RegistrationClassOne.java
│   │           └── LoginUserOne.java
│   │
│   └── test/
│       └── java/
│
├── pom.xml
├── messages.json
└── message-history.json
```

*Note: JSON files are generated or maintained during application use and may not be present in a fresh checkout.*

## Requirements

Before running the application, ensure that you have:

- JDK 17 or later installed.
- Apache Maven installed.
- NetBeans IDE or another Java-compatible development environment.

## Running the Application

### Using NetBeans

1. Clone or download the repository.
2. Extract the project if downloaded as a ZIP file.
3. Open NetBeans.
4. Select **File → Open Project**.
5. Locate and open the `ChatAppPart2` project.
6. Allow Maven to resolve the project dependencies.
7. Right-click the project and select **Clean and Build**.
8. Run `Main.java`.

### Using Command Prompt

Navigate to the project directory and execute:

```bash
mvn clean verify
```

To run the application using Maven:

```bash
mvn exec:java
```

## Testing

The project uses JUnit 5 for automated testing.

The Maven verification process compiles the project and executes the available automated tests.

The current GitHub Actions workflow runs the Maven build and verification process using Java 17.

The latest successful repository workflow reported:

- 9 tests executed.
- 0 failures.
- 0 errors.
- 0 skipped tests.
- Build successful.

## Version Control

Git and GitHub are used for source-code version control and project development.

The repository includes commit history documenting development and updates to the application.

## Repository

GitHub:  
https://github.com/mphigalalerinaefelicity-lgtm/ChatAppPart2

## Author

**Rinae Mphigalale**

PROG5121 – Programming Portfolio of Evidence

**Project:** QuickChat – Part 3

