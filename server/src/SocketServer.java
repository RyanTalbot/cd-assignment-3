import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private static ServerSocket serverSocket;
    private static final int PORT = 8000;
    private Socket clientSocket;
    private PrintWriter outputToClient;
    private BufferedReader inputFromClient;

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(PORT);
    }

    public void startServer(ServerSocket serverSocket) throws IOException {
        System.out.println("Start socket server BEGIN");

        clientSocket = serverSocket.accept();
        outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);
        inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String message = "";
        System.out.println("Connection established");

        while(!"stop".equalsIgnoreCase(message)) {
            message = inputFromClient.readLine();

            if (message == null) {
                continue;
            }

            outputToClient.println("You sent " + message);
        }

        inputFromClient.close();
        outputToClient.close();
        clientSocket.close();
        serverSocket.close();
        System.out.println("Start socket server END");
    }
}
