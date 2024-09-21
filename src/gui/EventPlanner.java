package gui;

import base.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class EventPlanner extends JPanel {

    private static final int PREFERRED_WIDTH = 600;
    private static final int PREFERRED_HEIGHT = 800;
    private static final LocalDateTime LABOR_DAY_START_DATE = LocalDateTime.of(2024, 9, 2, 0, 0);
    private static final LocalDateTime IMMINENT_DEADLINE_START_DATE = LocalDateTime.now().plusSeconds(10);
    private static final LocalDateTime HALLOWEEN_START_DATE = LocalDateTime.of(2024, 10, 31, 0, 0);
    private static final LocalDateTime POST_HALLOWEEN_START_DATE = LocalDateTime.of(2024, 11, 1, 8, 0);
    private static final LocalDateTime POST_HALLOWEEN_END_DATE = LocalDateTime.of(2024, 11, 1, 9, 0);
    private static final String POST_HALLOWEEN_STAND_UP_LOCATION = "Meeting Room 204";
    private static final boolean USE_DEFAULTS = true;
    private static final JFrame frame = new JFrame("Event Planner");
    private static final EventListPanel eventListPanel = new EventListPanel();

    public static void main(String[] args) {
        // Configure JFrame object
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));

        // Add EventListPanel
        frame.add(eventListPanel);

        // Add default events
        if (USE_DEFAULTS) {
            // Create default Deadline
            Deadline laborDay = new Deadline("Labor Day", LABOR_DAY_START_DATE);
            EventPanel laborDayPanel = new EventPanel(laborDay);

            // Create default Deadline
            Deadline imminentDeadline = new Deadline("Imminent Deadline", IMMINENT_DEADLINE_START_DATE);
            EventPanel imminentDeadlinePanel = new EventPanel(imminentDeadline);

            // Create default Deadline
            Deadline halloween = new Deadline("Halloween", HALLOWEEN_START_DATE);
            EventPanel halloweenPanel = new EventPanel(halloween);

            // Create default Meeting
            Meeting postHalloweenStandUp = new Meeting("Post-Halloween Stand Up", POST_HALLOWEEN_START_DATE, POST_HALLOWEEN_END_DATE, POST_HALLOWEEN_STAND_UP_LOCATION);
            EventPanel postHalloweenPanel = new EventPanel(postHalloweenStandUp);

            // Add default events to frame
            EventPanel[] events = {laborDayPanel, imminentDeadlinePanel, halloweenPanel, postHalloweenPanel};
            addDefaultEvents(events);
        }

        // Pack and display the JFrame
        frame.pack();
        frame.setVisible(true);

        Timer timer = new Timer(1000, e -> {
            for (Component component : eventListPanel.getComponents()) {
                if (component instanceof EventPanel eventPanel && eventPanel.getEvent() instanceof Completable completableEvent && !completableEvent.isComplete()) {
                    eventPanel.updateUrgency();
                    eventPanel.repaint();
                    eventPanel.revalidate();
                }
            }
        });
        timer.start();
    }

    public static void addDefaultEvents(EventPanel[] events) {
        for (EventPanel event : events) {
            eventListPanel.add(event);
        }
    }
}
