/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import domain.Administrator;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author x
 */
public class Server {
    private ServerSocket serverSocket;
    private List<ClientHandler> clientHandlers;
    private List<Administrator> loggedInAdministrators;
    private int maxClients;
    private int port;
    private boolean running;
    private int brojac;
    
    public Server(ServerConfig config) {
        this.clientHandlers = new ArrayList<>();
        this.maxClients = config.getMaxUsers();
        this.port = config.getPort();
        this.running = false;
        this.loggedInAdministrators = new ArrayList<>();
        this.brojac=0;
    } 
    public Server(){
        
    }

    public List<Administrator> getLoggedInAdministrators() {
        return loggedInAdministrators;
    }
    
    public synchronized boolean isAdministratorLoggedIn(Administrator administrator) {
        for (Administrator loggedInAdministrator : loggedInAdministrators) {
            if (loggedInAdministrator.getEmail().equals(administrator.getEmail())) {
                return true;
            }
        }
        return false;
    }
    
     public synchronized void addLoggedInAdministrator(Administrator administrator) {
        loggedInAdministrators.add(administrator);
        
    }
     
     public synchronized void removeLoggedInAdministrator(Administrator administrator) {
        System.out.println(administrator.getAdminId());

        Administrator administratorToRemove = null;
        for (Administrator a : loggedInAdministrators) {
            if (a.getEmail().equals(administrator.getEmail())) {
                administratorToRemove = a;
                System.out.println(administratorToRemove);
                break;
            }
        }

        if (administratorToRemove != null) {
            System.out.println("Vrednost izbacivanja iz liste " + loggedInAdministrators.remove(administratorToRemove));
            System.out.println("Izbacio sam menadzera u serveru");
            brojac--;
        } else {
            System.out.println("Administrator nije pronađen u listi");
        }
    }
    
    public boolean start() {
        try {
            serverSocket = new ServerSocket(port);
            running = true;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void stop() {
        running = false;
        for (ClientHandler handler : clientHandlers) {
            //da bi pozatvarao forme
            handler.close();
        }
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void listen() {
        try {
            while (running) {
                System.out.println("Waiting for clients...");
                try {
                    Socket clientSocket = serverSocket.accept();
                    if (brojac < maxClients) {
                        ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                        clientHandlers.add(clientHandler);
                        brojac++;
                        new Thread(clientHandler).start();
                        System.out.println("Client connected.");
                    } else {
                        clientSocket.close();
                        System.out.println("Maximum number of clients reached. Connection refused.");
                    }
                } catch (IOException e) {
                    if (running) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     public void removeClientHandler(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
    }
     public static void main(String[] args) {
        try {
            String propertiesFilePath = "src/properties/database_properties.properties";
            System.out.println("Attempting to load properties file from: " + propertiesFilePath);

            ServerConfig config = new ServerConfig(propertiesFilePath);
            System.out.println("Loaded properties file successfully.");

            Server server = new Server(config);
            if (server.start()) {
                System.out.println("Server started.");
                server.listen();
            } else {
                System.out.println("Failed to start the server.");
            }
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("General exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
