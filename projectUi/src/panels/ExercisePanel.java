package src.panels;

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

    private JTextArea exerciseList;
    private JTextField customExerciseField;
    private JButton addButton;
    private JButton[] dayButtons;
    private JButton activeDayButton;
    private JButton easyBtn, intermediateBtn, difficultBtn, extremeBtn;
    private Map<Integer, java.util.List<String>> dayExerciseMap;
    private int currentDay = 1;

    public ExercisePanel(int age, double height, double weight, String goal) {

        this.goal = goal.toLowerCase();
       
        setLayout(null);

        GradientPanel addbackground = new GradientPanel();
        addbackground.setLayout(null);
        addbackground.setBounds(0, 0, 400, 800);
        add(addbackground);

        dayExerciseMap = new HashMap<>();
        loadExerciseMap();

        JLabel title = new JLabel("Weekly Workout Plan", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setBounds(80, 15, 250, 30);
        addbackground.add(title);
        

        easyBtn = createDifficultyButton("Easy");
        // easyBtn.setForeground(Color.BLACK.darker());
        intermediateBtn = createDifficultyButton("Intermediate");
        difficultBtn = createDifficultyButton("Difficult");
        extremeBtn = createDifficultyButton("Extreme");

        easyBtn.setBounds(14, 65, 80, 50);
        intermediateBtn.setBounds(102, 65, 100, 50);
        difficultBtn.setBounds(211, 65, 80, 50);
        extremeBtn.setBounds(300, 65, 80, 50);

        easyBtn.setBorder(new RoundedBorder(0));
        intermediateBtn.setBorder(new RoundedBorder(0));
        difficultBtn.setBorder(new RoundedBorder(0));
        extremeBtn.setBorder(new RoundedBorder(0));

        styleButton(easyBtn);
        styleButton(intermediateBtn);
        styleButton(difficultBtn);
        styleButton(extremeBtn);

        addbackground.add(easyBtn);
        addbackground.add(intermediateBtn);
        addbackground.add(difficultBtn);
        addbackground.add(extremeBtn);

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

        exerciseList = new JTextArea();
        exerciseList.setEditable(false);
        exerciseList.setOpaque(false);
        exerciseList.setForeground(Color.WHITE);
        exerciseList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        exerciseList.setMargin(new Insets(10, 10, 100, 10));

        JScrollPane scrollPanel = new JScrollPane(exerciseList);
        scrollPanel.setBounds(100, 10, 240, 260);
        scrollPanel.setOpaque(false);
        scrollPanel.getViewport().setOpaque(false);
        scrollPanel.setBorder(null);
        scrollPanel.getViewport().setBorder(null);
        exerciseList.setBorder(null);
        dayExercisePanel.add(scrollPanel);

        JLabel addLabel = new JLabel("Want to add other Exercise?", SwingConstants.CENTER);
        addLabel.setFont(new Font("Garamond", Font.BOLD, 17));
        addLabel.setForeground(Color.WHITE);
        addLabel.setBounds(30, 450, 320, 30);
        addbackground.add(addLabel);

        JLabel subLabel = new JLabel("Just fill up this form", SwingConstants.CENTER);
        subLabel.setFont(new Font("Garamond", Font.PLAIN, 14));
        subLabel.setForeground(Color.WHITE);
        subLabel.setBounds(30, 490, 320, 20);
        addbackground.add(subLabel);

        bordeGradient formPanel = new bordeGradient();
        formPanel.setLayout(null);
        formPanel.setBounds(18, 550, 363, 120);
        formPanel.setOpaque(false);
        formPanel.setBorder(new RoundedBorder(20, Color.WHITE, 2f));
        addbackground.add(formPanel);

        JLabel exerciseLabel = new JLabel("Exercise");
        exerciseLabel.setForeground(Color.WHITE);
        exerciseLabel.setFont(new Font("Century", Font.BOLD, 15));
        exerciseLabel.setBounds(25, 10, 100, 20);
        formPanel.add(exerciseLabel);

        customExerciseField = new JTextField();
        customExerciseField.setOpaque(false);
        customExerciseField.setBorder(new RoundedBorder(10, Color.WHITE, 1f));
        customExerciseField.setForeground(Color.WHITE);
        customExerciseField.setCaretColor(Color.WHITE);
        customExerciseField.setFont(new Font("DialogInput", Font.PLAIN, 14));
        customExerciseField.setBounds(27, 50, 300, 30);
        formPanel.add(customExerciseField);

        
        Color violet = new Color(102, 0, 204);
        Color shadowColor = new Color(153, 102, 204);

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

        addButton.addActionListener(e -> addCustomExercise());
        addbackground.add(addButton);

        customExerciseField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { toggleAddButton(); }
            public void removeUpdate(DocumentEvent e) { toggleAddButton(); }
            public void changedUpdate(DocumentEvent e) { toggleAddButton(); }
        });

        showExercises(currentDay);
        setVisible(true);
    }

    private void toggleAddButton() {
        addButton.setEnabled(!customExerciseField.getText().trim().isEmpty());
    }

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

    private void loadExerciseMap() {
        for (int i = 1; i <= 7; i++) {
            switch (goal) {
                case "lose weight":
                    dayExerciseMap.put(1, new ArrayList<>(Arrays.asList("5 Push-ups", "5 Squats", "30 sec Plank")));
                    dayExerciseMap.put(2, new ArrayList<>(Arrays.asList("10 Push-ups", "10 Squats", "30 sec Plank")));
                    dayExerciseMap.put(3, new ArrayList<>(Arrays.asList("15 Push-ups", "20 Squats", "45 sec Plank")));
                    dayExerciseMap.put(4, new ArrayList<>(Arrays.asList("20 Push-ups", "30 Squats", "1 min Plank")));
                    dayExerciseMap.put(5, new ArrayList<>(Arrays.asList("30 Push-ups", "40 Squats", "1.5 min Plank")));
                    dayExerciseMap.put(6, new ArrayList<>(Arrays.asList("40 Push-ups", "50 Squats", "2 min Plank")));
                    dayExerciseMap.put(7, new ArrayList<>(Arrays.asList("50 Push-ups", "60 Squats", "3 min Plank")));
                    break;

                case "build muscle":
                    dayExerciseMap.put(1, new ArrayList<>(Arrays.asList("5 Push-ups", "5 Squats", "30 sec Plank")));
                    dayExerciseMap.put(2, new ArrayList<>(Arrays.asList("10 Push-ups", "10 Squats", "30 sec Plank")));
                    dayExerciseMap.put(3, new ArrayList<>(Arrays.asList("15 Push-ups", "20 Squats", "45 sec Plank")));
                    dayExerciseMap.put(4, new ArrayList<>(Arrays.asList("20 Push-ups", "30 Squats", "1 min Plank")));
                    dayExerciseMap.put(5, new ArrayList<>(Arrays.asList("30 Push-ups", "40 Squats", "1.5 min Plank")));
                    dayExerciseMap.put(6, new ArrayList<>(Arrays.asList("40 Push-ups", "50 Squats", "2 min Plank")));
                    dayExerciseMap.put(7, new ArrayList<>(Arrays.asList("50 Push-ups", "60 Squats", "3 min Plank")));
                    break;

                case "maintain":
                     dayExerciseMap.put(1, new ArrayList<>(Arrays.asList("5 Push-ups", "5 Squats", "30 sec Plank")));
                    dayExerciseMap.put(2, new ArrayList<>(Arrays.asList("10 Push-ups", "10 Squats", "30 sec Plank")));
                    dayExerciseMap.put(3, new ArrayList<>(Arrays.asList("15 Push-ups", "20 Squats", "45 sec Plank")));
                    dayExerciseMap.put(4, new ArrayList<>(Arrays.asList("20 Push-ups", "30 Squats", "1 min Plank")));
                    dayExerciseMap.put(5, new ArrayList<>(Arrays.asList("30 Push-ups", "40 Squats", "1.5 min Plank")));
                    dayExerciseMap.put(6, new ArrayList<>(Arrays.asList("40 Push-ups", "50 Squats", "2 min Plank")));
                    dayExerciseMap.put(7, new ArrayList<>(Arrays.asList("50 Push-ups", "60 Squats", "3 min Plank")));
                    break;
            }
           
        }
    }

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

    private JButton createDifficultyButton(String label) {
        JButton btn = new JButton(label);
        btn.setEnabled(false);
        return btn;
    }

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
}
