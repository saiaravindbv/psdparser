package psd;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import psd.section.LayerMaskInfo.LayerRecord;
import psd.section.PSDLayerMaskInfo;


public class TestPSD {
    
    public static void main(String[] args) throws IOException {
        PSD psd = null;
        try {
            psd = new PSD(new FileInputStream("/home/aravind/Desktop/psd-parser/image.psd"));
            System.out.println(psd.toString());
            PSDLayerMaskInfo layermaskinfo = psd.getLayerMaskInfo();
            List <LayerRecord> layers = layermaskinfo.getLayers();
            for(LayerRecord layer : layers) {
                System.out.println("Writing " + layer.getName() + ".png");
                BufferedImage image = layer.getImage();
                if (image != null)
                    ImageIO.write(image, "png", new File("/home/aravind/Desktop/img/" + layer.getName()));
            }
        } catch (PSDException psdex) {
            System.out.println(psdex.getMessage());
        }
    }
}
