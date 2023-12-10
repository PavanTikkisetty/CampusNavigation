package navigation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CampusMap extends JFrame {
    private String username;
    private JComboBox<String> locationComboBox;
    private JComboBox<String> endLocationComboBox;

    public CampusMap(String username) {
        this.username = username;
        setTitle("Campus Map - Welcome, " + username + "!");
        setPreferredSize(new Dimension(400, 200));

        JButton distanceButton = new JButton("Calculate Distance");
        distanceButton.addActionListener(new DistanceButtonListener());

        initializeComponents();

        pack();
    }

    private void initializeComponents() {
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel mapPanel = new JPanel(new GridLayout(3, 2));
        JLabel locationLabel = new JLabel("Select Location:");
        locationComboBox = new JComboBox<>(new String[] { "MainGate", "Exit", "Library", "Cafeteria", "Gym" });
        endLocationComboBox = new JComboBox<>(new String[] { "MainGate", "Exit", "Library", "Cafeteria", "Gym" });
        JButton showDistanceButton = new JButton("Show Distance");

        showDistanceButton.addActionListener(new DistanceButtonListener());

        mapPanel.add(locationLabel);
        mapPanel.add(locationComboBox);
        mapPanel.add(endLocationComboBox);
        mapPanel.add(new JLabel()); // Empty label for layout
        mapPanel.add(new JLabel()); // Empty label for layout
        mapPanel.add(new JLabel()); // Empty label for layout
        mapPanel.add(showDistanceButton);

        container.add(mapPanel, BorderLayout.CENTER);
    }

    private class DistanceButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String origin = (String) locationComboBox.getSelectedItem();
            String destination = (String) endLocationComboBox.getSelectedItem();

            // Use DistanceCalculator to calculate distance
            DistanceCalculator.showMap(origin, destination);
        }
    }

}