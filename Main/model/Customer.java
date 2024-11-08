package model;

import java.io.Serializable;
import java.util.UUID;

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String firstName;
    private String lastName;
    private String phone;

    public Customer() {
        this.id = UUID.randomUUID();
    }

    public Customer(String firstName, String lastName, String phone) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
