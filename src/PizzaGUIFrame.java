import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame {

    // Components for pizza size
    private JRadioButton smallPizza, mediumPizza, largePizza, extraLargePizza;

    // Components for payment method
    private JRadioButton cashPayment, creditCardPayment;

    // Topping checkboxes
    private JCheckBox sauceCheeseTopping, pepperoniTopping, sausageTopping, hamTopping, baconTopping, mushroomsTopping,
            greenPeppersTopping, onionsTopping, bananaPeppersTopping, pineappleTopping, tomatoesTopping,
            extraCheeseTopping, extraSauceTopping;

    // Other components
    private JTextField subtotalField, taxField, totalField;
    private JSpinner qtySpinner;
    private JTable orderTable;
    private DefaultTableModel tableModel;
    private JButton addButton, removeButton, quitButton;

    private static final double TAX_RATE = 0.07; // 7% tax rate

    public PizzaGUIFrame() {
        // Set up the frame
        setTitle("Pizza Ordering System");
        setSize(750, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        initializeComponents();

        // Set up layout and add components
        setUpLayout();

        // Add event listeners for buttons
        addEventListeners();
    }

    private void initializeComponents() {
        // Radio buttons for pizza sizes
        smallPizza = new JRadioButton("Small ($10)");
        mediumPizza = new JRadioButton("Medium ($14)");
        largePizza = new JRadioButton("Large ($20)", true); // default selected
        extraLargePizza = new JRadioButton("Extra Large ($26)");

        ButtonGroup sizeGroup = new ButtonGroup();
        sizeGroup.add(smallPizza);
        sizeGroup.add(mediumPizza);
        sizeGroup.add(largePizza);
        sizeGroup.add(extraLargePizza);

        // Quantity spinner
        qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        // Topping checkboxes with prices
        sauceCheeseTopping = new JCheckBox("Sauce & Cheese ($0)");
        pepperoniTopping = new JCheckBox("Pepperoni ($0.50)");
        sausageTopping = new JCheckBox("Sausage ($0.50)");
        hamTopping = new JCheckBox("Ham ($0.50)");
        baconTopping = new JCheckBox("Bacon ($0.60)");
        mushroomsTopping = new JCheckBox("Mushrooms ($0.30)");
        greenPeppersTopping = new JCheckBox("Green Peppers ($0.30)");
        onionsTopping = new JCheckBox("Onions ($0.20)");
        bananaPeppersTopping = new JCheckBox("Banana Peppers ($0.70)");
        pineappleTopping = new JCheckBox("Pineapple ($0.70)");
        tomatoesTopping = new JCheckBox("Tomatoes ($0.80)");
        extraCheeseTopping = new JCheckBox("Extra Cheese ($1.0)");
        extraSauceTopping = new JCheckBox("Extra Sauce ($1.0)");

        // Radio buttons for payment method
        cashPayment = new JRadioButton("Cash", true); // default selected
        creditCardPayment = new JRadioButton("Credit Card");
        ButtonGroup paymentGroup = new ButtonGroup();
        paymentGroup.add(cashPayment);
        paymentGroup.add(creditCardPayment);

        // Text fields for subtotal, tax, and total
        subtotalField = new JTextField(10);
        taxField = new JTextField(10);
        totalField = new JTextField(10);

        // Buttons for adding, removing orders, and quitting
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        quitButton = new JButton("Quit");

        // Table to display order details
        tableModel = new DefaultTableModel(new Object[]{"Item", "Price", "Qty", "Toppings", "Payment", "Total"}, 0);
        orderTable = new JTable(tableModel);
    }

    private void setUpLayout() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Left panel for pizza sizes
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(smallPizza);
        leftPanel.add(mediumPizza);
        leftPanel.add(largePizza);
        leftPanel.add(extraLargePizza);

        panel.add(leftPanel, BorderLayout.WEST);

        // Center panel for table and buttons
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(orderTable), BorderLayout.CENTER);

        JPanel qtyPanel = new JPanel();
        qtyPanel.add(new JLabel("Qty"));
        qtyPanel.add(qtySpinner);
        qtyPanel.add(addButton);
        qtyPanel.add(removeButton);
        qtyPanel.add(quitButton);

        centerPanel.add(qtyPanel, BorderLayout.SOUTH);
        panel.add(centerPanel, BorderLayout.CENTER);

        // Right panel for total, payment, and toppings fields
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        // Add payment method options
        rightPanel.add(new JLabel("Payment Method"));
        rightPanel.add(cashPayment);
        rightPanel.add(creditCardPayment);

        // Add topping options
        rightPanel.add(new JLabel("Toppings"));
        rightPanel.add(sauceCheeseTopping);
        rightPanel.add(pepperoniTopping);
        rightPanel.add(sausageTopping);
        rightPanel.add(hamTopping);
        rightPanel.add(baconTopping);
        rightPanel.add(mushroomsTopping);
        rightPanel.add(greenPeppersTopping);
        rightPanel.add(onionsTopping);
        rightPanel.add(bananaPeppersTopping);
        rightPanel.add(pineappleTopping);
        rightPanel.add(tomatoesTopping);
        rightPanel.add(extraCheeseTopping);
        rightPanel.add(extraSauceTopping);

        // Add subtotal, tax, and total fields (smaller panel)
        JPanel totalPanel = new JPanel(new GridLayout(3, 2));
        totalPanel.setBorder(BorderFactory.createTitledBorder("Summary"));
        totalPanel.add(new JLabel("Subtotal:"));
        totalPanel.add(subtotalField);
        totalPanel.add(new JLabel("Tax (7%):"));
        totalPanel.add(taxField);
        totalPanel.add(new JLabel("Total:"));
        totalPanel.add(totalField);

        rightPanel.add(totalPanel);

        panel.add(rightPanel, BorderLayout.EAST);

        add(panel);
    }

    private void addEventListeners() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrder();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeOrder();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        PizzaGUIFrame.this,
                        "Are you sure you want to quit?",
                        "Confirm Quit",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0); // Close the application if the user confirms
                }
            }
        });

    }

    private void addOrder() {
        String size = "";
        double price = 0;

        // Determine pizza size and base price
        if (smallPizza.isSelected()) {
            size = "Small";
            price = 10.0;
        } else if (mediumPizza.isSelected()) {
            size = "Medium";
            price = 14.0;
        } else if (largePizza.isSelected()) {
            size = "Large";
            price = 20.0;
        } else if (extraLargePizza.isSelected()) {
            size = "Extra Large";
            price = 26.0;
        }

        // Get quantity
        int qty = (int) qtySpinner.getValue();

        // Calculate the cost of toppings
        StringBuilder toppings = new StringBuilder();
        double toppingsCost = 0;
        if (sauceCheeseTopping.isSelected()) {
            toppings.append("C S ");
            toppingsCost += 0.0;
        }
        if (pepperoniTopping.isSelected()) {
            toppings.append("P ");
            toppingsCost += 0.50;
        }
        if (sausageTopping.isSelected()) {
            toppings.append("Sa ");
            toppingsCost += 0.50;
        }
        if (hamTopping.isSelected()) {
            toppings.append("H ");
            toppingsCost += 0.50;
        }
        if (baconTopping.isSelected()) {
            toppings.append("B ");
            toppingsCost += 0.60;
        }
        if (mushroomsTopping.isSelected()) {
            toppings.append("M ");
            toppingsCost += 0.30;
        }
        if (greenPeppersTopping.isSelected()) {
            toppings.append("GP ");
            toppingsCost += 0.30;
        }
        if (onionsTopping.isSelected()) {
            toppings.append("O ");
            toppingsCost += 0.20;
        }
        if (bananaPeppersTopping.isSelected()) {
            toppings.append("BP ");
            toppingsCost += 0.70;
        }
        if (pineappleTopping.isSelected()) {
            toppings.append("Pi ");
            toppingsCost += 0.70;
        }
        if (tomatoesTopping.isSelected()) {
            toppings.append("T ");
            toppingsCost += 0.80;
        }
        if (extraCheeseTopping.isSelected()) {
            toppings.append("EC ");
            toppingsCost += 1.0;
        }
        if (extraSauceTopping.isSelected()) {
            toppings.append("ES ");
            toppingsCost += 1.0;
        }

        // Determine payment method
        String paymentMethod = cashPayment.isSelected() ? "Cash" : "Card";

        // Calculate total price for this order
        double itemTotalPrice = (price + toppingsCost) * qty;

        // Add the item to the table
        tableModel.addRow(new Object[]{size, String.format("$%.2f", price), qty, toppings.toString(), paymentMethod, String.format("$%.2f", itemTotalPrice)});

        // Update subtotal, tax, and total
        updateTotalSummary();
    }

    private void removeOrder() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.removeRow(selectedRow);
            updateTotalSummary();
        }
    }

    private void updateTotalSummary() {
        double subtotal = 0;

        // Calculate subtotal by summing the total column of the table
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String totalValue = (String) tableModel.getValueAt(i, 5);
            subtotal += Double.parseDouble(totalValue.replace("$", ""));
        }

        // Calculate tax and total
        double tax = subtotal * TAX_RATE;
        double total = subtotal + tax;

        // Update text fields
        subtotalField.setText(String.format("$%.2f", subtotal));
        taxField.setText(String.format("$%.2f", tax));
        totalField.setText(String.format("$%.2f", total));
    }


}
