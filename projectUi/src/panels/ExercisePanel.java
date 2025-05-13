package src.panels;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;


public class ExercisePanel extends JPanel {

    private Map<String, java.util.List<String>> workoutPlans = new HashMap<>();
    private final JPanel workoutDisplayPanel;
    private final JLabel formLabel;
    private JButton activeDayButton = null;
    private final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    public ExercisePanel() {
        setLayout(null);
        setBounds(0, 0, 400, 800);
        setOpaque(false);

        NutritionPanel nutritionPanel = new NutritionPanel();
        NutritionPanel.GradientPanel backgroundPanel = nutritionPanel.new GradientPanel();
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, 400, 800);
        backgroundPanel.setOpaque(false);
        add(backgroundPanel);

        // Title Label
        JLabel titleLabel = new JLabel("Weekly Workout Plan", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(0, 10, 400, 30);
        backgroundPanel.add(titleLabel);

        // Difficulty Buttons
        String[] difficulties = {"Easy", "Intermediate", "Difficult", "Extreme"};
        int xOffset = 10;
        for (String level : difficulties) {
            JButton btn = new JButton(level);
            btn.setBounds(xOffset, 50, 90, 30);
            styleButton(btn);
            backgroundPanel.add(btn);
            xOffset += 95;
        }

        // Workout Display Panel
        workoutDisplayPanel = new JPanel(null);
        workoutDisplayPanel.setBounds(21, 100, 350, 300);
        workoutDisplayPanel.setOpaque(false);
        workoutDisplayPanel.setBorder(new RoundedBorder(20, Color.WHITE));
        backgroundPanel.add(workoutDisplayPanel);

        //// Day Buttons Panel
JPanel dayButtonsPanel = new JPanel(null);
dayButtonsPanel.setBounds(10, 10, 70, 290);
dayButtonsPanel.setOpaque(false);

workoutDisplayPanel.add(dayButtonsPanel);

int yOffset = 0;
for (String day : days) {
    String shortLabel = day.substring(0, 1);
    JButton dayButton = new JButton(shortLabel);
    dayButton.setBounds(0, yOffset, 60, 30);

    // Apply the same styling function from the previous code
    styleButton(dayButton);

    // Add the button to the panel
    dayButtonsPanel.add(dayButton);

    final String currentDay = day; // Create a final copy for use in the lambda
    dayButton.addActionListener(e -> {
        animateDayButton(dayButton);
        displayWorkouts(currentDay);
    });

    yOffset += 41;
}


        // Exercise List Panel
        JPanel exerciseListPanel = new JPanel(null);
        exerciseListPanel.setBounds(80, 10, 250, 280);
        exerciseListPanel.setOpaque(false);
        exerciseListPanel.setName("exerciseListPanel");
     

        workoutDisplayPanel.add(exerciseListPanel);

        // "Add Exercise" Text
        JLabel addTitle = new JLabel("Want to add other Exercise?");
        addTitle.setBounds(95, 410, 250, 25);
        addTitle.setFont(new Font("Dialog", Font.BOLD, 16));
        addTitle.setForeground(Color.WHITE);
        backgroundPanel.add(addTitle);

        // Form Label
        formLabel = new JLabel("Just fill up this form");
        formLabel.setBounds(150, 450, 200, 20);
        formLabel.setForeground(Color.WHITE);
        formLabel.setFont(new Font("Dialog", Font.BOLD, 12));
        backgroundPanel.add(formLabel);

        // Form Panel
       JPanel formPanel = new JPanel() {
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
formPanel.setLayout(null);
formPanel.setOpaque(false); // Needed for custom paint
formPanel.setBorder(BorderFactory.createEmptyBorder());
formPanel.setBounds(27, 490, 340, 160); // Set the bounds to the desired location and size
backgroundPanel.add(formPanel);

        // Exercise Field
        JLabel exerciseLabel = new JLabel("Exercise:");
        exerciseLabel.setBounds(20, 10, 100, 20);
        exerciseLabel.setForeground(Color.WHITE);
        exerciseLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
        formPanel.add(exerciseLabel);

        JTextField exerciseField = new JTextField();
        exerciseField.setBounds(20, 30, 290, 40);
        exerciseField.setOpaque(false);
        exerciseField.setForeground(Color.WHITE);
        exerciseField.setBackground(new Color(255, 255, 255, 60));
        formPanel.add(exerciseField);

        // Day Selector Panel
        JPanel daySelectorPanel = new JPanel(null);
        daySelectorPanel.setBounds(20, 90, 290, 50);
         daySelectorPanel.setBorder(new RoundedBorder(10, Color.WHITE));

       // daySelectorPanel.setBorder(new RoundedBorder(10));
        daySelectorPanel.setOpaque(false);
        formPanel.add(daySelectorPanel);

        JLabel selectedDayLabel = new JLabel("Select a day");
        selectedDayLabel.setBounds(10, 10, 200, 25);
        selectedDayLabel.setForeground(Color.WHITE);
        selectedDayLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
        daySelectorPanel.add(selectedDayLabel);

        ImageIcon icon = new ImageIcon(getClass().getResource("/images/day.png"));
        ImageIcon resizedIcon = new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        JButton dropdownButton = new JButton(resizedIcon);
        dropdownButton.setBounds(250, 10, 30, 30);
        dropdownButton.setFocusable(false);
        dropdownButton.setContentAreaFilled(false);
        dropdownButton.setBorderPainted(false);
        dropdownButton.setOpaque(false);
        daySelectorPanel.add(dropdownButton);

        JPopupMenu dayPopup = new JPopupMenu();
        for (String day : days) {
            final String currentDay = day;
            JMenuItem item = new JMenuItem(currentDay);
            item.addActionListener(e -> selectedDayLabel.setText(currentDay));
            dayPopup.add(item);
        }

        dropdownButton.addActionListener(e -> dayPopup.show(dropdownButton, 0, dropdownButton.getHeight()));

        // Add Button
        JButton addBtn = new JButton("Add");
        addBtn.setFont(new Font("Dialog", Font.BOLD, 14));
        addBtn.setBounds(140, 680, 120, 40);
        styleButton(addBtn);
        backgroundPanel.add(addBtn);

        addBtn.addActionListener(e -> {
            String exercise = exerciseField.getText().trim();
            String day = selectedDayLabel.getText();
            if (!exercise.isEmpty() && day != null && !day.equals("Select a day")) {
                workoutPlans.computeIfAbsent(day, k -> new ArrayList<>()).add(exercise);
                exerciseField.setText("");
                displayWorkouts(day);
            }
        });

        workoutPlans = getSampleWorkoutPlans();
        displayWorkouts("Monday");
    }

    private void styleButton(JButton btn) {
    btn.setFont(new Font("Dialog", Font.BOLD, 13));
    btn.setForeground(Color.WHITE);
    btn.setFocusPainted(false);
    btn.setOpaque(false);
    btn.setContentAreaFilled(false);
    btn.setBorder(new EmptyBorder(5, 10, 5, 10));
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

    btn.setUI(new BasicButtonUI() {
        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp = new GradientPaint(0, 0, new Color(0x702CFB), 0, c.getHeight(), new Color(0x5F26E3));
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
            g2.dispose();
            super.paint(g, c);
        }
    });

    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            btn.setBackground(new Color(0x5F26E3)); // Change color on hover
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            btn.setBackground(null); // Reset background color when mouse exits
        }
    });
}



    private void animateDayButton(JButton button) {
        if (activeDayButton != null && activeDayButton != button) {
            activeDayButton.setBounds(activeDayButton.getX(), activeDayButton.getY(), 60, 30);
            activeDayButton.setFont(new Font("Arial", Font.PLAIN, 12));
        }

        activeDayButton = button;

        Timer timer = new Timer(10, null);
        final int[] width = {60};
        final int[] height = {30};

        timer.addActionListener(e -> {
            if (width[0] < 70) {
                width[0] += 1;
                height[0] += 1;
                button.setBounds(button.getX(), button.getY(), width[0], height[0]);
                button.setFont(new Font("Arial", Font.BOLD, 12 + (width[0] - 60) / 2));
            } else {
                timer.stop();
            }
        });

        timer.start();
    }

    private void displayWorkouts(String day) {
        JPanel exerciseListPanel = null;

        for (Component comp : workoutDisplayPanel.getComponents()) {
            if (comp instanceof JPanel && "exerciseListPanel".equals(comp.getName())) {
                exerciseListPanel = (JPanel) comp;
                break;
            }
        }

        if (exerciseListPanel == null) return;

        exerciseListPanel.removeAll();

        int y = 10;
        java.util.List<String> exercises = workoutPlans.getOrDefault(day, new ArrayList<>());

        for (String ex : exercises) {
            JPanel itemPanel = new JPanel(null);
            itemPanel.setBounds(0, y, 280, 40);
            itemPanel.setOpaque(false);

            JLabel label = new JLabel(ex);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Dialog", Font.PLAIN, 14));
            label.setBounds(10, -5, 130, 20);
            itemPanel.add(label);

            if (ex.toLowerCase().contains("plank") || ex.toLowerCase().contains("jumping jacks")) {
                JLabel timerLabel = new JLabel("2:30 min");
                timerLabel.setForeground(Color.WHITE);
                timerLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
                timerLabel.setBounds(160, 10, 60, 20);
                itemPanel.add(timerLabel);

                ImageIcon timerIcon = new ImageIcon(getClass().getResource("/images/timer.png"));
                timerIcon = new ImageIcon(timerIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
                JLabel iconLabel = new JLabel(timerIcon);
                iconLabel.setBounds(220, 10, 16, 16);
                itemPanel.add(iconLabel);
            }

            JCheckBox checkBox = new JCheckBox();
            checkBox.setBounds(100, -5, 30, 30);
            checkBox.setOpaque(false);

            ImageIcon uncheckedIcon = new ImageIcon(getClass().getResource("/images/square.png"));
            ImageIcon checkedIcon = new ImageIcon(getClass().getResource("/images/check.png"));
            uncheckedIcon = new ImageIcon(uncheckedIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            checkedIcon = new ImageIcon(checkedIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            checkBox.setIcon(uncheckedIcon);
            checkBox.setSelectedIcon(checkedIcon);
            itemPanel.add(checkBox);

            exerciseListPanel.add(itemPanel);
            y += 45;
        }

        exerciseListPanel.revalidate();
        exerciseListPanel.repaint();
    }

    private Map<String, java.util.List<String>> getSampleWorkoutPlans() {
        Map<String, java.util.List<String>> samplePlans = new HashMap<>();
        samplePlans.put("Monday", Arrays.asList("Push-ups", "Squats", "Plank"));
        samplePlans.put("Tuesday", Arrays.asList("Jumping Jacks", "Lunges", "Burpees"));
        samplePlans.put("Wednesday", Arrays.asList("Rest"));
        samplePlans.put("Thursday", Arrays.asList("Deadlifts", "Pull-ups"));
        samplePlans.put("Friday", Arrays.asList("Push-ups", "Squats", "Running"));
        samplePlans.put("Saturday", Arrays.asList("Yoga", "Stretching"));
        samplePlans.put("Sunday", Arrays.asList("Rest"));
        return samplePlans;
    
        // ... your existing ExercisePanel code ...
    }
    // RoundedBorder class inside the same file
class RoundedBorder implements javax.swing.border.Border {
    private final int radius;
    private final Color color;

    public RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius + 1, radius + 1, radius + 2, radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Enable anti-aliasing for smoother and sharper rendering
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Optional: improve stroke rendering quality
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        g2.setColor(color);
        g2.setStroke(new BasicStroke(2f)); // 2-pixel stroke for visibility

        // Important: reduce width/height by 2 to prevent clipping or blur
        g2.drawRoundRect(x + 1, y + 1, width - 3, height - 3, radius, radius);
        g2.dispose();
    }
}




}


