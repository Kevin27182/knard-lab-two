package gui;

import base.Event;

import javax.swing.*;
import java.util.ArrayList;

public class EventListPanel extends JPanel {
    private ArrayList<Event> events = new ArrayList<Event>();
    private JPanel controlPanel;
    private JPanel displayPanel;
    private JComboBox sortDropDown;
    private JCheckBox filterDisplay;
    private JButton addEventButton;

    public EventListPanel() {
        this.setBackground(Theme.DARKER_BACKGROUND);
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

    public void drawEvents() {
        for (Event event : events) {
            EventPanel eventPanel = new EventPanel(event);
            this.add(eventPanel);
        }
    }
}
