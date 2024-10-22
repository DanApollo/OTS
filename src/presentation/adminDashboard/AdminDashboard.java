package presentation.adminDashboard;

import model.impl.Show;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class AdminDashboard extends JPanel {

    private final AdminDashboardViewModel viewModel;

    private DefaultListModel<Show> showListModel;
    private JList<Show> showList;
    private JTextField showNameField;
    private JTextField showDateTimeField;
    private JTextField showSeatsField;
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;

    public AdminDashboard(AdminDashboardViewModel viewModel) {
        this.viewModel = viewModel;
        initialiseComponents();
    }

    private void initialiseComponents() {
        setLayout(new BorderLayout());

        add(createHeader(), BorderLayout.NORTH);

        // Show List
        showListModel = new DefaultListModel<>();
        showList = new JList<>(showListModel);
        showList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        showList.setCellRenderer(new ShowListRenderer());
        JScrollPane showListScrollPane = new JScrollPane(showList);
        add(showListScrollPane, BorderLayout.WEST);

        // Show Details and Actions
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        // Show Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        detailsPanel.add(new JLabel("Show Name:"), gbc);
        gbc.gridx = 1;
        showNameField = new JTextField(20);
        detailsPanel.add(showNameField, gbc);

        // Show Date/Time
        gbc.gridx = 0;
        gbc.gridy = 1;
        detailsPanel.add(new JLabel("Date/Time (yyyy-MM-dd HH:mm):"), gbc);
        gbc.gridx = 1;
        showDateTimeField = new JTextField(20);
        detailsPanel.add(showDateTimeField, gbc);

        // Show Venue
        gbc.gridx = 0;
        gbc.gridy = 2;
        detailsPanel.add(new JLabel("Seats:"), gbc);
        gbc.gridx = 1;
        showSeatsField = new JTextField(20);
        detailsPanel.add(showSeatsField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        createButton = new JButton("Create");
        createButton.addActionListener(new CreateShowListener());
        buttonPanel.add(createButton);

        editButton = new JButton("Edit");
        editButton.addActionListener(new EditShowListener());
        editButton.setEnabled(false);
        buttonPanel.add(editButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new DeleteShowListener());
        deleteButton.setEnabled(false);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
        add(detailsPanel, BorderLayout.CENTER);

        // Load initial shows
        loadShows();

        // Add a listener to enable/disable edit and delete buttons based on selection
        showList.addListSelectionListener(e -> {
            boolean showSelected = !showList.isSelectionEmpty();
            editButton.setEnabled(showSelected);
            deleteButton.setEnabled(showSelected);
        });
    }

    // Load shows into the JList
    private void loadShows() {
        showListModel.clear();
        List<Show> shows = viewModel.getShows();
        for (Show show : shows) {
            showListModel.addElement(show);
        }
    }

    static class ShowListRenderer extends DefaultListCellRenderer {
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy 'at' HH:mm");

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Show show) {
                String formattedText = String.format("%s - %s", show.getName(), show.getDateTime().format(formatter));
                return super.getListCellRendererComponent(list, formattedText, index, isSelected, cellHasFocus);
            } else {
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        }
    }

    // Action listener for the Create button
    private class CreateShowListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String name = showNameField.getText();
                LocalDateTime dateTime = LocalDateTime.parse(showDateTimeField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                String seats = showSeatsField.getText();

                viewModel.createShow(name, dateTime, new ArrayList<>(), parseInt(seats), 5);
                loadShows(); // Refresh the show list

                // Clear the input fields
                showNameField.setText("");
                showDateTimeField.setText("");
                showSeatsField.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(AdminDashboard.this, "Error creating show: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Action listener for the Edit button
    private class EditShowListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Show selectedShow = showList.getSelectedValue();
            if (selectedShow != null) {
                // 1. Populate the input fields with the selected show's data
                showNameField.setText(selectedShow.getName());
                showDateTimeField.setText(selectedShow.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                showSeatsField.setText(Integer.toString(selectedShow.getSeats()));

                // 2. Optionally, disable the JList and other components to prevent conflicts while editing
                showList.setEnabled(false);
                createButton.setEnabled(false);
                deleteButton.setEnabled(false);

                // 3. Change the "Edit" button to a "Save" button
                editButton.setText("Save");
                editButton.removeActionListener(this); // Remove the current listener
                editButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            // 4. Retrieve the updated data from the input fields
                            String name = showNameField.getText();
                            LocalDateTime dateTime = LocalDateTime.parse(showDateTimeField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                            int seats = parseInt(showSeatsField.getText());

                            // 5. Update the selected show object
                            selectedShow.setName(name);
                            selectedShow.setDateTime(dateTime);
                            selectedShow.setSeatingChart(seats);

                            // 6. Update the show using the service
                            viewModel.modifyShow(selectedShow);
                            loadShows(); // Refresh the show list

                            // 7. Re-enable the JList and other components
                            showList.setEnabled(true);
                            createButton.setEnabled(true);
                            deleteButton.setEnabled(true);

                            // 8. Change the "Save" button back to "Edit"
                            editButton.setText("Edit");
                            editButton.removeActionListener(this);
                            editButton.addActionListener(new EditShowListener());

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(AdminDashboard.this, "Error updating show: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            }
        }

    }

    // Action listener for the Delete button
    private class DeleteShowListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Show selectedShow = showList.getSelectedValue();
            if (selectedShow != null) {
                int confirm = JOptionPane.showConfirmDialog(AdminDashboard.this, "Are you sure you want to delete this show?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    viewModel.deleteShow(selectedShow.getId());
                    loadShows(); // Refresh the show list
                }
            }
        }
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());

        JLabel name = new JLabel("Welcome " + viewModel.getUsersName());
        header.add(name, BorderLayout.WEST);

        JLabel title = new JLabel("Admin Dashboard");
        title.setHorizontalAlignment(JLabel.CENTER);
        header.add(title, BorderLayout.CENTER);

        JButton logout = new JButton("logout");
        logout.addActionListener(e -> viewModel.onLogoutButtonClick());
        header.add(logout, BorderLayout.EAST);

        return header;
    }
}
