package util;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

public class SeatHoldTimer {

    private Timer timer;
    private int elapsedMinutes = 0;

    public CompletableFuture<Void> startTimer() {
        CompletableFuture<Void> completionFuture = new CompletableFuture<>();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        elapsedMinutes = 0;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                elapsedMinutes++;
                if (elapsedMinutes == 5) {
                    // First prompt after 5 minutes
                    System.out.println("5 minutes have passed. Do you want to continue with your booking?");
                } else if (elapsedMinutes > 5 && elapsedMinutes <= 8) {
                    // Subsequent prompts every minute for 3 minutes
                    System.out.println("1 minute has passed. Do you want to continue with your booking?");
                } else if (elapsedMinutes > 8) {
                    // Release the seat after 8 minutes (5 + 3)

                    timer.cancel();
                    System.out.println("Seats hold has expired. Seat is now available.");
                    completionFuture.complete(null);
                }
            }
        }, 5 * 60 * 1000, 60 * 1000); // 5 minutes initial delay, 1-minute interval
        return completionFuture;
    }

    public void stopTimer() {
        timer.cancel();
        System.out.println("Seat hold canceled. Seats is now available.");
    }
}
