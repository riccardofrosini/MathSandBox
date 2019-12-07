package ai.maths.frog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

class MyPanel extends JPanel {


    private Color color;
    private BufferedImage bufferedImage;
    private Graphics graphics;

    private final HashMap<Color, Color> mapRound;

    MyPanel() {
        mapRound = new HashMap<>(3);
        mapRound.put(Color.RED, Color.GREEN);
        mapRound.put(Color.GREEN, Color.BLUE);
        mapRound.put(Color.BLUE, Color.RED);
        color = Color.RED;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (bufferedImage == null || bufferedImage.getWidth() != getWidth() || bufferedImage.getHeight() != getHeight()) {
                    bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                    graphics = bufferedImage.getGraphics();
                    graphics.setColor(Color.WHITE);
                    graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
                }
                graphics.setColor(color);
                graphics.fillOval(Math.max(e.getX() - 15, 0), Math.max(e.getY() - 15, 0), 30, 30);
                getGraphics().drawImage(bufferedImage, 0, 0, null);

            }
        });

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (bufferedImage == null || bufferedImage.getWidth() != getWidth() || bufferedImage.getHeight() != getHeight()) {
                    bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                    graphics = bufferedImage.getGraphics();
                    graphics.setColor(Color.WHITE);
                    graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
                }
                graphics.setColor(color);
                graphics.fillOval(Math.max(e.getX() - 15, 0), Math.max(e.getY() - 15, 0), 30, 30);

                getGraphics().drawImage(bufferedImage, 0, 0, null);

            }
        };
        addMouseMotionListener(mouseAdapter);
        addMouseListener(mouseAdapter);
    }

    void setColor(Color color) {
        this.color = color;
    }


    void runAnimation() {
        BufferedImage bufferedImageTemp = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        bufferedImageTemp.setData(bufferedImage.getData());
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                for (Map.Entry<Color, Color> colorColorEntry : mapRound.entrySet()) {
                    getRgb(i, j, colorColorEntry.getKey(), colorColorEntry.getValue(), bufferedImageTemp);

                }
            }
        }
        getGraphics().drawImage(bufferedImage, 0, 0, null);
    }

    private void getRgb(int cX, int cY, Color color1C, Color color2C, BufferedImage bufferedImageTemp) {
        int color1 = color1C.getRGB();
        int color2 = color2C.getRGB();
        int rgb = bufferedImageTemp.getRGB(cX, cY);
        if (rgb == color2) {
            int r = 10;
            int rSquared = r * r;
            int minX = Math.min(cX + r, getWidth() - 1);
            int maxX = Math.max(cX - r, 0);
            int minY = Math.min(cY + r, getHeight() - 1);
            int maxY = Math.max(cY - r, 0);

            for (int i = maxX; i <= minX; i++) {
                for (int j = maxY; j <= minY; j++) {
                    if ((i != cX && j != cY) && bufferedImageTemp.getRGB(i, j) == color1 && (i - cX) * (i - cX) + (j - cY) * (j - cY) <= rSquared) {
                        bufferedImage.setRGB(cX, cY, color1);
                        return;
                    }
                }
            }

        }
    }
}
