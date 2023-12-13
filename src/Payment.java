import java.text.SimpleDateFormat;
import java.util.*;

public class Payment {
    private Reservation reservation;
    private String payID;
    private double payAmount;
    private double subTotal;
    private Date payDate;
    private boolean payComplete;
    private double discTotal;
    private double sstTotal;
    private double scTotal;
    private String payMethod;
    private static boolean convertPay = false;
    private static double totalOfTableCost;
    private static final double DISCOUNT = 5;
    private static final double SERVICE_TAX = 6;
    private static final double SERVICE_CHARGES = 10;
    protected static final int MAX_ATTEMPT = 3;

    public Payment(Reservation reservation, String payID, double payAmount, double subTotal, Date payDate, boolean payComplete, double discTotal, double sstTotal, double scTotal, String payMethod) {
        this.reservation = reservation;
        this.payID = payID;
        this.payAmount = payAmount;
        this.subTotal = subTotal;
        this.payDate = payDate;
        this.payComplete = payComplete;
        this.discTotal = discTotal;
        this.sstTotal = sstTotal;
        this.scTotal = scTotal;
        this.payMethod = payMethod;
    }

    public Payment(String payID, double payAmount, Date payDate, boolean payComplete, String payMethod) {
        this.payID = payID;
        this.payAmount = payAmount;
        this.payDate = payDate;
        this.payComplete = payComplete;
        this.payMethod = payMethod;
    }

    public Payment() {this(new Reservation(), "", 0.0, 0.0, new Date(), false, 0.0, 0.0, 0.0, "");}

    public void setReservation(Reservation reservation) {this.reservation = reservation;}
    public void setPayID(String payID) {this.payID = payID;}
    public void setPayAmount(double payAmount) {this.payAmount = payAmount;}
    public void setSubTotal(double subTotal) {this.subTotal = subTotal;}
    public void setPayDate(Date payDate) {this.payDate = payDate;}
    public void setPayComplete(boolean payComplete) {this.payComplete = payComplete;}
    public void setDiscTotal(double discTotal) {this.discTotal = discTotal;}
    public void setSstTotal(double sstTotal) {this.sstTotal = sstTotal;}
    public void setScTotal(double scTotal) {this.scTotal = scTotal;}
    public void setPayMethod(String payMethod) {this.payMethod = payMethod;}
    public static void setTotalOfTableCost(double totalOfTableCost) {Payment.totalOfTableCost = totalOfTableCost;}
    public static void setConvertPay(boolean convertPay) {Payment.convertPay = convertPay;}

    public Reservation getReservation() {return reservation;}
    public String getPayID() {return payID;}
    public double getPayAmount() {return payAmount;}
    public double getSubTotal() {return subTotal;}
    public Date getPayDate() {return payDate;}
    public boolean isPayComplete() {return payComplete;}
    public double getDiscTotal() {return discTotal;}
    public double getSstTotal() {return sstTotal;}
    public double getScTotal() {return scTotal;}
    public String getPayMethod() {return payMethod;}
    public static double getTotalOfTableCost() {return totalOfTableCost;}
    public static boolean isConvertPay() {return convertPay;}

    public static Payment buildPayment(Payment payDetails, Reservation reservation) {
        Random rand = new Random();
        payDetails.setPayID("PAY" + rand.nextInt(9999999));
        for (OrderDetails order : reservation.getOrder().getOrderDetails()) {
            Payment.setTotalOfTableCost(calcTableCost(reservation.getTotalOfTableCharges(), order.getTotalOfOrder()));
            payDetails.setSubTotal(calcSubTotal(Payment.getTotalOfTableCost(), order.getTotalOfOrder()));
        }
        payDetails.setSstTotal(payDetails.calcServiceTax(payDetails.getSubTotal()));
        payDetails.setScTotal(payDetails.calcServiceCharges(payDetails.getSubTotal()));
        payDetails.setDiscTotal(payDetails.calcDiscount(payDetails.getSubTotal(), reservation.getCust().getMemberType()));
        payDetails.setPayAmount(calcGrantTotal(payDetails.getSubTotal(), payDetails.getSstTotal(), payDetails.getScTotal(), payDetails.getDiscTotal()));
        payDetails.setPayDate(new Date());
        payDetails.setPayComplete(false);
        payDetails.setReservation(reservation);
        payDetails.setPayMethod("Unknown");
        return payDetails;
    }

    public static double calcGrantTotal(double subtotal, double sst, double sc, double disc) { return subtotal + sst + sc - disc;}

    public static double calcTableCost(double tableCharges, double totalOfOrder) { return tableCharges / 100 * totalOfOrder;}

    public static double calcSubTotal(double tableCost, double totalOfOrder) { return tableCost + totalOfOrder; }

    public double calcServiceTax(double subTotal) { sstTotal = subTotal * SERVICE_TAX / 100; return sstTotal; }

    public double calcServiceCharges(double subTotal) { scTotal = subTotal * SERVICE_CHARGES / 100; return scTotal; }

    public double calcDiscount(double subTotal, String memberType) { discTotal = Objects.equals(memberType, "VIP") ? subTotal * DISCOUNT / 100 : 0; return discTotal; }

    public boolean checkConfirmation(char confirm) {
        return Character.toUpperCase(confirm) == 'Y';
    }

    public String contentWriteToFile() {
        return String.format("%s/%s/%.2f/%.2f/%s/%s/%.2f/%.2f/%.2f/%s", this.reservation.getReserveID(), this.getPayID(), this.getPayAmount(), this.getSubTotal(),
                new SimpleDateFormat("dd-MM-yyyy").format(this.getPayDate()), this.isPayComplete(), this.getDiscTotal(), this.getSstTotal(), this.getScTotal(), this.getPayMethod());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return payID.equals(payment.payID);
    }
}
