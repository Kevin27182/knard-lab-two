package gui;

import base.Completable;
import base.Event;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EventListPanel extends JPanel {
    private ArrayList<Event> events = new ArrayList<Event>();
    private JPanel controlPanel;
    private JPanel displayPanel = new JPanel();
    private JComboBox sortDropDown;
    private JCheckBox filterDisplay;
    private JButton addEventButton;

    public EventListPanel() {
        this.setPreferredSize(new Dimension(Theme.PREFERRED_WIDTH, Theme.PREFERRED_HEIGHT));
        this.setBackground(Theme.DARKER_BACKGROUND);

        displayPanel.setPreferredSize(new Dimension(Theme.PREFERRED_WIDTH, Theme.PREFERRED_HEIGHT));
        displayPanel.setBackground(Theme.TRANSPARENT);
        this.add(displayPanel);

        // Start a timer that constantly updates urgency
        Timer timer = new Timer(1000, e -> {
            for (Component component : displayPanel.getComponents()) {
                if (component instanceof EventPanel eventPanel && eventPanel.getEvent() instanceof Completable completableEvent && !completableEvent.isComplete()) {
                    eventPanel.updateUrgency();
                    eventPanel.repaint();
                    eventPanel.revalidate();
                }
            }
        });
        timer.start();
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

    public void drawEvents() {
        for (Event event : events) {
            EventPanel eventPanel = new EventPanel(event);
            displayPanel.add(eventPanel);
        }
        repaint();
        revalidate();
    }
}
