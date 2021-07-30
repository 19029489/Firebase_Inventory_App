package com.example.firebaseinventoryapp;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.List;

public class Item implements Serializable {
    private String id;
    private String name;
    private int cost;
    private List<String> options;

    public Item(String name, int cost, List<String> options) {
        this.name = name;
        this.cost = cost;
        this.options = options;
    }

    public Item() {

    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return name;
    }
}
