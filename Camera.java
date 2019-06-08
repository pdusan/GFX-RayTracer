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

public class Camera {
    
    private Document doc;
    private Point location, lookingAt;
    private Vector up;
    private double fieldOfView;
    public double width, height, bounces;

    public Camera(Document doc) {
        this.doc = doc;
        initCam();
    }

    private void initCam() {
        NodeList cameraOptions = this.doc.getElementsByTagName("camera").item(0).getChildNodes();

        double posX = Double.parseDouble(cameraOptions.item(1).getAttributes().getNamedItem("x").getTextContent());
        double posY = Double.parseDouble(cameraOptions.item(1).getAttributes().getNamedItem("y").getTextContent());
        double posZ = Double.parseDouble(cameraOptions.item(1).getAttributes().getNamedItem("z").getTextContent());

        double lookX = Double.parseDouble(cameraOptions.item(3).getAttributes().getNamedItem("x").getTextContent());
        double lookY = Double.parseDouble(cameraOptions.item(3).getAttributes().getNamedItem("y").getTextContent());
        double lookZ = Double.parseDouble(cameraOptions.item(3).getAttributes().getNamedItem("z").getTextContent());

        double upX = Double.parseDouble(cameraOptions.item(5).getAttributes().getNamedItem("x").getTextContent());
        double upY = Double.parseDouble(cameraOptions.item(5).getAttributes().getNamedItem("y").getTextContent());
        double upZ = Double.parseDouble(cameraOptions.item(5).getAttributes().getNamedItem("z").getTextContent());

        Point pos = new Point(posX, posY, posZ);
        Point look = new Point(lookX, lookY, lookZ);
        Vector up = new Vector(upX, upY, upZ);
        double fov = Double.parseDouble(cameraOptions.item(7).getAttributes().getNamedItem("angle").getTextContent());
        double width = Double.parseDouble(cameraOptions.item(9).getAttributes().getNamedItem("horizontal").getTextContent());
        double height = Double.parseDouble(cameraOptions.item(9).getAttributes().getNamedItem("vertical").getTextContent());
        double bounceCount = Double.parseDouble(cameraOptions.item(11).getAttributes().getNamedItem("n").getTextContent());

        this.location = pos;
        this.lookingAt = look;
        this.up = up;
        this.fieldOfView = fov;
        this.width = width;
        this.height = height;
        this.bounces = bounceCount;
    }

}