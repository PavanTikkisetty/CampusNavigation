package tests;
import static org.junit.Assert.*;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import navigation.LoginFrame;

public class TestLoginFrame extends LoginFrame {

    private TestLoginFrame loginFrame;

    @Before
    public void setUp() {
        loginFrame = new TestLoginFrame();
        loginFrame.setVisible(true);
    }

    @After
    public void tearDown() {
        loginFrame.dispose();
    }

    @Test
    public void testValidUserLogin() {
        setCredentialsInTextField("admin", "admin");
        clickLoginButton();
    }

    @Test
    public void testInvalidUserLogin() {
        setCredentialsInTextField("invalidUser", "invalidPassword");
        clickLoginButton();

    }

    @Test
    public void testSignUpWithValidCredentials() {
        setCredentialsInTextField("newUser", "newPassword");
        clickSignUpButton();

        assertTrue(showsInfoMessageDialog("Sign up successful. You can now log in."));
        assertTrue(loginFrame.isVisible());
    }

    @Test
    public void testSignUpWithExistingUsername() {
        setCredentialsInTextField("admin", "admin");
        clickSignUpButton();

        assertTrue(showsErrorMessageDialog("Username already exists. Choose a different username."));
        assertTrue(loginFrame.isVisible());
    }

    private void setCredentialsInTextField(String username, String password) {
        JTextField usernameTextField = (JTextField) findComponent(loginFrame, JTextField.class, "usernameTextField");
        JPasswordField passwordField = (JPasswordField) findComponent(loginFrame, JPasswordField.class, "passwordField");

        usernameTextField.setText(username);
        passwordField.setText(password);
    }

    private void clickLoginButton() {
        JButton loginButton = (JButton) findComponent(loginFrame, JButton.class, "Login");
        loginButton.doClick();
    }

    private void clickSignUpButton() {
        JButton signUpButton = (JButton) findComponent(loginFrame, JButton.class, "Sign Up");
        signUpButton.doClick();
    }

    private boolean showsErrorMessageDialog(String message) {
        return showsMessageDialog(JOptionPane.ERROR_MESSAGE, message);
    }

    private boolean showsInfoMessageDialog(String message) {
        return showsMessageDialog(JOptionPane.INFORMATION_MESSAGE, message);
    }

    private boolean showsMessageDialog(int messageType, String message) {
        Object[] options = { "OK" };
        int result = JOptionPane.showOptionDialog(loginFrame, message, "Message", JOptionPane.DEFAULT_OPTION,
                messageType, null, options, options[0]);
        return result == 0; // OK button index
    }

    // Helper method to find a dialog by its message
    private Component findComponent(Container container, Class<?> type, String name) {
        for (Component component : container.getComponents()) {
            if (type.isAssignableFrom(component.getClass()) && name.equals(component.getName())) {
                return component;
            }
            if (component instanceof Container) {
                Component found = findComponent((Container) component, type, name);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
