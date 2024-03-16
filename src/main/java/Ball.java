import org.json.simple.JSONObject;

public class Ball implements GameObject{
    public static final int radius = 10;
    public int sinkTally = 0;
    private String colour;
    private double originalPosX;
    private double originalPosY;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double mass;
    private boolean isCue;
    private boolean enabled = true;

    private BallPocketStrategy strat;

    public void setStrat(BallPocketStrategy s) {
        this.strat = s;
    }
    public void setOriginalCoords(double x, double y) {
        this.originalPosX = x;
        this.originalPosY = y;
    }
    public void setCoordinates(double x, double y) {
        this.positionX = x;
        this.positionY = y;
    }
    public void setVelocities(double x, double y) {
        this.velocityX = x;
        this.velocityY = y;
    }
    public void setColour(String s) {
        this.colour = s;
        if (s.equals("white")){
            System.out.println("cue ball set");
            this.isCue = true;
        }
    }
    public void setMass(double m) {
        this.mass = m;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean b) {
        this.enabled = b;
    }


    public double getPositionX(){
        return this.positionX;
    }
    public double getOGX() {
        return this.originalPosX;
    }
    public double getOGY() {
        return this.originalPosY;
    }
    public double getPositionY(){
        return this.positionY;
    }
    public double getVelocityX(){
        return this.velocityX;
    }
    public double getVelocityY(){
        return this.velocityY;
    }
    public String getColour() {
        return this.colour;
    }
    public boolean getIsCue() {
        return this.isCue;
    }
    public double getMass() {
        return this.mass;
    }


    public void tick(Table t) {
        positionX += velocityX;
        positionY += velocityY;
        if (Math.abs(velocityX) > 0.04){
            if (velocityX > 0) {
                velocityX -= t.getFriction()/7;
            } else {
                velocityX += t.getFriction()/7;
            }
        } else {
            velocityX = 0;
        }

        if (Math.abs(velocityY) > 0.04){
            if (velocityY > 0) {
                velocityY -= t.getFriction()/7;
            } else {
                velocityY += t.getFriction()/7;
            }
        } else {
            velocityY = 0;
        }

    }

    public int getRadius(){
        return this.radius;
    }

    public void sink(Table t) {
        this.strat.think(t,this);

    }
}
