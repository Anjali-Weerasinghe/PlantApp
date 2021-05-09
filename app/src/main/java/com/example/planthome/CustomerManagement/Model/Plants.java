package com.example.planthome.CustomerManagement.Model;

public class Plants {

    private String plantName;
    private String plantType;
    private Double plantPrice;
    private String imageUrl;
    private String plantKey;

    public Plants() {

    }

    public Plants(String plantName, String plantType, Double plantPrice, String imageUrl, String plantKey) {
        this.plantName = plantName;
        this.plantType = plantType;
        this.plantPrice = plantPrice;
        this.imageUrl = imageUrl;
        this.plantKey = plantKey;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPlantType() {
        return plantType;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }

    public Double getPlantPrice() {
        return plantPrice;
    }

    public void setPlantPrice(Double plantPrice) {
        this.plantPrice = plantPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPlantKey() {
        return plantKey;
    }

    public void setPlantKey(String plantKey) {
        this.plantKey = plantKey;
    }
}

