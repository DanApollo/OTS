package data.users;

import domain.users.IUser;

public abstract class User implements IUser {
    protected String firstName;
    protected String lastName;
    protected final String username;
    protected final String password;

    public User(String firstName, String secondName, String username, String password) {
        this.firstName = firstName;
        this.lastName = secondName;
        this.username = username;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String enteredUsername, String enteredPassword) {
        return this.username.equals(enteredUsername) && this.password.equals(enteredPassword);
    }

    public String getName() {
        return firstName + lastName;
    }
}
