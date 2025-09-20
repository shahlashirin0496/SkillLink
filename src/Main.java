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



