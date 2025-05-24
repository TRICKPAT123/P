package src.panels;
// ExercisePanel.java
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.Timer;

public class ExercisePanel extends JPanel {
    private String goal;
    private String workoutType;
// Fields for the exercise panel
    private JTextArea exerciseList;
    private JTextField customExerciseField;
    private JButton addButton;
    private JButton[] dayButtons;
    private JButton activeDayButton;
    private JButton easyBtn, intermediateBtn, difficultBtn, extremeBtn;
    private Map<Integer, java.util.List<String>> dayExerciseMap;
    private int currentDay = 1;

    // Add these fields at the top of ExercisePanel
    private int age;
    private double height;
    private double weight;
// Constructor to initialize the ExercisePanel with user info
    public ExercisePanel(int age, double height, double weight, String goal, String workoutType) {
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.goal = goal.toLowerCase();
        this.workoutType = workoutType.toLowerCase();
       
        setLayout(null);

        GradientPanel addbackground = new GradientPanel();
        addbackground.setLayout(null);
        addbackground.setBounds(0, 0, 400, 800);
        add(addbackground);

        dayExerciseMap = new HashMap<>();
        loadExerciseMap();
// Create and style the title label
        JLabel title = new JLabel("Weekly Workout Plan", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setBounds(80, 15, 250, 30);
        addbackground.add(title);
        

        // Difficulty button creation and styling
        String[] levels = {"Easy", "Intermediate", "Difficult", "Extreme"};
        Color[] bgColors = {
            new Color(102, 255, 102),      // Easy - Green
            new Color(255, 204, 0),        // Intermediate - Orange
            new Color(255, 102, 102),      // Difficult - Red
            new Color(204, 0, 204)         // Extreme - Purple
        };
        JButton[] diffBtns = new JButton[4];

        int btnWidth = 84; // Increased width for full text
        int btnHeight = 45;
        for (int i = 0; i < 4; i++) {
            JButton btn = new JButton(levels[i]);
            btn.setFont(new Font("Garamond", Font.BOLD, 17));
            btn.setForeground(Color.WHITE);
            btn.setBackground(bgColors[i]);
            btn.setFocusPainted(false);
            btn.setBorder(new RoundedBorder(15));
            btn.setOpaque(true);
            btn.setContentAreaFilled(true);
            btn.setPreferredSize(new Dimension(btnWidth, btnHeight));
            btn.setSize(btnWidth, btnHeight); // Ensure actual size
            btn.setHorizontalAlignment(SwingConstants.CENTER);
            btn.setEnabled(false);
            diffBtns[i] = btn;
        }

        // Positioning with spacing
        int spacing = 10;
        for (int i = 0; i < 4; i++) {
            diffBtns[i].setBounds(14 + i * (btnWidth + spacing), 65, btnWidth, btnHeight);
            addbackground.add(diffBtns[i]);
        }

        // Assign to your fields for highlightDifficulty
        easyBtn = diffBtns[0];
        intermediateBtn = diffBtns[1];
        difficultBtn = diffBtns[2];
        extremeBtn = diffBtns[3];
// Highlight the difficulty level based on the current day
        JPanel dayExercisePanel = new JPanel(null);
        dayExercisePanel.setBounds(18, 135, 363, 290);
        dayExercisePanel.setOpaque(false);
        dayExercisePanel.setBorder(new RoundedBorder(20, Color.WHITE, 2f));
        addbackground.add(dayExercisePanel);

        dayButtons = new JButton[7];
        for (int i = 0; i < 7; i++) {
            int day = i + 1;
            JButton btn = new JButton(String.valueOf(day));
            styleButton(btn);
            btn.setBounds(10, 10 + i * 40, 50, 25);
            dayButtons[i] = btn;
            JButton currentButton = btn;

            btn.addActionListener(e -> {
                currentDay = day;
                showExercises(day);
                animateDayButton(currentButton);
            });

            dayExercisePanel.add(btn);
        }
// Create and style the exercise list area
        exerciseList = new JTextArea();
        exerciseList.setEditable(false);
        exerciseList.setOpaque(false);
        exerciseList.setForeground(Color.WHITE);
        exerciseList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        exerciseList.setMargin(new Insets(10, 10, 100, 10));
// Set line wrap and word wrap for better readability
        JScrollPane scrollPanel = new JScrollPane(exerciseList);
        scrollPanel.setBounds(100, 10, 240, 260);
        scrollPanel.setOpaque(false);
        scrollPanel.getViewport().setOpaque(false);
        scrollPanel.setBorder(null);
        scrollPanel.getViewport().setBorder(null);
        exerciseList.setBorder(null);
        dayExercisePanel.add(scrollPanel);
// Create and style the background label
        JLabel addLabel = new JLabel("Want to add other Exercise?", SwingConstants.CENTER);
        addLabel.setFont(new Font("Garamond", Font.BOLD, 17));
        addLabel.setForeground(Color.WHITE);
        addLabel.setBounds(30, 450, 320, 30);
        addbackground.add(addLabel);
// Create and style the sub-label
        JLabel subLabel = new JLabel("Just fill up this form", SwingConstants.CENTER);
        subLabel.setFont(new Font("Garamond", Font.PLAIN, 14));
        subLabel.setForeground(Color.WHITE);
        subLabel.setBounds(30, 490, 320, 20);
        addbackground.add(subLabel);
// Create and style the form panel
        bordeGradient formPanel = new bordeGradient();
        formPanel.setLayout(null);
        formPanel.setBounds(18, 550, 363, 120);
        formPanel.setOpaque(false);
        formPanel.setBorder(new RoundedBorder(20, Color.WHITE, 2f));
        addbackground.add(formPanel);
// Create and style the exercise label
        JLabel exerciseLabel = new JLabel("Exercise");
        exerciseLabel.setForeground(Color.WHITE);
        exerciseLabel.setFont(new Font("Century", Font.BOLD, 15));
        exerciseLabel.setBounds(25, 10, 100, 20);
        formPanel.add(exerciseLabel);
// Create and style the custom exercise input field
        customExerciseField = new JTextField();
        customExerciseField.setOpaque(false);
        customExerciseField.setBorder(new RoundedBorder(10, Color.WHITE, 1f));
        customExerciseField.setForeground(Color.WHITE);
        customExerciseField.setCaretColor(Color.WHITE);
        customExerciseField.setFont(new Font("DialogInput", Font.PLAIN, 16));
        customExerciseField.setBounds(27, 50, 300, 30);
        formPanel.add(customExerciseField);

        
        Color violet = new Color(102, 0, 204);
        Color shadowColor = new Color(153, 102, 204);
// Create and style the add button
        addButton = new JButton("Add");
        addButton.setBounds(120, 690, 160, 50);
        addButton.setFont(new Font("Arial", Font.BOLD, 16));
        addButton.setBackground(violet);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        addButton.setContentAreaFilled(false);
        addButton.setOpaque(true);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setEnabled(true);

        addButton.setUI(new BasicButtonUI() {
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                JButton b = (JButton) c;
                int w = b.getWidth(), h = b.getHeight();
                g2.setColor(shadowColor);
                g2.fillRoundRect(6, 6, w - 6, h - 6, 18, 18);
                g2.setColor(violet);
                g2.fillRoundRect(0, 0, w - 6, h - 6, 18, 18);
                FontMetrics fm = g2.getFontMetrics();
                int textX = (w - fm.stringWidth(b.getText())) / 2;
                int textY = (h + fm.getAscent()) / 2 - 6;
                g2.setColor(Color.WHITE);
                g2.drawString(b.getText(), textX, textY);
                g2.dispose();
            }
            

            
        });

        addButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { addButton.setLocation(addButton.getX() - 3, addButton.getY() + 3); }
            public void mouseExited(MouseEvent e) { addButton.setLocation(addButton.getX() + 3, addButton.getY() - 3); }
            public void mousePressed(MouseEvent e) { addButton.setLocation(addButton.getX() - 3, addButton.getY() + 3); }
            public void mouseReleased(MouseEvent e) { addButton.setLocation(addButton.getX() + 3, addButton.getY() - 3); }
        });
