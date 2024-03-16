public class BallDirector {
    private BallBuilder bb;
    public BallDirector(BallBuilder bb) {
        this.bb = bb;
    }

    public void construct(String colour, double positionX, double positionY, double velocityX, double velocityY, double mass) {
        bb.setColour(colour);
        bb.setCoordinates(positionX,positionY);
        bb.setMass(mass);
        bb.setVelocities(velocityX,velocityY);

    }
}
