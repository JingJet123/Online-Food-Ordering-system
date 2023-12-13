import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;

public class Dessert extends Product {

    public Dessert(String dessertID, String dessertName, double dessertPrice) {
        super(dessertID, dessertName, dessertPrice);
    }
    public Dessert() { super(); }

    @Override
    public Product getProdSelect(int selection) throws ParseException, FileNotFoundException {
        ArrayList<Dessert> dessertDetails = HostSystem.getDessertDetails();
        Product dessert = new Dessert();
        for (int j = 0; j < dessertDetails.size(); j++) {
            if (j == selection - 1) {
                dessert = dessertDetails.get(j);
            }
        }
        return dessert;
    }

    @Override
    public String toString() {
        return String.format("  | %4s%-8s |  %-19s |  %13.2f%5s |\n", "", super.prodID, super.prodName, super.prodPrice, "");
    }
}