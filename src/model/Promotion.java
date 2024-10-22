package model;

import java.time.LocalDate;

public class Promotion {

    private int promotionId;
    private String name;
    private double discountPercentage;
    private LocalDate startDate;
    private LocalDate endDate;

    public Promotion(int promotionId, String name, double discountPercentage, LocalDate startDate, LocalDate endDate) {
        this.promotionId = promotionId;
        this.name = name;
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getPromotionId() {
        return promotionId;
    }

    public String getName() {
        return name;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
