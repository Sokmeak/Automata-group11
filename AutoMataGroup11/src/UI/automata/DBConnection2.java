package UI.automata;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Admin
 */
public class DBConnection2 {

    static Connection con = null;
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/automata","root","sok1376@");
           
            } catch (Exception e) {
            e.printStackTrace();
        }
        
         return con;
        }

   
}