// Add action listener to the button
        addButton.addActionListener(e -> addCustomExercise());
        addbackground.add(addButton);
// Add document listener to the custom exercise field
        customExerciseField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { toggleAddButton(); }
            public void removeUpdate(DocumentEvent e) { toggleAddButton(); }
            public void changedUpdate(DocumentEvent e) { toggleAddButton(); }
        });
// Set the initial state of the add button
        showExercises(currentDay);
        setVisible(true);

        // --- Floating Info Button ---
        JButton infoBtn = new JButton("i");
        infoBtn.setFont(new Font("Arial", Font.BOLD, 18));
        infoBtn.setForeground(Color.WHITE);
        infoBtn.setBackground(new Color(153, 102, 204));
        infoBtn.setFocusPainted(false);
        infoBtn.setBorder(new RoundedBorder(15));
        infoBtn.setBounds(350, 20, 35, 35); // Top-right corner
        infoBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addbackground.add(infoBtn);

        infoBtn.addActionListener(e -> showUserInfoDialog());
        // --- End Floating Info Button ---
    }
// Toggle the add button based on the custom exercise field content
    private void toggleAddButton() {
        addButton.setEnabled(!customExerciseField.getText().trim().isEmpty());
    }
// Style the day buttons with rounded corners and gradient background
    private void styleButton(JButton addButton) {
        addButton.setFont(new Font("Garamond", Font.BOLD, 17));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setOpaque(false);
        addButton.setContentAreaFilled(false);
        addButton.setBorder(new EmptyBorder(5, 10, 5, 10));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setUI(new BasicButtonUI() {
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
    }
// Load exercises based on the goal and workout type
    private void loadExerciseMap() {
        for (int i = 1; i <= 7; i++) {
            switch (goal) {
                case "lose weight":
                    if (workoutType.equals("home")) {
                        dayExerciseMap.put(1, new ArrayList<>(Arrays.asList("Jumping Jacks", "Squat Jumps", "Mountain Climbers")));
                        dayExerciseMap.put(2, new ArrayList<>(Arrays.asList("High Knees", "Plank Jacks", "Push-ups")));
                        dayExerciseMap.put(3, new ArrayList<>(Arrays.asList("March in Place", "Arm Circles", "Standing Crunches")));
                        dayExerciseMap.put(4, new ArrayList<>(Arrays.asList("Burpees", "Wall Sit", "Side Lunges")));
                        dayExerciseMap.put(5, new ArrayList<>(Arrays.asList("Jump Lunges", "Core Twists", "Toe Touches")));
                        dayExerciseMap.put(6, new ArrayList<>(Arrays.asList("HIIT (Bodyweight)", "Glute Bridges", "Skater Jumps")));
                        dayExerciseMap.put(7, new ArrayList<>(Arrays.asList("Light Yoga", "Stretching", "Deep Breathing")));
                    } else if (workoutType.equals("gym")) {
                        dayExerciseMap.put(1, new ArrayList<>(Arrays.asList("Treadmill Sprints", "Battle Ropes", "Kettlebell Swings")));
                        dayExerciseMap.put(2, new ArrayList<>(Arrays.asList("Stationary Bike", "Leg Press", "Cable Crunches")));
                        dayExerciseMap.put(3, new ArrayList<>(Arrays.asList("Rowing Machine", "Stair Climber", "Dumbbell Step-ups")));
                        dayExerciseMap.put(4, new ArrayList<>(Arrays.asList("Jump Rope", "Dumbbell Thrusters", "Plank to Row")));
                        dayExerciseMap.put(5, new ArrayList<>(Arrays.asList("HIIT on Treadmill", "Jump Squats (Weighted)", "Lateral Raises")));
                        dayExerciseMap.put(6, new ArrayList<>(Arrays.asList("Circuit Training", "Elliptical Intervals", "Dumbbell Lunges")));
                        dayExerciseMap.put(7, new ArrayList<>(Arrays.asList("Light Cardio", "Sauna + Stretching", "Core Work")));
                    }
                    break;

                case "build muscle":
                    if (workoutType.equals("home")) {
                        dayExerciseMap.put(1, new ArrayList<>(Arrays.asList("Push-ups", "Glute Bridges", "Wall Sit")));
                        dayExerciseMap.put(2, new ArrayList<>(Arrays.asList("Chair Dips", "Superman Hold", "Squat Pulses")));
                        dayExerciseMap.put(3, new ArrayList<>(Arrays.asList("Plank", "Calf Raises", "Step-ups (stairs)")));
                        dayExerciseMap.put(4, new ArrayList<>(Arrays.asList("Elevated Push-ups", "Wall Squats", "Side Plank")));
                        dayExerciseMap.put(5, new ArrayList<>(Arrays.asList("Isometric Holds", "Walking Lunges", "Bicycle Crunches")));
                        dayExerciseMap.put(6, new ArrayList<>(Arrays.asList("Core Circuit", "Push-up Hold", "Air Squats")));
                        dayExerciseMap.put(7, new ArrayList<>(Arrays.asList("Stretching", "Yoga", "Recovery Walk")));
                    } else if (workoutType.equals("gym")) {
                        dayExerciseMap.put(1, new ArrayList<>(Arrays.asList("Barbell Squats", "Deadlifts", "Leg Curls")));
                        dayExerciseMap.put(2, new ArrayList<>(Arrays.asList("Bench Press", "Pull-ups", "Dumbbell Rows")));
                        dayExerciseMap.put(3, new ArrayList<>(Arrays.asList("Cable Flys", "Barbell Curls", "Seated Row")));
                        dayExerciseMap.put(4, new ArrayList<>(Arrays.asList("Overhead Press", "Incline Press", "Triceps Pushdowns")));
                        dayExerciseMap.put(5, new ArrayList<>(Arrays.asList("Leg Press", "Dumbbell Lunges", "Standing Calf Raises")));
                        dayExerciseMap.put(6, new ArrayList<>(Arrays.asList("Weighted Dips", "Chin-ups", "Barbell Shrugs")));
                        dayExerciseMap.put(7, new ArrayList<>(Arrays.asList("Light Cardio", "Stretch & Foam Roll", "Active Mobility")));
                    }
                    break;

                case "maintain":
                    if (workoutType.equals("home")) {
                        dayExerciseMap.put(1, new ArrayList<>(Arrays.asList("Light Jog in Place", "Wall Sit", "Push-ups")));
                        dayExerciseMap.put(2, new ArrayList<>(Arrays.asList("Arm Circles", "Glute Bridges", "Side Leg Raises")));
                        dayExerciseMap.put(3, new ArrayList<>(Arrays.asList("Yoga Stretching", "Core Twist", "Balance Drill")));
                        dayExerciseMap.put(4, new ArrayList<>(Arrays.asList("Step Touch", "Standing Crunches", "March in Place")));
                        dayExerciseMap.put(5, new ArrayList<>(Arrays.asList("Light Circuit", "Calf Raises", "Chair Squats")));
                        dayExerciseMap.put(6, new ArrayList<>(Arrays.asList("Breathing Practice", "Core Activation Work", "Light Mobility")));
                        dayExerciseMap.put(7, new ArrayList<>(Arrays.asList("Rest & Recovery", "Walking Outdoors", "Stretching")));
                    } else if (workoutType.equals("gym")) {
                        dayExerciseMap.put(1, new ArrayList<>(Arrays.asList("Stationary Bike", "Resistance Band Rows", "Core Stability Drills")));
                        dayExerciseMap.put(2, new ArrayList<>(Arrays.asList("Elliptical", "Dumbbell Squats", "Shoulder Press")));
                        dayExerciseMap.put(3, new ArrayList<>(Arrays.asList("Lat Pulldown", "Seated Row", "Leg Extensions")));
                        dayExerciseMap.put(4, new ArrayList<>(Arrays.asList("Incline Walk", "Cable Woodchopper", "Arm Ergometer")));
                        dayExerciseMap.put(5, new ArrayList<>(Arrays.asList("Yoga or Pilates", "Balance Board Work", "Dumbbell Core Circuit")));
                        dayExerciseMap.put(6, new ArrayList<>(Arrays.asList("Light Machine Work", "Stretch Band Series", "Mobility Focus")));
                        dayExerciseMap.put(7, new ArrayList<>(Arrays.asList("Rest", "Stretch + Sauna", "Meditation")));
                    }
                    break;
            }
           
        }
    }
// Show exercises for the selected day
    private void showExercises(int day) {
        java.util.List<String> exercises = dayExerciseMap.getOrDefault(day, new ArrayList<>());
        exerciseList.setText("");
        for (String exercise : exercises) {
            exerciseList.append(exercise + "\n\n");
        }

        if (day <= 2) highlightDifficulty("Easy");
        else if (day <= 4) highlightDifficulty("Intermediate");
        else if (day <= 6) highlightDifficulty("Difficult");
        else if (day == 7) highlightDifficulty("Extreme");
    }
// Add a custom exercise to the current day's list
    private void addCustomExercise() {
        String input = customExerciseField.getText().trim();
        if (!input.isEmpty()) {
            java.util.List<String> exercises = dayExerciseMap.containsKey(currentDay) ? dayExerciseMap.get(currentDay) : new ArrayList<>();
            exercises.add(input);
            dayExerciseMap.put(currentDay, exercises);
            showExercises(currentDay);
            customExerciseField.setText("");
        }
    }
// Animate the day button when clicked
    private void animateDayButton(JButton btn) {
        if (activeDayButton != null && activeDayButton != btn) {
            activeDayButton.setBounds(activeDayButton.getX(), activeDayButton.getY(), 60, 30);
            activeDayButton.setFont(new Font("Arial", Font.PLAIN, 12));
        }

        activeDayButton = btn;

        Timer timer = new Timer(10, null);
        final int[] width = {60}, height = {30};

        timer.addActionListener(e -> {
            if (width[0] < 70) {
                width[0]++;
                height[0]++;
                btn.setBounds(btn.getX(), btn.getY(), width[0], height[0]);
                btn.setFont(new Font("Arial", Font.BOLD, 12 + (width[0] - 60) / 2));
            } else timer.stop();
        });

        timer.start();
    }

// Highlight the difficulty level buttons based on the current day
    private void highlightDifficulty(String level) {
        easyBtn.setBackground(null);
        intermediateBtn.setBackground(null);
        difficultBtn.setBackground(null);
        extremeBtn.setBackground(null);

        switch (level) {
            case "Easy": easyBtn.setBackground(Color.GREEN); break;
            case "Intermediate": intermediateBtn.setBackground(Color.ORANGE); break;
            case "Difficult": difficultBtn.setBackground(Color.RED); break;
            case "Extreme": extremeBtn.setBackground(Color.MAGENTA); break;
        }
    }
// RoundedBorder class for custom borders
    class RoundedBorder implements Border {
        private final int radius;
        private final Color borderColor;
        private final float thickness;

        public RoundedBorder(int radius) {
            this(radius, Color.WHITE, 1.5f);
        }

        public RoundedBorder(int radius, Color borderColor, float thickness) {
            this.radius = radius;
            this.borderColor = borderColor;
            this.thickness = thickness;
        }

        public Insets getBorderInsets(Component c) {
            int pad = (int) thickness;
            return new Insets(pad, pad, pad, pad);
        }

        public boolean isBorderOpaque() { return false; }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(thickness));
            int adj = (int) (thickness / 2);
            g2.drawRoundRect(x + adj, y + adj, width - adj * 2 - 1, height - adj * 2 - 1, radius, radius);
            g2.dispose();
        }
    }
