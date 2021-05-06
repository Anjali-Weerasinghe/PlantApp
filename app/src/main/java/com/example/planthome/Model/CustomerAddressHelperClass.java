package com.example.planthome.Model;



public class CustomerAddressHelperClass{

    String nic,no,address1,address2,city,country,postalCode;



    public CustomerAddressHelperClass() {

    }

    public CustomerAddressHelperClass(String nic,String no, String address1, String address2, String city, String country, String postalCode) {
        this.nic = nic;
        this.no = no;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
