import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Mirror {
    public void transMirror(BufferedImage img){
        int width=img.getWidth();
        int height=img.getHeight();
        BufferedImage mimg = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);

        // Create mirror image pixel by pixel
        for (int y = 0; y < height; y++)
        {
            for (int lx = 0, rx = width - 1; lx < width; lx++, rx--)
            {

                int p = img.getRGB(lx, y);

                mimg.setRGB(rx, y, p);
            }
        }

        // save mirror image
        try
        {
            File file = new File("/Users/vhlong/University/Kì 1 năm 4/Đa phương tiện/ImageHandle/Mirror.png");
            ImageIO.write(img, "png", file);
            System.out.println("Successfully converted a colored image into a Mirror image");
        }
        catch(IOException e)
        {
            System.out.println("Error: " + e);
        }
    }
}
