/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;
import domain.AbstractDomainObject;
import domain.Administrator;
import domain.Korisnik;
import domain.Rekvizit;
import domain.Termin;
import domain.TipRekvizita;
import domain.Zaduzenje;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author x
 */
public class DBBroker {
    private static DBBroker instance;
    private Connection connection;
    
    private DBBroker() {
        System.out.println("Usli smo u kontruktor brokera");
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/properties/database_properties.properties")); // Putanja do tvog property fajla
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            
            System.out.println("url: " + url + " username: " + username + " password: " + password);
            System.out.println("Connecting to the database...");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database successfully.");
            connection.setAutoCommit(false);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public static DBBroker getInstance() {
        if (instance == null) {
            instance = new DBBroker();
        }
        return instance;
    }

    public ArrayList<AbstractDomainObject> select(AbstractDomainObject ado) throws SQLException {
        String upit = "SELECT * FROM " + ado.nazivTabele() + " " + ado.alijas() + " " + ado.join() + " " + ado.uslov();
        System.out.println(upit);
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(upit);
        return ado.vratiListu(rs);
    }
    
    public ArrayList<AbstractDomainObject> bezuslovniSelect(AbstractDomainObject ado) throws SQLException {
        String upit = "SELECT * FROM " + ado.nazivTabele() + " " + ado.alijas() + " " + ado.join();
        System.out.println(upit);
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(upit);
        return ado.vratiListu(rs);
    }

    public PreparedStatement insert(AbstractDomainObject ado) throws SQLException {
        String upit = "INSERT INTO " + ado.nazivTabele() + " " + ado.koloneZaInsert() + " VALUES" + ado.vrednostiZaInsert() + "";
        System.out.println(upit);
        PreparedStatement ps = connection.prepareStatement(upit, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
        return ps;
    }

    public void update(AbstractDomainObject ado) throws SQLException {
        String upit = "UPDATE " + ado.nazivTabele() + " SET " + ado.vrednostiZaUpdate() + " WHERE " + ado.vrednostZaPrimarniKljuc();
        System.out.println(upit);
        Statement s = connection.createStatement();
        s.executeUpdate(upit);
    }

    public void delete(AbstractDomainObject ado) throws SQLException {
        String upit = "DELETE FROM " + ado.nazivTabele() + " WHERE " + ado.vrednostZaPrimarniKljuc();
        System.out.println(upit);
        Statement s = connection.createStatement();
        s.executeUpdate(upit);
    }
    
    public int selectCount(AbstractDomainObject ado) throws SQLException {
        String upit = "SELECT COUNT(*) FROM " + ado.nazivTabele() + " " + ado.alijas() + " " + ado.join() + " " + ado.uslov();
        System.out.println(upit);
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(upit);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
    
    public void deleteWithCondition(Class<? extends AbstractDomainObject> clazz, String condition) throws Exception {
        String tableName = clazz.newInstance().nazivTabele();
        String query = "DELETE FROM " + tableName + " WHERE " + condition;
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
    }

    public void updateWithCondition(AbstractDomainObject ado, String condition) throws SQLException {
    String upit = "UPDATE " + ado.nazivTabele() + " SET " + ado.vrednostiZaUpdate() + " WHERE " + condition;
    System.out.println(upit);
    Statement s = connection.createStatement();
    s.executeUpdate(upit);
}
    

    

    
    
    
}
