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

