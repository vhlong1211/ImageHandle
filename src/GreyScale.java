import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GreyScale {
    public void transGrey(BufferedImage image){
        int height=image.getHeight();
        int width=image.getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = image.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;
                int avg = (r + g + b) / 3;
                p = (a << 24) | (avg << 16) | (avg << 8) | avg;
                image.setRGB(x, y, p);
            }
        }
        try {
            File file = new File("/Users/vhlong/University/Kì 1 năm 4/Đa phương tiện/ImageHandle/greyscale.png");
            ImageIO.write(image, "png", file);
            System.out.println("Successfully converted a colored image into a grayscale image");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
