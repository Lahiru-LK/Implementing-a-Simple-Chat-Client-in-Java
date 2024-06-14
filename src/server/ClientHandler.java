package server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private ChatServer server;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private int clientId;

    public ClientHandler(ChatServer server, Socket socket, int clientId) {
        this.server = server;
        this.socket = socket;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String message;
            while ((message = in.readLine()) != null) {
                String formattedMessage = "Client " + clientId + ": " + message;
                System.out.println("Received: " + formattedMessage);
                server.broadcastMessage(formattedMessage, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}
