public class Customer {
    private String userID;
    private String password;
    private String custName;
    private char gender;
    private String custContact;
    private String custEmail;
    private String memberType;

    public Customer(String userID, String password, String custName, char gender, String custContact, String custEmail, String memberType) {
        this.userID = userID;
        this.password = password;
        this.custName = custName;
        this.gender = gender;
        this.custContact = custContact;
        this.custEmail = custEmail;
        this.memberType = memberType;
    }
    public Customer() {this("", "", "", 'M', "", "", "");}

    public void setUserID(String userID) { this.userID = userID; }
    public void setPassword(String password) { this.password = password; }
    public void setCustName(String custName) { this.custName = custName; }
    public void setGender(char gender) { this.gender = gender; }
    public void setCustContact(String custContact) { this.custContact = custContact; }
    public void setCustEmail(String custEmail) { this.custEmail = custEmail; }
    public void setMemberType(String memberType) { this.memberType = memberType; }

    public String getUserID() { return userID; }
    public String getPassword() { return password; }
    public String getCustName() { return custName; }
    public char getGender() { return gender; }
    public String getCustContact() { return custContact; }
    public String getCustEmail() { return custEmail; }
    public String getMemberType() { return memberType; }

    public static boolean checkConfirmation(char confirm) {
        return Character.toUpperCase(confirm) == 'Y';
    }

    public String contentWriteToFile() {
        return this.getUserID() + "/" + this.getPassword() + "/" + this.getCustName() + "/" + this.getGender() + "/" + this.getCustContact() + "/" + this.getCustEmail() + "/" + this.getMemberType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return userID.equals(customer.userID) && password.equals(customer.password);
    }

    @Override
    public String toString() {
        return String.format("%25s| %-14s | %-14s | %-22s | %3c%-3s | %-15s | %-23s |    %-10s |\n", "", this.getUserID(), this.getPassword(), this.getCustName(), this.getGender(), "", this.getCustContact(), this.getCustEmail(), this.getMemberType());
    }
}
