package com.mobiledev.ourapp.whatsfordinner;

public class Restaurant {

    String name;
    String type;
    int price_level;
    String location;
    String menu;
    boolean isSelected = false;

    public Restaurant(String name, String type, int price_level){
        this.name = name;
        this.type = type;
        this.price_level = price_level;
    }

    public Restaurant(String name, String location, int price_level, String menu){
        this.name = name;
        this.location = location;
        this.price_level = price_level;
        this.menu = menu;
    }

    public Restaurant(String name, String location){
        this.name = name;
        this.location = location;
    }

    public void setSelected(boolean selected){
        this.isSelected = selected;
    }

    public boolean getSelected() {
        return this.isSelected;
    }
}
