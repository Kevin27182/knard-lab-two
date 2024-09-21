package gui;

import base.*;
import base.Event;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class EventPlanner extends JPanel {

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
        frame.setPreferredSize(new Dimension(Theme.PREFERRED_WIDTH, Theme.PREFERRED_HEIGHT));

        // Add default events
        if (USE_DEFAULTS) {
            // Create default events
            Deadline laborDay = new Deadline("Labor Day", LABOR_DAY_START_DATE);
            Deadline imminentDeadline = new Deadline("Imminent Deadline", IMMINENT_DEADLINE_START_DATE);
            Deadline halloween = new Deadline("Halloween", HALLOWEEN_START_DATE);
            Meeting postHalloweenStandUp = new Meeting("Post-Halloween Stand Up", POST_HALLOWEEN_START_DATE, POST_HALLOWEEN_END_DATE, POST_HALLOWEEN_STAND_UP_LOCATION);

            // Add default events to frame
            Event[] events = {laborDay, imminentDeadline, halloween, postHalloweenStandUp};
            addDefaultEvents(events);
        }

        // Draw default events and add EventListPanel
        eventListPanel.drawEvents();
        frame.add(eventListPanel);

        // Pack and display the JFrame
        frame.pack();
        frame.setVisible(true);
    }

    public static void addDefaultEvents(Event[] events) {
        for (Event event : events) {
            eventListPanel.addEvent(event);
        }
    }
}
