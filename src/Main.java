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
