import java.awt.*; //class to for the GUI components
import java.awt.event.*; //event handling
import java.io.*; //input output streams
import java.net.*; //networking (serversocket)
import java.text.SimpleDateFormat; //used for date,time format
import java.util.ArrayList; //arraylist
import java.util.Collections; //collection
import java.util.Date; //current time


//Frame is from the awt class, it adds a prints/shows a basic window and window listener handles events (open close)
public class ChatServer extends Frame implements WindowListener {

    private ServerSocket serverSocket; //server socket listens for client(basically door)
    private static final int PORT = Constants.DEFAULT_PORT; //port is defined

    private java.util.List<ClientHandler> connectedClients = 
            Collections.synchronizedList(new ArrayList<>()); // synchronized arraylist to store all client handler for differnet clients
            //arraylist because dynamic size and fast access

    private Thread serverThread; 
    private volatile boolean isRunning = false; //used to stop server loop

    private TextArea serverLogArea; // for the logs in server
    private List clientList; //for the connected client list
    private Label serverStatusLabel; // shows server state
    private Label clientCountLabel; // number of clients
    private Button startButton; // the start button
    private Button stopButton; // for the stop button
    private Button clearLogsButton; // for the clear log button

    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss"); //to display time

    public static void main(String[] args) { // main (self explanatory)
        new ChatServer().setVisible(true); //object created, shows the window
    }

    public ChatServer() { // constructor
        super("Chat Server"); //calls the constructor of frame class
        initializeGUI(); //to build the window, creates UI and shi (from line 45)
        setupEventListeners(); //self explanatory (from line 78)
        log("[Server] Application started :D"); //prints in server log that it has started
    }
                // THE WHOLE GUI PART OF THE WINDOW!!!!!
    private void initializeGUI() {
        setSize(700, 600); //for size of the window(inpixels)
        setLayout(new BorderLayout());//divides the window into border

        serverStatusLabel = new Label("Status: Stopped"); // object creation for server status running/stopped
        clientCountLabel = new Label("Connected Clients: 0"); //Number of conected clients

        serverLogArea = new TextArea();  //object creation (for the log txt area)
        serverLogArea.setEditable(false); //makes the text area non editable(for the user)

        clientList = new List(); //object creation(for the client list)

        startButton = new Button("Start Server :D");// the start button object creation
        stopButton = new Button("Stop Server :C");//stop button object creation
        clearLogsButton = new Button("Clear Logs "); //clear log object creation

        stopButton.setEnabled(false); //disables the stop button (untill the server starts)

        Panel top = new Panel(); // panel is like container that hold other component (border replaces)
        top.add(serverStatusLabel); //adds the server status label and client count label on top
        top.add(clientCountLabel); 

        Panel bottom = new Panel(); // adding buttons in a panel    
        bottom.add(startButton);
        bottom.add(stopButton);
        bottom.add(clearLogsButton);

        add(top, BorderLayout.NORTH); // panel added on top
        add(serverLogArea, BorderLayout.CENTER);// log area in center
        add(clientList, BorderLayout.EAST); // client list on side
        add(bottom, BorderLayout.SOUTH); // button panel added on bottom
    }
                //THE ACTION PART(THE EVENT LISTENERRR)
    private void setupEventListeners() { //connects the buttons to its actions
        startButton.addActionListener(e -> { //when start button is clicked
            startServer(); // at line 95(starts the server)
            startButton.setEnabled(false); //disables the start button
            stopButton.setEnabled(true); //enables the start button
        });

        stopButton.addActionListener(e -> { //when stop button is clicked (e is lambda)
            stopServer(); //at line117 (stops the serber)
            startButton.setEnabled(true); //the start button is enabled back
            stopButton.setEnabled(false); //the stop button is disabled
        });

        clearLogsButton.addActionListener(e -> serverLogArea.setText("")); // when clear log button is clicked, log empties
        addWindowListener(this); //listens and tells class about actions from window
    }

                // THE SERVER STARTING PART
    private void startServer() {
        serverThread = new Thread(() -> { //new thread is created for the server
            try { //needed because networking can fail
                serverSocket = new ServerSocket(PORT); //creates a server on the given port and wait for clients
                isRunning = true; //changes to true, and used to control the loop
                log("Server started on port " + PORT); //prints message on log report

                while (isRunning) { //loop that keeps the server running continuosly
                    Socket socket = serverSocket.accept(); //accepts the clients
                    //here the thread is paused, until any connection is made
                    ClientHandler handler = new ClientHandler(socket, this); //the socket and reference of current object
                    handler.start(); //thread is started
                }

            } catch (IOException e) {
                log("Error: " + e.getMessage()); //for any error
            }
        });

        serverThread.start(); //calls the run method of server thread(lambda)
    }

                //SERVER STOPPING PART
    private void stopServer() {
        isRunning = false; //so loop stops

        try {
            if (serverSocket != null) 
                serverSocket.close(); //checks if server exist, if yes then it closes the socket
        } catch (IOException e) {
            log("Error closing server: " + e.getMessage());
        }

        for (ClientHandler c : new ArrayList<>(connectedClients)) { //closes all the client
            c.close();
        }

        connectedClients.clear(); //removes client ref from datastructure
        clientList.removeAll(); //removes client name from list (GUI)
        log("Server stopped"); //msg print
    }

    public synchronized void addClient(ClientHandler c) {
        connectedClients.add(c); // adds new client to list
        updateList(); //updates UI
    }

    public synchronized void removeClient(ClientHandler c) {
        connectedClients.remove(c); //removes client
        updateList(); //updates UI
    }

    public void broadcastMessage(String msg, ClientHandler sender) {
        for (ClientHandler c : new ArrayList<>(connectedClients)) {
            try {
                c.sendMessage(msg);
            } catch (Exception e) {
                log("Error sending message: " + e.getMessage());
            }
        }
        log(msg);
    }

    private void updateList() { 
        clientList.removeAll(); //clears UI
        for (ClientHandler c : connectedClients) { //adds client name to UI
            clientList.add(c.getClientName());
        }
        clientCountLabel.setText("Clients: " + connectedClients.size());
    }

    private void log(String msg) { //to put date and time in log msgs
        serverLogArea.append("[" + dateFormat.format(new Date()) + "] " + msg + "\n");
    }

    public void windowClosing(WindowEvent e) { //stops server and exit the program
        stopServer();
        System.exit(0);
    }
//we need to implement all the mthods of windowlistener interface...
    public void windowOpened(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    //harmony <3
} 