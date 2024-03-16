public class BallCreator implements GameObjectCreator{

    public GameObject create() {
        return new Ball();
    }
    public static void setAttributes(Ball b, String colour, double positionX, double positionY, double velocityX, double velocityY, double mass){
        BallBuilder ballBuilder = new NormalBallBuilder(b);
        BallDirector director = new BallDirector(ballBuilder);
        director.construct(colour, positionX, positionY, velocityX, velocityY, mass);

    }
}
