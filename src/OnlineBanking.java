import java.util.Date;

public class OnlineBanking extends Payment {
    private String bankType;
    private String accName;
    private String accPassword;
    private String accNo;
    private int tacNo;
    protected static final String[] BANK_NAME = new String[] {"MAYBANK", "PUBLIC BANK", "HONG LEONG", "CIMB", "OCBC", "HSBC"};
    protected static final int MAX_ATTEMPT = 3;

    public OnlineBanking(String payID, double payAmount, Date payDate, boolean payComplete, String payMethod, String bankType, String accName, String accPassword, String accNo, int tacNo) {
        super(payID, payAmount, payDate, payComplete, payMethod);
        this.bankType = bankType;
        this.accName = accName;
        this.accPassword = accPassword;
        this.accNo = accNo;
        this.tacNo = tacNo;
    }
    public OnlineBanking() {
        super();
        this.bankType = "";
        this.accName = "";
        this.accPassword = "";
        this.accNo = "";
        this.tacNo = 0;
    }

    public void setBankType(String bankType) {this.bankType = bankType;}
    public void setAccNo(String accNo) {this.accNo = accNo;}
    public void setAccPassword(String accPassword) {this.accPassword = accPassword;}
    public void setAccName(String accName) {this.accName = accName;}
    public void setTacNo(int tacNo) {this.tacNo = tacNo;}

    public String getBankType() {return bankType;}
    public String getAccNo() {return accNo;}
    public String getAccPassword() {return accPassword;}
    public String getAccName() {return accName;}
    public int getTacNo() {return tacNo;}

    public static boolean checkAccNumLength(int length) { return length >= 10 && length <= 12; }

    public static boolean checkAccPass(String pass) { return pass.matches("^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%&_]).{10,16}$"); }

    public static boolean checkTacNum(int tac) { return String.valueOf(tac).matches("^[0-9]{6}$"); }

    public boolean checkConfirmation(char confirm) {
        return Character.toUpperCase(confirm) == 'Y';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OnlineBanking that = (OnlineBanking) o;
        return bankType.equals(that.bankType) && accName.equals(that.accName) && accPassword.equals(that.accPassword) && accNo.equals(that.accNo);
    }

}