// GradientPanel class for the border gradient
    class bordeGradient extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
             Color color1 = new Color(153, 102, 204);
            Color color2 = new Color(102, 0, 204);
            int width = getWidth();
            int height = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);
        }
    }
//for the gradient background
    // GradientPanel class for the background gradient
    class GradientPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Color color1 = new Color(153, 102, 204);
            Color color2 = new Color(102, 0, 204);
            int width = getWidth();
            int height = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);
        }
    }

    // Add this method to ExercisePanel: //show user info dialog
    private void showUserInfoDialog() {
        JDialog infoDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Your Info", true);
        infoDialog.setUndecorated(true);
        infoDialog.setSize(260, 210);
        infoDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(102, 0, 204, 240));
        panel.setBorder(new RoundedBorder(20, Color.WHITE, 2f));

        JLabel title = new JLabel("Your Profile", SwingConstants.CENTER);
        title.setFont(new Font("Montserrat", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 15, 260, 30);
        panel.add(title);

        JLabel ageLabel = new JLabel("Age: " + age);
        ageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        ageLabel.setForeground(Color.WHITE);
        ageLabel.setBounds(30, 55, 200, 25);
        panel.add(ageLabel);

        JLabel heightLabel = new JLabel("Height: " + height + " cm");
        heightLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        heightLabel.setForeground(Color.WHITE);
        heightLabel.setBounds(30, 80, 200, 25);
        panel.add(heightLabel);

        JLabel weightLabel = new JLabel("Weight: " + weight + " kg");
        weightLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        weightLabel.setForeground(Color.WHITE);
        weightLabel.setBounds(30, 105, 200, 25);
        panel.add(weightLabel);

        // Calculate BMI
        double heightInMeters = height / 100.0;
        double bmi = weight / (heightInMeters * heightInMeters);
        JLabel bmiLabel = new JLabel(String.format("BMI: %.2f", bmi));
        bmiLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        bmiLabel.setForeground(Color.WHITE);
        bmiLabel.setBounds(30, 130, 200, 25);
        panel.add(bmiLabel);

        JButton closeBtn = new JButton("Close");
        closeBtn.setBounds(80, 165, 100, 28);
        closeBtn.setBackground(new Color(153, 102, 204));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        closeBtn.setFocusPainted(false);
        closeBtn.setBorder(new RoundedBorder(15));
        closeBtn.addActionListener(e -> infoDialog.dispose());
        panel.add(closeBtn);

        infoDialog.setContentPane(panel);
        infoDialog.setVisible(true);
    }
}
