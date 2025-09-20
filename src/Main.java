import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;


public class Main extends JFrame {
    public Main() {
        super("SkillLink Portal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JButton studentBtn = new JButton("Student Portal");
        studentBtn.setBounds(120, 50, 150, 30);
        studentBtn.addActionListener(e -> new StudentMainMenu());

        JButton companyBtn = new JButton("Company Portal");
        companyBtn.setBounds(120, 100, 150, 30);
        companyBtn.addActionListener(e -> new CompanyMainMenu());

        add(studentBtn);
        add(companyBtn);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
JFrame {
    public StudentMainMenu() {
        super("Student Portal");
        setSize(400, 300);
        setLayout(null);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(120, 50, 150, 30);
        loginBtn.addActionListener(e -> new StudentLogin());

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(120, 100, 150, 30);
        registerBtn.addActionListener(e -> new StudentRegister());

        add(loginBtn);
        add(registerBtn);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
// ====================== STUDENT LOGIN ======================
class StudentLogin extends JFrame {
    JTextField usernameField;
    JPasswordField passwordField;

    public StudentLogin() {
        super("Student Login");
        setSize(400, 250);
        setLayout(null);

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
                "jdbc:mysql://localhost:3306/skilllink", "root", "Kochu");
             PreparedStatement ps = con.prepareStatement(
                     "SELECT id, name FROM users WHERE username=? AND password=?")) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int studentId = rs.getInt("id");
                String name = rs.getString("name");
                JOptionPane.showMessageDialog(this, "Welcome " + name);
                new JobList(studentId);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
// ====================== STUDENT REGISTER ======================
class StudentRegister extends JFrame {
    JTextField nameField, usernameField, interestField, mobileField;
    JPasswordField passwordField;

    public StudentRegister() {
        super("Student Register");
        setSize(400, 450);
        setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 50, 180, 25);
        add(nameField);

        JLabel interestLabel = new JLabel("Field of Interest:");
        interestLabel.setBounds(50, 100, 120, 25);
        add(interestLabel);

        interestField = new JTextField();
        interestField.setBounds(150, 100, 180, 25);
        add(interestField);

        JLabel mobileLabel = new JLabel("Mobile No:");
        mobileLabel.setBounds(50, 150, 100, 25);
        add(mobileLabel);

        mobileField = new JTextField();
        mobileField.setBounds(150, 150, 180, 25);
        add(mobileField);

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
        String interest = interestField.getText();
        String mobile = mobileField.getText();

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/skilllink", "root", "Kochu");
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO users (name, username, password, role, extra_info, mobile) VALUES (?, ?, ?, 'Student', ?, ?)")) {

            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, interest);
            ps.setString(5, mobile);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registered successfully!");
            dispose();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error registering student: " + ex.getMessage());
        }
    }
}





