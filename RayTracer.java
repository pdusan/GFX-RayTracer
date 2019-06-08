import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class RayTracer {

    public static void main (String[] args) {
        try {
            Scene scene = new Scene(args[0]);
            File image = scene.getOutputFile();
            
            int width = (int) scene.cam.width;
            int height = (int) scene.cam.height;

            BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);            

            for (int i = 0; i < height; ++i) {
                for (int j = 0; j < width; ++j) {
                    buffer.setRGB(j, i, scene.getBackgroundColor());
                }
            }

            ImageIO.write(buffer, "PNG", image);            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}