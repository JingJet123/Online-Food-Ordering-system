import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;

public class Food extends Product {

    public Food(String foodID, String foodName, double foodPrice) {
        super(foodID, foodName, foodPrice);
    }
    public Food() { super(); }

    @Override
    public Product getProdSelect(int selection) throws ParseException, FileNotFoundException {
        ArrayList<Food> foodDetails = HostSystem.getFoodDetails();
        Product food = new Food();
        for (int j = 0; j < foodDetails.size(); j++) {
            if (j == selection - 1) {
                food = foodDetails.get(j);
            }
        }
        return food;
    }

    @Override
    public String toString() {
        return String.format("  | %3s%-6s |  %-28s |  %9.2f %5s|\n", "", super.prodID, super.prodName, super.prodPrice, "");
    }
}