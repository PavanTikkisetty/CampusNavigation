package navigation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LoginFrame extends JFrame {
    private JTextField usernameTextField;
    private JPasswordField passwordField;

    private static final String CSV_FILE = "user_credentials.csv";

    public LoginFrame() {
        setTitle("Campus Navigation Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeComponents();

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private void initializeComponents() {
        Container container = getContentPane();
        container.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameTextField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton signUpButton = new JButton("Sign Up");

        loginButton.addActionListener(new LoginButtonListener());
        signUpButton.addActionListener(new SignUpButtonListener());

        gbc.gridx = 0;
        gbc.gridy = 0;
        container.add(usernameLabel, gbc);

        gbc.gridx = 1;
        container.add(usernameTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        container.add(passwordLabel, gbc);

        gbc.gridx = 1;
        container.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        container.add(signUpButton, gbc);

        gbc.gridx = 1;
        container.add(loginButton, gbc);

        // Set preferred sizes for components to maintain mobile app dimensions
        usernameTextField.setPreferredSize(new Dimension(200, 25));
        passwordField.setPreferredSize(new Dimension(200, 25));
        signUpButton.setPreferredSize(new Dimension(100, 25));
        loginButton.setPreferredSize(new Dimension(100, 25));
    }

    private class LoginButtonListener implements ActionListener  {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameTextField.getText();
            char[] password = passwordField.getPassword();

            if (isValidUser(username, password)) {
                // Successfully logged in, open the campus map
                CampusMap campusMap = new CampusMap(username);
                campusMap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                campusMap.setLocationRelativeTo(null); // Center the frame on the screen
                campusMap.setVisible(true);

                // Close the login frame
                dispose();
            } else {
                JOptionPane.showMessageDialog(LoginFrame.this, "Invalid username or password. Try again.");
            }
        }

        private boolean isValidUser(String username, char[] password) {
            Map<String, String> userCredentials = readUserCredentials();
            return userCredentials.containsKey(username) && userCredentials.get(username).equals(new String(password));
        }
    }

    private class SignUpButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameTextField.getText();
            char[] password = passwordField.getPassword();

            if (username.isEmpty() || password.length == 0) {
                JOptionPane.showMessageDialog(LoginFrame.this, "Username and password cannot be empty.");
            } else {
                // Sign up the user and store the credentials
                Map<String, String> userCredentials = readUserCredentials();
                if (!userCredentials.containsKey(username)) {
                    userCredentials.put(username, new String(password));
                    writeUserCredentials(userCredentials);
                    JOptionPane.showMessageDialog(LoginFrame.this, "Sign up successful. You can now log in.");
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Username already exists. Choose a different username.");
                }
            }
        }
    }

    private Map<String, String> readUserCredentials() {
        Map<String, String> userCredentials = new HashMap<>();
        
        // Default user (admin)
        userCredentials.put("admin", "admin");

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                userCredentials.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userCredentials;
    }

    private void writeUserCredentials(Map<String, String> userCredentials) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (Map.Entry<String, String> entry : userCredentials.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // ... (unchanged)

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
