import javax.swing.*;
import javax.swing.border.Border;
//import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import src.panels.ExercisePanel;
import src.panels.NutritionPanel;
import src.panels.ProgressPanel;
import src.panels.UserPanel;

import java.awt.*;
//import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
//import java.awt.image.BufferedImage;
import java.io.InputStream;

public class FitMatchApp extends JFrame {

private JButton createAnimatedTab(String label, String iconPath, Runnable onClick) {
    ImageIcon baseIcon = new ImageIcon(iconPath);
    Image originalImage = baseIcon.getImage();
    Image scaledImage = originalImage.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
    ImageIcon icon = new ImageIcon(scaledImage);

    JButton button = new JButton(label, icon);
    button.setFocusPainted(false);
    button.setForeground(Color.WHITE);
    button.setFont(new Font("Dialog", Font.BOLD, 10));
    button.setHorizontalTextPosition(SwingConstants.CENTER);
    button.setVerticalTextPosition(SwingConstants.BOTTOM);
    button.setBorderPainted(false);
    button.setContentAreaFilled(false);
    button.setOpaque(false);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    button.addActionListener(e -> onClick.run());

    // Hover animation (scale icon)
    button.addMouseListener(new MouseAdapter() {
        Timer growTimer;
        Timer shrinkTimer;
        int size = 24;

        @Override
        public void mouseEntered(MouseEvent e) {
            if (shrinkTimer != null && shrinkTimer.isRunning()) shrinkTimer.stop();
            growTimer = new Timer(10, null);
            growTimer.addActionListener(ev -> {
                if (size < 32) {
                    size++;
                    button.setIcon(new ImageIcon(originalImage.getScaledInstance(size, size, Image.SCALE_SMOOTH)));
                } else {
                    growTimer.stop();
                }
            });
            growTimer.start();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (growTimer != null && growTimer.isRunning()) growTimer.stop();
            shrinkTimer = new Timer(10, null);
            shrinkTimer.addActionListener(ev -> {
                if (size > 24) {
                    size--;
                    button.setIcon(new ImageIcon(originalImage.getScaledInstance(size, size, Image.SCALE_SMOOTH)));
                } else {
                    shrinkTimer.stop();
                }
            });
            shrinkTimer.start();
        }
    });

    return button;
}

    private JPanel contentPanel; // This will hold the center content

