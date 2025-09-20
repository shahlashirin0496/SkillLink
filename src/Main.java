import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

// ====================== REFACTORED MAIN CLASS ======================
public class Main extends JFrame {
    public Main() {
        super("SkillLink Portal");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Use CardLayout for switching between panels
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(new Color(30, 30, 30));

        // Add the main menu to the card layout
        mainPanel.add(new MainMenuPanel(cardLayout, mainPanel), "MainMenu");

        // Set the content pane to the main panel
        setContentPane(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Set a modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new Main());
    }
}

// ====================== REFACTORED PANELS (Encapsulated UI) ======================

class MainMenuPanel extends JPanel {
    public MainMenuPanel(CardLayout layout, JPanel parentPanel) {
        setLayout(new GridBagLayout());
        setBackground(new Color(30, 30, 30));

        JLabel titleLabel = new JLabel("SkillLink Portal 🚀");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(236, 240, 241)); // Light gray

        JButton studentBtn = createStyledButton("Student Portal 🎓", new Color(52, 152, 219)); // PeterRiver
        studentBtn.addActionListener(e -> {
            parentPanel.add(new StudentMenuPanel(layout, parentPanel), "StudentMenu");
            layout.show(parentPanel, "StudentMenu");
        });

        JButton companyBtn = createStyledButton("Company Portal 🏢", new Color(231, 76, 60)); // Alizarin
        companyBtn.addActionListener(e -> {
            parentPanel.add(new CompanyMenuPanel(layout, parentPanel), "CompanyMenu");
            layout.show(parentPanel, "CompanyMenu");
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        add(studentBtn, gbc);

        gbc.gridy = 2;
        add(companyBtn, gbc);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(new Color(50, 50, 50)); // Dark gray
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        return button;
    }
}

class StudentMenuPanel extends JPanel {
    public StudentMenuPanel(CardLayout layout, JPanel parentPanel) {
        setLayout(new GridBagLayout());
        setBackground(new Color(30, 30, 30));

        JLabel titleLabel = new JLabel("Student Portal");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(236, 240, 241));

        JButton loginBtn = createStyledButton("Login", new Color(46, 204, 113));
        loginBtn.addActionListener(e -> {
            parentPanel.add(new StudentLoginPanel(layout, parentPanel), "StudentLogin");
            layout.show(parentPanel, "StudentLogin");
        });

        JButton registerBtn = createStyledButton("Register", new Color(52, 152, 219));
        registerBtn.addActionListener(e -> {
            parentPanel.add(new StudentRegisterPanel(layout, parentPanel), "StudentRegister");
            layout.show(parentPanel, "StudentRegister");
        });

        JButton backBtn = createBackButton(layout, parentPanel, "MainMenu");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(loginBtn, gbc);

        gbc.gridy = 2;
        add(registerBtn, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(20, 0, 10, 0);
        add(backBtn, gbc);
    }
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(new Color(50, 50, 50)); // Dark gray
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        return button;
    }

    private JButton createBackButton(CardLayout layout, JPanel parentPanel, String cardName) {
        JButton button = new JButton("Back");
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(new Color(60, 60, 60)); // Darker gray
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.addActionListener(e -> layout.show(parentPanel, cardName));
        return button;
    }
}

class StudentLoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private CardLayout layout;
    private JPanel parentPanel;

    public StudentLoginPanel(CardLayout layout, JPanel parentPanel) {
        this.layout = layout;
        this.parentPanel = parentPanel;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(30, 30, 30));
        setBorder(new EmptyBorder(40, 60, 40, 60));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Student Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(236, 240, 241));

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(new Color(236, 240, 241));
        formPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBackground(new Color(50, 50, 50));
        usernameField.setForeground(Color.WHITE);
        formPanel.add(usernameField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passLabel.setForeground(new Color(236, 240, 241));
        formPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBackground(new Color(50, 50, 50));
        passwordField.setForeground(Color.WHITE);
        formPanel.add(passwordField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(52, 152, 219));
        loginBtn.setForeground(new Color(50, 50, 50)); // Dark gray
        loginBtn.setFocusPainted(false);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginBtn.addActionListener(e -> login());
        formPanel.add(loginBtn, gbc);

        add(formPanel, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backBtn.setBackground(new Color(60, 60, 60));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> layout.show(parentPanel, "StudentMenu"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/skilllink", "root", "Kochu");
             PreparedStatement ps = con.prepareStatement("SELECT id, name FROM users WHERE username=? AND password=?")) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int studentId = rs.getInt("id");
                String name = rs.getString("name");
                JOptionPane.showMessageDialog(this, "Welcome " + name + "!");
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                frame.dispose(); // Close the current window
                new JobList(studentId); // Open the new window
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Login Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class StudentRegisterPanel extends JPanel {
    private JTextField nameField, usernameField, interestField, mobileField;
    private JPasswordField passwordField;
    private CardLayout layout;
    private JPanel parentPanel;

    public StudentRegisterPanel(CardLayout layout, JPanel parentPanel) {
        this.layout = layout;
        this.parentPanel = parentPanel;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(30, 30, 30));
        setBorder(new EmptyBorder(20, 60, 20, 60));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel titleLabel = new JLabel("Student Registration");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(236, 240, 241));

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(new Color(236, 240, 241));
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        nameField.setBackground(new Color(50, 50, 50));
        nameField.setForeground(Color.WHITE);
        formPanel.add(nameField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel interestLabel = new JLabel("Field of Interest:");
        interestLabel.setForeground(new Color(236, 240, 241));
        formPanel.add(interestLabel, gbc);
        gbc.gridx = 1;
        interestField = new JTextField(15);
        interestField.setBackground(new Color(50, 50, 50));
        interestField.setForeground(Color.WHITE);
        formPanel.add(interestField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel mobileLabel = new JLabel("Mobile No:");
        mobileLabel.setForeground(new Color(236, 240, 241));
        formPanel.add(mobileLabel, gbc);
        gbc.gridx = 1;
        mobileField = new JTextField(15);
        mobileField.setBackground(new Color(50, 50, 50));
        mobileField.setForeground(Color.WHITE);
        formPanel.add(mobileField, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(new Color(236, 240, 241));
        formPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        usernameField.setBackground(new Color(50, 50, 50));
        usernameField.setForeground(Color.WHITE);
        formPanel.add(usernameField, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(new Color(236, 240, 241));
        formPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        passwordField.setBackground(new Color(50, 50, 50));
        passwordField.setForeground(Color.WHITE);
        formPanel.add(passwordField, gbc);

        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(46, 204, 113));
        registerBtn.setForeground(new Color(50, 50, 50)); // Dark gray
        registerBtn.setFocusPainted(false);
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        registerBtn.addActionListener(e -> register());
        formPanel.add(registerBtn, gbc);

        add(formPanel, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backBtn.setBackground(new Color(60, 60, 60));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> layout.show(parentPanel, "StudentMenu"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void register() {
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String interest = interestField.getText();
        String mobile = mobileField.getText();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || interest.isEmpty() || mobile.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/skilllink", "root", "Kochu");
             PreparedStatement ps = con.prepareStatement("INSERT INTO users (name, username, password, role, extra_info, mobile) VALUES (?, ?, ?, 'Student', ?, ?)")) {

            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, interest);
            ps.setString(5, mobile);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registered successfully! You can now log in.");
            layout.show(parentPanel, "StudentLogin");

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error registering student: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

// ====================== STUDENT JOB LIST WINDOW ======================
class JobList extends JFrame {
    int studentId;
    DefaultListModel<String> model = new DefaultListModel<>();
    JList<String> jobList = new JList<>(model);

    public JobList(int studentId) {
        super("Available Jobs");
        this.studentId = studentId;
        setSize(600, 450);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(30, 30, 30));

        JLabel titleLabel = new JLabel("Available Jobs", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(236, 240, 241));
        add(titleLabel, BorderLayout.NORTH);

        loadJobs();
        jobList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jobList.setBackground(new Color(50, 50, 50));
        jobList.setForeground(new Color(236, 240, 241));
        jobList.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(new JScrollPane(jobList), BorderLayout.CENTER);

        JButton applyBtn = new JButton("Apply for Selected Job");
        applyBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        applyBtn.setBackground(new Color(52, 152, 219));
        applyBtn.setForeground(new Color(50, 50, 50)); // Dark gray
        applyBtn.setFocusPainted(false);
        applyBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        applyBtn.addActionListener(e -> applyJob());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(applyBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadJobs() {
        model.clear();
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/skilllink", "root", "Kochu");
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id, title FROM jobs")) {

            while (rs.next()) {
                model.addElement(rs.getInt("id") + " - " + rs.getString("title"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading jobs: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void applyJob() {
        String selected = jobList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a job to apply for.");
            return;
        }
        int jobId = Integer.parseInt(selected.split(" - ")[0]);

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/skilllink", "root", "Kochu");
             PreparedStatement ps = con.prepareStatement("INSERT INTO applications (job_id, user_id) VALUES (?, ?)")) {

            ps.setInt(1, jobId);
            ps.setInt(2, studentId);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Applied successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error applying: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

// ====================== COMPANY PANELS (REFACTORED) ======================

class CompanyMenuPanel extends JPanel {
    public CompanyMenuPanel(CardLayout layout, JPanel parentPanel) {
        setLayout(new GridBagLayout());
        setBackground(new Color(30, 30, 30));

        JLabel titleLabel = new JLabel("Company Portal");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(236, 240, 241));

        JButton loginBtn = createStyledButton("Login", new Color(231, 76, 60));
        loginBtn.addActionListener(e -> {
            parentPanel.add(new CompanyLoginPanel(layout, parentPanel), "CompanyLogin");
            layout.show(parentPanel, "CompanyLogin");
        });

        JButton registerBtn = createStyledButton("Register", new Color(243, 156, 18));
        registerBtn.addActionListener(e -> {
            parentPanel.add(new CompanyRegisterPanel(layout, parentPanel), "CompanyRegister");
            layout.show(parentPanel, "CompanyRegister");
        });

        JButton backBtn = createBackButton(layout, parentPanel, "MainMenu");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(loginBtn, gbc);

        gbc.gridy = 2;
        add(registerBtn, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(20, 0, 10, 0);
        add(backBtn, gbc);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(new Color(50, 50, 50)); // Dark gray
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        return button;
    }

    private JButton createBackButton(CardLayout layout, JPanel parentPanel, String cardName) {
        JButton button = new JButton("Back");
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(new Color(60, 60, 60));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.addActionListener(e -> layout.show(parentPanel, cardName));
        return button;
    }
}

class CompanyLoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private CardLayout layout;
    private JPanel parentPanel;

    public CompanyLoginPanel(CardLayout layout, JPanel parentPanel) {
        this.layout = layout;
        this.parentPanel = parentPanel;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(30, 30, 30));
        setBorder(new EmptyBorder(40, 60, 40, 60));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel titleLabel = new JLabel("Company Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(236, 240, 241));

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(new Color(236, 240, 241));
        formPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBackground(new Color(50, 50, 50));
        usernameField.setForeground(Color.WHITE);
        formPanel.add(usernameField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passLabel.setForeground(new Color(236, 240, 241));
        formPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBackground(new Color(50, 50, 50));
        passwordField.setForeground(Color.WHITE);
        formPanel.add(passwordField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(231, 76, 60));
        loginBtn.setForeground(new Color(50, 50, 50)); // Dark gray
        loginBtn.setFocusPainted(false);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginBtn.addActionListener(e -> login());
        formPanel.add(loginBtn, gbc);

        add(formPanel, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backBtn.setBackground(new Color(60, 60, 60));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> layout.show(parentPanel, "CompanyMenu"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/skilllink", "root", "Kochu");
             PreparedStatement ps = con.prepareStatement("SELECT id, name FROM companies WHERE username=? AND password=?")) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int companyId = rs.getInt("id");
                String name = rs.getString("name");
                JOptionPane.showMessageDialog(this, "Welcome " + name + "!");
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                frame.dispose();
                new CompanyDashboard(companyId);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Login Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class CompanyRegisterPanel extends JPanel {
    private JTextField nameField, usernameField, industryField;
    private JPasswordField passwordField;
    private CardLayout layout;
    private JPanel parentPanel;

    public CompanyRegisterPanel(CardLayout layout, JPanel parentPanel) {
        this.layout = layout;
        this.parentPanel = parentPanel;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(30, 30, 30));
        setBorder(new EmptyBorder(20, 60, 20, 60));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel titleLabel = new JLabel("Company Registration");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(236, 240, 241));

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel nameLabel = new JLabel("Company Name:");
        nameLabel.setForeground(new Color(236, 240, 241));
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        nameField.setBackground(new Color(50, 50, 50));
        nameField.setForeground(Color.WHITE);
        formPanel.add(nameField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel industryLabel = new JLabel("Industry:");
        industryLabel.setForeground(new Color(236, 240, 241));
        formPanel.add(industryLabel, gbc);
        gbc.gridx = 1;
        industryField = new JTextField(15);
        industryField.setBackground(new Color(50, 50, 50));
        industryField.setForeground(Color.WHITE);
        formPanel.add(industryField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(new Color(236, 240, 241));
        formPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        usernameField.setBackground(new Color(50, 50, 50));
        usernameField.setForeground(Color.WHITE);
        formPanel.add(usernameField, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(new Color(236, 240, 241));
        formPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        passwordField.setBackground(new Color(50, 50, 50));
        passwordField.setForeground(Color.WHITE);
        formPanel.add(passwordField, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton regBtn = new JButton("Register");
        regBtn.setBackground(new Color(243, 156, 18));
        regBtn.setForeground(new Color(50, 50, 50)); // Dark gray
        regBtn.setFocusPainted(false);
        regBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        regBtn.addActionListener(e -> register());
        formPanel.add(regBtn, gbc);

        add(formPanel, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backBtn.setBackground(new Color(60, 60, 60));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> layout.show(parentPanel, "CompanyMenu"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void register() {
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String industry = industryField.getText();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || industry.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/skilllink", "root", "Kochu");
             PreparedStatement ps = con.prepareStatement("INSERT INTO companies (name, username, password, industry) VALUES (?, ?, ?, ?)")) {

            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, industry);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Company registered successfully! You can now log in.");
            layout.show(parentPanel, "CompanyLogin");

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error registering company: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

// ====================== COMPANY DASHBOARD WINDOW ======================
class CompanyDashboard extends JFrame {
    int companyId;

    public CompanyDashboard(int companyId) {
        super("Company Dashboard");
        this.companyId = companyId;
        setSize(700, 500);
        setLayout(new BorderLayout(15, 15));
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(30, 30, 30));

        JLabel titleLabel = new JLabel("Welcome, Company! 💼", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(236, 240, 241));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton postJobBtn = createDashboardButton("Post a New Job");
        postJobBtn.addActionListener(e -> new PostJob(companyId));

        JButton viewAppsBtn = createDashboardButton("View Applications");
        viewAppsBtn.addActionListener(e -> new ViewApplications(companyId));

        buttonPanel.add(postJobBtn);
        buttonPanel.add(viewAppsBtn);
        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton createDashboardButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(41, 128, 185));
        button.setForeground(new Color(50, 50, 50)); // Dark gray
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 60));
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        return button;
    }
}

// ====================== POST JOB WINDOW ======================
class PostJob extends JFrame {
    private JTextField titleField;
    private JTextArea descArea;
    private int companyId;

    public PostJob(int companyId) {
        super("Post a New Job");
        this.companyId = companyId;
        setSize(450, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(30, 30, 30));

        JLabel titleLabel = new JLabel("Post a New Job", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(236, 240, 241));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel titleLabelField = new JLabel("Job Title:");
        titleLabelField.setForeground(new Color(236, 240, 241));
        formPanel.add(titleLabelField, gbc);
        gbc.gridx = 1;
        titleField = new JTextField(20);
        titleField.setBackground(new Color(50, 50, 50));
        titleField.setForeground(Color.WHITE);
        formPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel descLabel = new JLabel("Description:");
        descLabel.setForeground(new Color(236, 240, 241));
        formPanel.add(descLabel, gbc);
        gbc.gridx = 1;
        descArea = new JTextArea(5, 20);
        descArea.setLineWrap(true);
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descArea.setBackground(new Color(50, 50, 50));
        descArea.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(descArea);
        scrollPane.getViewport().setBackground(new Color(50, 50, 50));
        formPanel.add(scrollPane, gbc);

        add(formPanel, BorderLayout.CENTER);

        JButton postBtn = new JButton("Post Job");
        postBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        postBtn.setBackground(new Color(231, 76, 60));
        postBtn.setForeground(new Color(50, 50, 50)); // Dark gray
        postBtn.setFocusPainted(false);
        postBtn.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        postBtn.addActionListener(e -> postJob());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(postBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void postJob() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/skilllink", "root", "Kochu");
             PreparedStatement ps = con.prepareStatement("INSERT INTO jobs (company_id, title, description) VALUES (?, ?, ?)")) {

            ps.setInt(1, companyId);
            ps.setString(2, titleField.getText());
            ps.setString(3, descArea.getText());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Job posted successfully!");
            dispose();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error posting job: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

// ====================== VIEW APPLICATIONS WINDOW ======================
class ViewApplications extends JFrame {
    private int companyId;
    private DefaultTableModel tableModel = new DefaultTableModel(new String[]{"App ID", "Student Name", "Mobile", "Job Title", "Status"}, 0);
    private JTable table = new JTable(tableModel);

    public ViewApplications(int companyId) {
        super("Applications Received");
        this.companyId = companyId;
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(30, 30, 30));

        JLabel titleLabel = new JLabel("Applications Received", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(236, 240, 241));
        add(titleLabel, BorderLayout.NORTH);

        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(50, 50, 50));
        table.getTableHeader().setForeground(Color.WHITE);

        table.setBackground(new Color(50, 50, 50));
        table.setForeground(new Color(236, 240, 241));

        loadApplications();
        add(new JScrollPane(table), BorderLayout.CENTER);

        setVisible(true);
    }

    private void loadApplications() {
        tableModel.setRowCount(0);
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/skilllink", "root", "Kochu");
             PreparedStatement ps = con.prepareStatement(
                     "SELECT a.id, u.name, u.mobile, j.title, a.status " +
                             "FROM applications a " +
                             "JOIN users u ON a.user_id = u.id " +
                             "JOIN jobs j ON a.job_id = j.id " +
                             "WHERE j.company_id = ?")) {

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
                tableModel.addRow(new Object[]{"-", "No applications found.", "-", "-", "-"});
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            tableModel.addRow(new Object[]{"-", "Database error: " + ex.getMessage(), "-", "-", "-"});
        }
    }
}