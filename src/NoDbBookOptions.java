import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @Description Code that uses ArrayList of Book objects. All book data is lost once program ends.
 */
public class NoDbBookOptions {
    /**
     * @Description Creates and returns a book object with user input
     * @return Book
     */
    public Book addBook(){
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
    public void deleteBook(ArrayList<Book> list){
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
    public void updateStock(ArrayList<Book> books){
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
    public void printBooks(ArrayList<Book> books){
        System.out.println("\nHere are all the books in the registry: ");
        for(Book bookData : books){
            printBook(bookData);
        }
    }

    /**
     * @Description Prints attribute values of a book object
     * @param bookData
     */
    public void printBook(Book bookData){
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
    public Book searchBook(ArrayList<Book> list){
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
    public Book findBook(ArrayList<Book> list){
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
