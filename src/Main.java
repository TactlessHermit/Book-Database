import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        System.out.println("WELCOME");
        //runOption();
        dbTest();
    }

    public static void dbTest(){
        ConnectDB obj = new ConnectDB();
        Book data = new Book("CHERUB", "Dan Hodge", 20, 10.99);
        String query = obj.insertQuery(data);

        //INSERT QUERY
        //obj.connectToDb(query);
        //obj.
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
                System.out.println("Enter '3' to search for a book");
                System.out.println("Enter '4' to delete a book");
                System.out.println("Enter '5' to see all books");
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

    public static void runOption(){
        /*** Local Variables ***/
        boolean notDone = true;
        int option = -5;
        ArrayList<Book> books = new ArrayList<Book>();

        while(notDone){
            option = menuOptions();
            switch(option){
                case 1:
                    books.add(addBook());
                    break;
                case 2:
                    updateStock(books);
                    break;
                case 3:
                    searchBook(books);
                    break;
                case 4:
                    deleteBook(books);
                    break;
                case 5:
                    printBooks(books);
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
     * @Description Creates and returns a book object with user input
     * @return Book
     */
    public static Book addBook(){
        /*** Local Variables ***/
        Book bookData;
        String title;
        String author;
        double price = 0.00;
        int stock = 0;
        boolean valid = false;
        Scanner sc = new Scanner(System.in);

        //Get book title
        System.out.println("\nEnter book title: ");
        title = sc.nextLine();

        //Get book author
        System.out.println("Enter book author: ");
        author = sc.nextLine();

        //Get book price
        while(!valid){
            try{
                System.out.println("Enter book price: ");
                price = sc.nextDouble();
                sc.nextLine(); //Handle '\n'
                valid = true;
            }catch(InputMismatchException e){
                System.out.println("\nERROR:: Enter a valid decimal (e.g: 9.99)!!\n");
                sc.nextLine(); //Handle '\n'
            }
        }
        valid = false;  //Reset valid

        //Get book stock
        while(!valid){
            try{
                System.out.println("Enter book stock: ");
                stock = sc.nextInt();
                valid = true;
            }catch(InputMismatchException e){
                System.out.println("\nERROR:: Enter a numeric value!!\n");
                sc.nextLine(); //Handle '\n';
            }
        }

        //Create book object
        bookData = new Book(title, author, stock, price);

        return bookData;
    }

    /**
     * @Description Searches for and deletes a Book object from a Book arrayList via its ID attribute
     * @param list
     */
    public static void deleteBook(ArrayList<Book> list){
        /*** Local Variables ***/
        Book bookData;

        //Find book
        bookData = searchBook(list);

        //Delete book
        if(list.remove(bookData)){
            System.out.println("\nNOTE:: Book removed from registry.\n");
        }
    }

    /**
     * @Description Searches for and updates a Book object's stock attribute using its ID attri.
     * @param books
     */
    public static void updateStock(ArrayList<Book> books){
        /*** Local Variables ***/
        int newStock = 0;
        Scanner kb = new Scanner(System.in);
        Book bookData;
        boolean valid = false;

        //Find book
        bookData = searchBook(books);

        //Show returned book details
        printBook(bookData);

        //Get new stock
        System.out.println("\nEnter new stock: ");
        while(!valid){
            try{
                newStock = kb.nextInt();
                kb.nextLine(); //Handle '\n'
                valid = true;
            }catch(InputMismatchException e){
                System.out.println("\nERROR:: Enter a numeric value!!\n");
            }
        }

        //Update stock
        bookData.setStock(newStock);
    }

    /**
     * @Description Calls printBook() to print all Book objects in a Book arraylist
     * @param books
     */
    public static void printBooks(ArrayList<Book> books){
        System.out.println("\nHere are all the books in the registry: ");
        for(Book bookData : books){
            printBook(bookData);
        }
    }

    /**
     * @Description Prints attribute values of a book object
     * @param bookData
     */
    public static void printBook(Book bookData){
        System.out.println("Book ID: " + bookData.getID());
        System.out.println("Book Title: " + bookData.getTitle());
        System.out.println("Book Author: " + bookData.getAuthor());
        System.out.println("Book Price: " + bookData.getPrice());
        System.out.println("Book Stock: " + bookData.getStock() + "\n");
    }

    /**
     * @Description Calls findBook() to search for book object
     * @param list
     * @return Book
     */
    public static Book searchBook(ArrayList<Book> list){
        /**** Local Variables ****/
        Book resultBook = findBook(list);

        if(resultBook == null){
            System.out.println("\nNOTE:: Couldn't find a book with that ID.\n");
        }else{
            System.out.println("\nFound book: ");
            printBook(resultBook);
        }

        return resultBook;
    }

    /**
     * @Description Searches for a book object within the arraylist by its ID attribute
     * @param list
     * @return Book
     */
    public static Book findBook(ArrayList<Book> list){
        /*** Local Variables ***/
        int ID = -1;
        Book resultBook = null;
        boolean valid = false;
        Scanner kb = new Scanner(System.in);

        System.out.println("\nEnter book ID: ");
        while(!valid){
            try{
                ID = kb.nextInt();
                valid = true;
            }catch(InputMismatchException e){
                System.out.println("\nERROR:: Enter a numeric value!!\n");
            }
        }

        for(Book bookData : list){
            if(bookData. getID() == ID){
                resultBook = bookData;
                break;
            }
        }

        return resultBook;
    }
}