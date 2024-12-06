/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package properties;

import java.io.File;

/**
 *
 * @author x
 */
public class TestFilePath {
    public static void main(String[] args) {
        String propertiesFilePath = "C:\\Users\\x\\Desktop\\Projektovanje Softvera zavrsni rad\\ZavrsniServer\\src\\properties\\database_properties.properties";
        File file = new File(propertiesFilePath);

        if (file.exists()) {
            System.out.println("File exists: " + propertiesFilePath);
        } else {
            System.out.println("File does not exist: " + propertiesFilePath);
        }
    }
}
