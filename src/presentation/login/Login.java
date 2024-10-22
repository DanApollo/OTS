package presentation.login;

import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JPanel {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;
    private JButton toggleButton;

    private final LoginViewModel viewModel;

    public Login(LoginViewModel viewModel) {
        this.viewModel = viewModel;
        initialiseComponents();
    }

    private void initialiseComponents() {
        emailField = new JTextField(15);
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");
        statusLabel = new JLabel(" ");
        toggleButton = new JButton("Switch to Sign up");


        // Add action listener to the button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewModel.setUsername(emailField.getText());
                viewModel.setPassword(new String(passwordField.getPassword()));
                try {
                    viewModel.onLoginButtonClick();
                } catch (LoginException ex) {
                    displayErrorMessage(ex.getMessage());
                }
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.gridx = 0; // First column
        constraints.gridy = 0; // First row
        constraints.gridwidth = GridBagConstraints.REMAINDER; // Span all columns
        constraints.anchor = GridBagConstraints.NORTH; // Align to the left
        constraints.insets = new Insets(10, 10, 10, 10); // Add padding
        add(titleLabel, constraints);


        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.insets = new Insets(5, 5, 5, 5);

        int row = 1;

        constraints.gridwidth = 2;
        constraints.gridx = 0;

        addComponent(new JLabel("Email:"), constraints, 0, row, 1, 1);
        addComponent(emailField, constraints, 1, row++, 1, 1);

        addComponent(new JLabel("Password:"), constraints, 0, row, 1, 1);
        addComponent(passwordField, constraints, 1, row++, 1, 1);

        addComponent(statusLabel, constraints, 1, row++, 1, 1);

        addComponent(loginButton, constraints, 0, row++, 2, 1);

        addComponent(toggleButton, constraints, 0, row++, 2, 1);

        toggleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewModel.navigateTo("sign up");
            }
        });
        setVisible(true);

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