    public FitMatchApp() {
        setUndecorated(true);
        setSize(400, 800);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        // Place this line just before or after setUndecorated(true);
        setIconImage(new ImageIcon(getClass().getResource("/images/healthy.png")).getImage());

        GradientPanel gradientPanel = new GradientPanel();
        gradientPanel.setLayout(null);  // Use absolute positioning
        setContentPane(gradientPanel);
 
       JPanel bottomMenu = new JPanel() {
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
bottomMenu.setLayout(new GridLayout(1, 4));
bottomMenu.setBounds(0, 730, 400, 70);
bottomMenu.setOpaque(false);  // Transparent background to allow custom gradient to show

        // Create tabs with icon hover scaling
        JButton profileTab = createAnimatedTab("Home", "images/home-page.png", () -> showHomePanel());
        JButton nutritionTab = createAnimatedTab("Nutrition", "images/nutrition (2).png", () -> showPanel(new NutritionPanel()));
        JButton exerciseTab = createAnimatedTab("Exercise", "images/exercise.png", () -> showPanel(new ExercisePanel()));
        JButton progressTab = createAnimatedTab("Progress", "images/progress.png", () -> showPanel(new ProgressPanel()));

        bottomMenu.add(profileTab);
        bottomMenu.add(nutritionTab);
        bottomMenu.add(exerciseTab);
        bottomMenu.add(progressTab);
        gradientPanel.add(bottomMenu);


        // Central content panel
        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBounds(0, 0, 400, 730);
        contentPanel.setOpaque(false); // ✅ Transparent so background gradient shows
        gradientPanel.add(contentPanel);

         showHomePanel();
         setVisible(true);

    }

     JLabel createHeaderBox(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Dialog", Font.BOLD, 13));
        label.setForeground(Color.WHITE);
        label.setOpaque(false);
        label.setBackground(Color.WHITE);
        label.setBorder(new RoundedBorder(10));
        return label;
    }

    private void showPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.repaint();
        contentPanel.revalidate();

        panel.setBounds(0, 0, 400, 730);
        panel.setOpaque(false); // ✅ Make sub-panels transparent too
        contentPanel.add(panel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    private void showHomePanel() {
        contentPanel.removeAll();
        contentPanel.repaint();
        contentPanel.revalidate();
    try{
        InputStream fontStream = getClass().getResourceAsStream("/fonts/Cascadia_Mono/CascadiaMono-Italic-VariableFont_wght.ttf");
       
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.BOLD, 20);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            JLabel welcomeLabel = new JLabel("Good Morning Alex!", SwingConstants.CENTER);
            welcomeLabel.setForeground(Color.WHITE);
            welcomeLabel.setFont(customFont);
            welcomeLabel.setBounds(-1, 50, 300, 40);
            contentPanel.add(welcomeLabel);
       

    }catch(Exception e){
        System.err.println("Error loading font");
        e.printStackTrace();
    }
          // Common box size
  int boxWidth = 103;
int boxHeight = 40;
int startX = 30;
int spacing = 20;

try {
    InputStream fontStream = getClass().getResourceAsStream("/fonts/Cascadia_Mono/CascadiaMono-Italic-VariableFont_wght.ttf");
    Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.BOLD, 13);
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    ge.registerFont(customFont);

    // Weight Box
    JLabel weight = new GradientBoxLabel("Weight", 20);
    weight.setFont(customFont);
    weight.setBounds(startX, 100, boxWidth, boxHeight);
    contentPanel.add(weight);

    // Goal Box
    JLabel goal = new GradientBoxLabel("Goal", 20);
    goal.setFont(customFont);
    goal.setBounds(startX + boxWidth + spacing, 100, boxWidth, boxHeight);
    contentPanel.add(goal);

    // Calories Remaining Box (adjust width if needed)
    JLabel calories = new GradientBoxLabel("<html><center>Calories<br></center></html>", 20);
    calories.setFont(customFont);
    calories.setBounds(startX + 2 * (boxWidth + spacing), 100, boxWidth - 10, boxHeight);
    contentPanel.add(calories);

} catch (Exception e) {
    System.err.println("Error loading font");
    e.printStackTrace();
}
        // Create the Daily panel with gradient background
JPanel Daily = new JPanel() {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Apply gradient background to the panel
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradient = new GradientPaint(0, 0, new Color(0x702CFB), 0, getHeight(), new Color(0x5F26E3)); 
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
};
Daily.setLayout(null);
Daily.setOpaque(false); // Make panel transparent so the gradient shows through
Daily.setBounds(30, 150, 340, 200); // Set the position and size of the panel
Daily.setBorder(new RoundedBorder(20)); // Apply the custom RoundedBorder
contentPanel.add(Daily); // Add the panel to the content panel

// Optionally, add content to the Daily panel (buttons, labels, etc.)

        
        JLabel dailyTrac = new JLabel("Daily Calorie Tracker ");
        dailyTrac.setForeground(Color.WHITE);
        dailyTrac.setBounds(10, 10, 200, 30); // Replace with appropriate values for x, y, width, and height
        dailyTrac.setFont(new Font("Dialog",Font.BOLD,15));
        Daily.add(dailyTrac);

        JLabel Consumed = new JLabel("You've consumed ");
        Consumed.setForeground(Color.WHITE);
        Consumed.setBounds(10,60,200,30);
        Consumed.setFont(new Font("Dialog",Font.BOLD,13));
        Daily.add(Consumed);

        // Create the Work panel with gradient background
JPanel Work = new JPanel() {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Apply gradient background to the panel
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradient = new GradientPaint(0, 0, new Color(0x702CFB), 0, getHeight(), new Color(0x5F26E3)); 
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
};
Work.setLayout(null);
Work.setOpaque(false); // Make panel transparent so the gradient shows through
Work.setBounds(30, 373, 340, 120); // Set the position and size of the panel
Work.setBorder(new RoundedBorder(20)); // Apply the custom RoundedBorder
contentPanel.add(Work); // Add the panel to the content panel

// Optionally, add content to the Work panel (buttons, labels, etc.)


        JLabel WorkOutText = new JLabel("Workout Plan");
        WorkOutText.setForeground(Color.WHITE);
        WorkOutText.setBounds(10, 10, 200, 30); // Replace with appropriate values for x, y, width, and height
        WorkOutText.setFont(new Font("Dialog",Font.BOLD,15));
        Work.add(WorkOutText);

        JLabel Todays = new JLabel("Today's Workout: ");
        Todays.setForeground(Color.WHITE);
        Todays.setBounds(10,40,200,30);
        Todays.setFont(new Font("Dialog",Font.BOLD,12));
        Work.add(Todays);
       
        JButton StartButton = new JButton("Start Workout");
        StartButton.setBounds(14, 80, 310, 30); // Adjust width for 
        StartButton.setFont(new Font("Dialog", Font.BOLD, 14));
        StartButton.setForeground(Color.WHITE);
        StartButton.setFocusPainted(false);
        StartButton.setOpaque(false);
        StartButton.setContentAreaFilled(false);
        StartButton.setBorder(new RoundedBorder(20));
        StartButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Optional: center text properly
        StartButton.setHorizontalTextPosition(SwingConstants.CENTER);
        StartButton.setIconTextGap(10); // Safe to keep even without icon

        // Optional: hover effect for user feedback
        StartButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                 StartButton.setForeground(new Color(238, 130, 238)); // Change color on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                StartButton.setForeground(Color.WHITE);
            }
        });
        Work.add(StartButton);
