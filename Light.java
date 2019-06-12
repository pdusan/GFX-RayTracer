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

public class Light {

    private Document doc;
    public int ambientColor, pointColor, parallelColor, spotColor;
    private Point pointPos, spotPos;
    private Vector parallelDir, spotDir;
    private double fall1, fall2;

    public Light(Document doc) {
        this.doc = doc;
        initAmbient();
        initPoint();
        initParallel();
        initSpot();
    }

    
    private void initAmbient() {
        NodeList ambientOptions = this.doc.getElementsByTagName("lights").item(0).getChildNodes().item(1).getChildNodes();
        
        float ambientRed = Float.parseFloat(ambientOptions.item(1).getAttributes().getNamedItem("r").getTextContent());
        float ambientGreen = Float.parseFloat(ambientOptions.item(1).getAttributes().getNamedItem("g").getTextContent());
        float ambientBlue = Float.parseFloat(ambientOptions.item(1).getAttributes().getNamedItem("b").getTextContent());
        
        Color ambient = new Color(ambientRed, ambientGreen, ambientBlue);

        this.ambientColor = ambient.getRGB();
    }

    private void initPoint() {
        try {
            NodeList pointOptions = this.doc.getElementsByTagName("lights").item(0).getChildNodes().item(3).getChildNodes();

            float pointRed = Float.parseFloat(pointOptions.item(1).getAttributes().getNamedItem("r").getTextContent());
            float pointGreen = Float.parseFloat(pointOptions.item(1).getAttributes().getNamedItem("g").getTextContent());
            float pointBlue = Float.parseFloat(pointOptions.item(1).getAttributes().getNamedItem("b").getTextContent());

            Color point = new Color(pointRed, pointGreen, pointBlue);

            this.pointColor = point.getRGB();

            double posX = Double.parseDouble(pointOptions.item(3).getAttributes().getNamedItem("x").getTextContent());
            double posY = Double.parseDouble(pointOptions.item(3).getAttributes().getNamedItem("y").getTextContent());
            double posZ = Double.parseDouble(pointOptions.item(3).getAttributes().getNamedItem("z").getTextContent());

            this.pointPos = new Point(posX, posY, posZ);

        } catch (Exception e) {
            return;
        }
    }
    
    private void initParallel() {
        try {
            NodeList parallelOptions = this.doc.getElementsByTagName("lights").item(0).getChildNodes().item(5).getChildNodes();

            float parallelRed = Float.parseFloat(parallelOptions.item(1).getAttributes().getNamedItem("r").getTextContent());
            float parallelGreen = Float.parseFloat(parallelOptions.item(1).getAttributes().getNamedItem("g").getTextContent());
            float parallelBlue= Float.parseFloat(parallelOptions.item(1).getAttributes().getNamedItem("b").getTextContent());

            Color parallel = new Color(parallelRed, parallelGreen, parallelBlue);

            this.parallelColor = parallel.getRGB();

            double dirX = Double.parseDouble(parallelOptions.item(3).getAttributes().getNamedItem("x").getTextContent());
            double dirY = Double.parseDouble(parallelOptions.item(3).getAttributes().getNamedItem("y").getTextContent());
            double dirZ = Double.parseDouble(parallelOptions.item(3).getAttributes().getNamedItem("z").getTextContent());

            this.parallelDir = new Vector(dirX, dirY, dirZ);

        } catch (Exception e) {
            return;
        }
    }

    private void initSpot() {
        try {
            NodeList spotOptions = this.doc.getElementsByTagName("lights").item(0).getChildNodes().item(7).getChildNodes();

            float spotRed = Float.parseFloat(spotOptions.item(1).getAttributes().getNamedItem("r").getTextContent());
            float spotGreen = Float.parseFloat(spotOptions.item(1).getAttributes().getNamedItem("g").getTextContent());
            float spotBlue = Float.parseFloat(spotOptions.item(1).getAttributes().getNamedItem("b").getTextContent());

            Color spot = new Color(spotRed, spotGreen, spotBlue);

            this.spotColor = spot.getRGB();

            double posX = Double.parseDouble(spotOptions.item(3).getAttributes().getNamedItem("x").getTextContent());
            double posY = Double.parseDouble(spotOptions.item(3).getAttributes().getNamedItem("y").getTextContent());
            double posZ = Double.parseDouble(spotOptions.item(3).getAttributes().getNamedItem("z").getTextContent());

            this.spotPos = new Point(posX, posY, posZ);
            
            double dirX = Double.parseDouble(spotOptions.item(5).getAttributes().getNamedItem("x").getTextContent());
            double dirY = Double.parseDouble(spotOptions.item(5).getAttributes().getNamedItem("y").getTextContent());
            double dirZ = Double.parseDouble(spotOptions.item(5).getAttributes().getNamedItem("z").getTextContent());

            this.spotDir = new Vector(dirX, dirY, dirZ);

            this.fall1 = Double.parseDouble(spotOptions.item(7).getAttributes().getNamedItem("alpha1").getTextContent());
            this.fall2 = Double.parseDouble(spotOptions.item(7).getAttributes().getNamedItem("alpha2").getTextContent());
        } catch (Exception e) {
            return;
        }
    }

    public Point getPointPos() {
        return this.pointPos;
    }
}