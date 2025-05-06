package org.example.tralalelotralala;
import org.json.JSONObject;

public class Product {
    private int id = 0;
    private String name;
    private int price;
    private String description;
    private int cost;
    private boolean isdigital;

    public Product(int id,String name, int price, String description, int cost, boolean isdigital) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.cost = cost;
        this.isdigital = isdigital;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    public Boolean getWetherDigital() {
        return isdigital;
    }
    public int getId() {
        return id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPrice(int price){
        this.price = price;
    }
    public void setDescription(String description){
        this.description = description;

    }
    public void setCost(int cost){
        this.cost = cost;
    }
    public void setIsdigital(boolean isdigital){
        this.isdigital = isdigital;
    }

}
