package Model;

/**
 * Represents a client.
 */
public class Client {

    /** Primary key. */
    private int id;

    /** Client's name. */
    private String name;

    /** Client's email. */
    private String email;

    /** Client's address. */
    private String address;

    /** No arguments constructor */
    public Client() {

    }

    /**
     * Full constructor.
     *
     * @param id Client's unique identifier
     * @param name Client's name
     * @param email Client's email
     * @param address Client's address
     */
    public Client(int id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }


    public int getId() {
        return id;
    }

    public void setId(int idClient) {
        this.id = idClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns a readable string for the client;
     *
     * @return string with client's id, name, email and address
     *
     */
    public String toString() {
        return "Client [idClient=" + id + ", name=" + name + ", email=" + email + ", address= " + address + "]";
    }


}
