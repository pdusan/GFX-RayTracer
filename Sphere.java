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

public class Sphere {

    private Point center;
    private double radius;
    private int surfaceColor;
    private double ka, kd, ks, exp, reflect, transmit, refract;
    private Point translateBy;
    private double scaleX, scaleY, scaleZ, rotateX, rotateY, rotateZ;

    public Sphere(Node sphere) {
        this.radius = Double.parseDouble(sphere.getAttributes().getNamedItem("radius").getTextContent());

        Node position = sphere.getChildNodes().item(1);
        double posX = Double.parseDouble(position.getAttributes().getNamedItem("x").getTextContent());
        double posY = Double.parseDouble(position.getAttributes().getNamedItem("y").getTextContent());
        double posZ = Double.parseDouble(position.getAttributes().getNamedItem("z").getTextContent());
        this.center = new Point(posX, posY, posZ);

        Node material = sphere.getChildNodes().item(3);

        initMaterial(material);

        Node transform = sphere.getChildNodes().item(5);

        initTransforms(transform);

        if (this.translateBy != null)
            this.center = this.center.plus(this.translateBy);
    }

    private void initMaterial(Node material) {
        Node color = material.getChildNodes().item(1);
        float colRed = Float.parseFloat(color.getAttributes().getNamedItem("r").getTextContent());
        float colGreen = Float.parseFloat(color.getAttributes().getNamedItem("g").getTextContent());
        float colBlue = Float.parseFloat(color.getAttributes().getNamedItem("b").getTextContent());
        Color col = new Color(colRed, colGreen, colBlue);
        this.surfaceColor = col.getRGB();

        Node phong = material.getChildNodes().item(3);
        this.ka = Double.parseDouble(phong.getAttributes().getNamedItem("ka").getTextContent());
        this.kd = Double.parseDouble(phong.getAttributes().getNamedItem("kd").getTextContent());
        this.ks = Double.parseDouble(phong.getAttributes().getNamedItem("ks").getTextContent());
        this.exp = Double.parseDouble(phong.getAttributes().getNamedItem("exponent").getTextContent());

        Node reflectance = material.getChildNodes().item(5);
        this.reflect = Double.parseDouble(reflectance.getAttributes().getNamedItem("r").getTextContent());
        
        Node transmittance = material.getChildNodes().item(7);
        this.transmit = Double.parseDouble(transmittance.getAttributes().getNamedItem("t").getTextContent());
        
        Node refraction = material.getChildNodes().item(9);
        this.refract = Double.parseDouble(refraction.getAttributes().getNamedItem("iof").getTextContent());
    }

    private void initTransforms(Node transform) {
        try {
            Node translate = transform.getChildNodes().item(1);
            double posX = Double.parseDouble(translate.getAttributes().getNamedItem("x").getTextContent());
            double posY = Double.parseDouble(translate.getAttributes().getNamedItem("y").getTextContent());
            double posZ = Double.parseDouble(translate.getAttributes().getNamedItem("z").getTextContent());
            this.translateBy = new Point(posX, posY, posZ);

            Node scale = transform.getChildNodes().item(3);
            double scaleX = Double.parseDouble(scale.getAttributes().getNamedItem("x").getTextContent());
            double scaleY = Double.parseDouble(scale.getAttributes().getNamedItem("y").getTextContent());
            double scaleZ = Double.parseDouble(scale.getAttributes().getNamedItem("z").getTextContent());

            double rotateX = Double.parseDouble(transform.getChildNodes().item(5).getAttributes().getNamedItem("theta").getTextContent());
            double rotateY = Double.parseDouble(transform.getChildNodes().item(7).getAttributes().getNamedItem("theta").getTextContent());
            double rotateZ = Double.parseDouble(transform.getChildNodes().item(9).getAttributes().getNamedItem("theta").getTextContent());
        } catch(Exception e) {
            return;
        }
    }
}