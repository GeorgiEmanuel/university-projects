package Presentation;

import Model.AppModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Class responsible for the graphical user interface of the Orders Management Application.
 */
public class View extends JFrame {

    private AppModel model;

    private JPanel contentPane;
    private CardLayout cardLayout;

    private JPanel clientPanel;
    private JPanel productPanel;
    private JPanel orderPanel;

    private JTextField clientIdTextField;
    private JTextField clientNameTextField;
    private JTextField clientEmailTextField;
    private JTextField clientAddressTextField;
    private JTextField orderIdTextField;

    private JTextField productIdTextField;
    private JTextField productTitleTextField;
    private JTextField productPriceTextField;
    private JTextField productQuantityTextField;
    private JTextField orderQuantityTextField;

    private JButton addClientButton;
    private JButton editClientButton;
    private JButton deleteClientButton;
    private JButton viewAllClientsButton;

    private JButton addProductButton;
    private JButton editProductButton;
    private JButton deleteProductButton;
    private JButton viewAllProductsButton;

    private JButton placeOrderButton;
    private JButton viewAllOrdersButton;

    private JTable productTable;
    private JTable clientTable;
    private JTable orderTable;

    private JComboBox<String> clientComboBox;
    private JComboBox<String> productComboBox;

     /**
     * Constructs the main view of the app.
     * Initializes the graphical user interface components and links the model.
     *
     * @param model Application model for business logic
     */
    public View(AppModel model) {
        this.model = model;
        prepareGui();
    }

    /**
     * Prepares the main window and panels.
     */
    private void prepareGui() {
        setTitle("Orders Management Application");
        setSize(1400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contentPane = new JPanel(cardLayout);
        setContentPane(contentPane);

        prepareMenu();
        prepareClientPanel();
        prepareProductPanel();
        prepareOrderPanel();
        setVisible(true);
    }

    /**
     * Constructs the menu bar with options to switch between views.
     * The menu contains menu item for Client, Product, Orders.
     */
    private void prepareMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu navigationMenu = new JMenu("Menu");

        JMenuItem clientItem = new JMenuItem("Client");
        JMenuItem productItem = new JMenuItem("Product");
        JMenuItem orderItem = new JMenuItem("Orders");

        clientItem.addActionListener(e -> cardLayout.show(contentPane, "Client"));
        productItem.addActionListener(e -> cardLayout.show(contentPane, "Product"));
        orderItem.addActionListener(e -> cardLayout.show(contentPane, "Orders"));

        navigationMenu.add(clientItem);
        navigationMenu.add(productItem);
        navigationMenu.add(orderItem);

        menuBar.add(navigationMenu);
        setJMenuBar(menuBar);
    }

