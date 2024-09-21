package gui;

import base.Event;

import javax.swing.*;
import java.util.ArrayList;

public class EventListPanel extends JPanel {
    private ArrayList<Event> events;
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
}
