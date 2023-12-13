import java.io.FileNotFoundException;
import java.text.ParseException;

public abstract class Product {
    protected String prodID;
    protected String prodName;
    protected double prodPrice;

    protected Product(String prodID, String prodName, double prodPrice) {
        this.prodID = prodID;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
    }
    protected Product() { this("", "", 0.0); }

    public abstract Product getProdSelect(int selection) throws ParseException, FileNotFoundException;

}



