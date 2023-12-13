import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;

public class Order {
    private String orderID;
    private ArrayList<OrderDetails> orderDetails;
    private static int lastOrderID;

    public Order(String orderID, ArrayList<OrderDetails> orderDetails) {
        this.orderID = orderID;
        this.orderDetails = orderDetails;
    }

    public Order() { this("", new ArrayList<>()); }

    public String getOrderID() { return orderID; }
    public ArrayList<OrderDetails> getOrderDetails() { return orderDetails; }
    public static int getLastOrderID() {return lastOrderID;}

    public void setOrderID(String orderID) { this.orderID = orderID; }
    public void setOrderDetails(ArrayList<OrderDetails> orderDetails) { this.orderDetails = orderDetails; }
    public static void setLastOrderID(int lastOrderID) {Order.lastOrderID = lastOrderID;}

    public static Order addOrder(ArrayList<OrderDetails> orderDetails) throws FileNotFoundException, ParseException {
        return new Order("FDD" + lastOrderID, HostSystem.orderMenu(orderDetails));
    }
}
