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

    Map<String, Car> inventory = new HashMap<>();

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
        System.out.println("==== SERVER START ====");


        Car nissan = new Car("12D-54621", "Nissan", 17500, 5000, true);
        Car toyota = new Car("15G-45674", "Toyota", 25000, 100, true);

        inventory.put(nissan.getRegistration(), nissan);
        inventory.put(toyota.getRegistration(), toyota);

        clientSocket = serverSocket.accept();
        outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);
        inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        System.out.println("Connection Established");

        String message = "";

        while(!"stop".equalsIgnoreCase(message)) {

            message = inputFromClient.readLine();
            System.out.println(message);

            if (message.contains("add")) {
                createCarMenu();
            } else if (message.contains("sell")) {
                sellCar();
            } else if (message.contains("info")) {
                carInfo();
            }

        }

        inputFromClient.close();
        outputToClient.close();
        clientSocket.close();
        serverSocket.close();
        System.out.println("==== SERVER CLOSE ====");
    }

    public void createCarMenu() {
        outputToClient.println("Add Car");
    }

    public void sellCar() {
        outputToClient.println("Selling car");
    }

    public void carInfo() throws IOException {
        outputToClient.println("Enter REG:");
        String message = inputFromClient.readLine();
        String name = "", make = "", price = "", mileage = "", forSale = "";

        if (inventory.containsKey(message)) {
            for (String key : inventory.keySet()) {
                name = key;
                make = inventory.get(key).getMake();
                price = Integer.toString(inventory.get(key).getPrice());
                mileage = Integer.toString(inventory.get(key).getMileage());
                forSale = Boolean.toString(inventory.get(key).isForSale());
            }
        }
        outputToClient.println(" | " + name + " - " + make + " - " + price + " - " + mileage + " - " + forSale + " | ");
        System.out.println(message);
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
