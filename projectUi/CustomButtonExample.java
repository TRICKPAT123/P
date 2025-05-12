import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;

public class CustomButtonExample {

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Custom Button Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create the button
        JButton button = new JButton("Custom Button") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Set background color based on button state
                if (getModel().isArmed()) {
                    g2.setColor(new Color(0x5329D1)); // Active background color
                } else {
                    g2.setColor(new Color(0x702CFB)); // Default background color
                }

                // Draw rounded background
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Call the super method to draw the text and icon
                super.paintComponent(g);
            }

            @Override
            public void paintBorder(Graphics g) {
                // Prevent default border painting
            }
        };

        // Set button properties
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(false);  // Transparent background
        button.setContentAreaFilled(false); // No background content area
        button.setBorder(new RoundedBorder(20)); // Custom border
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Mouse hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBorder(new RoundedBorder(20, new Color(0x702CFB), 2f)); // Hover effect
                button.setForeground(new Color(238, 130, 238)); // Change text color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBorder(new RoundedBorder(20)); // Reset border on exit
                button.setForeground(Color.WHITE); // Reset text color
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBorder(new RoundedBorder(20, new Color(0x5329D1), 2f)); // Active border effect
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBorder(new RoundedBorder(20)); // Reset border after release
            }
        });

        // Add button to the frame
        frame.add(button);
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
    }
}

// === Custom Rounded Border ===
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
