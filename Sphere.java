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

public class Sphere extends Shape {

    private Point center;
    private double radius;
    private float surfaceRed, surfaceGreen, surfaceBlue;
    private float ka, kd, ks;
    private double exp, reflect, transmit, refract;
    private Point translateBy;
    private double scaleX, scaleY, scaleZ, rotateX, rotateY, rotateZ;
    private Light light;
    private Point eyeLocation;

    public Sphere(Node sphere, Light light, Camera cam) {
        this.eyeLocation = cam.getLocation();

        this.light = light;

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

        this.surfaceBlue = colBlue;
        this.surfaceGreen = colGreen;
        this.surfaceRed = colRed;

        Node phong = material.getChildNodes().item(3);
        this.ka = Float.parseFloat(phong.getAttributes().getNamedItem("ka").getTextContent());
        this.kd = Float.parseFloat(phong.getAttributes().getNamedItem("kd").getTextContent());
        this.ks = Float.parseFloat(phong.getAttributes().getNamedItem("ks").getTextContent());
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

    public double hit(Ray ray) {

        double b = 2 * (ray.direction.x * (ray.origin.x - this.center.x) + ray.direction.y * 
                        (ray.origin.y - this.center.y) + ray.direction.z * (ray.origin.z - this.center.z));
        double c = (ray.origin.x - this.center.x) * (ray.origin.x - this.center.x) + 
                    (ray.origin.y - this.center.y) * (ray.origin.y - this.center.y) + 
                    (ray.origin.z - this.center.z) * (ray.origin.z - this.center.z) - this.radius * this.radius;
        double d = b*b - 4 * c;

        if (d < 0) 
            return -1;
        else {
            double t0 = (-b - Math.sqrt(d))/2;
            
            if (t0 > 0)
                return t0;
            else {
                double t1 = (-b + Math.sqrt(b*b - 4 * c))/2;

                if (t1 > 0)
                    return t1;
                else return -1;
            }
        }
    }

    public int getColor(double t, Ray ray) {
        float ambientRed = 0;
        float ambientGreen = 0;
        float ambientBlue = 0;
        float diffuseRed = 0;
        float diffuseGreen = 0;
        float diffuseBlue = 0;
        float specRed = 0;
        float specGreen = 0;
        float specBlue = 0;

        ambientRed = this.ka * this.surfaceRed;
        ambientGreen = this.ka * this.surfaceGreen;
        ambientBlue = this.ka * this.surfaceBlue;
        
        if (this.light.getPointPos() != null) {
            double x = ray.origin.x + t * ray.direction.x;
            double y = ray.origin.y + t * ray.direction.y;
            double z = ray.origin.z + t * ray.direction.z;
            Point p = new Point(x, y, z);

            Vector n = this.center.minus(p);
            n.normalize();
            
            Vector v = this.eyeLocation.minus(p);
            v.normalize();

            Vector l = p.minus(this.light.getPointPos());
            l.normalize();

            Vector r = n.times(2 * l.dot(n)).minus(l).times(-1);
            r.normalize();

            diffuseRed += this.kd * this.surfaceRed * Math.max(l.dot(n), 0);
            diffuseGreen += this.kd * this.surfaceGreen * Math.max(l.dot(n), 0);
            diffuseBlue += this.kd * this.surfaceBlue * Math.max(l.dot(n), 0);

            if (l.dot(n) > 0) {
                specRed += this.ks * this.surfaceRed * Math.pow(Math.max(r.dot(v), 0), this.exp);
                specGreen += this.ks * this.surfaceGreen * Math.pow(Math.max(r.dot(v), 0), this.exp);
                // System.out.println(specRed);
                specBlue += this.ks * this.surfaceBlue * Math.pow(Math.max(r.dot(v), 0), this.exp);
            }
        }
        float finalRed = ambientRed + diffuseRed + specRed;
        float finalGreen = ambientGreen + diffuseGreen + specGreen;
        float finalBlue = ambientBlue + diffuseBlue + specBlue;
        if (finalRed > 1) finalRed = 1;
        if (finalGreen > 1) finalGreen = 1;
        if (finalBlue > 1) finalBlue = 1;

        if (finalRed < 0) finalRed = 0;
        if (finalGreen < 0) finalGreen = 0;
        if (finalBlue < 0) finalBlue = 0;

        Color res = new Color(finalRed, finalGreen, finalBlue);
        return res.getRGB();
    }
}