package gui;

import base.Completable;
import base.Event;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

public class EventListPanel extends JPanel {
    private ArrayList<Event> events = new ArrayList<Event>();
    private JPanel controlPanel = new JPanel();
    private JPanel displayPanel = new JPanel();
    private final String[] SORT_OPTIONS = new String[]{"Date Ascending", "Date Descending", "Name A-Z", "Name Z-A"};
    private JComboBox<String> sortDropDown = new JComboBox(SORT_OPTIONS);
    private JCheckBox filterDisplay = new JCheckBox();
    private JButton addEventButton = new JButton("Add Event");

    public EventListPanel() {
        this.setBackground(Theme.DARKER_BACKGROUND);
        this.setLayout(new BorderLayout());

        // Configure and add calendar Control Panel
        controlPanel.add(sortDropDown);
        controlPanel.add(filterDisplay);
        controlPanel.setBackground(Theme.DARK_BACKGROUND);
        this.add(controlPanel, BorderLayout.NORTH);

        // Configure and add sort event dropdown to Control Panel
        sortDropDown.setBackground(Theme.MID_BACKGROUND);
        sortDropDown.setForeground(Theme.TEXT_COLOR);
        sortDropDown.addActionListener(e -> {
            if (sortDropDown.getSelectedItem().equals(SORT_OPTIONS[0]))
                Collections.sort(events);
            if (sortDropDown.getSelectedItem().equals(SORT_OPTIONS[1]))
                events.sort(Collections.reverseOrder());
            if (sortDropDown.getSelectedItem().equals(SORT_OPTIONS[2]))
                events.sort((e1, e2) -> e1.getName().compareTo(e2.getName()));
            if (sortDropDown.getSelectedItem().equals(SORT_OPTIONS[3]))
                events.sort((e1, e2) -> e2.getName().compareTo(e1.getName()));
            displayPanel.removeAll();
            drawEvents();
        });

        // Configure and add new event button to Control Panel
        addEventButton.setBackground(Theme.MID_BACKGROUND);
        addEventButton.setForeground(Theme.TEXT_COLOR);
        addEventButton.setBorderPainted(false);
        addEventButton.setFocusPainted(false);
        addEventButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                addEventButton.setBackground(Theme.MID_BACKGROUND_HIGHLIGHT);
            }

            public void mouseExited(MouseEvent e) {
                addEventButton.setBackground(Theme.MID_BACKGROUND);
            }
        });
        controlPanel.add(addEventButton);

        // Configure and add event Display Panel
        displayPanel.setBackground(Theme.TRANSPARENT);
        this.add(displayPanel, BorderLayout.CENTER);

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
