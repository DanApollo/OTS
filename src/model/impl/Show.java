package model.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Show {

    private int id;
    private String name;
    private LocalDateTime dateTime;
    private List<Promotion> promotions;
    private List<Seat> seatingChart;
    private int maxSeatSelection;

    public Show(int id, String name, LocalDateTime dateTime, List<Promotion> promotions, List<Seat> seatingChart, int maxSeatSelection) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.promotions = promotions;
        this.seatingChart = seatingChart;
        this.maxSeatSelection = maxSeatSelection;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public int getSeats() {
        return seatingChart.size();
    }

    public void setSeatingChart(int seats) {
        List<Seat> seatList = new ArrayList<>();
        for (int i = 1; i <= seats; i++) {
            int seatNumber = (i - 1) % 10 + 1;
            int rowNumber = (i - 1) / 10 + 1;
            seatList.add(new Seat(rowNumber, seatNumber, 10));
        }
        seatingChart = seatList;
    }

    public int getMaxSeats() {
        return maxSeatSelection;
    }

    public List<Seat> getSeatingChart() {
        return seatingChart;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }
}
