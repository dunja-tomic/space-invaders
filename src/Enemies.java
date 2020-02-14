import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Enemies {
    private int NUM_ROWS = 5;
    private int NUM_COLS = 10;

    private int APP_WIDTH = 1280;

    private int DOWN = 15;

    private Group enemyGroup;
    private int totalEnemies;
    private boolean MOVING_RIGHT = true;

    Enemies() {
        totalEnemies = 50;
        enemyGroup = new Group();

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                if (row == 0) {
                    enemyGroup.getChildren().add(new Enemy(1, row, col).getImageView());
                } else if (row == 1 || row == 2) {
                    enemyGroup.getChildren().add(new Enemy(2, row, col).getImageView());
                } else {
                    enemyGroup.getChildren().add(new Enemy(3, row, col).getImageView());
                }
            }
        }
    }

    public Group getEnemies() {
        return this.enemyGroup;
    }

    public void removeEnemy(int i) {
        enemyGroup.getChildren().remove(i);
        totalEnemies--;
    }

    public void animateEnemies(double speed) {
        for (Node enemyImage: enemyGroup.getChildren()) {
            ImageView enemy = (ImageView)enemyImage;

            double x = enemy.getX();
            double y = enemy.getY();

            if (MOVING_RIGHT) {
                if (x + speed <= APP_WIDTH - 60) {
                    enemy.setX(x + speed);
                } else {
                    MOVING_RIGHT = false;
                    for (Node childNode: enemyGroup.getChildren()) {
                        ImageView child = (ImageView)childNode;
                        child.setY(child.getY() + DOWN);
                    }
                }
            } else {
                if (x - speed > 0) {
                    enemy.setX(x - speed);
                } else {
                    MOVING_RIGHT = true;
                    for (Node childNode: enemyGroup.getChildren()) {
                        ImageView child = (ImageView)childNode;
                        child.setY(child.getY() + DOWN);
                    }
                }
            }
        }
    }

    public boolean atTheBottom() {
        boolean atTheBottom = false;
        for (int i = 0; i < totalEnemies; i++) {
            ImageView enemy = (ImageView)enemyGroup.getChildren().get(i);
            double y = enemy.getY();

            if (y > 800) {
                atTheBottom = true;
                break;
            }
        }

        return atTheBottom;
    }

}
