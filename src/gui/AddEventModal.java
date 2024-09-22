package gui;

import base.*;
import base.Event;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Consumer;
import java.util.regex.*;

public class AddEventModal extends JDialog {

    private static final int NUM_ROWS = 3;
    private static final int NUM_COLS = 2;
    private static final int INNER_NUM_ROWS = 2;
    private static final int INNER_NUM_COLS = 1;
    private static final int PADDING = 10;
    private static final int NO_PADDING = 0;
    private static final int ERROR_MESSAGE_HEIGHT = 25;
    private static final int MAX_MINUTES = 1440;
    private static final String DATE_REGEX = "^([0-9]?[0-9])(?:[-.\\/])([0-9]?[0-9])(?:[-.\\/])([0-9]{4})$";
    private static final String TIME_REGEX = "^([0-9]{1,2}):([0-9]{1,2})(?: ?(am|AM|pm|PM))?$";
    private static final String[] EVENT_TYPES = {"Meeting", "Deadline"};
    private static String selectedFilterType = null;
    private JPanel addEventPanel = new JPanel();
    private JPanel inputGridPanel = new JPanel();
    private JLabel inputValidationMessage = new JLabel();
    private JButton createEventButton = new JButton("Create Event");
    private LabeledInputPanel typePanel = new LabeledInputPanel("Event Type");
    private CustomComboBox<String> typeDropDown = new CustomComboBox(EVENT_TYPES);
    private LabeledInputPanel eventNamePanel = new LabeledInputPanel("Event Name");
    private CustomTextField eventNameInput = new CustomTextField();
    private LabeledInputPanel startDatePanel = new LabeledInputPanel("Start Date (MM-DD-YYYY)");
    private CustomTextField startDateInput = new CustomTextField();
    private LabeledInputPanel startTimePanel = new LabeledInputPanel("Start Time (HH:MM AM/PM)");
    private CustomTextField startTimeInput = new CustomTextField();
    private LabeledInputPanel durationPanel = new LabeledInputPanel("Duration (minutes)");
    private CustomTextField durationInput = new CustomTextField();
    private LabeledInputPanel locationPanel = new LabeledInputPanel("Location");
    private CustomTextField locationInput = new CustomTextField();

    AddEventModal(Consumer<Event> addEvent) {

        // Configure Dialog Window options
        setTitle("Add Event");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(Theme.DIALOG_PREFERRED_WIDTH, Theme.DIALOG_PREFERRED_HEIGHT));

        // Configure and add Main Panel
        addEventPanel.setBackground(Theme.DARKER_BACKGROUND);
        addEventPanel.setLayout(new BorderLayout(PADDING, PADDING));
        addEventPanel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        this.add(addEventPanel);

        // Configure and add Input Grid
        inputGridPanel.setLayout(new GridLayout(NUM_ROWS, NUM_COLS, PADDING, PADDING));
        inputGridPanel.setOpaque(false);
        addEventPanel.add(inputGridPanel, BorderLayout.CENTER);

        // Configure and add Event Type drop down box
        selectedFilterType = (String) typeDropDown.getSelectedItem();
        typePanel.add(typeDropDown);
        inputGridPanel.add(typePanel);

        // Configure and add Event Name text input
        eventNamePanel.add(eventNameInput);
        inputGridPanel.add(eventNamePanel);

        // Configure and add Event Start Date text input
        startDatePanel.add(startDateInput);
        inputGridPanel.add(startDatePanel);

        // Configure and add Event Start Time text input
        startTimePanel.add(startTimeInput);
        inputGridPanel.add(startTimePanel);

        // Configure and add Event Duration text input
        durationPanel.add(durationInput);
        inputGridPanel.add(durationPanel);

        // Configure and add Event Location text input
        locationPanel.add(locationInput);
        inputGridPanel.add(locationPanel);

        // Configure and add Input Validation Message
        inputValidationMessage.setForeground(Theme.ERROR);
        inputValidationMessage.setPreferredSize(new Dimension(Theme.DIALOG_PREFERRED_WIDTH, ERROR_MESSAGE_HEIGHT));
        addEventPanel.add(inputValidationMessage, BorderLayout.NORTH);

