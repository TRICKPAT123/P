import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import uipro.MedicineFormPanel;
import uipro.MedicineFormPanel.ReminderData;

public class MediSyncApp {
    private JPanel formPanelContainer;
    private int panelCount = 0;
    private java.util.List<ReminderData> reminders = new java.util.ArrayList<>();

    private void createAndShowGUI() {
        JFrame frame = new JFrame("MediSync");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 800);
        frame.setResizable(false); // Prevent resizing
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Replace "LOGO" text with an actual logo image
        // Make sure logo.png is in your project directory or provide the correct path
        ImageIcon logoIcon = new ImageIcon("images\\logo.png");
        // Optionally resize the icon
        Image img = logoIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(img));
        topPanel.add(logoLabel, BorderLayout.WEST);

        JLabel title = new JLabel("MediSync", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        // Use a JPanel with null layout to allow setBounds, then add it to the center of topPanel
        JPanel titlePanel = new JPanel(null);
        titlePanel.setOpaque(false);
        title.setBounds(46, 0, 200, 40); // Centered horizontally in a 400px wide frame
        titlePanel.setPreferredSize(new Dimension(400, 40));
        titlePanel.add(title);

        topPanel.add(titlePanel, BorderLayout.CENTER);

        // Add a dynamic clock to the top panel
        JLabel timeLabel = new JLabel();
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        updateClock(timeLabel); // Set initial time
        // Timer to update the clock every second
        new javax.swing.Timer(1000, e -> {
            updateClock(timeLabel);
            checkAlarms();
        }).start();

        topPanel.add(timeLabel, BorderLayout.EAST);
        frame.add(topPanel, BorderLayout.NORTH);

        // Center Panel (for form & reminders)
        formPanelContainer = new JPanel();
        formPanelContainer.setLayout(new BoxLayout(formPanelContainer, BoxLayout.Y_AXIS));
        formPanelContainer.setBackground(new Color(0xD2, 0x06, 0x2A)); // Cherry red (#D2062A)

        // Custom scroll bar UI for better appearance
        JScrollPane scrollPane = new JScrollPane(
            formPanelContainer,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(100, 149, 237); // Cornflower blue
                this.trackColor = new Color(60, 63, 65);
            }
            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(new Color(60, 63, 65));
                return button;
            }
            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton button = super.createIncreaseButton(orientation);
                button.setBackground(new Color(60, 63, 65));
                return button;
            }
        });
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, Integer.MAX_VALUE));
        scrollPane.setBackground(new Color(0xD2, 0x06, 0x2A)); // Cherry red (#D2062A)
        scrollPane.getViewport().setBackground(new Color(0xD2, 0x06, 0x2A)); // Cherry red (#D2062A)
        frame.add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.LIGHT_GRAY);

        // Modern, circular "+" button with shadow and hover effect
        JButton addButton = new JButton("+") {
            private boolean hovered = false;

            {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                setFocusPainted(false);
                setContentAreaFilled(false);
                setBorderPainted(false);
                setOpaque(false);

                addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        hovered = true;
                        repaint();
                    }
                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                        hovered = false;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Shadow
                g2.setColor(new Color(0, 0, 0, 40));
                g2.fillOval(5, 8, getWidth() - 10, getHeight() - 10);

                // Button background
                if (hovered && isEnabled()) {
                    g2.setColor(new Color(80, 149, 237));
                } else if (!isEnabled()) {
                    g2.setColor(new Color(180, 180, 180));
                } else {
                    g2.setColor(new Color(100, 149, 237)); // Cornflower blue
                }
                g2.fillOval(0, 0, getWidth(), getHeight());

                // Button text
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        addButton.setFont(new Font("Arial", Font.BOLD, 32));
        addButton.setForeground(Color.WHITE);
        addButton.setPreferredSize(new Dimension(60, 60));
        addButton.setMaximumSize(new Dimension(60, 60));
        addButton.setMinimumSize(new Dimension(60, 60));
        addButton.setHorizontalAlignment(SwingConstants.CENTER);
        addButton.addActionListener(this::addFormPanel);

        bottomPanel.add(addButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void addFormPanel(ActionEvent e) {
        // Create a modal dialog for the form
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(formPanelContainer), "Add Medicine", true);
        MedicineFormPanel form = new MedicineFormPanel(dialog, this::addReminderCard);

        dialog.setUndecorated(true); // Optional: for a modern look
        dialog.getContentPane().add(form);
        dialog.pack();
        dialog.setLocationRelativeTo(formPanelContainer);
        dialog.setVisible(true);
    }

    private void addReminderCard(ReminderData data) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Draw shadow first (sharp corners)
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Shadow color (semi-transparent black)
                g2.setColor(new Color(0, 0, 0, 40));
                // Draw shadow slightly offset, sharp corners
                g2.fillRect(6, 6, getWidth() - 12, getHeight() - 12);

                // Card background (sharp corners)
                g2.setColor(new Color(240, 240, 240));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setLayout(new BorderLayout());
        card.setOpaque(false);
        // Sharp border: use LineBorder with no rounding
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2, false),
            BorderFactory.createEmptyBorder(18, 24, 18, 24)
        ));

        // Title (large, left-aligned)
        JLabel titleLabel = new JLabel(data.name);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Time and status (vertical, left-aligned)
        JLabel timeLabel = new JLabel("Time: " + data.time);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        timeLabel.setForeground(Color.DARK_GRAY);

        JLabel statusLabel = new JLabel("Not Taken");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        statusLabel.setForeground(Color.DARK_GRAY);

        // Info panel (vertical)
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(timeLabel);
        infoPanel.add(statusLabel);

        card.add(infoPanel, BorderLayout.CENTER);

        // Mark as Taken Button (modern, rounded, shadow, hover effect)
        JButton markTakenBtn = new JButton("Mark as Taken") {
            private boolean hovered = false;

            {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                setFocusPainted(false);
                setContentAreaFilled(false);
                setBorderPainted(false);
                setOpaque(false);

                addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        hovered = true;
                        repaint();
                    }
                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                        hovered = false;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Shadow
                g2.setColor(new Color(0, 0, 0, 40));
                g2.fillRoundRect(3, 5, getWidth() - 6, getHeight() - 6, 20, 20);

                // Button background
                if (hovered && isEnabled()) {
                    g2.setColor(new Color(80, 80, 80));
                } else if (!isEnabled()) {
                    g2.setColor(new Color(180, 180, 180));
                } else {
                    g2.setColor(new Color(120, 120, 120));
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Button text
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        markTakenBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        markTakenBtn.setForeground(Color.WHITE);
        markTakenBtn.setPreferredSize(new Dimension(140, 40));
        markTakenBtn.setHorizontalAlignment(SwingConstants.CENTER);
        markTakenBtn.addActionListener(ev -> {
            statusLabel.setText("Taken");
            markTakenBtn.setText("✔ Taken");
            markTakenBtn.setEnabled(false);
        });

        // Button panel for vertical centering
        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
        btnPanel.add(Box.createVerticalGlue());
        btnPanel.add(markTakenBtn);
        btnPanel.add(Box.createVerticalGlue());

        card.add(btnPanel, BorderLayout.EAST);

        // Card size and alignment (make card wider and taller for better fit)
        card.setMaximumSize(new Dimension(380, 130));
        card.setPreferredSize(new Dimension(380,130));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Also, ensure the button panel has enough space and the button is centered
        btnPanel.setMaximumSize(new Dimension(150, 140));
        markTakenBtn.setMaximumSize(new Dimension(140, 40));
        markTakenBtn.setMinimumSize(new Dimension(140, 40));
        markTakenBtn.setPreferredSize(new Dimension(140, 40));

        formPanelContainer.add(Box.createVerticalStrut(16));
        formPanelContainer.add(card);
        formPanelContainer.revalidate();
        formPanelContainer.repaint();

        reminders.add(data); // Add this line to store the reminder

        panelCount--; // Allow a new form to be added
    }

    // Add this method to your class
    private void updateClock(JLabel label) {
        java.time.LocalTime now = java.time.LocalTime.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("hh:mm a");
        label.setText(now.format(formatter));
    }

    private void checkAlarms() {
        String now = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
        for (ReminderData data : reminders) {
            if (!data.shown && now.equals(data.time.trim())) {
                showAlarm(data);
                data.shown = true; // Mark as shown so it doesn't repeat
            }
        }
    }

    private void showAlarm(ReminderData data) {
        JDialog alarmDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(formPanelContainer), "Alarm", true);
        alarmDialog.setUndecorated(true);
        alarmDialog.setSize(400, 800);
        alarmDialog.setLocationRelativeTo(null);

        JPanel alarmPanel = new JPanel();
        alarmPanel.setBackground(new Color(60, 60, 60));
        alarmPanel.setLayout(null);

        // Logo
        ImageIcon logoIcon = new ImageIcon("images\\logo.png");
        Image img = logoIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(img));
        logoLabel.setBounds(170, 40, 60, 60);
        alarmPanel.add(logoLabel);

        // Title
        JLabel title = new JLabel("MediSync", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.BLACK);
        title.setBounds(0, 110, 400, 40);
        alarmPanel.add(title);

        // "Time to take medicine:"
        JLabel takeLabel = new JLabel("Time to take medicine:", SwingConstants.CENTER);
        takeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        takeLabel.setForeground(Color.DARK_GRAY);
        takeLabel.setBounds(0, 170, 400, 30);
        alarmPanel.add(takeLabel);

        // Medicine Name
        JLabel medLabel = new JLabel(data.name, SwingConstants.CENTER);
        medLabel.setFont(new Font("Arial", Font.BOLD, 28));
        medLabel.setForeground(Color.BLACK);
        medLabel.setBounds(0, 200, 400, 40);
        alarmPanel.add(medLabel);

        // Date & Time
        JLabel dateLabel = new JLabel(data.time, SwingConstants.CENTER);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        dateLabel.setForeground(Color.DARK_GRAY);
        dateLabel.setBounds(0, 240, 400, 30);
        alarmPanel.add(dateLabel);

        // Notes
        JLabel notesTitle = new JLabel("Notes:", SwingConstants.LEFT);
        notesTitle.setFont(new Font("Arial", Font.BOLD, 18));
        notesTitle.setForeground(Color.BLACK);
        notesTitle.setBounds(30, 300, 340, 30);
        alarmPanel.add(notesTitle);

        JLabel notesLabel = new JLabel("<html>" + data.notes + "</html>", SwingConstants.LEFT);
        notesLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        notesLabel.setForeground(Color.BLACK);
        notesLabel.setBounds(30, 330, 340, 60);
        alarmPanel.add(notesLabel);

        // Done Button
        JButton doneBtn = new JButton("Done");
        doneBtn.setFont(new Font("Arial", Font.BOLD, 22));
        doneBtn.setForeground(Color.BLACK);
        doneBtn.setBackground(Color.LIGHT_GRAY);
        doneBtn.setFocusPainted(false);
        doneBtn.setBounds(40, 700, 120, 60);
        doneBtn.setBorder(BorderFactory.createEmptyBorder());
        doneBtn.setOpaque(true);
        doneBtn.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        doneBtn.addActionListener(e -> alarmDialog.dispose());
        alarmPanel.add(doneBtn);

        // Skip Button
        JButton skipBtn = new JButton("Skip");
        skipBtn.setFont(new Font("Arial", Font.BOLD, 22));
        skipBtn.setForeground(Color.BLACK);
        skipBtn.setBackground(Color.LIGHT_GRAY);
        skipBtn.setFocusPainted(false);
        skipBtn.setBounds(240, 700, 120, 60);
        skipBtn.setBorder(BorderFactory.createEmptyBorder());
        skipBtn.setOpaque(true);
        skipBtn.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        skipBtn.addActionListener(e -> alarmDialog.dispose());
        alarmPanel.add(skipBtn);

        alarmDialog.setContentPane(alarmPanel);
        alarmDialog.setVisible(true);

        try {
            // Replace "alarm.wav" with your sound file path (must be a valid WAV file)
            javax.sound.sampled.AudioInputStream audioIn = javax.sound.sampled.AudioSystem.getAudioInputStream(
                getClass().getResource("alarm\\mixkit-digital-clock-digital-alarm-buzzer-992") // If in resources folder
                // Or: new java.io.File("C:/path/to/alarm.wav")
            );
            javax.sound.sampled.Clip clip = javax.sound.sampled.AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MediSyncApp().createAndShowGUI());
    }
}
