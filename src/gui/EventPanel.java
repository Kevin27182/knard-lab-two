package gui;

import base.Event;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventPanel extends JPanel {
    private Event event;
    private JLabel eventLabel = new JLabel();
    private JLabel dateLabel = new JLabel();
    private JButton completeButton = new JButton("Complete");

    private static final int EVENT_WIDTH = 500;
    private static final int EVENT_HEIGHT = 50;
    private static final int LABEL_WIDTH = 300;
    private static final int LABEL_HEIGHT = 25;
    private static final int HORIZONTAL_PADDING = 10;
    private static final int VERTICAL_PADDING = 10;

    EventPanel(Event event) {
        this.event = event;
        this.setLayout(new FlowLayout(FlowLayout.CENTER, HORIZONTAL_PADDING, VERTICAL_PADDING));
        this.setBackground(Theme.DARK_BACKGROUND);

        dateLabel.setText(event.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        dateLabel.setForeground(Theme.TEXT_COLOR);
        this.add(dateLabel);

        eventLabel.setText(event.getName());
        eventLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        eventLabel.setForeground(Theme.TEXT_COLOR);
        this.add(eventLabel);

        completeButton.setAlignmentX(RIGHT_ALIGNMENT);
        completeButton.setBackground(Theme.MID_BACKGROUND);
        completeButton.setForeground(Theme.TEXT_COLOR);
        completeButton.setBorderPainted(false);
        completeButton.setFocusPainted(false);

        completeButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                completeButton.setBackground(Theme.MID_BACKGROUND_HIGHLIGHT);
            }

            public void mouseExited(MouseEvent e) {
                completeButton.setBackground(Theme.MID_BACKGROUND);
            }
        });
        completeButton.addActionListener(e -> {
            System.out.println(event.getName());
        });

        this.add(completeButton);
    }

    public void updateUrgency() {
        switch (event.getDateTime().compareTo(LocalDateTime.now())) {
            case (-1):
                // red
                break;
            case (0):
                // yellow
                break;
            case (1):
                // green
                break;
        }
    }
}
