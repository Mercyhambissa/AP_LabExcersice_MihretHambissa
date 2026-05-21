import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {

    private static final int PORT = 5000;
    private static Set<PrintWriter> clients = new HashSet<>();

    public static void main(String[] args) {

        System.out.println("Server starting...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Server running on port " + PORT);

            while (true) {

                Socket socket = serverSocket.accept();

                System.out.println("Client connected");

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                clients.add(out);

                new Thread(() -> handleClient(socket, out)).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket, PrintWriter out) {

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()))) {

            String msg;

            while ((msg = in.readLine()) != null) {
                broadcast(msg);
            }

        } catch (Exception e) {
            System.out.println("Client disconnected");
        } finally {
            clients.remove(out);

            try {
                socket.close();
            } catch (Exception ignored) {}
        }
    }

    private static void broadcast(String msg) {

        for (PrintWriter client : clients) {
            client.println(msg);
        }
    }
}