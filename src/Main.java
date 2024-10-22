import domain.interfaces.IUser;
import model.impl.*;
import presentation.adminDashboard.AdminDashboard;
import presentation.adminDashboard.AdminDashboardViewModel;
import presentation.agentDashboard.AgentDashboard;
import presentation.agentDashboard.AgentDashboardViewModel;
import presentation.dashboard.Dashboard;
import presentation.dashboard.DashboardViewModel;
import presentation.login.Login;
import presentation.login.LoginViewModel;
import presentation.navigation.Navigation;
import presentation.seatselector.SeatSelector;
import presentation.seatselector.SeatSelectorViewModel;
import presentation.signup.Signup;
import presentation.signup.SignupViewModel;
import services.BookingService;
import services.UserService;
import services.ShowManagementService;
import util.SeatHoldTimer;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main extends JFrame implements Navigation {

    private final CardLayout cardLayout;
    private final JPanel cards;
    private JPanel dashboard;
    private UserService userService;
    private SeatHoldTimer seatHoldTimer;
    private BookingService bookingService;
    private ShowManagementService showManagementService;

    public Main() {
        userService = new UserService();
        seatHoldTimer = new SeatHoldTimer();
        bookingService = new BookingService(seatHoldTimer);
        showManagementService = new ShowManagementService();


        setTitle("Online Ticketing System");
        setSize(1000,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.cardLayout = new CardLayout();
        this.cards = new JPanel(cardLayout);

        LoginViewModel loginViewModel = new LoginViewModel(this, userService);
        SignupViewModel signupViewModel = new SignupViewModel(this, userService);

        JPanel login = new Login(loginViewModel);
        JPanel signUp = new Signup(signupViewModel);

        cards.add("login", login);
        cards.add("sign up", signUp);

        initialiseData();

        add(cards);
        cardLayout.show(cards, "login");
        setVisible(true);
    }

    @Override
    public void onLogin(IUser user) {
        if (user instanceof Customer) {
            userService.setLoggedInCustomer((Customer) user);
            DashboardViewModel dashboardViewModel = new DashboardViewModel(this, userService, showManagementService);
            dashboard = new Dashboard(dashboardViewModel);
            cards.add("userDashboard", dashboard);
            navigateTo("userDashboard");
        } else if (user instanceof Agent) {
            userService.setLoggedInUser(user);
            AgentDashboardViewModel agentDashboardViewModel = new AgentDashboardViewModel(this, userService);
            cards.add("dashboard", new AgentDashboard(agentDashboardViewModel));
            navigateTo("dashboard");
        } else if(user instanceof Admin) {
            userService.setLoggedInUser(user);
            AdminDashboardViewModel adminDashboardViewModel = new AdminDashboardViewModel(this, userService, showManagementService);
            cards.add("dashboard", new AdminDashboard(adminDashboardViewModel));
            navigateTo("dashboard");
        }

    }


    @Override
    public void onLogout() {
        if (userService.getLoggedInUser() instanceof Agent) {
            if (userService.getLoggedInCustomer() != null) {
                navigateTo("dashboard");
                cards.remove(dashboard);
                userService.setLoggedInCustomer(null);
            }
        } else {
            userService.setLoggedInUser(null);
            cards.removeAll();

            LoginViewModel loginViewModel = new LoginViewModel(this, userService);
            SignupViewModel signupViewModel = new SignupViewModel(this, userService);

            JPanel login = new Login(loginViewModel);
            JPanel signUp = new Signup(signupViewModel);

            cards.add("login", login);
            cards.add("sign up", signUp);
            cardLayout.show(cards, "login");
        }
    }
    @Override
    public void onShowSelect(Show selectedShow) {
        SeatSelectorViewModel seatSelectorViewModel = new SeatSelectorViewModel(this, userService, bookingService, selectedShow);
        cards.add("seatSelector", new SeatSelector(seatSelectorViewModel));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }

    @Override
    public void navigateTo(String cardName) {
        cardLayout.show(cards, cardName);
    }

    private void initialiseData() {
        userService.addUser(new Customer("Daniel", "daniel@test.uk", "Password1"));
        userService.addUser(new Customer("Paul", "paul@test.uk", "Password1"));
        userService.addUser(new Admin("Admin", "admin@test.uk", "Password1"));
        userService.addUser(new Agent("Agent", "agent@test.uk", "Password1", 5, new ArrayList<>()));




        showManagementService.createShow("Spiderman", LocalDateTime.now(), new ArrayList<>(), 50, 5 );
    }

}