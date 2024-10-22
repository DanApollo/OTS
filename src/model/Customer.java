package model;

import model.abstracts.User;

public class Customer extends User {
    public Customer(String name, String email, String password) {
        super(name, email, password);
    }
}
