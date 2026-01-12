package uipro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class MedicineFormPanel extends JPanel {
    public MedicineFormPanel(JDialog dialog, Consumer<ReminderData> onDone) {
        setLayout(null);
        setBackground(new Color(245, 245, 245));
        setPreferredSize(new Dimension(350, 210)); // Smaller size
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2, false),
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);

        // Title
        JLabel formTitle = new JLabel("Add Medicine");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formTitle.setForeground(new Color(100, 149, 237));
        formTitle.setBounds(5, 6, 250, 22);
        add(formTitle);

        int y = 30, h = 22, gap = 6, labelW = 90, fieldW = 170;

        JLabel nameLabel = new JLabel("Medicine Name:");
        nameLabel.setFont(labelFont);
        nameLabel.setBounds(10, y, labelW, h);
        add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setFont(fieldFont);
        nameField.setBounds(135, y, fieldW, h);
        add(nameField);

        y += h + gap;
        JLabel timeLabel = new JLabel("Time:");
        timeLabel.setFont(labelFont);
        timeLabel.setBounds(10, y, labelW, h);
        add(timeLabel);

        JTextField timeField = new JTextField();
        timeField.setFont(fieldFont);
        timeField.setBounds(135, y, fieldW, h);
        add(timeField);

        y += h + gap;
        JLabel repeatLabel = new JLabel("Repeat:");
        repeatLabel.setFont(labelFont);
        repeatLabel.setBounds(10, y, labelW, h);
        add(repeatLabel);

        String[] repeatOptions = {"Once", "Twice", "3 times a day"};
        JComboBox<String> repeatCombo = new JComboBox<>(repeatOptions);
        repeatCombo.setFont(fieldFont);
        repeatCombo.setBounds(135, y, fieldW, h);
        add(repeatCombo);

        y += h + gap;
        JLabel ringtoneLabel = new JLabel("Ringtone:");
        ringtoneLabel.setFont(labelFont);
        ringtoneLabel.setBounds(10, y, labelW, h);
        add(ringtoneLabel);

        String[] ringtoneOptions = {"Ringtone", "Vibration", "Ringtone & Vibration"};
        JComboBox<String> ringtoneCombo = new JComboBox<>(ringtoneOptions);
        ringtoneCombo.setFont(fieldFont);
        ringtoneCombo.setBounds(135, y, fieldW, h);
        add(ringtoneCombo);

        y += h + gap;
        JLabel notesLabel = new JLabel("Notes:");
        notesLabel.setFont(labelFont);
        notesLabel.setBounds(10, y, labelW, h);
        add(notesLabel);

        JTextField notesField = new JTextField();
        notesField.setFont(fieldFont);
        notesField.setBounds(135, y, fieldW, h);
        add(notesField);

        // Buttons
        JButton cancelBtn = new JButton("Cancel") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(220, 53, 69));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setContentAreaFilled(false);
        cancelBtn.setBorderPainted(false);
        cancelBtn.setOpaque(false);
        cancelBtn.setBounds(45, y + h + 8, 80, 28);
        cancelBtn.addActionListener((ActionEvent e) -> dialog.dispose());
        add(cancelBtn);

        JButton doneBtn = new JButton("Done") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(40, 167, 69));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        doneBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        doneBtn.setForeground(Color.WHITE);
        doneBtn.setFocusPainted(false);
        doneBtn.setContentAreaFilled(false);
        doneBtn.setBorderPainted(false);
        doneBtn.setOpaque(false);
        doneBtn.setBounds(190, y + h + 8, 80, 28);
        doneBtn.addActionListener((ActionEvent e) -> {
            ReminderData data = new ReminderData(
                nameField.getText(),
                timeField.getText(),
                (String) repeatCombo.getSelectedItem(),
                (String) ringtoneCombo.getSelectedItem(),
                notesField.getText()
            );
            onDone.accept(data);
            dialog.dispose();
        });
        add(doneBtn);
    }

    // Helper data class
    public static class ReminderData {
        public String name, time, repeat, ringtone, notes;
        public boolean shown = false; // Add this flag

        public ReminderData(String name, String time, String repeat, String ringtone, String notes) {
            this.name = name;
            this.time = time;
            this.repeat = repeat;
            this.ringtone = ringtone;
            this.notes = notes;
        }
    }
}