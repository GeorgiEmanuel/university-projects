package BusinessLogic;

import DataAccess.ClientDAO;
import DataAccess.OrderDAO;
import DataAccess.ProductDAO;
import Model.Client;
import Model.Product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Business Logic Layer class responsible for managing client operations.
 * <p>
 * Provides methods to add, edit, delete, and retrieve clients from the database.
 * It sits between the presentation layer and the data access layer.
 *</p>
 *
 * @author Emanuel Georgi
 */
public class ClientBLL {
    private ClientDAO clientDAO;

    private List<Client> clientList;

    /**
     * Constructs an ClientBLL instance.
     * Initializes the clients list with all clients from the database.
     */

    public ClientBLL() {
        clientDAO = new ClientDAO();
        clientList = clientDAO.findAll();
    }

    /**
     * Adds a new client to the database and client list.
     *
     * @param id Client ID as String
     * @param name Client's name
     * @param email Client's email
     * @param address Client's address
     * @throws Exception if the input is invalid or a client with the same ID already exists
     */
    public void addClient(String id, String name, String email, String address) throws Exception {
        int clientId;
        try {
             clientId = Integer.parseInt(id);

        } catch (Exception e) {
            throw new Exception("Invalid client ID");
        }
        for(Client client : clientList) {
            if(clientId == client.getId()) {
                throw new Exception("Duplicate client ID");
            }
        }
        Client client = new Client(clientId, name, email, address);
        clientDAO.insert(client);
        clientList.add(client);
    }

    /**
     * Retrieves all the clients from the database.
     *
     * @return Local list of clients from the database.
     */
    public List<Client> viewAllClients() {
        return clientDAO.findAll();
    }

    /**
     * Updates the client's data.
     *
     * @param id Client ID as String
     * @param name Client's name
     * @param email Client's email
     * @param address Client's address
     * @throws Exception if the input is invalid or the client is not found
     */
    public void editClient(String id, String name, String email, String address) throws Exception {
        boolean found = false;
        int clientId;
        try {
             clientId = Integer.parseInt(id);

        } catch (Exception e) {
            throw new Exception("Invalid client ID");
        }
        for (Client client : clientList) {
            if (client.getId() == clientId) {
                client.setName(name);
                client.setEmail(email);
                client.setAddress(address);
                clientDAO.update(client);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new Exception("Client not found");
        }
    }

    /**
     * Removes a client from the database and client list.
     *
     * @param id Client ID as String
     * @throws Exception if the input is invalid or the client is not found
     */
    public void deleteClient(String id) throws Exception {
        boolean found = false;
        int clientId;
        try {
            clientId = Integer.parseInt(id);


        } catch (Exception e) {
            throw new Exception("Invalid client ID");

        }
        Iterator<Client> iterator = clientList.iterator();
        while (iterator.hasNext()) {
            Client client = iterator.next();
            if (client.getId() == clientId) {
                clientDAO.delete(client);
                iterator.remove();
                found = true;
                break;
            }
        }
        if (!found) {
            throw new Exception("Client not found");
        }
    }

    /**
     * Returns the current list of clients.
     *
     * @return Client's list
     */

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

}
