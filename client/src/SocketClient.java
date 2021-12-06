import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    private Socket clientSocket;
    private PrintWriter outputToServer;
    private BufferedReader inputFromServer;
    static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 8000;

    public static void main(String[] args) throws IOException {
        new SocketClient().startConnection();
    }

    public void startConnection() throws IOException {
        clientSocket = new Socket(ADDRESS, PORT);

        outputToServer = new PrintWriter(clientSocket.getOutputStream(), true);
        inputFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        Scanner scanner = new Scanner(System.in);
        String toServer = "";

        while (!"stop".equalsIgnoreCase(toServer)) {
            toServer = scanner.nextLine();
            System.out.println("Send to server: " + toServer);
            outputToServer.println(toServer);
            String responseFromServer = inputFromServer.readLine();
            System.out.println("Response from server: " + responseFromServer);
        }

        inputFromServer.close();
        outputToServer.close();
        clientSocket.close();
    }
}
