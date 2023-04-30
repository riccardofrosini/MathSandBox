package ai.maths.mandelbrot;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Asd {

    private JPanel panel;
    private MandelbrotCalculator mandelbrotCalculator;

    private Asd() {
        mandelbrotCalculator = new MandelbrotCalculator();
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mandelbrotCalculator.setWidthHeight(panel.getWidth(), panel.getHeight());
                if (e.getButton() == MouseEvent.BUTTON1) {
                    mandelbrotCalculator.zoomIn(e.getX(), e.getY());
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    mandelbrotCalculator.zoomOut(e.getX(), e.getY());
                }
                panel.repaint();
            }
        });
    }


    private void createUIComponents() {
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                mandelbrotCalculator.setWidthHeight(panel.getWidth(), panel.getHeight());
                for (int i = 0; i < getWidth(); i++) {
                    System.out.println(i);
                    for (int j = 0; j < getHeight(); j++) {
                        bufferedImage.setRGB(i, j, mandelbrotCalculator.pixelColor(i, j));
                    }
                }
                g.drawImage(bufferedImage, 0, 0, null);
            }
        };
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello");
        frame.setContentPane(new Asd().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.repaint();
    }
}