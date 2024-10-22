package presentation.seatselector;

import domain.enums.SeatStatus;
import model.Seat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SeatSelector extends JPanel {

    private final SeatSelectorViewModel viewmodel;
    private int selectedSeats = 0;

    public SeatSelector(SeatSelectorViewModel viewModel) {
        this.viewmodel = viewModel;

        setLayout(new BorderLayout());

        JButton backButton = new JButton("back");
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                viewModel.onBackClicked();
            }
        });
        add(backButton, BorderLayout.NORTH);


        JPanel test = new JPanel(new GridLayout(0, 10));

        // Create seat buttons
        for (Seat seat : viewmodel.getSeats()) {
            JButton seatButton = new JButton("R" + seat.getRow() + " " + "N" + seat.getNumber());
            seatButton.setPreferredSize(new Dimension(30, 30));
            seatButton.setOpaque(true);
            updateSeatButton(seatButton, seat);

            // Add mouse listener for seat selection
            seatButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (seat.getStatus() == SeatStatus.AVAILABLE) {
                        if (selectedSeats >= viewmodel.getMaxSeats()) {
                            return;
                        }
                        selectedSeats++;
                        viewmodel.holdSeat(seat);
                    } else if (seat.getStatus() == SeatStatus.HELD) {
                        selectedSeats--;
                        viewmodel.releaseSeat(seat);
                    }
                    updateSeatButton(seatButton, seat);
                }
            });

            test.add(seatButton);
        }

        add(test, BorderLayout.CENTER);
    }

    // Helper method to update the seat button's appearance
    private void updateSeatButton(JButton seatButton, Seat seat) {
        switch (seat.getStatus()) {
            case AVAILABLE:
                seatButton.setBackground(Color.GREEN);
                break;
            case HELD:
                seatButton.setBackground(Color.YELLOW);
                break;
            case SOLD:
                seatButton.setBackground(Color.RED);
                break;
        }
    }
}
