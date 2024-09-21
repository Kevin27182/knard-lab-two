package gui;

import base.*;
import javax.swing.*;
import java.time.LocalDateTime;

public class EventPanel extends JPanel {
    private Event event;
    private JButton completeButton;

    public void updateUrgency() {
        switch (event.getDateTime().compareTo(LocalDateTime.now())) {
            case (-1):
                // red
                break;
            case (0):
                // yellow
                break;
            case (1):
                // green
                break;
        }
    }
}
