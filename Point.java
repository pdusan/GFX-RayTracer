
public class Point {

    public double x, y, z;

    public Point() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }

    public Point plus(Point p) {
        double newX = this.x += p.x;
        double newY = this.y += p.y;
        double newZ = this.z += p.z;

        return new Point(newX, newY, newZ);
    }

    public Point minus(Point p) {
        double newX = this.x -= p.x;
        double newY = this.y -= p.y;
        double newZ = this.z -= p.z;

        return new Point(newX, newY, newZ);
    }

    public double dot(Vector v) {
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    public double dot(Point p) {
        return this.x * p.x + this.y * p.y + this.z * p.z;
    }

    public double dot(Normal n) {
        return this.x * n.x + this.y * n.y + this.z * n.z;
    }
    
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}