    /**
     * Responsible for initializing the client panel with text fields and buttons.
     * Used to perform CRUD operations.
     * Includes a table to display the clients.
     */
    private void prepareClientPanel() {
        clientPanel = new JPanel(new BorderLayout());

        JPanel fieldsPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        clientIdTextField = new JTextField(10);
        clientNameTextField = new JTextField(10);
        clientEmailTextField = new JTextField(10);
        clientAddressTextField = new JTextField(10);

        fieldsPanel.add(new JLabel("Client ID:"));
        fieldsPanel.add(clientIdTextField);
        fieldsPanel.add(new JLabel("Name:"));
        fieldsPanel.add(clientNameTextField);
        fieldsPanel.add(new JLabel("Email:"));
        fieldsPanel.add(clientEmailTextField);
        fieldsPanel.add(new JLabel("Address:"));
        fieldsPanel.add(clientAddressTextField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        addClientButton = new JButton("Add Client");
        editClientButton = new JButton("Edit Client");
        deleteClientButton = new JButton("Delete Client");
        viewAllClientsButton = new JButton("View Clients");

        init(fieldsPanel, buttonPanel, addClientButton, editClientButton, deleteClientButton, viewAllClientsButton, clientPanel);

        clientTable = new JTable();
        clientPanel.add(new JScrollPane(clientTable), BorderLayout.CENTER);
        contentPane.add(clientPanel, "Client");
    }

    /**
     * Used to initialize panels with fields and buttons.
     *
     * @param fieldsPanel The panel containing the input fields
     * @param buttonPanel The panel containing the buttons
     * @param addButton Button used to add a new record
     * @param editButton Button used to update a record
     * @param deleteButton Button used to delete a record
     * @param viewAllButton Button used to display all records
     * @param panel
     */
    private void init(JPanel fieldsPanel, JPanel buttonPanel, JButton addButton, JButton editButton, JButton deleteButton, JButton viewAllButton, JPanel panel) {
        for (JButton btn : new JButton[]{addButton, editButton, deleteButton, viewAllButton}) {
            btn.setBackground(Color.LIGHT_GRAY);
            buttonPanel.add(btn);
        }

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(fieldsPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);


        panel.add(topPanel, BorderLayout.NORTH);

    }
    /**
     * Responsible for initializing the product panel with text fields and buttons.
     * Used to perform CRUD operations.
     * Includes a table to display the products.
     */
    private void prepareProductPanel() {
        productPanel = new JPanel(new BorderLayout());

        JPanel fieldsPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        productIdTextField = new JTextField(10);
        productTitleTextField = new JTextField(10);
        productPriceTextField = new JTextField(10);
        productQuantityTextField = new JTextField(10);

        fieldsPanel.add(new JLabel("Product ID:"));
        fieldsPanel.add(productIdTextField);
        fieldsPanel.add(new JLabel("Title:"));
        fieldsPanel.add(productTitleTextField);
        fieldsPanel.add(new JLabel("Price:"));
        fieldsPanel.add(productPriceTextField);
        fieldsPanel.add(new JLabel("Quantity:"));
        fieldsPanel.add(productQuantityTextField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        addProductButton = new JButton("Add Product");
        editProductButton = new JButton("Edit Product");
        deleteProductButton = new JButton("Delete Product");
        viewAllProductsButton = new JButton("View Products");

        init(fieldsPanel, buttonPanel, addProductButton, editProductButton, deleteProductButton, viewAllProductsButton, productPanel);

        productTable = new JTable();
        productPanel.add(new JScrollPane(productTable), BorderLayout.CENTER);
        contentPane.add(productPanel, "Product");
    }
    /**
     * Responsible for initializing the order panel with text fields and buttons.
     * Used to perform CRUD operations.
     * Includes a table to display the orders.
     */
    private void prepareOrderPanel() {
        orderPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());

        clientComboBox = new JComboBox();
        productComboBox = new JComboBox();
        orderIdTextField = new JTextField(5);
        orderQuantityTextField = new JTextField(5);
        placeOrderButton = new JButton("Place Order");
        viewAllOrdersButton = new JButton("View All Orders");

        clientComboBox.setBackground(Color.LIGHT_GRAY);
        productComboBox.setBackground(Color.LIGHT_GRAY);
        placeOrderButton.setBackground(Color.LIGHT_GRAY);
        viewAllOrdersButton.setBackground(Color.LIGHT_GRAY);


        inputPanel.add(new JLabel("Order ID:"));
        inputPanel.add(orderIdTextField);

        inputPanel.add(new JLabel("Select Client:"));
        inputPanel.add(clientComboBox);

        inputPanel.add(new JLabel("Select Product:"));
        inputPanel.add(productComboBox);

        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(orderQuantityTextField);

        inputPanel.add(placeOrderButton);
        inputPanel.add(viewAllOrdersButton);

        orderPanel.add(inputPanel, BorderLayout.NORTH);

        orderTable = new JTable();
        orderPanel.add(new JScrollPane(orderTable), BorderLayout.CENTER);

        contentPane.add(orderPanel, "Orders");
    }


    public AppModel getModel() {
        return model;
    }

    public void setModel(AppModel model) {
        this.model = model;
    }

    public JTextField getClientIdTextField() {
        return clientIdTextField;
    }

    public void setClientIdTextField(JTextField clientIdTextField) {
        this.clientIdTextField = clientIdTextField;
    }

    public JTextField getClientNameTextField() {
        return clientNameTextField;
    }

    public void setClientNameTextField(JTextField clientNameTextField) {
        this.clientNameTextField = clientNameTextField;
    }

    public JTextField getClientEmailTextField() {
        return clientEmailTextField;
    }

    public void setClientEmailTextField(JTextField clientEmailTextField) {
        this.clientEmailTextField = clientEmailTextField;
    }

    public JTextField getClientAddressTextField() {
        return clientAddressTextField;
    }

    public void setClientAddressTextField(JTextField clientAddressTextField) {
        this.clientAddressTextField = clientAddressTextField;
    }

    public JTextField getOrderIdTextField() {
        return orderIdTextField;
    }

    public void setOrderIdTextField(JTextField orderIdTextField) {
        this.orderIdTextField = orderIdTextField;
    }

    public JTextField getProductIdTextField() {
        return productIdTextField;
    }

    public void setProductIdTextField(JTextField productIdTextField) {
        this.productIdTextField = productIdTextField;
    }

    public JTextField getProductTitleTextField() {
        return productTitleTextField;
    }

    public void setProductTitleTextField(JTextField productTitleTextField) {
        this.productTitleTextField = productTitleTextField;
    }

    public JTextField getProductPriceTextField() {
        return productPriceTextField;
    }

    public void setProductPriceTextField(JTextField productPriceTextField) {
        this.productPriceTextField = productPriceTextField;
    }

    public JTextField getProductQuantityTextField() {
        return productQuantityTextField;
    }

    public void setProductQuantityTextField(JTextField productQuantityTextField) {
        this.productQuantityTextField = productQuantityTextField;
    }


    public void addActionListenerAddClientButton(ActionListener actionListener) {
        this.addClientButton.addActionListener(actionListener);
    }
    public void addActionListenerEditClientButton(ActionListener actionListener) {
        this.editClientButton.addActionListener(actionListener);
    }
    public void addActionListenerDeleteClientButton(ActionListener actionListener) {
        this.deleteClientButton.addActionListener(actionListener);
    }
    public void addActionListenerViewAllClientsButton(ActionListener actionListener) {
        this.viewAllClientsButton.addActionListener(actionListener);
    }

    public void addActionListenerAddProductButton(ActionListener actionListener) {
        this.addProductButton.addActionListener(actionListener);
    }
    public void addActionListenerEditProductButton(ActionListener actionListener) {
        this.editProductButton.addActionListener(actionListener);
    }
    public void addActionListenerDeleteProductButton(ActionListener actionListener) {
        this.deleteProductButton.addActionListener(actionListener);
    }
    public void addActionListenerViewAllProductsButton(ActionListener actionListener) {
        this.viewAllProductsButton.addActionListener(actionListener);
    }
    public void addActionListenerPlaceOrderButton(ActionListener actionListener) {
        this.placeOrderButton.addActionListener(actionListener);
    }
    public void addActionListenerViewAllOrdersButton(ActionListener actionListener) {
        this.viewAllOrdersButton.addActionListener(actionListener);
    }
    public JTable getProductTable() {
        return productTable;
    }

    public void setProductTable(JTable productTable) {
        this.productTable = productTable;
    }

    public JTable getClientTable() {
        return clientTable;
    }

    public void setClientTable(JTable clientTable) {
        this.clientTable = clientTable;
    }

    public JComboBox<String> getClientComboBox() {
        return clientComboBox;
    }

    public void setClientComboBox(JComboBox<String> clientComboBox) {
        this.clientComboBox = clientComboBox;
    }

    public JComboBox<String> getProductComboBox() {
        return productComboBox;
    }

    public void setProductComboBox(JComboBox<String> productComboBox) {
        this.productComboBox = productComboBox;
    }

    public JTable getOrderTable() {
        return orderTable;
    }

    public void setOrderTable(JTable orderTable) {
        this.orderTable = orderTable;
    }

    public JTextField getOrderQuantityTextField() {
        return orderQuantityTextField;
    }

    public void setOrderQuantityTextField(JTextField orderQuantityTextField) {
        this.orderQuantityTextField = orderQuantityTextField;
    }
}


