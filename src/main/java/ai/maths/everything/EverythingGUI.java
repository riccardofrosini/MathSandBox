package ai.maths.everything;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class EverythingGUI {

    private JPanel panel;
    private JSlider slider;
    private JPanel image;
    private JButton button;
    private JTextField val;
    private boolean live;
    private final Thread runningGraphics;


    private EverythingGUI() {
        live = false;
        runningGraphics = new Thread(() -> {
            while (true) {
                if (live) {
                    ((EverythingGenerator) image).increaseImageData();
                    image.repaint();
                }
            }
        });
        slider.addChangeListener(evt -> {
            int pos = Integer.parseInt(val.getText());
            ((EverythingGenerator) image).updateImage(pos);
        });
        button.addActionListener(e -> {
            if (!live) {
                if (!runningGraphics.isAlive()) {
                    runningGraphics.start();
                }
                live = true;
                button.setText("Pause");
            } else {
                live = false;
                button.setText("Start");
            }
        });
    }


    public static void main(String[] args) {
        EverythingGUI everythingGUI = new EverythingGUI();
        JFrame frame = new JFrame("Hello");
        frame.setContentPane(everythingGUI.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        image = new EverythingGenerator();
    }
}
