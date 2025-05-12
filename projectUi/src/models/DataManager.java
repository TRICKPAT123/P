package src.models;
//package models;


//import panels.UserPanel;
//import models.User;
//import logic.Calculator;
import javax.swing.*;
// import javax.swing.border.EmptyBorder;
import java.awt.image.BufferedImage;
import java.awt.geom.RoundRectangle2D;
import java.awt.*;

public class DataManager extends JFrame {

    public DataManager() {
        // Window settings
        setUndecorated(true); // Removes top bar
        setSize(400, 850);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40)); // Rounded window corners
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        Image blankImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        setIconImage(blankImage);

        // Background Panel (with gradient)
        GradientPanel gradientPanel = new GradientPanel();
        gradientPanel.setLayout(null);
        setContentPane(gradientPanel);

    

        // 📱 Bottom Navigation Bar Panel
        JPanel navBar = new JPanel();
        navBar.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 0));
        navBar.setBounds(0, 800, 400, 40);
        navBar.setOpaque(false);

        // ◁ Back Button
        JButton backBtn = createNavButton("◁");
        backBtn.addActionListener(e -> {
            dispose();
            new DataManager().setVisible(true);
        });

        // ○ Home Button
        JButton homeBtn = createNavButton("○");
        homeBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Home button pressed!");
        });

        // ▢ Recent Button
        JButton recentBtn = createNavButton("▢");
        recentBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Recent apps button pressed!");
        });

        navBar.add(backBtn);
        navBar.add(homeBtn);
        navBar.add(recentBtn);
        gradientPanel.add(navBar);

        setVisible(true);
    }

    // 🛠️ Helper method to create transparent nav buttons
    private JButton createNavButton(String symbol) {
        JButton button = new JButton(symbol);
        button.setFont(new Font("Dialog", Font.PLAIN, 24));
        button.setForeground(Color.WHITE);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }


    

    // 🌈 Gradient background panel
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Color color1 = new Color(51, 0, 102);
            Color color2 = new Color(153, 102, 204);
            int width = getWidth();
            int height = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);
        }
    }

    public static void main(String[] args) {
        new DataManager();
    }
}


