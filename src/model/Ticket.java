package model;

import model.abstracts.User;

public class Ticket {

    private int ticketId;
    private Show show;
    private Seat seat;
    private double price;
    private double discount;
    private User customer;
    private Agent agent; // Optional, might be null if booked directly by customer

    public Ticket(Show show, Seat seat, double price, double discount, User customer, Agent agent) {
        this.show = show;
        this.seat = seat;
        this.price = price;
        this.discount = discount;
        this.customer = customer;
        this.agent = agent;
    }

    public int getTicketId() {
        return ticketId;
    }

    public Show getShow() {
        return show;
    }

    public Seat getSeat() {
        return seat;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

    public User getCustomer() {
        return customer;
    }

    public Agent getAgent() {
        return agent;
    }
}
