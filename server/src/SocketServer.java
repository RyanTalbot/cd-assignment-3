import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class SocketServer {
    private static ServerSocket serverSocket;
    private static final int PORT = 8000;
    private Socket clientSocket;
    private PrintWriter outputToClient;
    private BufferedReader inputFromClient;

    private static ArrayList<Car> carInventory;

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(PORT);

        for (int i = 0; i < 1; i++) {
            new Thread(() -> {
                try {
                    new SocketServer().startServer(serverSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void startServer(ServerSocket serverSocket) throws IOException {
        System.out.println("Start socket server BEGIN");

        carInventory = new ArrayList<>();

        clientSocket = serverSocket.accept();
        outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);
        inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        System.out.println("Connection established");

        String message = "";

        while(!"stop".equalsIgnoreCase(message)) {

            message = inputFromClient.toString();

            showMenu(message);


            outputToClient.println("You sent " + message);
        }

        inputFromClient.close();
        outputToClient.close();
        clientSocket.close();
        serverSocket.close();
        System.out.println("Start socket server END");
    }

    public void addCar() {
        System.out.println("Adding car");
    }

    public void sellCar() {
        System.out.println("Selling car");
    }

    public void carInfo() {
        System.out.println("Getting info...");
    }

    public void showMenu(String option) {
        outputToClient.println("** Menu **");
        outputToClient.println("1. Add Car\n2. Sell Car\n3. Car Info");

        switch (option) {
            case "1":
                addCar();
                break;
            case "2":
                sellCar();
                break;
            case "3":
                carInfo();
                break;
            case "0":
                break;
        }
    }
}

class Car {
    private String registration, make;
    private int price, mileage;
    private boolean forSale;

    public Car(String registration, String make, int price, int mileage, boolean forSale) {
        this.registration = registration;
        this.make = make;
        this.price = price;
        this.mileage = mileage;
        this.forSale = forSale;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public boolean isForSale() {
        return forSale;
    }

    public void setForSale(boolean forSale) {
        this.forSale = forSale;
    }
}
