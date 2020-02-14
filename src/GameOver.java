import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class GameOver {
    StackPane gameOver;

    GameOver(int score, boolean winner, int level) {
        Rectangle r = new Rectangle();
        r.setWidth(800);
        r.setHeight(400);
        r.setArcWidth(20);
        r.setArcHeight(20);
        r.setFill(Color.LIGHTGREY);

        Font Lucida42 = new Font("Lucida Console", 42);
        Font Lucida22 = new Font("Lucida Console", 22);
        Font Lucida20 = new Font("Lucida Console", 20);

        gameOver = new StackPane();
        gameOver.getChildren().add(r);

        String title = winner ? "You Won!" : "Game Over";
        Label instruction1 = new Label(title);
        Label instruction2 = new Label("Your score: " + score);

        String next;
        if (winner && level == 4) {
            next = "Press ENTER to start again from level 1";
        } else {
            next = winner ? "Press ENTER to advance to level " + level : "Press ENTER to try level " + level + " again";
        }
        Label instruction3 = new Label(next);

        Label instruction4 = new Label("Or press Q to quit.");

        instruction1.setFont(Lucida42);
        instruction2.setFont(Lucida22);
        instruction3.setFont(Lucida20);
        instruction4.setFont(Lucida20);

        VBox instructions = new VBox(10);
        instructions.getChildren().addAll(
                instruction1,
                instruction2,
                instruction3,
                instruction4
        );
        instructions.setAlignment(Pos.CENTER);
        gameOver.getChildren().add(instructions);
        gameOver.setLayoutX(250);
        gameOver.setLayoutY(200);
    }

    public StackPane getGameOver() {
        return this.gameOver;
    }
}
