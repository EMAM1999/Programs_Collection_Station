package style;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game {

    private static final URL GAME_URL = Game.class.getResource("FXMLDocument.fxml");
    private final Stage stage;
    private static URL backRootURL;
    private static Game instance;


    private Game(Stage stage , URL backRootURL) throws Exception {
        this.stage = stage;
        Game.backRootURL = backRootURL;
    }


    public static Game getInstance(Stage stage , URL backRootURL) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(GAME_URL)));
        return instance == null
                ? (instance = new Game(stage , backRootURL))
                : instance;
    }


    public URL getBackRootURL() {
        return backRootURL;
    }


    public static void exit() {
        instance.stage.close();
    }


    public static void minimize() {
        instance.stage.setIconified(true);
    }


    public static void back() throws IOException {
        instance.getStage().setScene(new Scene(FXMLLoader.load(backRootURL)));
        instance.getStage().show();
    }


    public Stage getStage() {
        return stage;
    }

}