//meal
        // Create the Meal panel with gradient background
        JPanel Meal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Apply gradient background to the panel
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0x702CFB), 0, getHeight(), new Color(0x5F26E3));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        Meal.setLayout(null);
        Meal.setOpaque(false);
        
        Meal.setBorder(BorderFactory.createEmptyBorder());  // Remove default border
        Meal.setBorder(new RoundedBorder(20));
        Meal.setBounds(30, 515, 340, 140);
        contentPanel.add(Meal);

        // Create the "Meal Plan" label
        JLabel MealText = new JLabel("Meal Plan");
        MealText.setForeground(Color.WHITE);
        MealText.setBounds(10, 10, 200, 30); 
        MealText.setFont(new Font("Dialog", Font.BOLD, 20));  // Increase font size for title
        Meal.add(MealText);

        // Create the custom "View Full Meal Plan" button
        JButton view = new JButton("View Full Meal Plan");
        view.setBounds(10, 90, 150, 40); // Adjust width and height
        view.setFont(new Font("Arial", Font.BOLD, 12));  // Set the font
        view.setForeground(Color.WHITE);  // Text color
        view.setFocusPainted(false);  // Remove focus outline
        view.setOpaque(false);  // Make it transparent
        view.setContentAreaFilled(false);  // Make content area transparent
        view.setBorder(new RoundedBorder(20));  // Custom rounded border
        view.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Set hand cursor on hover

        // Hover effect for "View Full Meal Plan" button
        view.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                view.setForeground(new Color(238, 130, 238));  // Change text color on hover
                view.setBackground(new Color(0, 0, 0, 0));  // Transparent background on hover
                view.setBorder(new SoftRoundedBorder(20));  // Soft border effect on hover
            }

            public void mouseExited(MouseEvent evt) {
                view.setForeground(Color.WHITE);  // Reset text color
                view.setBorder(new RoundedBorder(20));  // Restore original rounded border
            }
        });

        // Create the custom "Log Meal" button
        JButton log = new JButton("Log Meal");
        log.setBounds(170, 90, 160, 40); // Adjust width as needed
        log.setFont(new Font("Arial", Font.BOLD, 12));  // Set the font
        log.setForeground(Color.WHITE);  // Text color
        log.setFocusPainted(false);  // Remove focus outline
        log.setOpaque(false);  // Make it transparent
        log.setContentAreaFilled(false);  // Make content area transparent
        log.setBorder(new RoundedBorder(20));  // Custom rounded border
        log.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Set hand cursor on hover

        // Hover effect for "Log Meal" button
        log.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                log.setForeground(new Color(238, 130, 238));  // Change text color on hover
                log.setBorder(new SoftRoundedBorder(20));  // Apply soft border effect
            }

            public void mouseExited(MouseEvent evt) {
                log.setForeground(Color.WHITE);  // Reset text color
                log.setBorder(new RoundedBorder(20));  // Restore original rounded border
            }
        });

        // Add the buttons to the panel
        Meal.add(view);
        Meal.add(log);


        JLabel suggestLunch = new JLabel("Suggested Lunch:");
        suggestLunch.setForeground(Color.WHITE);
        suggestLunch.setBounds(10, 40, 200, 30); // Replace with appropriate values for x, y, width, and height
        suggestLunch.setFont(new Font("Dialog",Font.BOLD,12));
        Meal.add(suggestLunch);

         class CircularProgressBar extends JComponent {
            private int progress = 0; // percentage (0–100)
        
            public void setProgress(int progress) {
                this.progress = Math.min(100, Math.max(0, progress));
                repaint();
            }
        
            // public int getProgress() {
            //     return progress;
            // }
        
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
        
                int size = Math.min(getWidth(), getHeight());
                int strokeWidth = 10;
                int radius = size - strokeWidth;
        
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
                // Background circle
                g2.setColor(new Color(255, 255, 255, 50)); // translucent white
                g2.setStroke(new BasicStroke(strokeWidth));
                g2.drawOval(strokeWidth / 2, strokeWidth / 2, radius, radius);
        
                // Progress arc
                g2.setColor(Color.WHITE);
                int angle = (int) (360 * progress / 100.0);
                g2.drawArc(strokeWidth / 2, strokeWidth / 2, radius, radius, 90, -angle);
        
                // Text in the center
                String text = progress + "% to goal";
                g2.setFont(new Font("Dialog", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getAscent();
                g2.drawString(text, (size - textWidth) / 2, (size + textHeight) / 2 - 4);
            }
        }
        CircularProgressBar circleBar = new CircularProgressBar();
        circleBar.setBounds(200, 20, 120, 120); // Adjust as needed
        circleBar.setProgress(1); // Set your goal %
        Daily.add(circleBar);

        // Utility method for image scaling

 

JButton addButton = new JButton("Add");
addButton.setFont(new Font("Arial", Font.BOLD, 16)); // Slightly larger font
addButton.setBounds(290, 670, 80, 50); // Bigger size

// Custom colors
Color violet = new Color(73, 39, 184); // Violet color
Color shadowColor = new Color(50, 25, 65); // Darker violet for shadow

// Set the icon (replace with your actual icon path)


// Basic appearance
addButton.setBackground(violet);
addButton.setForeground(Color.WHITE);
addButton.setFocusPainted(false);
addButton.setBorderPainted(false);
addButton.setContentAreaFilled(false);
addButton.setOpaque(true);
addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

// Position the text and icon
addButton.setHorizontalTextPosition(SwingConstants.RIGHT); // Text on the right of the icon
addButton.setIconTextGap(10); // Space between the icon and text

// Custom UI paint (rounded with shadow)
addButton.setUI(new BasicButtonUI() {
    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        JButton b = (JButton) c;
        int w = b.getWidth();
        int h = b.getHeight();

        // Shadow layer
        g2.setColor(shadowColor);
        g2.fillRoundRect(6, 6, w - 6, h - 6, 18, 18);

        // Main button layer
        g2.setColor(violet);
        g2.fillRoundRect(0, 0, w - 6, h - 6, 18, 18);

        // Draw label
        FontMetrics fm = g2.getFontMetrics();
        int textX = (w - fm.stringWidth(b.getText())) / 2;
        int textY = (h + fm.getAscent()) / 2 - 6;
        g2.setColor(Color.WHITE);
        g2.drawString(b.getText(), textX, textY);

        g2.dispose();
    }
});

