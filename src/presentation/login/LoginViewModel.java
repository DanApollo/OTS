package presentation.login;

import domain.interfaces.IUser;
import presentation.navigation.Navigation;
import services.UserService;

import javax.security.auth.login.LoginException;

public class LoginViewModel {
    private final Navigation navigation;
    private UserService userService;
    private String email;
    private String password;

    public LoginViewModel(Navigation navigation, UserService userService) {
        this.navigation = navigation;
        this.userService = userService;
    }

    public void setUsername(String username) {
        this.email = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void onLoginButtonClick() throws LoginException {
        // 1. Basic input validation
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new LoginException("Username and password are required.");
        }

        // 2. Authentication logic
        for (IUser user : userService.getAllUsers()) {
            if (user.authenticate(email, password)) {
                navigation.onLogin(user);
            }
        }

        // 3. Throw exception if no match found
        throw new LoginException("Invalid username or password.");
    }

    public void navigateTo(String page) {
        navigation.navigateTo(page);
    }
}
