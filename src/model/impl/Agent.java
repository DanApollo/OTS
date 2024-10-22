package model.impl;

import domain.enums.SeatStatus;
import model.abstracts.User;

import java.util.List;

public class Agent extends User {

    private final double commissionRate;
    private final List<Seat> assignedSeats;

    public Agent(String name, String email, String password, double commissionRate, List<Seat> assignedSeats) {
        super(name, email, password);
        this.commissionRate = commissionRate;
        this.assignedSeats = assignedSeats;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public List<Seat> getAssignedSeats() {
        return assignedSeats;
    }

    public List<Seat> viewAvailableSeats(Show show) {
        // Filter seats from the show's seating chart that are both AVAILABLE and assigned to this agent
        return show.getSeatingChart().stream().filter(seat -> seat.getStatus() == SeatStatus.AVAILABLE && seat.isAssignedToAgent(this)).toList();
    }
}
