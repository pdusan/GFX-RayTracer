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
            
            int width = (int) scene.getCam().getPlaneWidth();
            int height = (int) scene.getCam().getPlaneHeight();

            BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);            
            for (int i = 0; i < height; ++i) {
                for (int j = 0; j < width; ++j) {
                    Ray ray = scene.getCam().makeRay(j, i);
                    for(Shape s : scene.getShapes()) {
                        if (s.hit(ray) > 0) {
                            buffer.setRGB(j, i, s.getColor(s.hit(ray), ray));
                        }
                        else continue;
                    }
                }
            }

            ImageIO.write(buffer, "PNG", image);            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}