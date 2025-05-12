package src.panels;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.ComboPopup;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;

public class UserPanel extends JPanel {

     JTextField ageField, heightField, weightField;
    private JComboBox<String> activityLevelBox, dietPreferenceBox, workoutTypeBox;
     JCheckBox loseWeightBox, buildMuscleBox, maintainBox;
    private JButton continueButton;

    public UserPanel() {
        setLayout(null);
        setBounds(0, 0, 400, 800);

        GradientPanel backgroundPanel = new GradientPanel();
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, 400, 800);
        add(backgroundPanel);

        JLabel welcomeLabel = new JLabel("Welcome to FitPlan, Alex!", SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        welcomeLabel.setBounds(50, 30, 300, 30);
        backgroundPanel.add(welcomeLabel);

        JLabel subtitleLabel = new JLabel("Let's set up plan to reach your goals.", SwingConstants.CENTER);
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        subtitleLabel.setBounds(50, 71, 300, 20);
        backgroundPanel.add(subtitleLabel);

        // Step 1: Goal
        JPanel goalPanel = createRoundedPanel();
        goalPanel.setBounds(30, 146, 340, 140);
        goalPanel.setLayout(null);
        backgroundPanel.add(goalPanel);

        JLabel goalLabel = createSectionLabel("Set your Goal - Step 1 of 3");
        goalLabel.setBounds(40, 120, 300, 25);
        backgroundPanel.add(goalLabel);

        String[] goals = {"Lose Weight", "Build Muscle", "Maintain"};
        int y = 25;

        // Load icons safely and scale them to fit within the checkbox size
        URL uncheckedURL = getClass().getResource("/images/square.png");
        URL checkedURL = getClass().getResource("/images/check.png");

        if (uncheckedURL == null || checkedURL == null) {
            System.err.println("⚠️ Icon file not found. Make sure /images/square.png and /images/check.png exist in resources.");
        }

        ImageIcon uncheckedIcon = new ImageIcon(new ImageIcon(uncheckedURL).getImage().getScaledInstance(21, 21, Image.SCALE_SMOOTH));
        ImageIcon checkedIcon = new ImageIcon(new ImageIcon(checkedURL).getImage().getScaledInstance(21, 21, Image.SCALE_SMOOTH));

        for (String goal : goals) {
            JPanel row = new JPanel(null);
            row.setOpaque(false);
            row.setBounds(10, y, 300, 25);

            JLabel label = new JLabel(goal);
            label.setForeground(Color.WHITE);
            label.setBounds(10, 0, 200, 25);
            row.add(label);

            JCheckBox checkBox = new JCheckBox();
            checkBox.setBounds(250, 2, 35, 25);
            checkBox.setOpaque(false);
            checkBox.setIcon(uncheckedIcon);
            checkBox.setSelectedIcon(checkedIcon);
            checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
            row.add(checkBox);

            goalPanel.add(row);
            y += 35;

            switch (goal) {
                case "Lose Weight":
                    loseWeightBox = checkBox;
                    break;
                case "Build Muscle":
                    buildMuscleBox = checkBox;
                    break;
                case "Maintain":
                    maintainBox = checkBox;
                    break;
            }
        }

        // Step 2: Personal Detail
        JPanel personalPanel = createRoundedPanel();
        personalPanel.setBounds(30, 315, 340, 170);
        personalPanel.setLayout(null);
        backgroundPanel.add(personalPanel);

        JLabel personalLabel = createSectionLabel("Enter Personal Detail - Step 2 of 3");
        personalLabel.setBounds(40, 290, 310, 20);
        backgroundPanel.add(personalLabel);

        ageField = createTextField(personalPanel, "Age:", 30);
        heightField = createTextField(personalPanel, "Height (cm):", 60);
        weightField = createTextField(personalPanel, "Weight (kg):", 90);

        JLabel activityLabel = new JLabel("Activity Level:");
        activityLabel.setForeground(Color.WHITE);
        activityLabel.setBounds(10, 120, 100, 20);
        personalPanel.add(activityLabel);

        activityLevelBox = createTransparentComboBox(new String[]{"Easy", "Intermediate", "Difficult", "Extreme"});
        activityLevelBox.setBounds(120, 120, 180, 20);
        personalPanel.add(activityLevelBox);

        // Step 3: Preferences
        JPanel preferencePanel = createRoundedPanel();
        preferencePanel.setBounds(30, 515, 340, 120);
        preferencePanel.setLayout(null);
        backgroundPanel.add(preferencePanel);

        JLabel preferenceLabel = createSectionLabel("Customize Preference                                      Step 3 of 3");
        preferenceLabel.setBounds(40, 490, 310, 20);
        backgroundPanel.add(preferenceLabel);

        JLabel dietLabel = new JLabel("Diet Preference:");
        dietLabel.setForeground(Color.WHITE);
        dietLabel.setBounds(10, 40, 100, 20);
        preferencePanel.add(dietLabel);

        dietPreferenceBox = createTransparentComboBox(new String[]{"Vegetarian", "High-Protein"});
        dietPreferenceBox.setBounds(120, 40, 180, 20);
        preferencePanel.add(dietPreferenceBox);

        JLabel workoutLabel = new JLabel("Workout Type:");
        workoutLabel.setForeground(Color.WHITE);
        workoutLabel.setBounds(10, 70, 100, 20);
        preferencePanel.add(workoutLabel);

        workoutTypeBox = createTransparentComboBox(new String[]{"Home", "Gym", "HIIT"});
        workoutTypeBox.setBounds(120, 70, 180, 20);
        preferencePanel.add(workoutTypeBox);

        // Continue Button
        continueButton = new JButton("CONTINUE");
        continueButton.setBounds(150, 670, 100, 30);
        continueButton.setFont(new Font("Dialog", Font.BOLD, 12));
        continueButton.setForeground(Color.WHITE);
        continueButton.setFocusPainted(false);
        continueButton.setContentAreaFilled(false);
        continueButton.setOpaque(false);
        continueButton.setBorder(new RoundedBorder(15, Color.WHITE));
        continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        continueButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                continueButton.setForeground(new Color(204, 204, 255));
                continueButton.setBorder(new RoundedBorder(15, new Color(204, 204, 255)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                continueButton.setForeground(Color.WHITE);
                continueButton.setBorder(new RoundedBorder(15, Color.WHITE));
            }
        });

