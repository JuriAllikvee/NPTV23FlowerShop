package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Order implements Serializable {
    private Flower flower;
    private Customer customer;
    private LocalDate orderDate;

    public void setFlower(Flower flower) {
        this.flower = flower;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Flower getFlower() {
        return flower;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
}