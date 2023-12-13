import java.util.Date;
import java.util.Objects;

public class CreditCard extends Payment {
    private long credCardNo;
    private String cardName;
    private Date cardExpDate;
    private int cvvNo;
    private int cardPinNo;

    public CreditCard(String payID, double payAmount, Date payDate, boolean payComplete, String payMethod, long credCardNo, String cardName, Date cardExpDate, int cvvNo, int cardPinNo) {
        super(payID, payAmount, payDate, payComplete, payMethod);
        this.credCardNo = credCardNo;
        this.cardName = cardName;
        this.cardExpDate = cardExpDate;
        this.cvvNo = cvvNo;
        this.cardPinNo = cardPinNo;
    }
    public CreditCard() {
        super();
        this.credCardNo = 0;
        this.cardName = "";
        this.cardExpDate = new Date();
        this.cvvNo = 0;
        this.cardPinNo = 0;
    }

    public void setCredCardNo(long credCardNo) {this.credCardNo = credCardNo;}
    public void setCardExpDate(Date cardExpDate) {this.cardExpDate = cardExpDate;}
    public void setCvvNo(int cvvNo) {this.cvvNo = cvvNo;}
    public void setCardPinNo(int cardPinNo) {this.cardPinNo = cardPinNo;}
    public void setCardName(String cardName) {this.cardName = cardName;}

    public long getCredCardNo() {return credCardNo;}
    public Date getCardExpDate() {return cardExpDate;}
    public int getCvvNo() {return cvvNo;}
    public int getCardPinNo() {return cardPinNo;}
    public String getCardName() {return cardName;}

    public static boolean checkCardNum(long cardNo) {
        int length = 0;
        long temp = 1;
        while(temp <= cardNo) {
            length++;
            temp *= 10;
        }
        int sum = 0;
        int[] num = new int[length];
        String[] digit = String.valueOf(cardNo).split("");
        for (int i = length - 1; i >= 0; i--) {
            num[i] = Integer.parseInt((digit[i]));
            if (i % 2 == 1) {
                sum += num[i];
            } else if (num[i] < 5) {
                sum += num[i] * 2;
            } else {
                sum += num[i] * 2 - 9;
            }
        }
        return sum % 10 == 0 && length == 16;
    }

    public static boolean checkExpDate(Date expDate) { return expDate.after(new Date()); }

    public static boolean checkCvvNum(int cvvNum) { return String.valueOf(cvvNum).matches("^[0-9]{3}$"); }

    public static boolean checkPinNum(int pinNum) { return String.valueOf(pinNum).matches("^[0-9]{6}$"); }

    public boolean checkConfirmation(char confirm) {
        return Character.toUpperCase(confirm) == 'Y';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return credCardNo == that.credCardNo && cvvNo == that.cvvNo && cardPinNo == that.cardPinNo && cardName.equals(that.cardName) && Objects.equals(cardExpDate, that.cardExpDate);
    }
}