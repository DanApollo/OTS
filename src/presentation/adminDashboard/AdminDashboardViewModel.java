package presentation.adminDashboard;

import model.Promotion;
import model.Show;
import services.UserService;
import presentation.navigation.Navigation;
import services.ShowManagementService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboardViewModel {

    private final Navigation navigation;
    private UserService userService;
    private ShowManagementService showManagementService;

    public AdminDashboardViewModel(Navigation navigation, UserService userService, ShowManagementService showManagementService) {
        this.navigation = navigation;
        this.userService = userService;
        this.showManagementService = showManagementService;
    }

    public String getUsersName() {
        return userService.getLoggedInUser().getName();
    }

    public void onLogoutButtonClick() {
        navigation.onLogout();
    }

    public void onShowClick(Show selectedShow) {
        navigation.onShowSelect(selectedShow);
        navigation.navigateTo("seatSelector");
    }

    public List<Show> getShows() {
        return showManagementService.getAllShows();
    }

    public void createShow(String name, LocalDateTime dateTime, ArrayList<Promotion> objects, int i, int i1) {
        showManagementService.createShow(name, dateTime, objects, 10, 10);
    }

    public void modifyShow(Show selectedShow) {
        showManagementService.modifyShow(selectedShow);
    }

    public void deleteShow(int id) {
        showManagementService.deleteShow(id);
    }
}
