package ai.maths.frog;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Asd {

    private JPanel panel;
    private JRadioButton redButton;
    private JRadioButton greenButton;
    private JRadioButton blueButton;
    private JPanel image;
    private JButton runAnimation;
    private boolean live = false;

    private Asd() {
        redButton.addActionListener(evt -> ((MyPanel) image).setColor(Color.RED));
        greenButton.addActionListener(evt -> ((MyPanel) image).setColor(Color.GREEN));
        blueButton.addActionListener(evt -> ((MyPanel) image).setColor(Color.BLUE));
        runAnimation.addActionListener(e -> {
            if (!live) {
                live = true;
                new Thread(() -> {
                    while (live) {
                        try {
                            Thread.sleep(10);
                            image.repaint();
                        } catch (InterruptedException ignored) {
                        }
                    }
                }).start();
            } else {
                live = false;
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello");
        frame.setContentPane(new Asd().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        image = new MyPanel();
    }
}