package src.panels;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NutritionPanel extends JPanel {

    public NutritionPanel() {
        setLayout(null); // Keeping null layout for absolute positioning
        setBounds(0, 0, 400, 800);

        // Background gradient panel
        GradientPanel backgroundPanel = new GradientPanel();
        backgroundPanel.setLayout(null); // Still using null layout for absolute positioning
        backgroundPanel.setBounds(0, 0, 400, 800);
        add(backgroundPanel);

        // Title label
        JLabel titleLabel = new JLabel("Daily Calories Needs");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(100, 50, 200, 30); // Absolute positioning
        backgroundPanel.add(titleLabel);

        // Calories label
        JLabel kcal = new JLabel(" + kcal");
        kcal.setFont(new Font("SansSerif", Font.BOLD, 14));
        kcal.setForeground(Color.WHITE);
        kcal.setHorizontalAlignment(SwingConstants.CENTER);
        kcal.setBounds(100, 100, 200, 30); // Absolute positioning
        backgroundPanel.add(kcal);

        // Meal panel with transparent background and rounded border
        JPanel mealPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // Smooth gradient: deep violet to purple
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0x702CFB), 0, getHeight(), new Color(0x5F26E3));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded fill
            }
        };
        mealPanel.setLayout(null);
        mealPanel.setOpaque(false);
        mealPanel.setBorder(BorderFactory.createEmptyBorder()); // Clear default border
        mealPanel.setBorder(new RoundedBorder(20)); // Add your custom rounded border
        mealPanel.setBounds(26, 145, 350, 140);
        backgroundPanel.add(mealPanel);

        // Fill-up form panel
        JPanel fillup = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // Gradient from top (deep violet) to bottom (purple)
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0x702CFB), 0, getHeight(), new Color(0x5F26E3));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded rectangle fill
            }
        };
        fillup.setLayout(null);
        fillup.setOpaque(false); // Needed for custom paint
        fillup.setBorder(BorderFactory.createEmptyBorder());
        fillup.setBorder(new RoundedBorder(20)); // Your custom border class
        fillup.setBounds(26, 310, 350, 270);
        backgroundPanel.add(fillup);

        JLabel addFood = new JLabel("Want to add other foods?");
        addFood.setFont(new Font("Arial", Font.BOLD, 16));
        addFood.setForeground(Color.WHITE);
        addFood.setBounds(78, 10, 250, 25); // Absolute positioning
        fillup.add(addFood);

        JLabel subtitleLabel = new JLabel("Just fill up this form");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setBounds(125, 35, 200, 20); // Absolute positioning
        fillup.add(subtitleLabel);

        JLabel foodNameLabel = new JLabel("Food Item Name");
        foodNameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        foodNameLabel.setForeground(Color.WHITE);
        foodNameLabel.setBounds(30, 60, 100, 20); // Absolute positioning
        fillup.add(foodNameLabel);

        JTextField foodNameField = new JTextField();
        foodNameField.setOpaque(false);
        foodNameField.setForeground(Color.WHITE);
        foodNameField.setBorder(createRoundedBorder(Color.WHITE));
        foodNameField.setBounds(30, 80, 290, 40); // Absolute positioning
        fillup.add(foodNameField);

        JLabel caloriesLabel = new JLabel("Calories");
        caloriesLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        caloriesLabel.setForeground(Color.WHITE);
        caloriesLabel.setBounds(30, 125, 100, 20); // Absolute positioning
        fillup.add(caloriesLabel);

        JTextField caloriesField = new JTextField();
        caloriesField.setOpaque(false);
        caloriesField.setForeground(Color.WHITE);
        caloriesField.setBorder(createRoundedBorder(Color.WHITE));
        caloriesField.setBounds(30, 145, 290, 40); // Absolute positioning
        fillup.add(caloriesField);

        JLabel macrosLabel = new JLabel("Macros (P/C/F grams)");
        macrosLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        macrosLabel.setForeground(Color.WHITE);
        macrosLabel.setBounds(30, 190, 150, 20); // Absolute positioning
        fillup.add(macrosLabel);

        JTextField macrosField = new JTextField();
        macrosField.setOpaque(false);
        macrosField.setForeground(Color.WHITE);
        macrosField.setBorder(createRoundedBorder(Color.WHITE));
        macrosField.setBounds(30, 210, 290, 40); // Absolute positioning
        fillup.add(macrosField);

        JButton addButton = new JButton("Add") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient background (same style as GradientBoxLabel)
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0x702CFB), 0, getHeight(), new Color(0x5F26E3));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded corners

                super.paintComponent(g);
                g2d.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                // Optional: Skip drawing border to keep it clean
            }
        };
        addButton.setFont(new Font("Dialog", Font.BOLD, 14));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setOpaque(false); // Important for custom paint to work
        addButton.setContentAreaFilled(false); // Disable default fill
        addButton.setBorder(new EmptyBorder(5, 15, 5, 15)); // Padding
        addButton.setBounds(130, 608, 150, 30);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backgroundPanel.add(addButton);

        // Add ActionListener to the button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String foodName = foodNameField.getText();
                String calories = caloriesField.getText();
                String macros = macrosField.getText();

                // For now, print out the values (you can process or save these)
                System.out.println("Food: " + foodName + ", Calories: " + calories + ", Macros: " + macros);

                // Clear the fields after adding
                foodNameField.setText("");
                caloriesField.setText("");
                macrosField.setText("");
            }
        });
        backgroundPanel.add(addButton);


        JPanel totalPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // Gradient from top (deep violet) to bottom (purple)
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0x702CFB), 0, getHeight(), new Color(0x5F26E3));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded rectangle fill
            }
        };
        totalPanel.setLayout(null);
        totalPanel.setOpaque(false); // Needed for custom paint
        totalPanel.setBorder(BorderFactory.createEmptyBorder());
        totalPanel.setBounds(26, 660, 350, 40); // Set the bounds to the desired location and size
        backgroundPanel.add(totalPanel);

        JLabel totalCalories = new JLabel("Total Calories consumed:");
        totalCalories.setFont(new Font("Dialog", Font.BOLD, 13));
        totalCalories.setForeground(Color.WHITE);
        totalCalories.setBounds(10, 5, 300, 30); // Absolute positioning
        totalPanel.add(totalCalories);
    }

    private Border createRoundedBorder(Color color) {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        );
    }

    // Background gradient panel
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g; // Declare and initialize g2d
            Color color1 = new Color(153, 102, 204);  //rgb(50, 25, 65)
            Color color2 = new Color(73, 39, 184); //rgb(73, 39, 184)
            int width = getWidth();
            int height = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);
        }
    }

    // Custom Rounded Border class
    static class RoundedBorder implements Border {
        private int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius, this.radius, this.radius, this.radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
