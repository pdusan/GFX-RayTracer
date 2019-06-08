
public class Normal {

    public double x, y, z;

    public Normal() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Normal(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Normal(Normal n) {
        this.x = n.x;
        this.y = n.y;
        this.z = n.z;
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