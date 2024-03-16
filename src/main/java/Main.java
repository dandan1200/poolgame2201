import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.*;

public class Main extends Application {
    private static ConfigReader cr;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;
    public static void main(String[] args) {
        String configPath;
        if (args.length > 0) {
            configPath = args[1];
        } else {
            configPath = "src/main/resources/config.json";
        }

        cr = new ConfigReader(configPath);

        launch(args);
    }

    public void start(Stage primaryStage){
        GameWindow window = new GameWindow((Table) cr.getTable());
        window.run();
        primaryStage.setTitle("Pool Game");
        primaryStage.setScene(window.getScene());
        primaryStage.show();
        window.run();


    }
}
