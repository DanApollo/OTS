package model.abstracts;

import domain.interfaces.IUser;

public abstract class User implements IUser {
    protected String name;
    protected String email;
    protected String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    // Authentication method
    @Override
    public boolean authenticate(String enteredUsername, String enteredPassword) {
        return this.email.equals(enteredUsername) && this.password.equals(enteredPassword);
    }
}
