import java.awt.*; //gui
import java.awt.event.*; //events
import java.io.*; //stream
import java.net.*; //socket

public class ChatClient extends Frame implements WindowListener { 

    private Socket socket; //for connection
    private BufferedReader in;//receives msg
    private PrintWriter out; //sends msg

    private TextArea area; //gui
    private TextField field; //gui
    private Button send, connect; //gui

    public static void main(String[] args) {
        new ChatClient().setVisible(true);
    }

    public ChatClient() {
        setTitle("Client"); //sets the window Title
        setSize(400, 400); //sets the size of size
        setLayout(new BorderLayout()); //Divides window into 5 regions

        area = new TextArea(); //multi line box to display messages 
        area.setEditable(false); //User cdannot type here

        field = new TextField(); //singe line input
        send = new Button("Send"); //sends message
        connect = new Button("Connect"); //connects to server 

        Panel bottom = new Panel(new BorderLayout()); //a panel created to group components
        bottom.add(field, BorderLayout.CENTER); //in center takes most space
        bottom.add(send, BorderLayout.EAST); //appears on the right ; this Creates a typical chat input bar

        add(connect, BorderLayout.NORTH); //connect button at top 
        add(area, BorderLayout.CENTER); //chat display area
        add(bottom, BorderLayout.SOUTH); //Input panel (text field + send button)

        connect.addActionListener(e -> connect());
        send.addActionListener(e -> send());

        addWindowListener(this);
    }

    private void connect() { //called when the user click so connect button
        try { 
            socket = new Socket(Constants.DEFAULT_HOST, Constants.DEFAULT_PORT); 
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            area.append("Connected to server\n");

            new Thread(() -> {  
                try { 
                    String msg; 
                    while ((msg = in.readLine()) != null) {
                        area.append(msg + "\n");
                    }
                } catch (IOException e) {
                    area.append("Disconnected from server: " + e.getMessage() + "\n");
                }
            }).start();

        } catch (IOException e) {
            area.append("Connection failed: " + e.getMessage() + "\n"); 
        }
    }

    private void send() { 
        try {
            if (out != null) {
                out.println(field.getText());
                field.setText("");
            } else {
                area.append("Not connected to server\n");
            }
        } catch (Exception e) {
            area.append("Error sending message: " + e.getMessage() + "\n");
        }
    }

    public void windowClosing(WindowEvent e) {
        try {
            if (socket != null) socket.close();
        } catch (IOException ex) {
            area.append("Error closing connection: " + ex.getMessage() + "\n");
        }
        System.exit(0);
    }

    public void windowOpened(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
}