import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ChatServer server;

    private String name;
    private static int count = 0;

    public ClientHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
        this.name = "Client_" + (++count);

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Error setting up client " + name + ": " + e.getMessage());
        }
    }

    public void run() {
        server.addClient(this);

        try {
            String msg;
            while ((msg = in.readLine()) != null) {
                server.broadcastMessage("[" + name + "] " + msg, this);
            }
        } catch (IOException e) {
            System.out.println(name + " disconnected: " + e.getMessage());
        }

        close();
    }

    public void sendMessage(String msg) {
        try {
            if (out != null) {
                out.println(msg);
            }
        } catch (Exception e) {
            System.out.println("Error sending message to " + name + ": " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing socket for " + name + ": " + e.getMessage());
        }

        server.removeClient(this);
    }

    public String getClientName() {
        return name;
    }
}