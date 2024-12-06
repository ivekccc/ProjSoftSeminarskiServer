/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author x
 */
public class ServerConfig {
    private String url;
    private String username;
    private String password;
    private int maxUsers;
    private int port;
    Properties properties;
    
    
    
    public ServerConfig(String propertiesFilePath) throws IOException {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(propertiesFilePath)) {
            properties.load(fis);
            this.url = properties.getProperty("url");
            this.username = properties.getProperty("username");
            this.password = properties.getProperty("password");
            this.maxUsers = Integer.parseInt(properties.getProperty("max_users"));
            this.port = Integer.parseInt(properties.getProperty("port"));
        }
        System.out.println("Loaded properties file successfully.");
        System.out.println("URL: " + url);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }
    
    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public int getPort() {
        return port;
    }
    
}
