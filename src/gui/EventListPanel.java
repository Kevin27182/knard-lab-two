
// Title: EventListPanel.java
// Author: Kevin Nard
// Panel that holds event list and control panels

package gui;

import base.Event;
import base.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class EventListPanel extends JPanel {

    private ArrayList<Event> events = new ArrayList<Event>();
    private JPanel controlPanel = new JPanel();
    private JPanel displayPanel = new JPanel();
    private final String[] SORT_OPTIONS = new String[]{"Date Ascending", "Date Descending", "Name A-Z", "Name Z-A"};
    private JComboBox<String> sortDropDown = new JComboBox(SORT_OPTIONS);
    private final String[] FILTERS = new String[]{"Not Completed", "Completed", "Deadlines", "Meetings"};
    private ArrayList<JCheckBox> filterBoxes = new ArrayList<JCheckBox>();
    private JButton clearFiltersButton = new JButton("Clear Filters");
    private JButton addEventButton = new JButton("Add Event");
    private AddEventModal addEventModal;

    // Construct list panel and add/configure components
    public EventListPanel() {

        // Configure list panel
        this.setBackground(Theme.DARKER_BACKGROUND);
        this.setLayout(new BorderLayout());

        // Configure and add calendar Control Panel
        controlPanel.setBackground(Theme.DARK_BACKGROUND);
        this.add(controlPanel, BorderLayout.NORTH);

        // Configure and add filter check boxes to Control Panel
        for (String filter : FILTERS) {

            // Configure check box
            JCheckBox checkBox = new JCheckBox(filter);
            checkBox.setForeground(Theme.TEXT_COLOR);
            checkBox.setOpaque(false);
            checkBox.setFocusPainted(false);

            // Uncheck other boxes when selected
            checkBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (JCheckBox box : filterBoxes) {
                        if (box.isSelected() && !box.getText().equals(checkBox.getText())) {
                            box.setSelected(false);
                        }
                    }
                    sortEvents();
                }
            });

            filterBoxes.add(checkBox);
            controlPanel.add(checkBox);
        }

        // Configure and add clear filters button to Control Panel
        clearFiltersButton.setBackground(Theme.MID_BACKGROUND);
        clearFiltersButton.setForeground(Theme.TEXT_COLOR);
        clearFiltersButton.setBorderPainted(false);
        clearFiltersButton.setFocusPainted(false);

        // Highlight button on mouse hover
        clearFiltersButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                clearFiltersButton.setBackground(Theme.MID_BACKGROUND_HIGHLIGHT);
            }

            public void mouseExited(MouseEvent e) {
                clearFiltersButton.setBackground(Theme.MID_BACKGROUND);
            }
        });

        // Clear all filter boxes on button click
        clearFiltersButton.addActionListener(e -> {
            for (JCheckBox box : filterBoxes) {
                box.setSelected(false);
            }
            sortEvents();
        });

        controlPanel.add(clearFiltersButton);

        // Configure and add sort event dropdown to Control Panel
        controlPanel.add(sortDropDown);
        sortDropDown.setBackground(Theme.MID_BACKGROUND);
        sortDropDown.setForeground(Theme.TEXT_COLOR);
        sortDropDown.addActionListener(e -> {
            sortEvents();
        });

        // Configure and add new event button to Control Panel
        addEventButton.setBackground(Theme.MID_BACKGROUND);
        addEventButton.setForeground(Theme.TEXT_COLOR);
        addEventButton.setBorderPainted(false);
        addEventButton.setFocusPainted(false);
        controlPanel.add(addEventButton);

        // Highlight button on mouse hover
        addEventButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                addEventButton.setBackground(Theme.MID_BACKGROUND_HIGHLIGHT);
            }

            public void mouseExited(MouseEvent e) {
                addEventButton.setBackground(Theme.MID_BACKGROUND);
            }
        });

        // Create dialog on button click
        addEventButton.addActionListener(e -> {
            addEventModal = new AddEventModal(event -> {
                addEvent(event);
                sortEvents();
            });
            addEventModal.pack();
            addEventModal.setLocationRelativeTo(this.getParent());
            addEventModal.setVisible(true);
        });

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

    // Add a new event
    public void addEvent(Event event) {
        this.events.add(event);
    }

    // Filter and re-draw events list
    public void drawEvents() {

        // Stores the active filter checkbox
        String activeFilter = null;

        // Get the active filter
        for (JCheckBox filterBox : filterBoxes) {
            if (filterBox.isSelected()) {
                activeFilter = filterBox.getText();
            }
        }

        // Get filtered events
        ArrayList<Event> filteredEvents = null;
        if (activeFilter != null)
            filteredEvents = filterEvents(activeFilter);
        else
            filteredEvents = events;

        // Remove all current events
        displayPanel.removeAll();

        // Construct new EventPanels and add to display panel
        for (Event event : filteredEvents) {
            EventPanel eventPanel = new EventPanel(event, () -> {
                drawEvents();
            });
            displayPanel.add(eventPanel);
        }

        // Update graphics
        repaint();
        revalidate();
    }

    // Return filtered events
    private ArrayList<Event> filterEvents(String filter) {

        ArrayList<Event> filteredList = new ArrayList<>(events);

        switch (filter) {

            // Filter to events that are not completed
            case "Not Completed":
                filteredList.removeIf(e -> {
                    if (e instanceof Completable completableEvent)
                        return completableEvent.isComplete();
                    return false;
                });
                break;

            // Filter to events that are completed
            case "Completed":
                filteredList.removeIf(e -> {
                    if (e instanceof Completable completableEvent)
                        return !completableEvent.isComplete();
                    return false;
                });
                break;

            // Filter to Deadlines
            case "Deadlines":
                filteredList.removeIf(e -> {
                    return !(e instanceof Deadline);
                });
                break;

            // Filter to Meetings
            case "Meetings":
                filteredList.removeIf(e -> {
                    return !(e instanceof Meeting);
                });
                break;
        }

        return filteredList;
    }

    // Sort events and draw graphics
    private void sortEvents() {
        if (sortDropDown.getSelectedItem().equals(SORT_OPTIONS[0]))
            Collections.sort(events);
        if (sortDropDown.getSelectedItem().equals(SORT_OPTIONS[1]))
            events.sort(Collections.reverseOrder());
        if (sortDropDown.getSelectedItem().equals(SORT_OPTIONS[2]))
            events.sort((e1, e2) -> e1.getName().compareTo(e2.getName()));
        if (sortDropDown.getSelectedItem().equals(SORT_OPTIONS[3]))
            events.sort((e1, e2) -> e2.getName().compareTo(e1.getName()));
        drawEvents();
    }
}
