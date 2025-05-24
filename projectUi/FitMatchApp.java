import java.net.URL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.ComboPopup;

import src.panels.ExercisePanel;

import java.awt.event.ActionEvent;
import java.awt.geom.RoundRectangle2D;

// FitMatchApp.java
public class FitMatchApp extends JFrame {
private static final boolean True = false;
JTextField ageField, heightField, weightField;
private JCheckBox loseWeightBox;
private JCheckBox buildMuscleBox;
private JCheckBox maintainBox;

// Constructor to set up the main frame and components
public FitMatchApp() {
    // Set up the main frame
    setUndecorated(true);
    setSize(400, 800);
    setLocationRelativeTo(null);
    setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Show introduction dialog before anything else
    showIntroDialog();
// Create the main background panel with gradient
    GradientPanel addbackground = new GradientPanel();
    addbackground.setLayout(null);
    addbackground.setBounds(0, 0, 400, 800);
// Set background color with transparency
    JLabel title = new JLabel("Welcome to the FitPlan Alex!", SwingConstants.CENTER);
    title.setBounds(20, 30, 360, 50);
    title.setFont(new Font("Serif", Font.BOLD, 20));
    title.setForeground(Color.WHITE);
    addbackground.add(title);
// Subtitle
    JLabel text = new JLabel("Let's Setup your plan", SwingConstants.CENTER);
    text.setBounds(20, 60, 360, 50);
    text.setFont(new Font("Century", Font.BOLD, 15));
    text.setForeground(Color.WHITE);
    addbackground.add(text);

// Goal Panel
    bordeGradient Panel1 = new bordeGradient();
    Panel1.setLayout(null);
    Panel1.setBackground(new Color(0, 0, 0, 0));
    Panel1.setBounds(20, 143, 360, 180);
    Panel1.setBorder(new RoundedBorder(20));
    addbackground.add(Panel1);
// Goal Label
    JLabel goalLabel = new JLabel("Set your Goal - Step 1 of 3");
    goalLabel.setBounds(30, 120, 300, 25);
    goalLabel.setForeground(Color.WHITE);
    goalLabel.setFont(new Font("Garamond", Font.BOLD, 14));
    addbackground.add(goalLabel);
// Create checkboxes for fitness goals
    String[] goals = {"Lose Weight", "Build Muscle", "Maintain"};
    int y = 27;
// Load icons for checkboxes
    URL uncheckedURL = getClass().getResource("/images/square.png");
    URL checkedURL = getClass().getResource("/images/check.png");
// Check if the URLs are null
    if (uncheckedURL == null || checkedURL == null) {
        System.err.println("⚠️ Icon file not found. Make sure /images/square.png and /images/check.png exist in resources.");
    }
// Load and scale the icons
    ImageIcon uncheckedIcon = new ImageIcon(new ImageIcon(uncheckedURL).getImage().getScaledInstance(21, 21, Image.SCALE_SMOOTH));
    ImageIcon checkedIcon = new ImageIcon(new ImageIcon(checkedURL).getImage().getScaledInstance(21, 21, Image.SCALE_SMOOTH));
// Create checkboxes for each goal
    for (String goal : goals) {
        JPanel row = new JPanel(null);
        row.setOpaque(false);
        row.setBounds(25, y, 300, 25);
// Create a label for the goal
        JLabel label = new JLabel(goal);
        label.setFont(new Font("Century", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 16));
        label.setBounds(10, 0, 200, 25);
        row.add(label);
// Create a transparent checkbox with custom icons
        JCheckBox checkBox = new JCheckBox();
        checkBox.setBounds(250, 2, 35, 25);
        checkBox.setOpaque(false);
        checkBox.setIcon(uncheckedIcon);
        checkBox.setSelectedIcon(checkedIcon);
        checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        row.add(checkBox);

        Panel1.add(row);
        y += 50;

        switch (goal) {
            case "Lose Weight": loseWeightBox = checkBox; break;
            case "Build Muscle": buildMuscleBox = checkBox; break;
            case "Maintain": maintainBox = checkBox; break;
        }
    }
// Setup behavior for goal checkboxes to allow only one selection at a time
    setupGoalCheckboxBehavior();

    // Personal Panel
    bordeGradient Panel2 = new bordeGradient();
    Panel2.setLayout(null);
    Panel2.setBounds(20, 360, 360, 170);
    Panel2.setBorder(new RoundedBorder(20));
    addbackground.add(Panel2);
// Personal Detail Label
    JLabel personalLabel = new JLabel("Enter Personal Detail - Step 2 of 3");
    personalLabel.setBounds(30, 340, 310, 20);
    personalLabel.setForeground(Color.WHITE);
    personalLabel.setFont(new Font("Garamond", Font.BOLD, 14));
    addbackground.add(personalLabel);
// Create text fields for age, height, and weight
    ageField = createTextField(Panel2, "Age", 25);
    ageField.setBorder(new RoundedBorder(15, Color.WHITE, 1f));
    ageField.setFont(new Font("DialogInput", Font.PLAIN,19));
    heightField = createTextField(Panel2, "Height (cm)", 65);
    heightField.setBorder(new RoundedBorder(15, Color.WHITE, 1f));
    heightField.setFont(new Font("DialogInput", Font.PLAIN,19));
    // Create text field for weight
    weightField = createTextField(Panel2, "Weight (kg)", 110);
    weightField.setBorder(new RoundedBorder(15, Color.WHITE, 1f));
    weightField.setFont(new Font("DialogInput", Font.PLAIN,19));
    // Empty Panel 3
    bordeGradient Panel3 = new bordeGradient();
    Panel3.setLayout(null);
    Panel3.setBounds(20, 580, 360, 90);
    Panel3.setBorder(new RoundedBorder(20));
    addbackground.add(Panel3);
// Preference Label
    JLabel preferenceLabel = new JLabel("Customize Preference Step - 3 of 3");
    preferenceLabel.setForeground(Color.WHITE);
    preferenceLabel.setFont(new Font("Garamond", Font.BOLD, 14));
    preferenceLabel.setBounds(30, 560, 310, 20);
    addbackground.add(preferenceLabel);
// Workout Type Label and ComboBox
    JLabel workoutLabel = new JLabel("Workout Type");
    workoutLabel.setFont(new Font("Century", Font.BOLD, 12));
    workoutLabel.setForeground(Color.WHITE);
    workoutLabel.setBounds(18, 34, 100, 20);
    Panel3.add(workoutLabel);
// Create a transparent JComboBox for workout type
    JComboBox<String> workoutTypeBox = createTransparentComboBox(new String[]{"Home", "Gym"});
    workoutTypeBox.setBounds(140, 28, 180, 40);
    workoutTypeBox.setFont(new Font("Dialog", Font.BOLD,15));
    Panel3.add(workoutTypeBox);

    // Add Continue Button
    JButton addButton = new JButton("Continue");
    addButton.setFont(new Font("Arial", Font.BOLD, 16));
    addButton.setBounds(150, 695, 100, 50);

    Color violet = new Color(102, 0, 204);
    Color shadowColor = new Color(153, 102, 204);
    addButton.setBackground(violet);
    addButton.setForeground(Color.WHITE);
    addButton.setFocusPainted(false);
    addButton.setBorderPainted(false);
    addButton.setContentAreaFilled(false);
    addButton.setOpaque(true);
    addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    addButton.setHorizontalTextPosition(SwingConstants.RIGHT);
    addButton.setIconTextGap(10);

    addButton.setUI(new BasicButtonUI() {
        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            JButton b = (JButton) c;
            int w = b.getWidth();
            int h = b.getHeight();
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
   addButton.addActionListener(new ActionListener() {
@Override
public void actionPerformed(ActionEvent e) {
    try {
        int age = Integer.parseInt(ageField.getText());
        double height = Double.parseDouble(heightField.getText());
        double weight = Double.parseDouble(weightField.getText());

        String goal = "";
        if (loseWeightBox.isSelected()) goal = "Lose Weight";
        else if (buildMuscleBox.isSelected()) goal = "Build Muscle";
        else if (maintainBox.isSelected()) goal = "Maintain";

        if (goal.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a goal.");
            return;
        }

        Profile profile = new Profile(age, height, weight);
        double bmi = profile.calculateBMI();
        System.out.println("BMI: " + bmi);



        String workoutType = (String) workoutTypeBox.getSelectedItem();
        ExercisePanel exercisePanel = new ExercisePanel(age, height, weight, goal, workoutType); // Now passing all inputs
        setContentPane(exercisePanel);
        revalidate();
        repaint();

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Please enter valid numbers.");
    }
}

});

    addButton.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            addButton.setLocation(addButton.getX() - 3, addButton.getY() + 3);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            addButton.setLocation(addButton.getX() + 3, addButton.getY() - 3);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            addButton.setLocation(addButton.getX() - 3, addButton.getY() + 3);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            addButton.setLocation(addButton.getX() + 3, addButton.getY() - 3);
        }
    });

