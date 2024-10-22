package services;

import model.*;
import domain.enums.SeatStatus;
import util.SeatHoldTimer;

import java.util.List;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class BookingService {

    private List<Ticket> tickets = new ArrayList<>();
    private List<Seat> seats = new ArrayList<>();
    private SeatHoldTimer seatHoldTimer;

    // Private constructor
    public BookingService(SeatHoldTimer seatHoldTimer) {
        this.seatHoldTimer = seatHoldTimer;
    }

    // Hold a seat for a user
    public void holdSeat(Seat seat) {
        if (seat.getStatus() != SeatStatus.AVAILABLE) {
        }

        seats.add(seat);
        seat.setStatus(SeatStatus.HELD);
        CompletableFuture<Void> timerFuture = seatHoldTimer.startTimer();

        timerFuture.thenRun(() -> {
            seats = new ArrayList<>();
        });
    }

    // Release a held seat
    public void releaseSeat(Seat seat) {
        if (seat.getStatus() == SeatStatus.HELD) {
            seats.remove(seat);
            seat.setStatus(SeatStatus.AVAILABLE);
            if (seats.isEmpty()) {
                seatHoldTimer.stopTimer();
            }
        }
    }

    // Complete a booking
    public void completeBooking(Customer user, Show show, Agent agent) {
        // ... (Process payment)

        for (Seat seat : seats) {
            if (seat.getStatus() != SeatStatus.HELD) {
            }
            seat.setStatus(SeatStatus.SOLD);

            // Create a Ticket object and add it to the list
        }
    }

    // Get tickets sold for a specific show
    public List<Ticket> getTicketsSoldForShow(Show show) {
        return tickets.stream().filter(ticket -> ticket.getShow().equals(show)).collect(Collectors.toList());
    }

    // Get tickets sold within a date range
    public List<Ticket> getTicketsSoldForDateRange(LocalDate startDate, LocalDate endDate) {
        return tickets.stream().filter(ticket -> ticket.getShow().getDateTime().toLocalDate().isAfter(startDate.minusDays(1)) && ticket.getShow().getDateTime().toLocalDate().isBefore(endDate.plusDays(1))).collect(Collectors.toList());
    }
}
