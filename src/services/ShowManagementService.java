package services;

import model.impl.Promotion;
import model.impl.Seat;
import model.impl.Show;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShowManagementService {

    private int nextShowId = 1;

    private List<Show> shows = new ArrayList<>();

    // Get upcoming shows within a date range
    public List<Show> getUpcomingShows(LocalDate startDate, LocalDate endDate) {
        List<Show> upcomingShows = new ArrayList<>();
        for (Show show : shows) {
            if (show.getDateTime().toLocalDate().isAfter(startDate.minusDays(1)) && show.getDateTime().toLocalDate().isBefore(endDate.plusDays(1))) {
                upcomingShows.add(show);
            }
        }
        return upcomingShows;
    }

    // Create a new show
    public void createShow(String name, LocalDateTime dateTime, List<Promotion> promotions, int seats, int maxSeatSelection) {
        List<Seat> seatList = new ArrayList<>();
        for (int i = 1; i <= seats; i++) {
            int seatNumber = (i - 1) % 10 + 1;
            int rowNumber = (i - 1) / 10 + 1;
            seatList.add(new Seat(rowNumber, seatNumber, 10));
        }
        Show show = new Show(nextShowId++, name, dateTime, promotions, seatList, maxSeatSelection);
        shows.add(show);
    }

    // Delete a show
    public void deleteShow(int id) {
        shows.removeIf(it -> it.getId() == id);
    }

    // Modify an existing show
    public void modifyShow(Show updatedShow) {
        // Find the show by ID and update its details
        for (int i = 0; i < shows.size(); i++) {
            if (shows.get(i).getId() == updatedShow.getId()) {
                shows.set(i, updatedShow);
                break;
            }
        }
    }

    // Select best available seats based on criteria
    public List<Seat> selectBestAvailableSeats(Show show, double minPrice, double maxPrice, int numSeats) {
        // ... seat selection logic
        return new ArrayList<>();
    }

    public List<Show> getAllShows() {
        return shows;
    }
}
