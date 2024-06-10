import java.sql.*;

public class ConnectDB {
    static final String DB_URL = "smeich.com:8090";
    static final String USER = "smei_alfy";
    static final String PASS = "alfy123";
    // /Users/elkhaamil/Downloads com.mysql.jdbc.Driver

    /**
     * @Description Establishes connection to a database
     * @return Connection
     */
    public Connection connectToDb(){
        /****   LOCAL VARIABLES ****/
        Connection conn = null;

        // Open a connection
        try{
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    /**
     * @Description Runs update query (INSERT, UPDATE, DELETE) in connected database
     * @param query
     * @param con
     */
    public void runUpdateQuery(String query, Connection con){
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void runSelectQuery(String query, Connection con){
        try {
            Statement stmt = con.createStatement();
            ResultSet rs =  stmt.executeQuery(query);

            while (rs.next()) {
                //Print each row data
                System.out.println("BOOK:: " + rs.getString("title"));
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Author: " + rs.getString("author"));
                System.out.println("Price: " + rs.getDouble("price"));
                System.out.println("Stock: " + rs.getInt("stock") + "\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description Returns query string to insert a Book object into table
     * @param bookData
     * @return String
     */
    public String insertQuery(Book bookData){
        String query = "INSERT INTO bookStock VALUES (" + bookData.getID() + ", " + bookData.getTitle() +
                ", " + bookData.getAuthor() + ", " + bookData.getPrice() + ", " + bookData.getStock() + ")";
        return query;
    }

    /**
     * @Description Returns query string to get all table entries
     * @return String
     */
    public String selectAll(){
        String query = "SELECT * FROM bookStock";

        return query;
    }

    /**
     * @Description Returns query string to select all table entries with the specified author
     * @param author
     * @return String
     */
    public String selectAuthor(String author){
        String authorString = '\'' + author + '\'';
        String query = "SELECT * FROM bookStock WHERE author=" + authorString;

        return query;
    }
}
