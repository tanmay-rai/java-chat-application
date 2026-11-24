# Java Chat Application

A multi-client desktop chat application built using Java Socket Programming, Multithreading and AWT. The application allows multiple clients to connect to a central server and exchange messages in real time.

## Features

- Multi-client communication.
- Client-server architecture using Java Sockets.
- Multithreading to handle multiple connected clients concurrently.
- Real-time message broadcasting.
- AWT-based graphical user interfaces for both client and server.
- Server dashboard displaying connected clients and server logs.
- Start and stop server functionality.
- Connection and disconnection handling.

## Technologies Used

- Java
- Socket Programming (`Socket`, `ServerSocket`)
- Multithreading (`Thread`)
- AWT (Abstract Window Toolkit)
- BufferedReader and PrintWriter
- Java Event Handling

## Project Structure

```text
ChatApp/
├── ChatClient.java
├── ChatServer.java
├── ClientHandler.java
├── Constants.java
├── .gitignore
└── README.md
```

## Architecture

The application follows a client-server architecture.

1. The server starts and listens for incoming client connections using `ServerSocket`.
2. A client connects to the server using `Socket`.
3. The server creates a separate `ClientHandler` thread for each connected client.
4. Each handler manages communication with its respective client.
5. Incoming messages are broadcast to connected clients through the server.
6. Java I/O streams handle sending and receiving messages.

## How to Run

### Prerequisites

- Java Development Kit (JDK) installed.
- Terminal or an IDE such as VS Code.

### 1. Clone the repository

```bash
git clone https://github.com/tanmay-rai/java-chat-application.git
cd java-chat-application
```

### 2. Compile the source files

```bash
javac *.java
```

### 3. Start the server

```bash
java ChatServer
```

Click the Start Server button in the server window.

### 4. Start the clients

Open separate terminal windows in the project directory and run:

```bash
java ChatClient
```

Launch multiple clients to test simultaneous communication.

Click Connect in each client window and start exchanging messages.

## Concepts Demonstrated

- Client-server networking.
- TCP socket communication.
- Multithreading and concurrent client handling.
- Input/output streams.
- GUI development using Java AWT.
- Event-driven programming.
- Basic connection and error handling.

## Author

Tanmay

GitHub: [tanmay-rai](https://github.com/tanmay-rai)
