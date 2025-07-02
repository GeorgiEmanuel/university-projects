package Model;
/**
 * Represents a bill with details about an order.
 *
 * @param id       the order ID
 * @param name     the client name
 * @param title    the product title
 * @param quantity the quantity of the product ordered
 */
public record Bill(int id, String name, String title, int quantity) {
}
