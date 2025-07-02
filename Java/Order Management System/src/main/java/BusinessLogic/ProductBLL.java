package BusinessLogic;

import DataAccess.ProductDAO;
import Model.Client;
import Model.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Business Logic Layer class responsible for managing product operations.
 * <p>
 * Provides methods to add, edit, delete, and retrieve products from the database.
 * It sits between the presentation layer and the data access layer.
 *</p>
 *
 * @author Emanuel Georgi
 */
public class ProductBLL {

    private ProductDAO productDAO;
    private List<Product> productList;

    /**
     * Constructs an ProductBLL instance.
     * Initializes the products list with all products from the database.
     */
    public ProductBLL() {
        productDAO = new ProductDAO();
        productList = productDAO.findAll();
    }

    /**
     * Retrieves all the products from the database.
     *
     * @return Local list of products from the database.
     */
    public List<Product> viewAllProducts() {
        return productList;
    }

    /**
     * Adds a new product to the database and products list.
     *
     * @param id Product's id
     * @param title Product's name
     * @param price Product's price
     * @param quantity Product available quantity
     * @throws Exception if the input is invalid or a products with the same ID already exists
     */
    public void addProduct(String id, String title, String price, String quantity) throws Exception{
        int productId;
        try {
            productId = Integer.parseInt(id);
        } catch (Exception e) {
            throw new Exception("Invalid product ID");
        }
        int productPrice;
        try {
            productPrice = Integer.parseInt(price);
        } catch (Exception e) {
            throw new Exception("Invalid product price");
        }
        int productQuantity;
        try {
            productQuantity = Integer.parseInt(quantity);
        } catch (Exception e) {
            throw new Exception("Invalid product quantity");
        }
        for(Product product : productList){
            if(productId == product.getId()){
                throw new Exception("Duplicate product ID");
            }
        }
        Product product = new Product(productId, title, productPrice, productQuantity);
        productDAO.insert(product);
        productList.add(product);
    }

    /**
     * Updates the product's data.
     *
     * @param id Product's id
     * @param title Product's name
     * @param price Product's price
     * @param quantity Product available quantity
     * @throws Exception if the input is invalid or the products is not found
     */
    public void editProduct(String id, String title, String price, String quantity) throws Exception {
        int productId;
        try {
            productId = Integer.parseInt(id);
        } catch (Exception e) {
            throw new Exception("Invalid product ID");
        }
        int productPrice;
        try {
            productPrice = Integer.parseInt(price);
        } catch (Exception e) {
            throw new Exception("Invalid product price");
        }
        int productQuantity;
        try {
            productQuantity = Integer.parseInt(quantity);
        } catch (Exception e) {
            throw new Exception("Invalid product quantity");
        }
        boolean found = false;
        for (Product product : productList) {
            if (product.getId() == productId) {
                found = true;
                product.setTitle(title);
                product.setPrice(productPrice);
                product.setQuantity(productQuantity);
                productDAO.update(product);
                break;
            }
        }
        if (!found) {
            throw new Exception("Product not found");
        }
    }

    /**
     * Removes a product from the database and products list.
     *
     * @param id Product's id
     * @throws Exception if the input is invalid or the product is not found
     */
    public void deleteProduct(String id) throws Exception {
        int productId;
        try {
            productId = Integer.parseInt(id);
        } catch (Exception e) {
            throw new Exception("Invalid product ID");
        }
        boolean found = false;
        Iterator<Product> iterator = productList.iterator();
        while(iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getId() == productId) {
                found = true;
                productDAO.delete(product);
                iterator.remove();
                break;
            }
        }
        if (!found) {
            throw new Exception("Product not found");
        }
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
