import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShipBullet {
    private ImageView bullet;

    ShipBullet(double ship_x) {
        double start_position = ship_x + 55;

        Image bulletImage = new Image("images/player_bullet.png");
        bullet = new ImageView(bulletImage);
        bullet.setX(start_position);
        bullet.setY(725);
    }

    ImageView getBullet() {
        return this.bullet;
    }
}
