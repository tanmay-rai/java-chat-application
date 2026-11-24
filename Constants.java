/**
 * Constants Configuration Class
 * Responsibility: STUDENT 4 (Data Management & Exception Handling)
 * 
 * Purpose: Centralized configuration for server and client settings
 * - Server port and host settings
 * - Message format and limits
 * - Connection timeout values
 * - Buffer sizes
 */

public class Constants {
    
    // ============ SERVER CONFIGURATION ============
    public static final int DEFAULT_PORT = 5001;
    public static final String DEFAULT_HOST = "localhost";
    public static final int MAX_CLIENTS = 100;
    
    // ============ CONNECTION SETTINGS ============
    public static final int CONNECTION_TIMEOUT = 5000;      // 5 seconds
    public static final int SOCKET_TIMEOUT = 30000;         // 30 seconds
    public static final int BUFFER_SIZE = 4096;
    
    // ============ MESSAGE PROTOCOL ============
    public static final String MESSAGE_DELIMITER = "|";
    public static final String SPECIAL_MESSAGE_PREFIX = "!";
    public static final int MAX_MESSAGE_LENGTH = 1000;
    
    // ============ SPECIAL MESSAGES ============
    public static final String CMD_LIST_CLIENTS = "!LIST";
    public static final String CMD_DISCONNECT = "!DISCONNECT";
    public static final String CMD_SERVER_SHUTDOWN = "!SHUTDOWN";
    
    // ============ GUI SETTINGS ============
    public static final int FRAME_WIDTH = 600;
    public static final int FRAME_HEIGHT = 500;
    public static final int SERVER_FRAME_WIDTH = 700;
    public static final int SERVER_FRAME_HEIGHT = 600;
    
    // ============ COLORS ============
    public static final String COLOR_BACKGROUND = "#FFFFFF";
    public static final String COLOR_FOREGROUND = "#000000";
    public static final String COLOR_BUTTON = "#4CAF50";
    
    // ============ ERROR MESSAGES ============
    public static final String ERROR_CONNECTION_FAILED = "Failed to connect to server";
    public static final String ERROR_INVALID_PORT = "Invalid port number";
    public static final String ERROR_SOCKET_EXCEPTION = "Socket error occurred";
    public static final String ERROR_IO_EXCEPTION = "I/O error occurred";
    public static final String ERROR_EMPTY_MESSAGE = "Message cannot be empty";
    
    // ============ INFO MESSAGES ============
    public static final String INFO_CONNECTED = "Connected to server";
    public static final String INFO_DISCONNECTED = "Disconnected from server";
    public static final String INFO_CLIENT_JOINED = "joined the chat";
    public static final String INFO_CLIENT_LEFT = "left the chat";
    
    // ============ PRIVATE CONSTRUCTOR ============
    private Constants() {
        // Prevent instantiation
    }
}
