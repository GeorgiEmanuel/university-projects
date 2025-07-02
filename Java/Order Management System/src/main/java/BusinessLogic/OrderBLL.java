package BusinessLogic;

import DataAccess.BillDAO;
import DataAccess.OrderDAO;
import Model.Bill;
import Model.Client;
import Model.Orders;
import Model.Product;

import java.util.List;
/**
 * Business Logic Layer class responsible for managing order operations.
 * <p>
 * Provides methods to add and view orders
 * It sits between the presentation layer and the data access layer.
 *</p>
 *
 * @author Emanuel Georgi
 */
public class OrderBLL {

    private OrderDAO orderDAO;
    private List<Orders> orderList;
    private List<Product> productList;
    private List<Client> clientList;
    private BillDAO billDao;

    /**
     * Constructs an OrderBLL instance with the provided lists of products and clients.
     * Initializes the order lists with all orders from the database.
     *
     * @param productList Local list of products
     * @param clientList Local list of clients
     */
    public OrderBLL(List<Product> productList, List<Client> clientList ) {
        orderDAO = new OrderDAO();
        this.productList = productList;
        this.clientList = clientList;
        this.orderList = orderDAO.findAll();
        billDao = new BillDAO();
    }

    /**
     * Adds a new order to the database and to orders list if the input is valid.
     *
     * @param id Order's id as String
     * @param clientId Client's id as String
     * @param productId Product's id as String
     * @param quantity Product's quantity as String
     * @throws Exception if the input is invalid, the client or product was not found or the product's quantity exceeds the available stock
     */
    public void addOrder(String id, String clientId, String productId, String quantity) throws Exception {
        int orderId;
        String clientName = null;
        String productTitle = null;
        try{
            orderId = Integer.parseInt(id);
        }catch(Exception e){
            throw new Exception("Invalid order ID");
        }
        int clientIdInteger;
        try{
            clientIdInteger = Integer.parseInt(clientId);

        }catch (Exception e){
            throw new Exception("Invalid client ID");
        }
        int productIdInteger;
        try{
            productIdInteger = Integer.parseInt(productId);
        }catch (Exception e){
            throw new Exception("Invalid product ID");
        }
        int productQuantity;
        try{
            productQuantity = Integer.parseInt(quantity);
        }catch (Exception e){
            throw new Exception("Invalid product quantity");
        }
        ProductBLL productBLL = new ProductBLL();
        boolean foundProduct = false;
        boolean foundClient = false;
        for(Client client : clientList){
            if(clientIdInteger == client.getId()){
                clientName = client.getName();
                foundClient = true;
                break;
            }
        }
        if(!foundClient){
            throw new Exception("Client not found");
        }
        for(Product product : productList){
            if(product.getId() == productIdInteger){
                productTitle = product.getTitle();
                if(product.getQuantity() < productQuantity){
                    throw new Exception("Ordered quantity exceeds available stock");
                }
                else {
                    if(product.getQuantity() - productQuantity > 0){
                        product.setQuantity(product.getQuantity() - productQuantity);
                        productBLL.editProduct(Integer.toString(product.getId()), product.getTitle(),
                                Integer.toString(product.getPrice()), Integer.toString(product.getQuantity()));
                    }
                    else if(product.getQuantity() - productQuantity == 0) {
                        productBLL.deleteProduct(Integer.toString(product.getId()));
                        productList.remove(product);
                    }
                }
                foundProduct = true;
                break;
            }
        }
        if(!foundProduct){
            throw new Exception("Product not found");
        }
        else {
            Orders order = new Orders(orderId, clientIdInteger, productIdInteger, productQuantity);
            orderDAO.insert(order);
            orderList.add(order);
            Bill bill = new Bill(orderId, clientName, productTitle, productQuantity);
            billDao.insert(bill);
        }

    }

    /**
     * Sets the local list of orders.
     *
     * @param orderList Local list of orders to set
     */
    public void setOrderList(List<Orders> orderList) {
        this.orderList = orderList;
    }

    /**
     * Returns the local list of orders.
     *
     * @return List of orders
     */
    public List<Orders> getOrderList() {
        return orderList;
    }
}
