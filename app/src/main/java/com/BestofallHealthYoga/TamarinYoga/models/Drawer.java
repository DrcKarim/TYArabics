package com.BestofallHealthYoga.TamarinYoga.models;

public class Drawer {
    String image;
    boolean isSelected;
    String name;
    int position;

    public Drawer() {
    }

    public Drawer(String str, String str2, int i, boolean z) {
        this.name = str;
        this.image = str2;
        this.position = i;
        this.isSelected = z;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String str) {
        this.image = str;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }
}
