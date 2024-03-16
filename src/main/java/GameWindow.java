import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafx.util.Pair;

public class GameWindow {
    private Table table;
    private Scene scene;
    private final GraphicsContext gc;

    private Line cue;

    private boolean clickOnCue = false;

    private Ball cueBall;



    GameWindow(Table table) {
        this.table = table;
        Pane pane = new Pane();
        setClickEvents(pane);
        this.scene = new Scene(pane, table.getSizeX(), table.getSizeY());
        this.scene.setFill(Paint.valueOf(table.getColour()));
        Canvas canvas = new Canvas(table.getSizeX(), table.getSizeY());
        gc = canvas.getGraphicsContext2D();
        pane.getChildren().add(canvas);
    }
    Scene getScene(){
        return this.scene;
    }

    void run() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(40),
                t -> this.draw()));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void drawPockets() {
        for (Pocket p : table.getPockets()) {
            gc.setFill(Paint.valueOf("Black"));
            gc.fillOval(p.getX() - p.getRadius(),
                    p.getY() - p.getRadius(),
                    p.getRadius()*2,
                    p.getRadius()*2);
        }
    }

    private void draw() {
        if (table.getEnabledBallsList().size() == 1) {
            gameOver();
        }

        gc.setFill(Paint.valueOf(table.getColour()));
        gc.fillRect(0,0, table.getSizeX(), table.getSizeY());
        drawPockets();
        table.tick(table);
        drawPockets();
        for (Ball ball : table.getEnabledBallsList()) {

            gc.setFill(Paint.valueOf(ball.getColour()));

            gc.fillOval(ball.getPositionX() - ball.getRadius(),
                    ball.getPositionY() - ball.getRadius(),
                    ball.getRadius() * 2,
                    ball.getRadius() * 2);
        }
    }

    private void setClickEvents(Pane pane) {
        pane.setOnMousePressed(event -> {
            for (Ball b: this.table.getBallsList()) {
                if (b.getIsCue()) {
                    if (event.getX() > b.getPositionX() - b.getRadius() && event.getX() < b.getPositionX() + b.getRadius() && event.getY() > b.getPositionY() - b.getRadius() && event.getY() < b.getPositionY() + b.getRadius()) {
                        cue = new Line(event.getX(), event.getY(), event.getX(), event.getY());
                        cue.setStroke(Color.BROWN);
                        cue.setStrokeWidth(2);
                        pane.getChildren().add(cue);
                        clickOnCue = true;
                        cueBall = b;
                    }
                    break;
                }

            }



        });
        pane.setOnMouseDragged(event -> {
            if (clickOnCue) {
                cue.setEndX(event.getX());
                cue.setEndY(event.getY());
            }

        });

        pane.setOnMouseReleased(event -> {
            if (clickOnCue) {
                pane.getChildren().remove(cue);
                clickOnCue = false;
                shoot(cueBall);
            }

        });
    }

    private void shoot(Ball b) {
        cueBall.setVelocities((cue.getStartX()-cue.getEndX())/10,(cue.getStartY()-cue.getEndY())/10);
    }
    public void gameOver() {
        System.out.println("Win and Bye!");
        System.exit(0);
    }
}
