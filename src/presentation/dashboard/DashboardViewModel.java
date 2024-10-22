package presentation.dashboard;

import model.impl.Show;
import services.UserService;
import presentation.navigation.Navigation;
import services.ShowManagementService;

import java.time.LocalDate;
import java.util.List;

public class DashboardViewModel {

    private final Navigation navigation;
    private UserService userService;
    private ShowManagementService showManagementService;

    public DashboardViewModel(Navigation navigation, UserService userService, ShowManagementService showManagementService) {
        this.navigation = navigation;
        this.userService = userService;
        this.showManagementService = showManagementService;
    }

    public String getUsersName() {
        return userService.getLoggedInCustomer().getName();
    }

    public void onLogoutButtonClick() {
        navigation.onLogout();
    }

    public void onShowClick(Show selectedShow) {
        navigation.onShowSelect(selectedShow);
        navigation.navigateTo("seatSelector");
    }

    public List<Show> getShows() {
        LocalDate currentDate = LocalDate.now();
        return showManagementService.getUpcomingShows(currentDate, currentDate.plusWeeks(1));
    }
}
