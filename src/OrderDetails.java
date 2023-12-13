import static java.lang.CharSequence.compare;

public class OrderDetails implements Comparable<OrderDetails> {
    private int prodQty;
    private String prodDesc;
    private double subTotalPerOrder;
    private Product prod;
    private double totalOfOrder;
    private static int noOfOrder;

    public OrderDetails(int prodQty, String prodDesc, double subTotalPerOrder, Product prod) {
        this.prodQty = prodQty;
        this.prodDesc = prodDesc;
        this.subTotalPerOrder = subTotalPerOrder;
        this.prod = prod;
        this.totalOfOrder = 0;
    }
    public OrderDetails() { this(0, "", 0.0, null); }

    public void setProdQty(int prodQty) {this.prodQty = prodQty;}
    public void setProdDesc(String prodDesc) {this.prodDesc = prodDesc;}
    public void setSubTotalPerOrder(double subTotalPerOrder) {this.subTotalPerOrder = subTotalPerOrder;}
    public void setProd(Product prod) {this.prod = prod;}
    public void setTotalOfOrder(double totalOfOrder) { this.totalOfOrder = totalOfOrder;}
    public static void setNoOfOrder(int numOfOrder) { noOfOrder = numOfOrder;}

    public int getProdQty() {return prodQty;}
    public String getProdDesc() {return prodDesc;}
    public double getSubTotalPerOrder() {return subTotalPerOrder;}
    public Product getProd() {return prod;}
    public double getTotalOfOrder() {return totalOfOrder;}
    public static int getNoOfOrder() {return noOfOrder;}

    public double calcSubTotalPerOrder(int qty, double unitPrice) { return qty * unitPrice; }

    public static double calcTotalOfOrder(double total, double subTotalPerOrder) { return total + subTotalPerOrder; }

    public static boolean checkConfirmation(char confirm) {
        return Character.toUpperCase(confirm) == 'Y';
    }

    public String contentWriteToFile() {
        return String.format("%d/%s/%.2f/%s/%s/%.2f\n", this.getProdQty(), this.getProdDesc(), this.getSubTotalPerOrder(), this.getProd().prodID, this.getProd().prodName, this.getProd().prodPrice);
    }

    @Override
    public String toString() {
       String name = prod.prodName;
       name += " (" + this.getProdDesc() + ")";
       return String.format("  || %4s%-7s ||   %-46s || %4s%3d%5s ||  %11.2f%6s ||  %10.2f%6s ||", "",
               this.getProd().prodID, name, "", this.getProdQty(), "", this.getProd().prodPrice, "", this.subTotalPerOrder, "");
    }

    @Override
    public int compareTo(OrderDetails o) { return compare(this.getProd().prodID, o.getProd().prodID);}
}

