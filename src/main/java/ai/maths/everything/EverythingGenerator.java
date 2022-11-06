package ai.maths.everything;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class EverythingGenerator extends JPanel {

    BufferedImage bufferedImage;
    byte[] imageData = new byte[2359296];

    @Override
    protected void paintComponent(Graphics g) {
        if (bufferedImage == null) {
            bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics graphics = bufferedImage.getGraphics();
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        }
        g.drawImage(imageDataToBufferedImage(), 0, 0, null);

    }

    void increaseImageData() {
        byte imageDatum = imageData[0];
        imageData[0] = ++imageDatum;
        for (int i = 1; i < imageData.length && imageDatum == 0; i++) {
            imageDatum = imageData[i];
            imageData[i] = ++imageDatum;
        }
    }

    public BufferedImage imageDataToBufferedImage() {
        int width = getWidth();
        int height = getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int imageDatumR = Byte.toUnsignedInt(imageData[i + j * width]);
                int imageDatumG = Byte.toUnsignedInt(imageData[i + j * width + 786432]);
                int imageDatumB = Byte.toUnsignedInt(imageData[i + j * width + 1572864]);
                int rgb = (imageDatumR << 16) ^ (imageDatumG << 8) ^ imageDatumB;
                bufferedImage.setRGB(i, j, rgb);
            }
        }
        return bufferedImage;
    }

    public void updateImage(int pos) {
    }
}
