package it.unibo.mvc;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUI {

    private static final int PROPORTION = 2;
    private final JFrame frame = new JFrame();
    private final Controller controller;

    public SimpleGUI() {
        this.controller = new SimpleController();

        final JPanel canvas = new JPanel();
        canvas.setLayout(new BorderLayout());

        final JTextField textField = new JTextField();
        final JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        final JButton printButton = new JButton("Print");
        final JButton showHistoryButton = new JButton("Show history");

        canvas.add(textField, BorderLayout.NORTH);
        canvas.add(textArea, BorderLayout.CENTER);

        final JPanel buttons = new JPanel();
        buttons.add(printButton);
        buttons.add(showHistoryButton);
        canvas.add(buttons, BorderLayout.SOUTH);

        frame.setContentPane(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Event handlers
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                controller.setNextString(textField.getText());
                controller.printCurrentString();
            }
        });

        showHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                final StringBuilder result = new StringBuilder();
                List<String> history = controller.getPrintedStringHistory();
                for (final String string : history) {
                    result.append(string + "\n");
                }
                textArea.setText(result.toString());
            }
        });
    }

    private void display() {
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int height = (int) screen.getHeight();
        final int width = (int) screen.getWidth();
        frame.setSize(height / PROPORTION, width / PROPORTION);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SimpleGUI().display();
    }

}
