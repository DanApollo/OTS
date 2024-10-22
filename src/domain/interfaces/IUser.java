package domain.interfaces;

public interface IUser {
    String getName();

    void setName(String name);

    String getEmail();

    void setEmail(String email);

    void setPassword(String password);

    boolean authenticate(String enteredUsername, String enteredPassword);

}
