package src.panels;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProgressPanel extends JPanel {

    public ProgressPanel() {
        setLayout(null);
        setBounds(0, 0, 400, 800);

        GradientPanel backgroundPanel = new GradientPanel();
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, 400, 800);
        backgroundPanel.setOpaque(false);
        add(backgroundPanel);

        JLabel title = new JLabel("📈 Progress Tracker", SwingConstants.CENTER);
        title.setBounds(50, 20, 300, 40);
        title.setForeground(new Color(255, 255, 255));
        title.setFont(new Font("Verdana", Font.BOLD, 20));
        title.setOpaque(false);
        backgroundPanel.add(title);

        // Table (Transparent with modern style)
        String[] columns = {"Date", "Weight (kg)"};
        Object[][] data = {
            {"2024-03-01", "75"},
            {"2024-03-08", "74.5"},
            {"2024-03-15", "74"},
        };
        JTable table = new JTable(new DefaultTableModel(data, columns)) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        table.setOpaque(false);
        table.setBackground(new Color(0, 0, 0, 0));
        table.setForeground(Color.WHITE);
        table.setShowGrid(false);
        table.setGridColor(new Color(0, 0, 0, 0));
        table.setRowHeight(30); // Increased row height for better readability
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 80, 300, 150);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        backgroundPanel.add(scrollPane);

        // Weight Progress with updated styling
        JLabel weightLabel = new JLabel("Weight Progress", SwingConstants.CENTER);
        weightLabel.setBounds(100, 240, 200, 30);
        weightLabel.setForeground(Color.WHITE);
        weightLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        backgroundPanel.add(weightLabel);

        JLabel weightProgress = createBigPercentageLabel("58%");
        weightProgress.setBounds(100, 270, 200, 70);
        backgroundPanel.add(weightProgress);

        // Goal Progress
        JLabel goalLabel = new JLabel("Goal Progress", SwingConstants.CENTER);
        goalLabel.setBounds(100, 350, 200, 30);
        goalLabel.setForeground(Color.WHITE);
        goalLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        backgroundPanel.add(goalLabel);

        JLabel goalProgress = createBigPercentageLabel("86%");
        goalProgress.setBounds(100, 380, 200, 70);
        backgroundPanel.add(goalProgress);

        // BMI and BMR Panels
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(1, 2, 10, 0));
        statsPanel.setOpaque(false);
        statsPanel.setBounds(50, 470, 300, 60);

        statsPanel.add(createStatBox("Current BMI", "23.1"));
        statsPanel.add(createStatBox("Current BMR", "1650 kcal/day"));
        backgroundPanel.add(statsPanel);
    }

    private JLabel createBigPercentageLabel(String percentText) {
        JLabel label = new JLabel(percentText, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        label.setOpaque(true);
        label.setBackground(new Color(0, 0, 0, 50)); // Slightly darker transparent gray
        label.setForeground(new Color(255, 255, 255));
        label.setBorder(new LineBorder(new Color(255, 255, 255), 2, true));
        label.setBorder(BorderFactory.createCompoundBorder(
            label.getBorder(), BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        return label;
    }

    private JPanel createStatBox(String title, String value) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(true);
        panel.setBackground(new Color(0, 0, 0, 60)); // Darker transparent background
        panel.setBorder(new LineBorder(new Color(255, 255, 255), 2, true));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 18));
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        valueLabel.setForeground(Color.WHITE);

        panel.add(Box.createVerticalStrut(5));
        panel.add(titleLabel);
        panel.add(valueLabel);
        panel.add(Box.createVerticalStrut(5));

        return panel;
    }

    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
             Color color1 = new Color(153, 102, 204);  //rgb(50, 25, 65)
          Color color2 = new Color(73, 39, 184); //rgb(73, 39, 184)
            int width = getWidth();
            int height = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);
        }
    }
}
