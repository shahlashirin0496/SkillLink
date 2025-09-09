import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Main extends JFrame {
    public Main() {
        super("Login/Register Demo");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(120, 50, 150, 30);
        loginBtn.addActionListener(e -> new LoginPage());

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(120, 100, 150, 30);
        registerBtn.addActionListener(e -> new RegisterPage());

        add(loginBtn);
        add(registerBtn);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}

// ====================== LOGIN PAGE ======================
class LoginPage extends JFrame {
    JTextField usernameField;
    JPasswordField passwordField;

    public LoginPage() {
        super("Login");
        setSize(400, 250);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 100, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 50, 180, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 100, 100, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 180, 25);
        add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(150, 150, 100, 30);
        loginBtn.addActionListener(e -> login());
        add(loginBtn);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/skilllink",
                "root",
                "Kochu");
             PreparedStatement ps = con.prepareStatement("SELECT name FROM users WHERE username=? AND password=?")) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new WelcomeFrame(name); // Open new frame
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

// ====================== REGISTER PAGE ======================
class RegisterPage extends JFrame {
    JTextField nameField, usernameField, interestField;
    JPasswordField passwordField;
    JComboBox<String> roleBox;

    public RegisterPage() {
        super("Register");
        setSize(400, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 50, 180, 25);
        add(nameField);

        JLabel roleLabel = new JLabel("Register As:");
        roleLabel.setBounds(50, 100, 100, 25);
        add(roleLabel);

        roleBox = new JComboBox<>(new String[]{"Student", "Employee"});
        roleBox.setBounds(150, 100, 180, 25);
        add(roleBox);

        JLabel interestLabel = new JLabel("Field of Interest:");
        interestLabel.setBounds(50, 150, 120, 25);
        add(interestLabel);

        interestField = new JTextField();
        interestField.setBounds(150, 150, 180, 25);
        add(interestField);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 200, 100, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 200, 180, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 250, 100, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 250, 180, 25);
        add(passwordField);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(150, 300, 100, 30);
        registerBtn.addActionListener(e -> register());
        add(registerBtn);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void register() {
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleBox.getSelectedItem();
        String interest = interestField.getText();

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/skilllink?useSSL=false&serverTimezone=UTC",
                "root",
                "Kochu");
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO users (name, username, password, role, extra_info) VALUES (?, ?, ?, ?, ?)")) {

            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, role);
            ps.setString(5, interest);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registration Successful!");
            dispose();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Username already exists or database error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

// ====================== WELCOME FRAME ======================
class WelcomeFrame extends JFrame {
    public WelcomeFrame(String name) {
        super("Welcome");
        setSize(400, 200);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel welcomeLabel = new JLabel("Welcome, " + name + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
