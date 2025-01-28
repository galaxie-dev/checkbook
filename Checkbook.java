import java.util.ArrayList;
import java.util.List;

// The Checkbook class handles the checkbook operations such as deposit, withdraw and keeps track of transactions
public class Checkbook {
    private double balance;
    private List<String> transactions;

    // Constructor to initialize the checkbook with a zero balance and an empty transaction list
    public Checkbook() {
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    // Getter method to return the current balance
    public double getBalance() {
        return balance;
    }

    // Getter method to return the list of transactions
    public List<String> getTransactions() {
        return transactions;
    }

    // Method to deposit an amount, update the balance, and record the transaction
    public void deposit(double amount) {
        balance += amount;
        transactions.add("deposit: " + String.format("%.2f", amount));
    }

    // Method to withdraw an amount if there are sufficient funds, otherwise record a failed transaction
    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactions.add("withdraw: " + String.format("%.2f", amount));
        } else {
            transactions.add("failed withdrawal: " + String.format("%.2f", amount));
        }
    }
}

CheckbookGUI.java

// Import statements for Swing components and event handling
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// The CheckbookGUI class handles the graphical user interface for the checkbook application
public class CheckbookGUI extends JFrame {
    private Checkbook checkbook;
    private JTextArea transactionArea;
    private JTextField amountField;
    private JLabel balanceLabel;
    private JRadioButton depositButton;
    private JRadioButton withdrawButton;
    private JButton newTransactionButton;

    // Constructor to initialize the CheckbookGUI and display a welcome message
    public CheckbookGUI() {
        checkbook = new Checkbook();
        initUI();
        showMessage("Select transaction: deposit or withdraw");
    }

    // Method to initialize the user interface components
    private void initUI() {
        setTitle("Checkbook");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        transactionArea = new JTextArea(5, 20);
        transactionArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(transactionArea);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new GridLayout(7, 1, 5, 5));
        JPanel rightPanel = new JPanel(new GridLayout(4, 3, 5, 5));

        amountField = new JTextField();
        amountField.setPreferredSize(new Dimension(200, 30));

        newTransactionButton = new JButton("New Transaction");
        newTransactionButton.setPreferredSize(new Dimension(100, 40));
        newTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                amountField.setText("");
                depositButton.setSelected(false);
                withdrawButton.setSelected(false);
                showMessage("Select transaction: deposit or withdraw");
            }
        });

        depositButton = new JRadioButton("Deposit");
        withdrawButton = new JRadioButton("Withdraw");
        ButtonGroup group = new ButtonGroup();
        group.add(depositButton);
        group.add(withdrawButton);

        JButton transactButton = new JButton("Transact");
        transactButton.setPreferredSize(new Dimension(100, 40));
        transactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transact();
            }
        });

        // Allow the Enter key to trigger the transact action
        amountField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transact();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(100, 40));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        balanceLabel = new JLabel("Balance: 0.00");

        leftPanel.add(newTransactionButton);
        leftPanel.add(depositButton);
        leftPanel.add(withdrawButton);
        leftPanel.add(transactButton);
        leftPanel.add(exitButton);
        leftPanel.add(balanceLabel);

        for (int i = 1; i <= 9; i++) {
            addButton(rightPanel, String.valueOf(i));
        }
        addButton(rightPanel, ".");
        addButton(rightPanel, "0");
        addButton(rightPanel, "<");

        panel.add(amountField, BorderLayout.NORTH);
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }

    // Helper method to add buttons for numeric input and a backspace button
    private void addButton(JPanel panel, String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(50, 50));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("<".equals(text)) {
                    String currentText = amountField.getText();
                    if (currentText.length() > 0) {
                        amountField.setText(currentText.substring(0, currentText.length() - 1));
                    }
                } else {
                    amountField.setText(amountField.getText() + text);
                }
            }
        });
        panel.add(button);
    }

    // Method to handle transaction logic and update the UI accordingly
    private void transact() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (depositButton.isSelected()) {
                checkbook.deposit(amount);
                JOptionPane.showMessageDialog(CheckbookGUI.this, "Deposited: " + amount, "Transaction", JOptionPane.INFORMATION_MESSAGE);
            } else if (withdrawButton.isSelected()) {
                if (checkbook.getBalance() >= amount) {
                    checkbook.withdraw(amount);
                    JOptionPane.showMessageDialog(CheckbookGUI.this, "Withdrew: " + amount, "Transaction", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(CheckbookGUI.this, "Insufficient funds!", "Transaction", JOptionPane.ERROR_MESSAGE);
                }
            }
            updateUI();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(CheckbookGUI.this, "Invalid amount!", "Transaction", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to update the UI with the current balance and transaction history
    private void updateUI() {
        StringBuilder transactions = new StringBuilder();
        for (String transaction : checkbook.getTransactions()) {
            transactions.append(transaction).append("\n");
        }
        transactionArea.setText(transactions.toString());
        balanceLabel.setText("<html>Balance:<br>" + String.format("%.2f", checkbook.getBalance()) + "</html>");
    }

    // Method to display a message in the transaction area
    private void showMessage(String message) {
        transactionArea.setText(message);
    }
}
