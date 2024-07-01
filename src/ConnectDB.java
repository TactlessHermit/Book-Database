import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class ConnectDB {
    static String DB_URL = "";
    static String USER = "";
    static String PASS = "";


    /**
     * @Description Establishes connection to a database
     * @return Connection
     */
    public Connection connectToDb(){
        /****   LOCAL VARIABLES ****/
        Connection conn = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("database.properties");
        Properties propFile = new Properties();


        try {
            propFile.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DB_URL = propFile.getProperty("db.URL");
        USER = propFile.getProperty("db.username");
        PASS = propFile.getProperty("db.password");

        // Open a connection
        try{
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("ERROR:: Issue connecting to database.");
            e.printStackTrace();
        }

        return conn;
    }

}