    addbackground.add(addButton);
    setContentPane(addbackground);
    setVisible(true);
}

// Create a text field with label and add it to the specified panel
private JTextField createTextField(JPanel panel, String labelText, int yPosition) {
    JLabel label = new JLabel(labelText);
    label.setBounds(30, yPosition, 100, 25);
    label.setForeground(Color.WHITE);
    label.setFont(new Font("Century", Font.PLAIN, 16));
    panel.add(label);

    JTextField textField = new JTextField();
    textField.setBounds(150, yPosition, 170, 30);
    textField.setOpaque(True);
    textField.setFont(new Font("Arial", Font.PLAIN, 12));
     textField.setForeground(Color.WHITE);
    panel.add(textField);

    return textField;
}
// Setup behavior for goal checkboxes to allow only one selection at a time
private void setupGoalCheckboxBehavior() {
    JCheckBox[] checkBoxes = {loseWeightBox, buildMuscleBox, maintainBox};
    for (JCheckBox checkBox : checkBoxes) {
        checkBox.addActionListener(e -> {
            for (JCheckBox other : checkBoxes) {
                if (other != checkBox) {
                    other.setSelected(false);
                }
            }
        });
    }
}
// Create a transparent JComboBox with custom UI
  private JComboBox<String> createTransparentComboBox(String[] options) {
    JComboBox<String> comboBox = new JComboBox<>(options);
    comboBox.setOpaque(true);
    comboBox.setBackground(new Color(138, 43, 226, 120));
    comboBox.setForeground(Color.WHITE);
    comboBox.setUI(new BasicComboBoxUI() {
        @Override
        protected ComboPopup createPopup() {
            ComboPopup popup = super.createPopup();
            JComponent popupComponent = (JComponent) popup;
            popupComponent.setBackground(new Color(138, 43, 226, 120));
            popupComponent.setOpaque(true);
            return popup;
        }
    });
    comboBox.setBorder(null);
    return comboBox;
}
// Custom border class for rounded corners
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

    @Override
    public Insets getBorderInsets(Component c) {
        int pad = (int) thickness;
        return new Insets(pad, pad, pad, pad);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    @Override
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
    @Override
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
    @Override
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

// Simple Profile class for BMI calculation
class Profile {
    //private int age;
    private double height; // in cm
    private double weight; // in kg

    public Profile(int age, double height, double weight) {
        //this.age = age;
        this.height = height;
        this.weight = weight;
    }

    public double calculateBMI() {
        double heightInMeters = height / 100.0;
        return weight / (heightInMeters * heightInMeters);
    }
}


private void showIntroDialog() {
    JDialog introDialog = new JDialog(this, "Welcome to FitMatch", true);
    introDialog.setSize(400, 800);
    introDialog.setLocationRelativeTo(null);
    introDialog.setUndecorated(true);
    introDialog.setShape(new RoundRectangle2D.Double(0, 0, 400, 800, 40, 40));

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(new Color(102, 0, 204, 230));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // outer padding

    JLabel title = new JLabel("Welcome to FitMatch!", SwingConstants.CENTER);
    title.setFont(new Font("Montserrat", Font.BOLD, 20));
    title.setForeground(Color.WHITE);
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(title);

    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    // Scrollable content
    JTextPane about = new JTextPane();
    about.setContentType("text/html");
    about.setText(
        "<html><body style='font-family:Segoe UI; font-size:12px; color:white;'>"
        + "FitMatch helps you create a personalized fitness plan based on your goals and preferences.<br><br>"
        + "&#8226; Set your fitness goal (Lose Weight, Build Muscle, Maintain)<br>"
        + "&#8226; Choose your workout environment (Home or Gym)<br>"
        + "&#8226; Get a weekly exercise plan tailored for you!<br><br>"
        + "<b>Terms & Agreement:</b><br>"
        + "By using this application, you agree to use the information provided for personal guidance only.<br>"
        + "Consult a healthcare professional before starting any new exercise program.<br>"
        + "FitMatch is not responsible for any injury or health issues resulting from the use of this app.<br><br>"
        + "<b>Created by BSIT-1:</b><br>"
        + "Michaella Jiliane Bungcayao<br>"
        + "Adrian Gamayon Dulloog<br>"
        + "Angel Magpali<br>"
        + "Samoza Kaye<br>"
        + "Patrick Agtang"
        + "</body></html>"
    );
    about.setEditable(false);
    about.setOpaque(false);
    about.setBorder(null);
    about.setFocusable(false);

    JScrollPane scrollPane = new JScrollPane(about);
    scrollPane.setPreferredSize(new Dimension(340, 500)); // height adjusted
    scrollPane.setBorder(null);
    scrollPane.setOpaque(false);
    scrollPane.getViewport().setOpaque(false);
    panel.add(scrollPane);

    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    JCheckBox agreeBox = new JCheckBox("I have read and agree to the terms above.");
    
    agreeBox.setForeground(Color.WHITE);
    agreeBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
    agreeBox.setOpaque(false);
    agreeBox.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(agreeBox);

    
    panel.add(Box.createRigidArea(new Dimension(0, 50)));

    JButton acceptBtn = new JButton("Accept & Continue");
   
    acceptBtn.setEnabled(false);
    acceptBtn.setBackground(new Color(153, 102, 204));
    acceptBtn.setForeground(Color.WHITE);
    acceptBtn.setFont(new Font("Montserrat", Font.BOLD, 20));
    acceptBtn.setFocusPainted(false);
    acceptBtn.setBorder(new RoundedBorder(10, Color.WHITE, 1.5f));
    acceptBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(acceptBtn);

    panel.add(Box.createRigidArea(new Dimension(0, 55)));

    agreeBox.addActionListener(e -> acceptBtn.setEnabled(agreeBox.isSelected()));
    acceptBtn.addActionListener(e -> introDialog.dispose());

    introDialog.setContentPane(panel);
    introDialog.setVisible(true);
}



public static void main(String[] args) {
SwingUtilities.invokeLater(() -> new FitMatchApp());
}}