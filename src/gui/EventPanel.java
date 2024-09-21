package gui;

import base.Event;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import static java.time.temporal.ChronoUnit.*;

public class EventPanel extends JPanel {
    private Event event;
    private JLabel eventLabel = new JLabel();
    private JLabel dateLabel = new JLabel();
    private JButton completeButton = new JButton("Complete");
    private JPanel urgencyPanel = new JPanel();

    private static final int URGENCY_WIDTH = 5;
    private static final int URGENCY_HEIGHT = 30;
    private static final int DATE_TIME_WIDTH = 125;
    private static final int DATE_TIME_HEIGHT = 25;
    private static final int LABEL_WIDTH = 250;
    private static final int LABEL_HEIGHT = 25;
    private static final int HORIZONTAL_PADDING = 10;
    private static final int VERTICAL_PADDING = 10;
    private static final int DISTANT_HOURS = 4;

    EventPanel(Event event) {
        this.event = event;
        this.setLayout(new FlowLayout(FlowLayout.CENTER, HORIZONTAL_PADDING, VERTICAL_PADDING));
        this.setBackground(Theme.DARK_BACKGROUND);

        urgencyPanel.setPreferredSize(new Dimension(URGENCY_WIDTH, URGENCY_HEIGHT));
        this.add(urgencyPanel);

        dateLabel.setText(event.getDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
        dateLabel.setPreferredSize(new Dimension(DATE_TIME_WIDTH, DATE_TIME_HEIGHT));
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

        updateUrgency();
    }

    public void updateUrgency() {
        // Get number of minutes and days between now and event
        boolean distant = HOURS.between(LocalDateTime.now(), event.getDateTime()) > DISTANT_HOURS;
        boolean overdue = LocalDateTime.now().isAfter(event.getDateTime());

        // If event is distant, set background to Theme.Distant
        if (distant) {
            urgencyPanel.setBackground(Theme.DISTANT);
            return;
        }

        // If event is overdue, set background to Theme.OVERDUE
        if (overdue) {
            urgencyPanel.setBackground(Theme.OVERDUE);
            return;
        }

        // If event is imminent, set background to Theme.IMMINENT
        urgencyPanel.setBackground(Theme.IMMINENT);
    }
}
