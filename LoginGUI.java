// Import statements for Swing components and event handling
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// The LoginGUI class handles the graphical user interface for the login screen
public class LoginGUI extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;

    // Constructor to initialize the LoginGUI
    public LoginGUI() {
        initUI();
    }

    // Method to initialize the user interface components for the login screen
    private void initUI() {
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        userField = new JTextField();
        userField.setPreferredSize(new Dimension(150, 30));

        JLabel passLabel = new JLabel("Password:");
        passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(150, 30));

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        // Allow the Enter key to trigger the login action
        passField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel()); // Placeholder
        panel.add(loginButton);

        add(panel);
    }

    // Method to handle login logic
    private void login() {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        if (authenticate(username, password)) {
            EventQueue.invokeLater(() -> {
                CheckbookGUI checkbookGUI = new CheckbookGUI();
                checkbookGUI.setVisible(true);
            });
            dispose();
        } else {
            JOptionPane.showMessageDialog(LoginGUI.this,
                    "Invalid username or password",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to authenticate the user (for demonstration purposes, the username is "user" and the password is "1234")
    private boolean authenticate(String username, String password) {
        return "user".equals(username) && "1234".equals(password);
    }

    // Main method to run the LoginGUI application
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            LoginGUI loginGUI = new LoginGUI();
            loginGUI.setVisible(true);
        });
    }
}
