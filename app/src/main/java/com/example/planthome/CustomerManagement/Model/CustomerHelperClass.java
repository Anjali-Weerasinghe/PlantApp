package com.example.planthome.CustomerManagement.Model;

public class CustomerHelperClass {

    private String Name, Nic, Mobile, Email, Password;

    public CustomerHelperClass() {

    }

    public CustomerHelperClass(String name, String nic, String mobile, String email, String password) {
        Name = name;
        Nic = nic;
        Mobile = mobile;
        Email = email;
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNic() {
        return Nic;
    }

    public void setNic(String nic) {
        Nic = nic;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
