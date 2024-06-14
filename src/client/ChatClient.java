package client;

import java.io.*;
import java.net.Socket;

public class ChatClient {
    private String host;
    private int port;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader consoleReader;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            consoleReader = new BufferedReader(new InputStreamReader(System.in));

            new Thread(new ReadThread()).start();

            String message;
            while ((message = consoleReader.readLine()) != null) {
                out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ReadThread implements Runnable {
        @Override
        public void run() {
            String response;
            try {
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient("localhost", 12346); // Update the port number here
        client.start();
    }
}
