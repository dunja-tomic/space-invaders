import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy {
    private ImageView enemyImageView;

    public Enemy(int id, double col, double row) {
        String imageUrl = "images/enemy" + id + ".png";
        Image enemyImage = new Image(imageUrl, 60.0, 60.0, true, true);
        enemyImageView = new ImageView(enemyImage);
        enemyImageView.setX(row * 70 + 100);
        enemyImageView.setY(col * 70 + 50);
    }

    public ImageView getImageView() {
        return this.enemyImageView;
    }
}
