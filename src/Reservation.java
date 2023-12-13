import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Reservation {
    private String reserveID;
    private Date reserveDate;
    private Date startTime;
    private Date endTime;
    private int noOfPerson;
    private double totalOfTableCharges;
    private Customer cust;
    private ArrayList<Table> tables;
    private Order order;
    private static int lastReserveID;
    private static boolean reserveConfirm = false;

    public Reservation(String reserveID, Date reserveDate, Date startTime, Date endTime, int noOfPerson, double totalOfTableCharges, Customer cust, ArrayList<Table> tables, Order order) {
        this.reserveID = reserveID;
        this.reserveDate = reserveDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.noOfPerson = noOfPerson;
        this.totalOfTableCharges = totalOfTableCharges;
        this.cust = cust;
        this.tables = tables;
        this.order = order;
    }
    public Reservation() { this("", new Date(), new Date(), new Date(), 0, 0.0, new Customer(), new ArrayList<Table>(), new Order());}

    public void setReserveID(String reserveID) {this.reserveID = reserveID;}
    public void setReserveDate(Date reserveDate) {this.reserveDate = reserveDate;}
    public void setStartTime(Date startTime) {this.startTime = startTime;}
    public void setEndTime(Date endTime) {this.endTime = endTime;}
    public void setNoOfPerson(int noOfPerson) {this.noOfPerson = noOfPerson;}
    public void setTotalOfTableCharges(double totalOfTableCharges) { this.totalOfTableCharges = totalOfTableCharges; }
    public void setCust(Customer cust) {this.cust = cust;}
    public void setTables(ArrayList<Table> tables) {this.tables = tables;}
    public void setOrder(Order order) {this.order = order;}
    public static void setLastReserveID(int lastReserveID) {Reservation.lastReserveID = lastReserveID;}
    public static void setReserveConfirm(boolean reserveConfirm) {Reservation.reserveConfirm = reserveConfirm;}

    public String getReserveID() {return reserveID;}
    public Date getReserveDate() {return reserveDate;}
    public Date getStartTime() {return startTime;}
    public Date getEndTime() {return endTime;}
    public int getNoOfPerson() {return noOfPerson;}
    public Customer getCust() {return cust;}
    public ArrayList<Table> getTables() {return tables;}
    public Order getOrder() {return order;}
    public double getTotalOfTableCharges() { return totalOfTableCharges; }
    public static int getLastReserveID() {return lastReserveID;}
    public static boolean isReserveConfirm() {return reserveConfirm;}

    public boolean checkConfirmation(char confirm) {return Character.toUpperCase(confirm) == 'Y';}

    public boolean checkMaxDayReserve(Date dateReserve, Date curDate) {
        long dayDiff = ChronoUnit.DAYS.between(curDate.toInstant(), dateReserve.toInstant());
        return dayDiff <= 14 && dateReserve.after(new Date());
    }

    public boolean checkReserveTime(Date startTime, Date endTime) {
        long timeDiff = ChronoUnit.HOURS.between(startTime.toInstant(), endTime.toInstant());
        return timeDiff <= 3 && endTime.after(startTime);
    }

    public String contentWriteToFile() {
        return this.getReserveID() + "/" + new SimpleDateFormat("dd-MM-yyyy").format(this.getReserveDate()) + "/" +
                new SimpleDateFormat("HH:mm").format(this.getStartTime()) + "/" + new SimpleDateFormat("HH:mm").format(this.getEndTime()) + "/" +
                this.getNoOfPerson() + "/" + this.getTotalOfTableCharges() + "/" + this.getCust().getUserID() + "/" +
                new SimpleDateFormat("dd-MM-yyyy").format(this.getReserveDate()) + "/" + this.getOrder().getOrderID();
    }

    public void addTableCharges(double charges) { this.totalOfTableCharges += charges;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(reserveID, that.reserveID);
    }
}
