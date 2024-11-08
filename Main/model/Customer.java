package model;

import java.io.Serializable;
import java.util.UUID;

public class Customer implements Serializable {
    private UUID id;
    private String firstName;
    private String lastName;

    public Customer() {
        this.id = UUID.randomUUID();
    }

    public Customer(String firstName, String lastName) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}