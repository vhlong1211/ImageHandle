import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

            GreyScale greyOb=new GreyScale();
            Negative negativeOb=new Negative();
            Sepia sepiaOb=new Sepia();
            Mirror mirrorOb=new Mirror();
            HuffmanEncoder3 Ob=new HuffmanEncoder3();
            BufferedImage image = null;
            File file = null;
            try {
                file = new File("/Users/vhlong/University/Kì 1 năm 4/Đa phương tiện/testImage/test13.png");
                image = ImageIO.read(file);
            } catch (IOException e) {
                System.out.println(e);
            }
        System.out.println("Nhập 1 để chuyển ảnh thành ảnh xám\n" +
                "Nhập 2 để chuyển ảnh thành dạng âm bản\n" +
                "Nhập 3 để chuyển ảnh thành dạng Sepia\n" +
                "Nhập 4 để chuyển ảnh thành dạng Mirror\n"+
                "Nhập 5 để nén\n"+
                "Nhập 6 để giải nén\n");
        String s = in.nextLine();
        int temp=Integer.parseInt(s);


        if(temp==1){
            greyOb.transGrey(image);
        }else if(temp==2){
            negativeOb.transNeg(image);
        }else if(temp==3){
            sepiaOb.transSepia(image);
        }else if(temp==4){
            mirrorOb.transMirror(image);
        }else if(temp==5){
            Ob.ComP(file);
        }else if(temp==6){
            Ob.DeComP();
        }else{
            System.out.println("Dữ liệu nhập ko hợp lệ");
        }



    }
}

