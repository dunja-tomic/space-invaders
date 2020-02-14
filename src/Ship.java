import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ship {
    private double SCREEN_WIDTH = 1280;
    private double ship_x = SCREEN_WIDTH/2 - 62;
    private double max_x = SCREEN_WIDTH - 124;

    private ImageView ship;

    Ship() {
        Image shipImage = new Image("images/player.png");
        ship = new ImageView(shipImage);
        ship.setX(ship_x);
        ship.setY(720);
    }

    public ImageView getShip() {
        return this.ship;
    }

    public void move_right() {
        if (ship_x < max_x) {
            ship_x += 5;
            ship.setX(ship_x);
        }
    }

    public void move_left() {
        if (ship_x > 0) {
            ship_x -= 5;
            ship.setX(ship_x);
        }
    }

    public double getPosition() {
        return this.ship_x;
    }
}
