package it.unibo.mvc;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {

    private static final int PROPORTION = 5;
    private final JFrame frame = new JFrame();
    private final Controller controller = new Controller();

    public SimpleGUIWithFileChooser() {
        // Setting the app canvas that will contain all the components.
        final JPanel canvas = new JPanel();
        canvas.setLayout(new BorderLayout());

        // Creating and adding components to the canvas.
        final JTextArea textArea = new JTextArea();
        final JButton saveButton = new JButton("Save");
        canvas.add(textArea, BorderLayout.CENTER);
        canvas.add(saveButton, BorderLayout.SOUTH);

        // Part 3
        final JPanel fileChooserPanel = new JPanel();
        final JTextField textField = new JTextField();
        final JButton browseButton = new JButton("Browse...");
        textField.setEditable(false);
        textField.setText(controller.getCurrentFile().getName());
        fileChooserPanel.setLayout(new BorderLayout());
        fileChooserPanel.add(textField, BorderLayout.CENTER);
        fileChooserPanel.add(browseButton, BorderLayout.EAST);
        canvas.add(fileChooserPanel, BorderLayout.NORTH);

        // Setting the frame.
        frame.setContentPane(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Event handlers.
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                try {
                    System.out.println("SAVE: " + controller.getCurrentFile());
                    controller.saveToFile(textArea.getText());
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(frame, e, "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });

        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                // Creating and displaying the file chooser.
                final JFileChooser chooser = new JFileChooser();
                final var choice = chooser.showSaveDialog(frame);

                switch (choice) {
                    case JFileChooser.APPROVE_OPTION:
                        final File selectedFile = chooser.getSelectedFile();
                        controller.setCurrentFile(selectedFile);
                        System.out.println("LOAD: " + controller.getCurrentFile());
                        textField.setText(selectedFile.getName());
                        textArea.setText("");   // Resets the text area once a new file is loaded.
                        break;

                    case JFileChooser.CANCEL_OPTION:
                        break;
                
                    default:
                        JOptionPane.showMessageDialog(frame, choice, "Oops", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            }
        });
    }

    /**
     * Displays the graphical interface.
     */
    private void display() {
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int height = (int) screen.getHeight();
        final int width = (int) screen.getWidth();
        frame.setSize(width / PROPORTION, height / PROPORTION);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SimpleGUIWithFileChooser().display();
    }
}
