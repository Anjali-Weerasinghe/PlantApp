package com.example.planthome.Model;

public class Items {

    private String itemKey, name, date, time, price, description, image, category;

    public Items (){

    }

    public Items(String itemKey, String name, String date, String time, String price, String description, String image, String category) {
        this.itemKey = itemKey;
        this.name = name;
        this.date = date;
        this.time = time;
        this.price = price;
        this.description = description;
        this.image = image;
        this.category = category;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
