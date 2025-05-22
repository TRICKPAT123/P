package uipro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class MedicineFormPanel extends JPanel {
    public MedicineFormPanel(JPanel container, Consumer<ReminderData> onDone) {
        setLayout(null);
        setBackground(new Color(245, 245, 245));
        setPreferredSize(new Dimension(380, 260));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2, false),
            BorderFactory.createEmptyBorder(18, 24, 18, 24)
        ));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 15);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15);

        // Title
        JLabel formTitle = new JLabel("Add Medicine");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        formTitle.setForeground(new Color(100, 149, 237));
        formTitle.setBounds(5, 8, 350, 28);
        add(formTitle);

        int y = 45, h = 28, gap = 8, labelW = 130, fieldW = 200;

        JLabel nameLabel = new JLabel("Medicine Name:");
        nameLabel.setFont(labelFont);
        nameLabel.setBounds(10, y, labelW, h);
        add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setFont(fieldFont);
        nameField.setBounds(145, y, fieldW, h);
        add(nameField);

        y += h + gap;
        JLabel timeLabel = new JLabel("Time:");
        timeLabel.setFont(labelFont);
        timeLabel.setBounds(10, y, labelW, h);
        add(timeLabel);

        JTextField timeField = new JTextField();
        timeField.setFont(fieldFont);
        timeField.setBounds(145, y, fieldW, h);
        add(timeField);

        y += h + gap;
        JLabel repeatLabel = new JLabel("Repeat:");
        repeatLabel.setFont(labelFont);
        repeatLabel.setBounds(10, y, labelW, h);
        add(repeatLabel);

        JTextField repeatField = new JTextField();
        repeatField.setFont(fieldFont);
        repeatField.setBounds(145, y, fieldW, h);
        add(repeatField);

        y += h + gap;
        JLabel ringtoneLabel = new JLabel("Ringtone & Vibration:");
        ringtoneLabel.setFont(labelFont);
        ringtoneLabel.setBounds(10, y, labelW, h);
        add(ringtoneLabel);

        JTextField ringtoneField = new JTextField();
        ringtoneField.setFont(fieldFont);
        ringtoneField.setBounds(145, y, fieldW, h);
        add(ringtoneField);

        y += h + gap;
        JLabel notesLabel = new JLabel("Notes:");
        notesLabel.setFont(labelFont);
        notesLabel.setBounds(10, y, labelW, h);
        add(notesLabel);

        JTextField notesField = new JTextField();
        notesField.setFont(fieldFont);
        notesField.setBounds(145, y, fieldW, h);
        add(notesField);

        // Buttons with modern design
        JButton cancelBtn = new JButton("Cancel") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(220, 53, 69));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setContentAreaFilled(false);
        cancelBtn.setBorderPainted(false);
        cancelBtn.setOpaque(false);
        cancelBtn.setBounds(70, y + h + 10, 100, 36);
        cancelBtn.addActionListener((ActionEvent e) -> {
            container.remove(this);
            container.revalidate();
            container.repaint();
            if (onDone != null) {
                onDone.accept(null); // null indicates cancel
            }
        });
        add(cancelBtn);

        JButton doneBtn = new JButton("Done") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(40, 167, 69));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        doneBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        doneBtn.setForeground(Color.WHITE);
        doneBtn.setFocusPainted(false);
        doneBtn.setContentAreaFilled(false);
        doneBtn.setBorderPainted(false);
        doneBtn.setOpaque(false);
        doneBtn.setBounds(200, y + h + 10, 100, 36);
        doneBtn.addActionListener((ActionEvent e) -> {
            ReminderData data = new ReminderData(
                nameField.getText(),
                timeField.getText(), // <--- USER INPUT TIME
                repeatField.getText(),
                ringtoneField.getText(),
                notesField.getText()
            );
            onDone.accept(data); // This sends the data back to MediSyncApp
            container.remove(this);
            container.revalidate();
            container.repaint();
        });
        add(doneBtn);
    }

    // Helper data class
    public static class ReminderData {
        public String name, time, repeat, ringtone, notes;
        public ReminderData(String name, String time, String repeat, String ringtone, String notes) {
            this.name = name;
            this.time = time;
            this.repeat = repeat;
            this.ringtone = ringtone;
            this.notes = notes;
        }
    }
}