package presentation.agentDashboard;

import model.impl.Customer;
import presentation.navigation.Navigation;
import services.UserService;

import java.util.List;

public class AgentDashboardViewModel {
    private final Navigation navigation;
    private UserService userService;

    public AgentDashboardViewModel(Navigation navigation, UserService userService) {
        this.navigation = navigation;
        this.userService = userService;
    }

    public void onLogin(Customer customer) {
        navigation.onLogin(customer);
        navigation.navigateTo("userDashboard");
    }

    public List<Customer> getAllCustomers() {
        return userService.getAllCustomers();
    }
}
