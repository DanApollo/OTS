package domain.users;

public interface IUser {
    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getUsername();

    boolean authenticate(String enteredUsername, String enteredPassword);

    String getName();
}
