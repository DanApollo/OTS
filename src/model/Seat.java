package model;

import domain.enums.SeatStatus;

public class Seat {

    private final int row;
    private final int number;
    private Customer customer;
    private SeatStatus status = SeatStatus.AVAILABLE; // Default to available
    private final double price;

    public Seat(int row, int number, double price) {
        this.row = row;
        this.number = number;
        this.price = price;
    }

    public int getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }

    public Customer getCustomer() {
        return customer;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public double getPrice() {
        return price;
    }

    public void setStatus(SeatStatus newStatus) {
        this.status = newStatus;
    }

    public boolean isAssignedToAgent(Agent agent) {
        return agent.getAssignedSeats().contains(this);
    }
}
