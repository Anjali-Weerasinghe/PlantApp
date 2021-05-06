package com.example.planthome.Model;

public class CustomerPaymentHelperClass {

    private String cardNo,expiryDate,cvc,nic,type;

    public CustomerPaymentHelperClass() {

    }

    public CustomerPaymentHelperClass(String cardNo, String expiryDate, String cvc,String nic,String type) {
        this.cardNo = cardNo;
        this.expiryDate = expiryDate;
        this.cvc = cvc;
        this.nic = nic;
        this.type = type;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getNic() {
        return nic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }
}
