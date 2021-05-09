package com.example.planthome.PlantsManagement;

public class samplePlantItem {

    String plantId;
    String plantName;
    String plantType;
    Integer plantPrice;

    public samplePlantItem() {
    }

    public samplePlantItem(String plantId, String plantName, String plantType, Integer plantPrice) {
        this.plantId = plantId;
        this.plantName = plantName;
        this.plantType = plantType;
        this.plantPrice = plantPrice;
    }

    public String getPlantId() {
        return plantId;
    }

    public String getPlantName() {
        return plantName;
    }

    public String getPlantType() {
        return plantType;
    }

    public Integer getPlantPrice() {
        return plantPrice;
    }
}
