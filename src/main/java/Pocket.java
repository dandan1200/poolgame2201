import javafx.geometry.Point2D;

public class Pocket {
    private double xPos;
    private double yPos;
    private double radius;

    public Pocket(double x, double y, double radius) {
        this.xPos = x;
        this.yPos = y;
        this.radius = radius;
    }

    public double getX() {
        return this.xPos;
    }
    public double getY() {
        return this.yPos;
    }
    public double getRadius() {
        return this.radius;
    }

}
