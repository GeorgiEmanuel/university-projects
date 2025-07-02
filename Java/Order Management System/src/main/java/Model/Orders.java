package Model;

public class Orders {

    /** Primary key. */

    private int id;

    /** Client's id. */
    private int idClient;

    /** Product's id. */
    private int idProduct;

    /** Product's required quantity. */
    private int quantity;

    /** No arguments constructor */
    public Orders(){

    }

    /**
     * Full constructor.
     *
     * @param id Order's id
     * @param idClient  Client's id
     * @param idProduct  Product's id
     * @param quantity  Product's available quantity
     */
    public Orders(int id, int idClient, int idProduct, int quantity) {
        this.id = id;
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