        backgroundPanel.add(continueButton);
    }

    // Constructor with action
    public UserPanel(Runnable onContinue) {
        this();
        continueButton.addActionListener((ActionEvent e) -> {
            if (onContinue != null) {
                onContinue.run();
            }
        });
    }

    private JPanel createRoundedPanel() {
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 20));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 20, 20);
            }
        };
        panel.setOpaque(false);
        return panel;
    }

    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Dialog", Font.BOLD, 12));
        return label;
    }

    private JTextField createTextField(JPanel parent, String label, int y) {
        JLabel jLabel = new JLabel(label);
        jLabel.setForeground(Color.WHITE);
        jLabel.setBounds(10, y, 100, 20);
        parent.add(jLabel);

        JTextField field = new JTextField();
        field.setBounds(120, y, 180, 20);
        field.setOpaque(false);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        parent.add(field);
        return field;
    }

    // Create Transparent ComboBox
   private JComboBox<String> createTransparentComboBox(String[] options) {
    JComboBox<String> comboBox = new JComboBox<>(options);
    comboBox.setOpaque(true);  // Set opaque to true to apply a background color
    comboBox.setBackground(new Color(138, 43, 226, 120)); // Semi-transparent black background
    comboBox.setForeground(Color.WHITE);  // Set text color to white
    comboBox.setUI(new BasicComboBoxUI() {
        @Override
        protected ComboPopup createPopup() {
            ComboPopup popup = super.createPopup();
            JComponent popupComponent = (JComponent) popup;
            popupComponent.setBackground(new Color(138, 43, 226, 120));  // Semi-transparent background for popup
            popupComponent.setOpaque(true);  // Ensure the popup also has a background color
            return popup;
        }
    });
    comboBox.setBorder(null);  // Remove the border to make it visually clean
    return comboBox;
}


    // Rounded Border Class
    public static class RoundedBorder extends AbstractBorder {
        private int radius;
        private Color color;

        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(5, 5, 5, 5); // Adjust padding for the rounded border
        }
    }

    // GradientPanel Class
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
             Color color1 = new Color(73, 39, 184); //rgb(73, 39, 184)
          Color color2 = new Color(153, 102, 204);  //rgb(50, 25, 65)
            int width = getWidth();
            int height = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);
        }
    }
}
