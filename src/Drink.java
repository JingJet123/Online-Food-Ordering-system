import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;

public class Drink extends Product{
    private String drinkType;
    private double hotPrice;
    private double smallCold;
    private double largeCold;

    public Drink(String drinkID, String drinkName, double drinkPrice) {
        super(drinkID, drinkName, drinkPrice);
        this.hotPrice = 0.0;
        this.smallCold = 0.0;
        this.largeCold = 0.0;
    }

    public Drink() {
        super();
        this.hotPrice = 0.0;
        this.smallCold = 0.0;
        this.largeCold = 0.0;
    }

    public void setHotPrice(double hotPrice) { this.hotPrice = hotPrice; }
    public void setSmallCold(double smallCold) { this.smallCold = smallCold; }
    public void setLargeCold(double largeCold) { this.largeCold = largeCold; }
    public double getHotPrice() { return hotPrice; }
    public double getSmallCold() { return smallCold; }
    public double getLargeCold() { return largeCold; }
    public String getDrinkType() {return drinkType;}
    public void setDrinkType(String drinkType) {this.drinkType = drinkType;}

    @Override
    public Product getProdSelect(int selection) throws ParseException, FileNotFoundException {
        ArrayList<Drink> drinkDetails = HostSystem.getDrinkDetails();
        Product drink = new Drink();
        for (int j = 0; j < drinkDetails.size(); j++) {
            if (j == selection - 1) {
                drink = drinkDetails.get(j);
            }
        }
        return drink;
    }

    @Override
    public String toString() {
        return String.format("  | %3s%-6s |  %-16s | %10.2f%5s | %11.2f%5s | %11.2f%5s |\n", "", super.prodID, super.prodName, this.getHotPrice(), "", this.getSmallCold(), "", this.getLargeCold(), "");
    }
}
