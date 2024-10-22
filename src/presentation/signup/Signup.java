package presentation.signup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Signup extends JPanel {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton signupButton;
    private JLabel statusLabel;
    private JButton toggleButton;

    private final SignupViewModel viewModel;

    public Signup(SignupViewModel viewModel) {
        this.viewModel = viewModel;
        initialiseComponents();
    }

    private void initialiseComponents() {
        nameField = new JTextField(15);
        emailField = new JTextField(15);
        passwordField = new JPasswordField(15);
        signupButton = new JButton("Sign up");
        statusLabel = new JLabel(" ");
        toggleButton = new JButton("Switch to Login");

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewModel.setName(nameField.getText());
                viewModel.setEmail(emailField.getText());
                viewModel.setPassword(new String(passwordField.getPassword()));
                try {
                    viewModel.onSignupButtonClick();
                } catch (SignupViewModel.SignupException ex) {
                    displayErrorMessage(ex.getMessage());
                }
            }
        });
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Sign up");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.gridx = 0; // First column
        constraints.gridy = 0; // First row
        constraints.gridwidth = GridBagConstraints.REMAINDER; // Span all columns
        constraints.anchor = GridBagConstraints.NORTH; // Align to the left
        constraints.insets = new Insets(10, 10, 10, 10);
        add(titleLabel, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.insets = new Insets(5, 5, 5, 5);

        int row = 1;

        addComponent(new JLabel("Name:"), constraints, 0, row, 1, 1);
        addComponent(nameField, constraints, 1, row++, 1, 1);

        addComponent(new JLabel("Email:"), constraints, 0, row, 1, 1);
        addComponent(emailField, constraints, 1, row++, 1, 1);

        addComponent(new JLabel("Password:"), constraints, 0, row, 1, 1);
        addComponent(passwordField, constraints, 1, row++, 1, 1);

        constraints.gridwidth = 2; // Span both columns for the button and status label
        constraints.gridx = 0;
        addComponent(signupButton, constraints, 0, row++, 2, 1);
        addComponent(statusLabel, constraints, 0, row, 2, 1);
        addComponent(toggleButton, constraints, 0, row, 2, 1);

        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewModel.navigateTo("login");
            }
        });
    }

    private void addComponent(Component comp, GridBagConstraints constraints, int x, int y, int width, int height) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        add(comp, constraints);
    }

    public void displayErrorMessage(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(Color.RED);
    }
}
