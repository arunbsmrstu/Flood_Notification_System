/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flood_notification_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class Flood_Notification_System extends Thread {
    
  Notification nf = new Notification();
 

    public int signal = 1;

    Flood_Notification_System() {

        start();
    }

    public void run() {
        if (signal == 1) {
            while (true) {
                Data d = new Data();

                try {
                    this.sleep(1000);
                    d.send();

                } catch (InterruptedException ex) {
                }
            }

        } else {

            while (true) {
                try {
                    this.sleep(999999);
                    Data d = new Data();
                    d.send();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Flood_Notification_System.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Flood_Notification_System fns = new Flood_Notification_System();
        Home h = new Home();
        h.show();
    }

    static void send_message() {

    }

}

class Data {

    public Statement statement = null;
    public ResultSet resultset = null;
    Connection con;

    public int send() {
        String host = "jdbc:mysql://localhost/fns";
        String uName = "root";
        String uPass = "";
        int sens = 0;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(host, uName, uPass);

            Statement stat = con.createStatement();

            String s = "SELECT * FROM residents;";
            stat.execute(s);

            ResultSet rs = stat.executeQuery(s);

            resultset = rs;
            statement = stat;

            while (rs.next()) {
                String nmae = rs.getString("name");
                String pnum = rs.getString("pnum");

                System.out.println("message send to " + nmae + " ; phone number " + pnum);
            }
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("\n");
        return sens;
    }

}
