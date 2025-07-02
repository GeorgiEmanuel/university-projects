package Presentation;

import BusinessLogic.ClientBLL;
import BusinessLogic.OrderBLL;
import BusinessLogic.ProductBLL;
import Model.Client;
import Model.Orders;
import Model.Product;
import Tools.TableReflection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Class responsible for coordinating the interaction between the graphical user interface
 * and the business logic layer.
 *<p>
 * The controller sets up action listeners for buttons and updates the interface based on the
 * operations performed.
 * </p>
 */
public class Controller {
    private View view;
    private ProductBLL productBLL;
    private OrderBLL orderBLL;
    private ClientBLL clientBLL;

    /**
     * Construct a Controller and links the graphical user interface with the business logic.
     *
     * @param view
     */
    public Controller(View view) {
        this.view = view;
        this.productBLL = new ProductBLL();
        this.clientBLL = new ClientBLL();

        this.orderBLL = new OrderBLL(productBLL.getProductList(), clientBLL.getClientList());

        this.view.addActionListenerViewAllClientsButton(new AddViewAllClientsActionListener());
        this.view.addActionListenerAddClientButton(new AddAddClientActionListener());
        this.view.addActionListenerEditClientButton(new AddClientEditActionListener());
        this.view.addActionListenerDeleteClientButton(new AddDeleteClientActionListener());

        this.view.addActionListenerViewAllProductsButton(new AddViewProductsActionListener());
        this.view.addActionListenerAddProductButton(new AddProductActionListener());
        this.view.addActionListenerEditProductButton(new AddEditProductActionListener());
        this.view.addActionListenerDeleteProductButton(new AddDeleteProductActionListener());

        this.view.addActionListenerPlaceOrderButton(new AddPlaceOrderActionListener());
        this.view.addActionListenerViewAllOrdersButton(new AddViewOrdersActionListener());
        initialize();
    }

    /**
     * Initializes the combo boxes with the updated list of clients and products.
     * Used in constructor and for updates when a new client or product is inserted.
     */
    private void initialize() {
        List<Client> clientList = clientBLL.getClientList();
        List<Product> productList = productBLL.getProductList();
        for(Client client : clientList){
            view.getClientComboBox().addItem(Integer.toString(client.getId()));
        }
        for(Product product: productList){
            view.getProductComboBox().addItem(Integer.toString(product.getId()));
        }
    }

    /**
     * Clears the combo boxes and repopulates them.
     */
    private void updateIds() {
        List<Client> clientList = clientBLL.getClientList();
        List<Product> productList = productBLL.getProductList();
        JComboBox<String> clientComboBox = view.getClientComboBox();
        JComboBox<String> productComboBox = view.getProductComboBox() ;

        clientComboBox.removeAllItems();
        productComboBox.removeAllItems();

        initialize();

    }
    /** Updates the client table in the graphical user interface with refreshed client data **/
    private void updateClientGuiTable() {
        List<Client> clientList = clientBLL.getClientList();
        TableReflection.populateTable(view.getClientTable(), clientList);


    }

    /** Updates the product table in the graphical user interface with refreshed product data **/
    private void updateProductGuiTable() {
        List<Product> productList = productBLL.getProductList();
        TableReflection.populateTable(view.getProductTable(), productList);
    }

    /** Updates the order table in the graphical user interface with refreshed order data **/
    private void updateOrderGuiTable() {
        List<Orders> orderList = orderBLL.getOrderList();
        TableReflection.populateTable(view.getOrderTable(), orderList);
    }

    /** Resets the graphical user interface text fields */
    private void refreshFields(JTextField field1, JTextField field2, JTextField field3, JTextField field4) {
        field1.setText("");
        field2.setText("");
        field3.setText("");
        field4.setText("");

    }

    // Action listeners

    private class AddViewAllClientsActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateClientGuiTable();
        }
    }

    private class AddAddClientActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JTextField idField = view.getClientIdTextField();
                JTextField nameField = view.getClientNameTextField();
                JTextField emailField = view.getClientEmailTextField();
                JTextField addressField = view.getClientAddressTextField();
                clientBLL.addClient(idField.getText(), nameField.getText(),
                        emailField.getText(), addressField.getText());
                JOptionPane.showMessageDialog(view, "Client added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateClientGuiTable();
                refreshFields(idField, nameField, emailField, addressField);
                updateIds();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class AddClientEditActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JTextField idField = view.getClientIdTextField();
                JTextField nameField = view.getClientNameTextField();
                JTextField emailField = view.getClientEmailTextField();
                JTextField addressField = view.getClientAddressTextField();
                clientBLL.editClient(idField.getText(), nameField.getText(),
                        emailField.getText(), addressField.getText());
                JOptionPane.showMessageDialog(view, "Client edited successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateClientGuiTable();
                refreshFields(idField, nameField, emailField, addressField);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class AddDeleteClientActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JTextField idField = view.getClientIdTextField();
                clientBLL.deleteClient(idField.getText());
                JOptionPane.showMessageDialog(view, "Client deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateClientGuiTable();
                idField.setText("");
                updateIds();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class AddProductActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JTextField idField = view.getProductIdTextField();
                JTextField titleField = view.getProductTitleTextField();
                JTextField priceField = view.getProductPriceTextField();
                JTextField quantityField = view.getProductQuantityTextField();

                productBLL.addProduct(idField.getText(), titleField.getText(), priceField.getText(), quantityField.getText());
                JOptionPane.showMessageDialog(view, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateProductGuiTable();
                refreshFields(idField, titleField, priceField, quantityField);
                updateIds();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class AddEditProductActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JTextField idField = view.getProductIdTextField();
                JTextField titleField = view.getProductTitleTextField();
                JTextField priceField = view.getProductPriceTextField();
                JTextField quantityField = view.getProductQuantityTextField();

                productBLL.editProduct(idField.getText(), titleField.getText(), priceField.getText(), quantityField.getText());
                JOptionPane.showMessageDialog(view, "Product edited successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateProductGuiTable();

                refreshFields(idField, titleField, priceField, quantityField);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class AddDeleteProductActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JTextField idField = view.getProductIdTextField();
                productBLL.deleteProduct(idField.getText());
                JOptionPane.showMessageDialog(view, "Product deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateProductGuiTable();
                idField.setText("");
                updateIds();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private class AddViewProductsActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateProductGuiTable();

        }
    }
    private class AddPlaceOrderActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String orderId = view.getOrderIdTextField().getText();
            String clientId = (String)view.getClientComboBox().getSelectedItem();
            String productId = (String)view.getProductComboBox().getSelectedItem();
            String productQuantity = view.getOrderQuantityTextField().getText();

            try {
                orderBLL.addOrder(orderId, clientId, productId, productQuantity);
                JOptionPane.showMessageDialog(view, "Order added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            updateOrderGuiTable();

        }
    }
    private class AddViewOrdersActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateOrderGuiTable();
        }
    }

}
