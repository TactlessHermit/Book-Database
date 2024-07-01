

public class Book {
    private int ID;
    private String title;
    private String author;
    private int stock;
    private double price;
    private static int counter = 1200;

    public Book(String title, String author, int stock, double price){
        this.ID = counter++;
        this.title = title;
        this.author = author;
        this.stock = stock;
        this.price = price;
    }

    public Book(){
        this.ID = counter++;
    }

    public int getID(){
        return this.ID;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getAuthor(){
        return this.author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public int getStock(){
        return this.stock;
    }

    public void setStock(int stock){
        this.stock = stock;
    }

    public double getPrice(){
        return this.price;
    }

    public void setPrice(Double price){
        this.price = price;
    }
}
