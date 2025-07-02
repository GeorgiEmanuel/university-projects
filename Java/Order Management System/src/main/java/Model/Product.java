package Model;

public class Product {

    /** Primary key */
    private int id;

    /** Product's name */
    private String title;

    /** Product's price */
    private int price;

    /** Product's available quantity. */
    private int quantity;

    /** No arguments constructor */
    public Product() {

    }

    /**
     * Full constructor.
     *
     * @param id  /** Product's unique identifier
     * @param title /** Product's name
     * @param price /** Product's price
     * @param quantity /** Product's available quantity
     */
    public Product(int id, String title, int price, int quantity) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;

    }

    public int getId() {
        return id;
    }

    public void setId(int idClient) {
        this.id = idClient;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     *  Returns a readable string for the product.
     *
     *
     *
     *
     * @return string with product's id, title, price and available quantity
     */
    public String toString(){
        return "Product [id=" + id + ", title=" + title + ", price=" + price + ", quantity=" + quantity + "]";
    }
}
