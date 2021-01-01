import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Negative {
    public void transNeg(BufferedImage img){
        int height=img.getHeight();
        int width=img.getWidth();
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int p = img.getRGB(x,y);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;

                r = 255 - r;
                g = 255 - g;
                b = 255 - b;

                p = (a<<24) | (r<<16) | (g<<8) | b;
                img.setRGB(x, y, p);
            }
        }

        // write image
        try
        {
            File file = new File("/Users/vhlong/University/Kì 1 năm 4/Đa phương tiện/ImageHandle/Negative.png");
            ImageIO.write(img, "png", file);
            System.out.println("Successfully converted a colored image into a Negative image");
        }
        catch(IOException e)
        {
            System.out.println(e);
        }

    }
}
