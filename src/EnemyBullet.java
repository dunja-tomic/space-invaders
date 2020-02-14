import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EnemyBullet {
    ImageView bullet;

    EnemyBullet(int id, double enemy_x, double enemy_y) {
        Image bulletImage = new Image("images/bullet" + id + ".png");
        bullet = new ImageView(bulletImage);
        bullet.setX(enemy_x);
        bullet.setY(enemy_y);
    }

    ImageView getBullet() {
        return this.bullet;
    }
}
