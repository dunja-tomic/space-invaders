import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Banner {
    HBox banner;
    Label score_label;
    Label lives_label;
    Label level_label;

    Banner(int score, int lives, int level) {
        score_label = new Label("Score: " + score);
        lives_label = new Label("Lives: " + lives);
        level_label = new Label("Level: "  + level);

        score_label.setFont(new Font("Lucida Console", 22));
        lives_label.setFont(new Font("Lucida Console", 22));
        level_label.setFont(new Font("Lucida Console", 22));

        score_label.setTextFill(Color.WHITE);
        lives_label.setTextFill(Color.WHITE);
        level_label.setTextFill(Color.WHITE);

        HBox right_banner = new HBox(30);
        right_banner.getChildren().addAll(lives_label, level_label);

        banner = new HBox(600);
        banner.setPrefWidth(1280);
        banner.setAlignment(Pos.TOP_CENTER);
        banner.getChildren().addAll(score_label, right_banner);
    }

    public HBox getBanner() {
        return this.banner;
    }

    public void updateBanner(int score, int lives, int level) {
        score_label.setText("Score: " + score);
        lives_label.setText("Lives: " + lives);
        level_label.setText("Level: " + level);
    }
}