// Hover & click effects
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

// Add button action
addButton.addActionListener(e -> {
    showPanel(new UserPanel(() -> {
        JOptionPane.showMessageDialog(this, "Your details have been saved!");
        showHomePanel();
    }));
});

// Add to panel
contentPanel.add(addButton);


        contentPanel.revalidate();
        contentPanel.repaint();
    }


    
    
// Custom Rounded Border class
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

        // Draw the border with the specified radius and thickness
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(thickness));
        int adj = (int) (thickness / 2);
        g2.drawRoundRect(x + adj, y + adj, width - adj * 2 - 1, height - adj * 2 - 1, radius, radius);

        g2.dispose();
    }
}

// Custom SoftRoundedBorder for the hover state (softer look on hover)
class SoftRoundedBorder extends RoundedBorder {
    public SoftRoundedBorder(int radius) {
        super(radius, new Color(160, 130, 240), 2f); // Softer color for the hover state
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set a softer, thicker border with more gradient-like effect
        g2.setColor(new Color(160, 130, 240, 180)); // Lighter color with transparency
        g2.setStroke(new BasicStroke(3f)); // Thicker border

        int adj = 1; // Make it a bit less sharp
        g2.drawRoundRect(x + adj, y + adj, width - adj * 2 - 1, height - adj * 2 - 1, 20, 20); // Slightly softer

        g2.dispose();
    }
}



// Removed unused method getScaledImage
class GradientBoxLabel extends JLabel {
    private int cornerRadius;

    public GradientBoxLabel(String text, int cornerRadius) {
        super(text, SwingConstants.CENTER); // Center the text
        this.cornerRadius = cornerRadius;
        setOpaque(false);
        setForeground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Gradient background: top (0x702CFB) to bottom (0x5F26E3)
        GradientPaint gradient = new GradientPaint(0, 0, new Color(0x702CFB), 0, getHeight(), new Color(0x5F26E3));
        g2d.setPaint(gradient);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        super.paintComponent(g);
        g2d.dispose();
    }
}


    // Gradient background panel
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

    public static void main(String[] args) {
        new FitMatchApp();
    }
}
