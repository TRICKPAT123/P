package src.panels.Style;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.ComboPopup;

import java.awt.Color;
import java.awt.Font;

import java.awt.*;
import java.net.URL;

public class UIUtils {
   

    public static void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
    }


    public static JLabel createHeaderLabel(String text, int x, int y, int w, int h) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Dialog", Font.BOLD, 16));
        label.setBounds(x, y, w, h);
        return label;
    }

    public static JLabel createSubLabel(String text, int x, int y, int w, int h) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Dialog", Font.PLAIN, 12));
        label.setBounds(x, y, w, h);
        return label;
    }

    public static JLabel createSectionLabel(String text, int x, int y, int w, int h) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Dialog", Font.BOLD, 12));
        label.setBounds(x, y, w, h);
        return label;
    }

    public static JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setBounds(x, y, 100, 20);
        return label;
    }

    public static JPanel createRoundedPanel() {
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

    public static JTextField createTextField(JPanel parent, String label, int y) {
        JLabel jLabel = createLabel(label, 10, y);
        parent.add(jLabel);

        JTextField field = new JTextField();
        field.setBounds(120, y, 180, 20);
        field.setOpaque(false);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(new EmptyBorder(0, 5, 0, 5));
        parent.add(field);
        return field;
    }

    public static JComboBox<String> createTransparentComboBox(String[] options) {
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
    

    public static JCheckBox createCheckBox(String labelText, int x, int y, int checkX, int checkY, ImageIcon uncheckedIcon, ImageIcon checkedIcon) {
        JPanel row = new JPanel(null);
        row.setOpaque(false);
        row.setBounds(x, y, 300, 25);

        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        label.setBounds(10, 0, 200, 25);
        row.add(label);

        JCheckBox checkBox = new JCheckBox();
        checkBox.setBounds(checkX, checkY, 35, 25);
        checkBox.setOpaque(false);
        checkBox.setIcon(uncheckedIcon);
        checkBox.setSelectedIcon(checkedIcon);
        checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        row.add(checkBox);
        checkBox.putClientProperty("parentPanel", row);  // To retrieve parent if needed
        return checkBox;
    }

    public static JButton createStyledButton(String text, int x, int y, int w, int h) {
        JButton button = new JButton(text);
        button.setBounds(x, y, w, h);
        button.setFont(new Font("Dialog", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorder(new RoundedBorder(15, Color.WHITE));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Attach hover effect
        createButtonHoverEffect(button);
        
        return button;
    }

    public static void createButtonHoverEffect(final JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(204, 204, 255));  // Change to desired hover color
                button.setBorder(new RoundedBorder(15, new Color(204, 204, 255)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.WHITE);  // Reset to original color
                button.setBorder(new RoundedBorder(15, Color.WHITE));
            }
        });
    }

    public static ImageIcon loadIcon(String path, int w, int h) {
        URL url = UIUtils.class.getResource(path);
        if (url == null) {
            System.err.println("⚠️ Icon not found: " + path);
            return null;
        }
        return new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
    }
    
}
