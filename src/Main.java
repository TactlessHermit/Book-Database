import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("WELCOME");
        ConnectDB connectDB = new ConnectDB();
        Connection con = connectDB.connectToDb();
        noRunOption(con);
        //DbBookOptions dbBookOptions = new DbBookOptions();
        //dbBookOptions.runSelectAllQuery(con);


        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description Dispays program options and returns user choice via input
     * @return Integer
     */
    public static int menuOptions(){
        /*** Local variables ***/
        int option = -1;
        boolean valid = false;
        Scanner kb = new Scanner(System.in);

        while(!valid){
            try{
                //Print menu
                System.out.println("\nEnter '1' to add a book");
                System.out.println("Enter '2' to update a book's stock");
                System.out.println("Enter '3' to delete a book via its ID");
                System.out.println("Enter '4' to see all books");
                System.out.println("Enter '5' to search for a book via its ID");
                System.out.println("Enter '6' to search for all books by an author");
                System.out.println("Enter '0' to exit");
                option = kb.nextInt();
                valid = true;
            }catch(InputMismatchException e){
                System.out.println("\nERROR:: Enter a numeric value!!\n");
                kb.nextLine(); //Handle '\n'
            }
        }

        return option;
    }

    /**
     * @Description Runs code that works with arraylist of books
     */
    public static void runOption(){
        /*** Local Variables ***/
        boolean notDone = true;
        int option = -5;
        NoDbBookOptions bookOp = new NoDbBookOptions();
        ArrayList<Book> books = new ArrayList<>();

        while(notDone){
            option = menuOptions();
            switch(option){
                case 1:
                    books.add(bookOp.addBook());
                    break;
                case 2:
                    bookOp.updateStock(books);
                    break;
                case 3:
                    bookOp.searchBook(books);
                    break;
                case 4:
                    bookOp.deleteBook(books);
                    break;
                case 5:
                    bookOp.printBooks(books);
                    break;
                case 0:
                    notDone = false;
                    break;
                default:
                    System.out.println("\nERROR:: Invalid option. Must chose from specified number range.\n");
                    break;
            }
        }

        System.out.println("ENDING PROGRAM...");
        System.exit(0);
    }

    /**
     * @Description Runs code that works with Postgres Database
     * @param con
     */
    public static void noRunOption(Connection con){
        /****   LOCAL VARIABLES ****/
        boolean notDone = true;
        int option = -1;
        Book book;
        DbBookOptions dbBookOptions = new DbBookOptions();
        NoDbBookOptions bookOptions = new NoDbBookOptions();

        //Reads table name for queries from database.properties
        dbBookOptions.getTableName();

        while(notDone){
            option = menuOptions();

            switch (option){
                case 1: //INSERT BOOK
                    book = bookOptions.addBook();
                    dbBookOptions.runInsertQuery(book, con);
                    break;

                case 2: //UPDATE BOOK
                    dbBookOptions.runUpdateQuery(con);
                    break;

                case 3: //DELETE BOOK
                    dbBookOptions.runDeleteQuery(con);
                    break;

                case 4: //SEARCH ALL
                    dbBookOptions.runSelectAllQuery(con);
                    break;

                case 5: //SEARCH BY ID
                    dbBookOptions.runSelectByIdQuery(con);
                    break;

                case 6: //SEARCH BY AUTHOR
                    dbBookOptions.runSelectByAuthorQuery(con);
                    break;

                case 0: //EXIT PROGRAM
                    notDone = false;
                    System.out.println("NOTE:: TERMINATING PROGRAM...");
                    break;

                default:
                    System.out.println("ERROR:: Enter a valid option from those displayed!!\n");
                    break;
            }
        }
    }


}