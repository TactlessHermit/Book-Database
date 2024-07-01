import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

/**
 * @Description Code to work with user created postgres database and 'books' table
 */
public class DbBookOptions {
    static String tableName = "";

    /**
     * @Description Reads table name for queries from database.properties
     */
    public void getTableName(){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("database.properties");
        Properties propFile = new Properties();

        try {
            propFile.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        tableName = propFile.getProperty("db.tableName");
    }

    /**
     * @Description Runs SELECT queries
     * @param query
     * @param con
     */
    public void runSelectQuery(String query, Connection con){
        /****   LOCAL VARIABLES ****/
        int noOfRows = 0;

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
                noOfRows++;
            }

            if(noOfRows == 0){
                System.out.println("NOTE:: Query returned no results...");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description Runs query to insert a book into table
     */
    public void runInsertQuery(Book book, Connection con){
        /****   LOCAL VARIABLES ****/
        String query = "INSERT INTO books (id,title,author,stock,price) VALUES(?,?,?,?,?)";

        try {
            PreparedStatement prepStmt = con.prepareStatement(query);
            prepStmt.setInt(1, book.getID());
            prepStmt.setString(2, book.getTitle());
            prepStmt.setString(3, book.getAuthor());
            prepStmt.setInt(4, book.getStock());
            prepStmt.setDouble(5, book.getPrice());
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description Runs query to delete a book from table via its ID
     */
    public void runDeleteQuery(Connection con){
        /****   LOCAL VARIABLES ****/
        int id = getIntInput("Book ID");
        String query = "DELETE from books WHERE id = ?";

        try {
            PreparedStatement prepStmt = con.prepareStatement(query);
            prepStmt.setInt(1,id);
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description Runs query to update the stock of a book
     * @param con
     */
    public void runUpdateQuery(Connection con){
        /****   LOCAL VARIABLES ****/
        int id = getIntInput("Book ID");
        int stock = getIntInput("updated stock");
        String query = "UPDATE books SET stock = ? WHERE id = ?";

        try {
            PreparedStatement prepStmt = con.prepareStatement(query);
            prepStmt.setInt(1, stock);
            prepStmt.setInt(2, id);
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description Runs query to get a specific book from table
     * @param con
     */
    public void runSelectByIdQuery(Connection con){
        /****   LOCAL VARIABLES ****/
        int id = getIntInput("Book ID");
        String query = "SELECT * FROM books WHERE id = ?";

        try {
            PreparedStatement prepStmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            prepStmt.setInt(1, id);
            ResultSet rs = prepStmt.executeQuery();

            if(rs.next()){
                rs.beforeFirst();
                while (rs.next()) {
                    //Print each row data
                    System.out.println("BOOK:: " + rs.getString("title"));
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Author: " + rs.getString("author"));
                    System.out.println("Price: " + rs.getDouble("price"));
                    System.out.println("Stock: " + rs.getInt("stock") + "\n");
                }
            }else{
                System.out.println("NOTE:: Query returned no results.\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description Runs query to get all books by an author
     * @param con
     */
    public void runSelectByAuthorQuery(Connection con){
        /****   LOCAL VARIABLES ****/
        String author = "";
        Scanner kb = new Scanner(System.in);
        String query = "SELECT * FROM books WHERE author = ?";

        try {
            System.out.println("Enter author name: ");
            author = kb.nextLine();

            PreparedStatement prepStmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            prepStmt.setString(1, author);
            ResultSet rs = prepStmt.executeQuery();

            if(rs.next()){
                rs.beforeFirst();
                while (rs.next()) {
                    //Print each row data
                    System.out.println("BOOK:: " + rs.getString("title"));
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Author: " + rs.getString("author"));
                    System.out.println("Price: " + rs.getDouble("price"));
                    System.out.println("Stock: " + rs.getInt("stock") + "\n");
                }
            }else{
                System.out.println("NOTE:: Query returned no results.\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description Runs query to get all books from table
     * @param con
     */
    public void runSelectAllQuery(Connection con){
        /****   LOCAL VARIABLES ****/
        String query = "SELECT * FROM books";

        try {
            PreparedStatement prepStmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = prepStmt.executeQuery();

            if(rs.next()){
                rs.beforeFirst();
                while (rs.next()) {
                    //Print each row data
                    System.out.println("BOOK:: " + rs.getString("title"));
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Author: " + rs.getString("author"));
                    System.out.println("Price: " + rs.getDouble("price"));
                    System.out.println("Stock: " + rs.getInt("stock") + "\n");
                }
            }else{
                System.out.println("NOTE:: Query returned no results.\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description Prompts user to enter specific int value
     * @return int
     */
    public int getIntInput(String inputName){
        /****   LOCAL VARIABLES ****/
        int num = -1;
        boolean valid = false;
        Scanner kb = new Scanner(System.in);

        while (!valid){
            try {
                System.out.println("Enter " +inputName+ ": ");
                num = kb.nextInt();
                kb.nextLine();  //Bypass dangling newline
                valid = true;
            }catch (InputMismatchException e){
                System.out.println("ERROR:: Enter valid numeric input!!\n");
            }
        }

        return num;
    }
}
