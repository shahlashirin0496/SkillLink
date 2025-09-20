import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

// ====================== MAIN MENU ======================
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

// ====================== STUDENT PORTAL ======================
class StudentMainMenu extends JFrame {
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

// ====================== JOB LIST (Student View) ======================
class JobList extends JFrame {
    int studentId;
    DefaultListModel<String> model = new DefaultListModel<>();
    JList<String> jobList = new JList<>(model);

    public JobList(int studentId) {
        super("Available Jobs");
        this.studentId = studentId;
        setSize(500, 300);
        setLayout(new BorderLayout());

        loadJobs();
        add(new JScrollPane(jobList), BorderLayout.CENTER);

        JButton applyBtn = new JButton("Apply");
        applyBtn.addActionListener(e -> applyJob());
        add(applyBtn, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadJobs() {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/skilllink", "root", "Kochu");
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id, title FROM jobs")) {

            while (rs.next()) {
                model.addElement(rs.getInt("id") + " - " + rs.getString("title"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void applyJob() {
        String selected = jobList.getSelectedValue();
        if (selected == null) return;
        int jobId = Integer.parseInt(selected.split(" - ")[0]);

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/skilllink", "root", "Kochu");
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO applications (job_id, user_id) VALUES (?, ?)")) {

            ps.setInt(1, jobId);
            ps.setInt(2, studentId);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Applied successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error applying: " + ex.getMessage());
        }
    }
}

// ====================== COMPANY PORTAL ======================
class CompanyMainMenu extends JFrame {
    public CompanyMainMenu() {
        super("Company Portal");
        setSize(400, 300);
        setLayout(null);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(120, 50, 150, 30);
        loginBtn.addActionListener(e -> new CompanyLogin());

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(120, 100, 150, 30);
        registerBtn.addActionListener(e -> new CompanyRegister());

        add(loginBtn);
        add(registerBtn);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}

// ====================== COMPANY LOGIN ======================
class CompanyLogin extends JFrame {
    JTextField usernameField;
    JPasswordField passwordField;

    public CompanyLogin() {
        super("Company Login");
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
                     "SELECT id, name FROM companies WHERE username=? AND password=?")) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int companyId = rs.getInt("id");
                String name = rs.getString("name");
                JOptionPane.showMessageDialog(this, "Welcome " + name);
                new CompanyDashboard(companyId);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

// ====================== COMPANY REGISTER ======================
class CompanyRegister extends JFrame {
    JTextField nameField, usernameField, industryField;
    JPasswordField passwordField;

    public CompanyRegister() {
        super("Company Register");
        setSize(400, 350);
        setLayout(null);

        JLabel nameLabel = new JLabel("Company Name:");
        nameLabel.setBounds(50, 50, 120, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(180, 50, 150, 25);
        add(nameField);

        JLabel indLabel = new JLabel("Industry:");
        indLabel.setBounds(50, 100, 120, 25);
        add(indLabel);

        industryField = new JTextField();
        industryField.setBounds(180, 100, 150, 25);
        add(industryField);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 150, 120, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(180, 150, 150, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 200, 120, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(180, 200, 150, 25);
        add(passwordField);

        JButton regBtn = new JButton("Register");
        regBtn.setBounds(150, 250, 100, 30);
        regBtn.addActionListener(e -> register());
        add(regBtn);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void register() {
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String industry = industryField.getText();

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/skilllink", "root", "Kochu");
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO companies (name, username, password, industry) VALUES (?, ?, ?, ?)")) {

            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, industry);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Company registered!");
            dispose();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

// ====================== COMPANY DASHBOARD ======================
class CompanyDashboard extends JFrame {
    int companyId;

    public CompanyDashboard(int companyId) {
        super("Company Dashboard");
        this.companyId = companyId;
        setSize(600, 400);
        setLayout(new FlowLayout());

        JButton postJobBtn = new JButton("Post Job");
        postJobBtn.addActionListener(e -> new PostJob(companyId));

        JButton viewAppsBtn = new JButton("View Applications");
        viewAppsBtn.addActionListener(e -> new ViewApplications(companyId));

        add(postJobBtn);
        add(viewAppsBtn);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}

// ====================== POST JOB ======================
class PostJob extends JFrame {
    JTextField titleField;
    JTextArea descArea;
    int companyId;

    public PostJob(int companyId) {
        super("Post Job");
        this.companyId = companyId;
        setSize(400, 300);
        setLayout(null);

        JLabel titleLbl = new JLabel("Job Title:");
        titleLbl.setBounds(50, 50, 100, 25);
        add(titleLbl);

        titleField = new JTextField();
        titleField.setBounds(150, 50, 180, 25);
        add(titleField);

        JLabel descLbl = new JLabel("Description:");
        descLbl.setBounds(50, 100, 100, 25);
        add(descLbl);

        descArea = new JTextArea();
        descArea.setBounds(150, 100, 180, 80);
        add(descArea);

        JButton postBtn = new JButton("Post");
        postBtn.setBounds(150, 200, 100, 30);
        postBtn.addActionListener(e -> postJob());
        add(postBtn);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void postJob() {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/skilllink", "root", "Kochu");
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO jobs (company_id, title, description) VALUES (?, ?, ?)")) {

            ps.setInt(1, companyId);
            ps.setString(2, titleField.getText());
            ps.setString(3, descArea.getText());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Job posted!");
            dispose();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

// ====================== VIEW APPLICATIONS ======================
class ViewApplications extends JFrame {
    int companyId;
    DefaultTableModel tableModel = new DefaultTableModel(new String[]{"App ID","Student","Mobile","Job","Status"}, 0);
    JTable table = new JTable(tableModel);

    public ViewApplications(int companyId) {
        super("Applications Received");
        this.companyId = companyId;
        setSize(700, 400);
        setLayout(new BorderLayout());

        loadApplications();
        add(new JScrollPane(table), BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadApplications() {
        tableModel.setRowCount(0); // clear old entries
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/skilllink", "root", "Kochu");
             PreparedStatement ps = con.prepareStatement(
                     "SELECT a.id, u.name, u.mobile, j.title, a.status " +
                             "FROM applications a " +
                             "JOIN users u ON a.user_id = u.id " +
                             "JOIN jobs j ON a.job_id = j.id " +
                             "JOIN companies c ON j.company_id = c.id " +
                             "WHERE c.id = ?")) {

            ps.setInt(1, companyId);
            ResultSet rs = ps.executeQuery();

            boolean hasResults = false;
            while (rs.next()) {
                hasResults = true;
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("mobile"),
                        rs.getString("title"),
                        rs.getString("status")
                });
            }

            if (!hasResults) {
                tableModel.addRow(new Object[]{"-", "No applications found", "-", "-", "-"});
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            tableModel.addRow(new Object[]{"-", "Database error: "+ex.getMessage(), "-", "-", "-"});
        }
    }
}
