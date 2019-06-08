
public class Vector {

    public double x, y, z;

    public Vector() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(Vector v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public Vector plus(Vector v) {
        double newX = this.x += v.x;
        double newY = this.y += v.y;
        double newZ = this.z += v.z;

        return new Vector(newX, newY, newZ);
    }

    public Vector minus(Vector v) {
        double newX = this.x -= v.x;
        double newY = this.y -= v.y;
        double newZ = this.z -= v.z;

        return new Vector(newX, newY, newZ);
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
    
    public void normalize() {
        double magnitude = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);

        this.x /= magnitude;
        this.y /= magnitude;
        this.z /= magnitude;
    }
    
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}