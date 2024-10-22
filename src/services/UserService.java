package services;

import domain.interfaces.IUser;
import model.abstracts.User;
import model.impl.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {

    private final List<User> users = new ArrayList<>();
    private IUser loggedInUser;
    private Customer loggedInCustomer;

    // === User Management Methods ===

    public void addUser(User user) throws IllegalArgumentException {
        if (user == null || user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Invalid user data: User or email cannot be null or empty.");
        }
        // Check for unique username
        if (findUserByUsername(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Username (email) already exists.");
        }

        users.add(user);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public Optional<User> findUserByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        return users.stream().filter(user -> user.getEmail().equals(username)).findFirst();
    }

    public List<Customer> getAllCustomers() {
        return users.stream().filter(user -> user instanceof Customer).map(user -> (Customer) user).toList();
    }

    public void deleteUser(String username) throws UserNotFoundException {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        boolean removed = users.removeIf(user -> user.getEmail().equals(username));
        if (!removed) {
            throw new UserNotFoundException("User not found with username: " + username);
        }
    }

    public void updateUser(User updatedUser) throws UserNotFoundException {
        if (updatedUser == null || updatedUser.getEmail() == null || updatedUser.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Invalid user data: User or email cannot be null or empty.");
        }
        boolean found = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(updatedUser.getEmail())) {
                users.set(i, updatedUser);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new UserNotFoundException("User not found with username: " + updatedUser.getEmail());
        }
    }

    // === Logged-in User Management ===

    public void setLoggedInUser(IUser user) {
        this.loggedInUser = user;
    }

    public IUser getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInCustomer(Customer customer) {
        this.loggedInCustomer = customer;
    }

    public Customer getLoggedInCustomer() {
        return this.loggedInCustomer;
    }

    // Custom exception class for user not found
    public static class UserNotFoundException extends Exception {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}
