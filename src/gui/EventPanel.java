
// Title: EventPanel.java
// Author: Kevin Nard
// Panel that holds event graphics

package gui;

import base.*;
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
    private JLabel durationLabel = new JLabel();
    private JLabel locationLabel = new JLabel();
    private JButton completeButton = new JButton("Complete");
    private JPanel urgencyPanel = new JPanel();
    private Color textColor = Theme.TEXT_COLOR;
    private static final int URGENCY_WIDTH = 5;
    private static final int URGENCY_HEIGHT = 30;
    private static final int DATE_TIME_WIDTH = 125;
    private static final int DATE_TIME_HEIGHT = 25;
    private static final int DURATION_WIDTH = 125;
    private static final int DURATION_HEIGHT = 25;
    private static final int LOCATION_WIDTH = 125;
    private static final int LOCATION_HEIGHT = 25;
    private static final int LABEL_WIDTH = 250;
    private static final int LABEL_HEIGHT = 25;
    private static final int HORIZONTAL_PADDING = 10;
    private static final int VERTICAL_PADDING = 10;
    private static final int DISTANT_HOURS = 4;

    // Construct a new event panel around given event
    EventPanel(Event event, Runnable updateEventList) {

        // Set the event for this panel
        this.event = event;

        // Detect if event is complete
        boolean complete = false;
        if (this.event instanceof Completable completableEvent && completableEvent.isComplete())
            complete = true;

        // If event is complete, change all text color to Theme.INACTIVE
        if (complete)
            textColor = Theme.INACTIVE;

        // Configure EventPanel
        this.setLayout(new FlowLayout(FlowLayout.CENTER, HORIZONTAL_PADDING, VERTICAL_PADDING));
        this.setBackground(Theme.DARK_BACKGROUND);

        // Configure and add Urgency Highlight
        urgencyPanel.setPreferredSize(new Dimension(URGENCY_WIDTH, URGENCY_HEIGHT));
        urgencyPanel.setBackground(textColor);
        if (!complete)
            updateUrgency();
        this.add(urgencyPanel);

        // Configure and add Date Label
        dateLabel.setText(event.getDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
        dateLabel.setPreferredSize(new Dimension(DATE_TIME_WIDTH, DATE_TIME_HEIGHT));
        dateLabel.setForeground(textColor);
        this.add(dateLabel);

        // Configure and add Duration Label
        durationLabel.setPreferredSize(new Dimension(DURATION_WIDTH, DURATION_HEIGHT));
        durationLabel.setForeground(textColor);
        if (this.event instanceof DurationEvent durationEvent && this.event instanceof Event baseEvent) {
            int minutesBetween = (int) MINUTES.between(baseEvent.getDateTime(), durationEvent.getEndDateTime());
            int hoursBetween = (int) HOURS.between(baseEvent.getDateTime(), durationEvent.getEndDateTime());
            if (minutesBetween == 1)
                durationLabel.setText(minutesBetween + " minute");
            else if (minutesBetween < 60)
                durationLabel.setText(minutesBetween + " minutes");
            else if (hoursBetween == 1)
                durationLabel.setText(hoursBetween + " hour");
            else
                durationLabel.setText(hoursBetween + " hours");
        }
        this.add(durationLabel);

        // Configure and add Location Label
        locationLabel.setPreferredSize(new Dimension(LOCATION_WIDTH, LOCATION_HEIGHT));
        locationLabel.setForeground(textColor);
        if (this.event instanceof LocationEvent locationEvent)
            locationLabel.setText(locationEvent.getLocation());
        this.add(locationLabel);

        // Configure and add Event Label
        eventLabel.setText(this.event.getName());
        eventLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        eventLabel.setForeground(textColor);
        this.add(eventLabel);

        // Configure Complete Button
        completeButton.setBackground(Theme.MID_BACKGROUND);
        completeButton.setForeground(textColor);
        completeButton.setBorderPainted(false);
        completeButton.setFocusPainted(false);

        // Add listener to highlight complete button on mouse hover
        completeButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                completeButton.setBackground(Theme.MID_BACKGROUND_HIGHLIGHT);
            }

            public void mouseExited(MouseEvent e) {
                completeButton.setBackground(Theme.MID_BACKGROUND);
            }
        });

        // Add listener to complete event on button click
        completeButton.addActionListener(e -> {
            if (this.event instanceof Completable completeableEvent && !completeableEvent.isComplete()) {
                completeableEvent.complete();
                updateEventList.run();
            }
        });

        // Add Complete Button
        if (complete) {
            completeButton.setOpaque(false);
            completeButton.setEnabled(false);
        }
        this.add(completeButton);
    }

    // Updates urgency graphic color
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

    // Return the current event
    public Event getEvent() {
        return event;
    }

    // Return the current completion button
    public JButton getCompleteButton() {
        return completeButton;
    }
}
