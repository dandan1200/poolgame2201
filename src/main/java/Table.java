import javafx.geometry.Point2D;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Table implements GameObject {
    private String colour;
    private double friction;
    private long sizeX;
    private long sizeY;

    private List<Pocket> pockets;

    private List<GameObject> ballList;

    public void setAttributes(String colour, double friction, long sizeX, long sizeY, List<GameObject> ballsList) {
        this.colour = colour;
        this.friction = friction;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.ballList = ballsList;

        this.pockets = new ArrayList<Pocket>();
        double pocketRadius = Ball.radius + 6;
        pockets.add(new Pocket(pocketRadius,pocketRadius,pocketRadius));
        pockets.add(new Pocket(pocketRadius,getSizeY()-pocketRadius,pocketRadius ));
        pockets.add(new Pocket(getSizeX()/2,pocketRadius,pocketRadius ));
        pockets.add(new Pocket(getSizeX()/2,getSizeY()-pocketRadius,pocketRadius ));
        pockets.add(new Pocket(getSizeX() - pocketRadius,pocketRadius,pocketRadius ));
        pockets.add(new Pocket(getSizeX()-pocketRadius,getSizeY()-pocketRadius,pocketRadius ));



    }
    public String getColour() {
        return this.colour;
    }
    public double getFriction() {
        return this.friction;
    }
    public long getSizeX() {
        return this.sizeX;
    }
    public long getSizeY() {
        return this.sizeY;
    }
    public List<Ball> getBallsList() {
        return (List<Ball>)(List<?>) this.ballList;
    }

    public List<Pocket> getPockets() {
        return this.pockets;
    }


    public void checkPockets(Ball b) {
        for (Pocket p : getPockets()) {
            if ((Math.abs(b.getPositionX() - p.getX()) < Math.abs(p.getRadius()-b.getRadius())) && (Math.abs(b.getPositionY() - p.getY()) < Math.abs(p.getRadius()-b.getRadius()))) {
                b.sink(this);
                return;
            }
        }
    }

    public List<Ball> getEnabledBallsList() {
        List<Ball> enabledBalls = new ArrayList<Ball>();
        for (Ball b : getBallsList()) {
            if (b.getEnabled()) {
                enabledBalls.add(b);
            }
        }
        return enabledBalls;
    }


    public void tick(Table t) {

        for(Ball ball: getEnabledBallsList()) {
            //Handle click and drag
            checkPockets(ball);
            ball.tick(this);

            // Handle the edges (balls don't get a choice here)
            if (ball.getPositionX() + ball.getRadius() > sizeX) {
                ball.setCoordinates(sizeX - ball.getRadius(),ball.getPositionY());
                ball.setVelocities(ball.getVelocityX() * -1,ball.getVelocityY());
            }
            if (ball.getPositionX() - ball.getRadius() < 0) {
                ball.setCoordinates(0 + ball.getRadius(),ball.getPositionY());
                ball.setVelocities(ball.getVelocityX() * -1,ball.getVelocityY());
            }
            if (ball.getPositionY() + ball.getRadius() > sizeY) {
                ball.setCoordinates(ball.getPositionX(),sizeY - ball.getRadius());
                ball.setVelocities(ball.getVelocityX(),ball.getVelocityY() * -1);
            }
            if (ball.getPositionY() - ball.getRadius() < 0) {
                ball.setCoordinates(ball.getPositionX(),0 + ball.getRadius());
                ball.setVelocities(ball.getVelocityX(),ball.getVelocityY() * -1);
            }


            for(Ball ballB: getEnabledBallsList()) {
                if (checkCollision(ball, ballB)) {
//                    System.out.println("collided");
                    Pair newVels = calculateCollision(
                            new Point2D(ball.getPositionX(),ball.getPositionY()),
                            new Point2D(ball.getVelocityX(), ball.getVelocityY()),
                            ball.getMass(),
                            new Point2D(ballB.getPositionX(), ballB.getPositionY()),
                            new Point2D(ballB.getVelocityX(), ballB.getVelocityY()),
                            ballB.getMass()
                    );
                    ball.setVelocities(((Point2D) (newVels.getKey())).getX(),((Point2D) (newVels.getKey())).getY());
                    ballB.setVelocities(((Point2D) (newVels.getValue())).getX(),((Point2D) (newVels.getValue())).getY());
                }
            }


        }
    }
    public boolean checkCollision(Ball ballA, Ball ballB) {
        if (ballA == ballB) {
            return false;
        }

        return Math.abs(ballA.getPositionX() - ballB.getPositionX()) < ballA.getRadius() + ballB.getRadius() &&
                Math.abs(ballA.getPositionY() - ballB.getPositionY()) < ballA.getRadius() + ballB.getRadius();
    }
    public static Pair<Point2D, Point2D> calculateCollision(Point2D positionA, Point2D velocityA, double massA, Point2D positionB, Point2D velocityB, double massB) {

        // Find the angle of the collision - basically where is ball B relative to ball A. We aren't concerned with
        // distance here, so we reduce it to unit (1) size with normalize() - this allows for arbitrary radii
        Point2D collisionVector = positionA.subtract(positionB);
        collisionVector = collisionVector.normalize();

        // Here we determine how 'direct' or 'glancing' the collision was for each ball
        double vA = collisionVector.dotProduct(velocityA);
        double vB = collisionVector.dotProduct(velocityB);

        // If you don't detect the collision at just the right time, balls might collide again before they leave
        // each others' collision detection area, and bounce twice.
        // This stops these secondary collisions by detecting
        // whether a ball has already begun moving away from its pair, and returns the original velocities
        if (vB <= 0 && vA >= 0) {
            return new Pair<>(velocityA, velocityB);
        }

        // This is the optimisation function described in the gamasutra link. Rather than handling the full quadratic
        // (which as we have discovered allowed for sneaky typos)
        // this is a much simpler - and faster - way of obtaining the same results.
        double optimizedP = (2.0 * (vA - vB)) / (massA + massB);

        // Now we apply that calculated function to the pair of balls to obtain their final velocities
        Point2D velAPrime = velocityA.subtract(collisionVector.multiply(optimizedP).multiply(massB));
        Point2D velBPrime = velocityB.add(collisionVector.multiply(optimizedP).multiply(massA));

        return new Pair<>(velAPrime, velBPrime);
    }

    public void resetGame() {
        for (Ball b : getBallsList()) {
            b.setVelocities(0,0);
            b.setCoordinates(b.getOGX(), b.getOGY());
            b.setEnabled(true);
            b.sinkTally = 0;
        }
    }

}
