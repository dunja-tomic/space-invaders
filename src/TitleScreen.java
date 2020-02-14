import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class TitleScreen {
    private Scene homeScreen;

    TitleScreen() {
        Group homeScreenRoot = new Group();

        Font Lucida36 = new Font("Lucida Console", 36);
        Font Lucida14 = new Font("Lucida Console", 14);

        Image logo = new Image("images/logo.png");
        ImageView logoView = new ImageView(logo);

        Label instruction1 = new Label("Instructions");
        Label instruction2 = new Label("Press ENTER to start");
        Label instruction3 = new Label("A or ◀, D or ▶ - Move ship left or right");
        Label instruction4 = new Label("SPACE - Fire!");
        Label instruction5 = new Label("Q - Quit game");
        Label instruction6 = new Label("1, 2, or 3 - Start game at specific level");

        Label credit = new Label("Implemented by Dunja Tomic for CS349, University of Waterloo, W20");

        instruction1.setFont(Lucida36);
        instruction2.setFont(Lucida14);
        instruction3.setFont(Lucida14);
        instruction4.setFont(Lucida14);
        instruction5.setFont(Lucida14);
        instruction6.setFont(Lucida14);
        credit.setFont(Lucida14);

        VBox instructions = new VBox(10);
        instructions.getChildren().addAll(
                instruction1,
                instruction2,
                instruction3,
                instruction4,
                instruction5,
                instruction6
        );
        instructions.setAlignment(Pos.CENTER);

        VBox text = new VBox(250, instructions, credit);
        text.setAlignment(Pos.CENTER);

        VBox box = new VBox(100);
        box.getChildren().addAll(logoView, text);
        box.setPrefSize(1280, 800);
        box.setAlignment(Pos.CENTER);

        homeScreenRoot.getChildren().add(box);
        homeScreen = new Scene(homeScreenRoot, 1280, 800);
    }

    public Scene getScene() {
        return this.homeScreen;
    }
}
