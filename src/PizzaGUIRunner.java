import javax.swing.SwingUtilities;

public class PizzaGUIRunner {
    public static void main(String[] args) {
        // Create the GUI frame and make it visible
        SwingUtilities.invokeLater(() -> {
            new PizzaGUIFrame().setVisible(true);
        });
    }
}
