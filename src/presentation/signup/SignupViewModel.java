package presentation.signup;

import model.abstracts.User;
import model.Customer;
import presentation.navigation.Navigation;
import services.UserService;

public class SignupViewModel {

    private final Navigation navigation;
    private UserService userService;
    private String name;
    private String email;
    private String password;

    public SignupViewModel(Navigation navigation, UserService userService) {
        this.navigation = navigation;
        this.userService = userService;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void onSignupButtonClick() throws SignupException {
        // 1. Basic input validation
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new SignupException("Email and password are required.");
        }

        // 2. Check if username is already taken
        for (User user : userService.getAllUsers()) {
            if (user.getEmail().equals(email)) {
                throw new SignupException("Email already taken.");
            }
        }

        // 3. Check password strength
        if (!isPasswordStrongEnough(password)) {
            StringBuilder errorMessage = getErrorMessageStringBuilder();
            throw new SignupException(errorMessage.toString());
        }

        // 4. Create new user and add to the list
        Customer newUser = new Customer(this.name, this.email, this.password);
        userService.addUser(newUser);

        navigation.onLogin(newUser);
    }

    private StringBuilder getErrorMessageStringBuilder() {
        StringBuilder errorMessage = new StringBuilder("Password is not strong enough. It must:\n");
        if (password.length() < 8) {
            errorMessage.append("- Be at least 8 characters long\n");
        }
        if (!password.matches(".*[A-Z].*")) {
            errorMessage.append("- Contain at least one uppercase letter\n");
        }
        if (!password.matches(".*[a-z].*")) {
            errorMessage.append("- Contain at least one lowercase letter\n");
        }
        if (!password.matches(".*\\d.*")) {
            errorMessage.append("- Contain at least one digit\n");
        }
        return errorMessage;
    }

    // Helper method to check password strength
    private boolean isPasswordStrongEnough(String password) {
        // 1. Minimum Length
        if (password.length() < 8) {
            return false;
        }

        // 2. At Least One Uppercase Letter
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // 3. At Least One Lowercase Letter
        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        // 4. At Least One Digit
        if (!password.matches(".*\\d.*")) {
            return false;
        }

        return true; // All criteria met, password is strong enough
    }

    public void navigateTo(String page) {
        navigation.navigateTo(page);
    }

    public static class SignupException extends Exception {
        public SignupException(String message) {
            super(message);
        }
    }
}