        // Configure and add Create Event button
        createEventButton.setBackground(Theme.MID_BACKGROUND);
        createEventButton.setForeground(Theme.TEXT_COLOR);
        createEventButton.setBorderPainted(false);
        createEventButton.setFocusPainted(false);
        createEventButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                createEventButton.setBackground(Theme.MID_BACKGROUND_HIGHLIGHT);
            }

            public void mouseExited(MouseEvent e) {
                createEventButton.setBackground(Theme.MID_BACKGROUND);
            }
        });
        createEventButton.addActionListener(e -> {
            String validated = validateInputAndAddEvent(addEvent);
            if (validated.equals("Validated")) {
                inputValidationMessage.setText("");
                closeModal();
            } else {
                inputValidationMessage.setText(validated);
            }
            repaint();
            revalidate();
        });
        addEventPanel.add(createEventButton, BorderLayout.SOUTH);

        // Closes modal on lost focus
        this.addWindowFocusListener(new WindowFocusListener() {
            public void windowGainedFocus(WindowEvent e) {}
            public void windowLostFocus (WindowEvent e) { closeModal(); }
        });
    }

    // Inner class for creating customized JPanels for user input
    private class LabeledInputPanel extends JPanel {

        private JLabel label;

        LabeledInputPanel(String label) {
            // Set panel configurations
            setBackground(Theme.DARK_BACKGROUND);
            setLayout(new GridLayout(INNER_NUM_ROWS, INNER_NUM_COLS, PADDING, PADDING));
            setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));

            // Configure and add Label
            this.label = new JLabel(label);
            this.label.setForeground(Theme.TEXT_COLOR);
            add(this.label);
        }

        public JLabel getLabel() {
            return label;
        }
    }

    // Inner class for creating customized JTextFields
    private class CustomTextField extends JTextField {
        CustomTextField() {
            // Set text field configurations
            setBackground(Theme.MID_BACKGROUND);
            setForeground(Theme.TEXT_COLOR);
            setCaretColor(Theme.TEXT_COLOR);
            setBorder(BorderFactory.createEmptyBorder(NO_PADDING, PADDING, NO_PADDING, PADDING));
        }

        // Optional constructor for adding default text
        CustomTextField(String text) {
            this();
            setText(text);
        }
    }

    // Inner class for creating customized JComboBoxes
    private class CustomComboBox<T> extends JComboBox<T> {

        CustomComboBox(T[] param) {
            setBackground(Theme.MID_BACKGROUND);
            setForeground(Theme.TEXT_COLOR);
            setModel(new DefaultComboBoxModel(param));
            addActionListener(e -> {
                if (getSelectedItem().equals("Meeting")) {
                    durationPanel.getLabel().setForeground(Theme.TEXT_COLOR);
                    durationInput.setFocusable(true);
                    durationInput.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                    locationPanel.getLabel().setForeground(Theme.TEXT_COLOR);
                    locationInput.setFocusable(true);
                    locationInput.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                }
                if (getSelectedItem().equals("Deadline")) {
                    durationPanel.getLabel().setForeground(Theme.INACTIVE);
                    durationInput.setFocusable(false);
                    durationInput.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    locationPanel.getLabel().setForeground(Theme.INACTIVE);
                    locationInput.setFocusable(false);
                    locationInput.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
                repaint();
                revalidate();
            });
        }
    }

    // Closes Dialog Window
    private void closeModal() {
        this.dispose();
    }

    // Validates all input within dialog and adds event if validation succeeds
    private String validateInputAndAddEvent(Consumer<Event> addEvent) {

        // Check that event has a name
        if (eventNameInput.getText().equals(""))
            return "ERROR: Event name is required!";

        // Regex matching and groups for Start Date Input
        Matcher startDateMatcher = Pattern.compile(DATE_REGEX).matcher(startDateInput.getText());
        if (!startDateMatcher.matches())
            return "ERROR: Invalid Start Date!";
        int month = Integer.parseInt(startDateMatcher.group(1));
        int day = Integer.parseInt(startDateMatcher.group(2));
        int year = Integer.parseInt(startDateMatcher.group(3));

        // Check that Start Date is valid
        LocalDate startDate;
        try {
            startDate = LocalDate.of(year, month, day);
        }
        catch (DateTimeException e) {
            return "ERROR: Invalid Start Date!";
        }

        // Check that Start Date is in the future
        if (startDate.isBefore(LocalDate.now()))
            return "ERROR: Start Date must be in the future!";

        // Regex matching and groups for Start Time Input
        Matcher startTimeMatcher = Pattern.compile(TIME_REGEX).matcher(startTimeInput.getText());
        if (!startTimeMatcher.matches())
            return "ERROR: Invalid Start Time!";
        int hours = Integer.parseInt(startTimeMatcher.group(1));
        int minutes = Integer.parseInt(startTimeMatcher.group(2));
        if (startTimeMatcher.group(3) != null && startTimeMatcher.group(3).toUpperCase().equals("PM"))
            hours += 12;

        // Check that Start Time is valid
        LocalTime startTime;
        try {
            startTime = LocalTime.of(hours, minutes);
        } catch (DateTimeException e) {
            return "ERROR: Invalid Start Time!";
        }

        // Check that Start Time is in the future
        if (startDate.equals(LocalDate.now()) && startTime.isBefore(LocalTime.now()))
            return "ERROR: Start Time must be in the future!";

        if (typeDropDown.getSelectedItem().equals("Meeting")) {

            // Validate Duration Input
            int duration;
            try {
                duration = Integer.parseInt(durationInput.getText());
            }
            catch (NumberFormatException e) {
                return "ERROR: Invalid Duration!";
            }

            // Check that duration is within valid range
            if (duration <= 0 || duration > MAX_MINUTES)
                return "ERROR: Duration must be between 1 and 1440 minutes (24 hours)!";

            // Check that event has a location
            if (locationInput.getText().equals(""))
                return "ERROR: Meeting location is required!";

            // Prepare arguments
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDateTime endDateTime = startDateTime.plusMinutes(duration);

            // Create and add new Meeting
            Meeting newMeeting = new Meeting(eventNameInput.getText(), startDateTime, endDateTime, locationInput.getText());
            addEvent.accept(newMeeting);

            return "Validated";
        }

        if (typeDropDown.getSelectedItem().equals("Deadline")) {

            // Prepare arguments
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);

            // Create and add new Deadline
            Deadline newDeadline = new Deadline(eventNameInput.getText(), startDateTime);
            addEvent.accept(newDeadline);

            return "Validated";
        }

        // Default error message
        return "ERROR: Something unexpected happened!";
    }
}
