import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


public class SpaceInvaders extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Space Invaders");
        stage.setResizable(false);

        Scene titleScreen = new TitleScreen().getScene();

        GameScreen game = new GameScreen();
        Scene gameScreen = game.getScene();

        titleScreen.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.DIGIT1) {
                game.initialize(1, 0);
                stage.setScene(gameScreen);
            } else if (keyEvent.getCode() == KeyCode.DIGIT2) {
                game.initialize(2, 0);
                stage.setScene(gameScreen);
            } else if (keyEvent.getCode() == KeyCode.DIGIT3) {
                game.initialize(3, 0);
                stage.setScene(gameScreen);
            }
        });

        gameScreen.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.Q) {
                System.exit(0);
            } else if (keyEvent.getCode() == KeyCode.ENTER && game.isGameOver()) {
                game.restart();
            }
        });

        stage.setScene(titleScreen);
        stage.show();
    }
}