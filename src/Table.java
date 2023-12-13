import static java.lang.CharSequence.compare;

public class Table implements Comparable<Table>{
    private String tableID;
    private int maxSeat;
    private String tableDesc;
    private double tableCharges;
    private boolean availability;

    public Table(String tableID, int maxSeat, String tableDesc, double tableCharges, boolean availability) {
        this.tableID = tableID;
        this.maxSeat = maxSeat;
        this.tableDesc = tableDesc;
        this.tableCharges = tableCharges;
        this.availability = availability;
    }

    public Table() {
        this.tableID = "";
        this.maxSeat = 0;
        this.tableDesc = "";
        this.tableCharges = 0;
        this.availability = true;
    }

    public String getTableID() { return tableID; }
    public int getMaxSeat() { return maxSeat; }
    public String getTableDesc() { return tableDesc; }
    public double getTableCharges() { return tableCharges; }
    public boolean isAvailability() { return availability; }

    public void setTableID(String tableID) { this.tableID = tableID; }
    public void setMaxSeat(int maxSeat) { this.maxSeat = maxSeat; }
    public void setTableDesc(String tableDesc) { this.tableDesc = tableDesc; }
    public void setTableCharges(double tableCharges) { this.tableCharges = tableCharges; }
    public void setAvailability(boolean availability) { this.availability = availability; }

    public String contentWriteToFile() {
        return this.getTableID() + "/" + this.getMaxSeat() + "/" + this.getTableDesc() + "/" +  this.getTableCharges() + "/" + this.isAvailability() + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return tableID.equals(table.tableID);
    }

    @Override
    public String toString() {
        return String.format("  | %3s%-6s |  %2d %-8s |  %-18s | %9.2f  |", "", this.getTableID(), this.getMaxSeat(), "people", this.getTableDesc(), this.getTableCharges());
    }

    @Override
    public int compareTo(Table o) {
        return compare(this.getTableID(), o.getTableID());
    }

}
