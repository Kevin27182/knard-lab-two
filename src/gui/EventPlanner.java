package gui;

import base.*;
import javax.swing.*;
import java.awt.*;

public class EventPlanner extends javax.swing.JPanel {
    private static final int PREFERRED_WIDTH = 600;
    private static final int PREFERRED_HEIGHT = 800;

    public static void main(String[] args) {
        // Create and configure JFrame object
        JFrame frame = new JFrame("Event Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));

//        // Add EventListPanel
//        EventListPanel eventListPanel = new EventListPanel;
//        frame.add(eventListPanel);

        // Pack and display the JFrame
        frame.pack();
        frame.setVisible(true);
    }

//    public static void addDefaultEvents(EventPanel events) {
//
//    }
}
