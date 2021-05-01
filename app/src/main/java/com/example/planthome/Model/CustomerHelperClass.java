package com.example.planthome.Model;

public class CustomerHelperClass {

    String name,nic,mobile,email,password;

    public CustomerHelperClass() {

    }

    public CustomerHelperClass(String name, String nic, String mobile, String email, String password) {
        this.name = name;
        this.nic = nic;
        this.mobile = mobile;
        this.email = email;
        this.password = password;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

