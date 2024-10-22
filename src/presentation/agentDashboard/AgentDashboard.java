package presentation.agentDashboard;

import model.impl.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class AgentDashboard extends JPanel {

    private final AgentDashboardViewModel viewModel;
    private List<Customer> customers;

    public AgentDashboard(AgentDashboardViewModel viewModel) {
        this.viewModel = viewModel;
        customers = viewModel.getAllCustomers();

        setLayout(new BorderLayout());

        // Customer List (West)
        JList<Customer> customerList = new JList<>(customers.toArray(new Customer[0]));
        customerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customerList.setCellRenderer(new CustomerListRenderer());
        JScrollPane customerListScrollPane = new JScrollPane(customerList);
        add(customerListScrollPane, BorderLayout.WEST);

        // Add a listener to navigate to the user dashboard when a customer is selected
        customerList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Customer selectedCustomer = customerList.getSelectedValue();
                if (selectedCustomer != null) {
                    viewModel.onLogin(selectedCustomer);
                }
            }
        });
    }

    // Custom cell renderer for the JList
    static class CustomerListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Customer customer) {
                // Customise how the customer is displayed in the list
                String displayText = customer.getName() + " (" + customer.getEmail() + ")";
                return super.getListCellRendererComponent(list, displayText, index, isSelected, cellHasFocus);
            } else {
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            }
        }
    }
}
