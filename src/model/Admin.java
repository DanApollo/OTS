package model;

import model.abstracts.User;

public class Admin extends User {
    public Admin(String name, String email, String password) {
        super(name, email, password);
    }
}
