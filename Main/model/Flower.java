package model;

import java.io.Serializable;

public class Flower implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private double price;

    public Flower(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
