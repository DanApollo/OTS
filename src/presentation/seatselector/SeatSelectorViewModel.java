package presentation.seatselector;

import model.Show;
import services.UserService;
import model.Seat;
import presentation.navigation.Navigation;
import services.BookingService;

import java.util.List;

public class SeatSelectorViewModel {

    private final Navigation navigation;
    private final Show show;
    private UserService userService;
    private BookingService bookingService;

    public SeatSelectorViewModel(Navigation navigation, UserService userService, BookingService bookingService, Show selectedShow) {
        this.navigation = navigation;
        this.userService = userService;
        this.bookingService = bookingService;
        this.show = selectedShow;
    }

    public void onBackClicked() {
        navigation.navigateTo("userDashboard");
    }

    public List<Seat> getSeats() {
        return show.getSeatingChart();
    }

    public int getMaxSeats() {
        return show.getMaxSeats();
    }

    public void holdSeat(Seat seat) {
        bookingService.holdSeat(seat);
    }

    public void releaseSeat(Seat seat) {
        bookingService.releaseSeat(seat);
    }
}
