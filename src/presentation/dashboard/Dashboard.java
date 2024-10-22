package presentation.dashboard;

import model.Show;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Dashboard extends JPanel {

    private final DashboardViewModel viewModel;

    public Dashboard(DashboardViewModel viewModel) {
        this.viewModel = viewModel;
        initialiseComponents();
    }

    private void initialiseComponents() {
        setLayout(new BorderLayout());

        add(createHeader(), BorderLayout.NORTH);

        // Create show tiles
        add(createShowTile(viewModel.getShows()), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());

        JLabel name = new JLabel("Welcome " + viewModel.getUsersName());
        header.add(name, BorderLayout.WEST);

        JLabel title = new JLabel("Dashboard");
        title.setHorizontalAlignment(JLabel.CENTER);
        header.add(title, BorderLayout.CENTER);

        JButton logout = new JButton("logout");
        logout.addActionListener(e -> viewModel.onLogoutButtonClick());
        header.add(logout, BorderLayout.EAST);

        return header;
    }

    private JPanel createShowTile(List<Show> shows) {
        JPanel showsPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        for (Show show : shows) {
            JPanel tile = new JPanel();
            tile.setLayout(new BorderLayout());
            tile.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Add a border

            // Title label
            JLabel titleLabel = new JLabel(show.getName());
            titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
            titleLabel.setHorizontalAlignment(JLabel.CENTER);
            tile.add(titleLabel, BorderLayout.NORTH);

            // Date/time label
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy 'at' HH:mm");
            JLabel dateTimeLabel = new JLabel(show.getDateTime().format(formatter));
            dateTimeLabel.setHorizontalAlignment(JLabel.CENTER);
            tile.add(dateTimeLabel, BorderLayout.CENTER);

            // Add mouse listener for navigation
            tile.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    viewModel.onShowClick(show);
                }
            });
            showsPanel.add(tile);
        }
        return showsPanel;
    }
}
