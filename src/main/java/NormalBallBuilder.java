public class NormalBallBuilder implements BallBuilder{
    Ball b;
    NormalBallBuilder(Ball b){
        this.b = b;
    }
    public void setCoordinates(double x, double y) {
        b.setCoordinates(x,y);
        b.setOriginalCoords(x,y);
    }
    public void setVelocities(double x, double y) {
        b.setVelocities(x,y);
    }
    public void setColour(String colour) {
        b.setColour(colour);
        if (colour.equals("white")) {
            b.setStrat(new BallStrategyCueBall());
        } else if (colour.equals("blue")) {
            b.setStrat(new BallStrategyBlueBall());
        } else if (colour.equals("red")) {
            b.setStrat(new BallStrategyRedBall());
        }
    }
    public void setMass(double mass) {
        b.setMass(mass);
    }
}
