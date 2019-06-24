package Duelyst;

import Duelyst.View.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage basicStage;

    @Override
    public void start(Stage stage) throws Exception {
        basicStage = stage;

        View.makeLoginScene();
        basicStage.setFullScreen(true);
        basicStage.setResizable(false);
        basicStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
