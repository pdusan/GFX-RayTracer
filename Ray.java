
public class Ray {

    public Point origin;
    public Vector direction;

    public Ray(Point origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "O: " + this.origin.toString() + "D: " + this.direction.toString();
    }
}