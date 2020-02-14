import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GameScreen {
    private static final double APP_HEIGHT = 800.0;
    private static final int APP_WIDTH = 1280;

    private static final int DOWN = 5;
    private static final int SHOOT_RATE = 500;
    private static final int TURBO_SHOOT_RATE = 100;
    private static final double ENEMY_SPEED_LEVEL_1 = 5.0;
    private static final double ENEMY_SPEED_LEVEL_2 = 8.0;
    private static final double ENEMY_SPEED_LEVEL_3 = 12.0;

    private static boolean GAME_OVER = false;
    private static boolean STOPPED = true;
    private static boolean MOVING_RIGHT = false;
    private static double ENEMY_SPEED;
    private static int NUM_ALIENS;

    private AnimationTimer timer;
    private Scene gameScene;
    private SoundEffect explosionSound, invaderKilled, shootSound;

    private long last_fire_time = System.currentTimeMillis();
    private long enemy_last_fire_time = System.currentTimeMillis();

    private int SCORE;
    private int LIVES;
    private int LEVEL;

    private Group gameRoot;
    private Banner banner;
    private Ship ship;
    private Enemies enemies;

    ArrayList<ShipBullet> bullets;
    ArrayList<EnemyBullet> enemyBullets;

    GameScreen() {
        gameRoot = new Group();
        gameScene = new Scene(gameRoot, APP_WIDTH, APP_HEIGHT, Color.BLACK);
        gameRoot.requestFocus();
    }

    public void initialize(int level, int score) {
        GAME_OVER = false;
        NUM_ALIENS = 50;

        SCORE = score;
        LIVES = 3;
        LEVEL = level;

        setEnemySpeed();
        setSoundEffects();

        bullets = new ArrayList<>();
        enemyBullets = new ArrayList<>();

        banner = new Banner(SCORE, LIVES, LEVEL);
        ship = new Ship();
        enemies = new Enemies();

        gameRoot.getChildren().addAll(
                banner.getBanner(),
                ship.getShip(),
                enemies.getEnemies()
        );

        startGame();

        gameRoot.setOnKeyPressed(keyEvent -> {
            // Move the ship on the bottom
            if (STOPPED && isLeftKey(keyEvent.getCode())) {
                STOPPED = false;
                MOVING_RIGHT = false;
            } else if (STOPPED && isRightKey(keyEvent.getCode())) {
                STOPPED = false;
                MOVING_RIGHT = true;
            }

            // Shoot the bullets
            if (keyEvent.getCode() == KeyCode.SPACE) {
                shoot(false);
            } else if (keyEvent.getCode() == KeyCode.TAB) {
                shoot(true);
            }
        });

        gameRoot.setOnKeyReleased(keyEvent -> {
           // When the left/right keys are released only THEN stop moving
            if (isLeftKey(keyEvent.getCode()) || isRightKey(keyEvent.getCode())) {
                STOPPED = true;
            }
        });
    }

    private boolean isLeftKey(KeyCode key) {
        return (key == KeyCode.A || key == KeyCode.LEFT);
    }

    private boolean isRightKey(KeyCode key) {
        return (key == KeyCode.D || key == KeyCode.RIGHT);
    }

    private void shoot(boolean turbo) {
        int rate = turbo ? TURBO_SHOOT_RATE : SHOOT_RATE;

        // Can only shoot once every half second
        if (System.currentTimeMillis() - rate > last_fire_time) {
            last_fire_time = System.currentTimeMillis();

            ShipBullet shipBullet = new ShipBullet(ship.getPosition());
            bullets.add(shipBullet);
            gameRoot.getChildren().add(shipBullet.getBullet());

            shootSound.playClip();
        }
    }

    private void startGame() {

        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                animateEnemies();
                moveShip();

                animateShipBullet();
                checkIfHitEnemy();
                checkIfHitBullet();

                enemyShoot();
                animateEnemyShoot();
                checkIfHitShip();

                if (GAME_OVER) {
                    timer.stop();
                    gameOverPopup();
                }
            }
        };
        timer.start();
    }

    private void setEnemySpeed() {
        switch(LEVEL) {
            case 1:
                ENEMY_SPEED = ENEMY_SPEED_LEVEL_1;
                break;
            case 2:
                ENEMY_SPEED = ENEMY_SPEED_LEVEL_2;
                break;
            case 3:
                ENEMY_SPEED = ENEMY_SPEED_LEVEL_3;
                break;
        }
    }

    private void setSoundEffects() {
        explosionSound = new SoundEffect("sounds/explosion.wav");
        invaderKilled = new SoundEffect("sounds/invaderKilled.wav");
        shootSound = new SoundEffect("sounds/shoot.wav");
    }

    public Scene getScene() { return this.gameScene; }

    private void moveShip() {
        if (!STOPPED && MOVING_RIGHT) {
            ship.move_right();
        } else if (!STOPPED && !MOVING_RIGHT) {
            ship.move_left();
        }
    }

    private void respawnShip() {
        gameRoot.getChildren().remove(ship.getShip());
        ship = new Ship();
        gameRoot.getChildren().add(ship.getShip());
    }

    private void animateEnemies() {
        enemies.animateEnemies(ENEMY_SPEED);

        if (enemies.atTheBottom()) {
            GAME_OVER = true;
        }
    }

    private void enemyShoot() {
        if (System.currentTimeMillis() - (ENEMY_SPEED * 250 / (LEVEL * LEVEL)) > enemy_last_fire_time) {
            enemy_last_fire_time = System.currentTimeMillis();

            int random = (int) (Math.random() * NUM_ALIENS);
            int bullet_num = (int) (Math.random() * 3) + 1;

            double x = enemies.getEnemies().getChildren().get(random).getBoundsInParent().getCenterX();
            double y = enemies.getEnemies().getChildren().get(random).getBoundsInParent().getMaxY();

            // Make it shoot a bullet
            EnemyBullet new_bullet = new EnemyBullet(bullet_num, x, y);
            enemyBullets.add(new_bullet);
            gameRoot.getChildren().add(new_bullet.getBullet());
        }
    }

    private void animateShipBullet() {
        for (int i = 0; i < bullets.size(); i++) {
            ShipBullet bullet = bullets.get(i);
            double y = bullet.getBullet().getY();

            if (y < 0) {
                removeShipBullet(bullet);
            } else {
                bullet.getBullet().setY(y - DOWN);
            }
        }
    }

    private void animateEnemyShoot() {
        for (int i = 0; i < enemyBullets.size(); i++) {
            EnemyBullet bullet = enemyBullets.get(i);
            double y = bullet.getBullet().getY();
            if (y > 800) {
                removeEnemyBullet(bullet);
            } else {
                bullet.getBullet().setY(y + DOWN);
            }
        }
    }

    private boolean didCollide(Node node, Node other) {
        return node.getBoundsInParent().intersects(other.getBoundsInParent());
    }

    private void destroyEnemy(int i, ShipBullet bullet) {
        NUM_ALIENS--;
        enemies.removeEnemy(i);
        invaderKilled.playClip();

        bullets.remove(bullet);
        gameRoot.getChildren().remove(bullet.getBullet());

        // speed up the aliens!!
        ENEMY_SPEED += 0.25;

        if (NUM_ALIENS == 0) {
            GAME_OVER = true;
        }
    }

    private void destroyEnemyBullet(ShipBullet shipBullet, EnemyBullet enemyBullet) {
        gameRoot.getChildren().remove(enemyBullet.getBullet());
        gameRoot.getChildren().remove(shipBullet.getBullet());

        removeEnemyBullet(enemyBullet);
        removeShipBullet(shipBullet);

        explosionSound.playClip();
    }

    private void removeEnemyBullet(EnemyBullet bullet) {
        enemyBullets.remove(bullet);
        gameRoot.getChildren().remove(bullet.getBullet());
    }

    private void checkIfHitEnemy() {
        if (!enemies.getEnemies().getChildren().isEmpty()) {
            for (int i = 0; i < NUM_ALIENS; i++) {
                for (int j = 0; j < bullets.size(); j++) {
                    ShipBullet bullet = bullets.get(j);
                    System.out.println(NUM_ALIENS + " and i is: " + i);
                    Node enemy = enemies.getEnemies().getChildren().get(i);
                    if (didCollide(bullet.getBullet(), enemy)) {
                        destroyEnemy(i, bullet);
                        updateScore(i);
                    }
                }
            }
        }
    }

    private void removeShipBullet(ShipBullet bullet) {
        bullets.remove(bullet);
        gameRoot.getChildren().remove(bullet.getBullet());
    }

    private void checkIfHitBullet() {
        for (int i = 0; i < enemyBullets.size(); i++) {
            EnemyBullet enemyBullet = enemyBullets.get(i);

            for (int j = 0; j < bullets.size(); j++) {
                ShipBullet shipBullet = bullets.get(j);
                if (didCollide(shipBullet.getBullet(), enemyBullet.getBullet())) {
                    destroyEnemyBullet(shipBullet, enemyBullet);
                    updateScore(50);
                }
            }
        }
    }

    private void checkIfHitShip() {
        for (int i = 0; i < enemyBullets.size(); i++) {
            EnemyBullet bullet = enemyBullets.get(i);
            if (didCollide(bullet.getBullet(), ship.getShip())) {
                // Play blinking animation
                explosionSound.playClip();

                // Delete the current ship and spawn a new one
                respawnShip();

                // Decrease lives
                loseALife();
                updateScore(60);

                // Remove enemy bullet from the game
                removeEnemyBullet(bullet);
            }
        }

        // If an enemy collides with the ship, lose a life
        for (int i = 0; i < NUM_ALIENS; i++) {
            Node enemy = enemies.getEnemies().getChildren().get(i);
            if (didCollide(ship.getShip(), enemy)) {
                explosionSound.playClip();
                respawnShip();
                loseALife();
                updateScore(60);
            }
        }
    }


    private void updateScore(int i) {
        int hitScore;

        // Dole out points based on the alien's row
        switch(i/10) {
            case 0:
                hitScore = 30;
                break;
            case 1:
            case 2:
                hitScore = 20;
                break;
            case 3:
            case 4:
                hitScore = 10;
                break;

            // Case 5 if hit an enemy bullet
            case 5:
                hitScore = 25;
                break;

            // Lose score if you lose a life
            case 6:
                hitScore = -10;
                break;
            default:
                hitScore = 0;
        }
        SCORE += hitScore;
        banner.updateBanner(SCORE, LIVES, LEVEL);
    }

    private void loseALife() {
        LIVES--;
        banner.updateBanner(SCORE, LIVES, LEVEL);

        if (LIVES == 0) {
            GAME_OVER = true;
        }
    }

    public boolean isGameOver() { return GAME_OVER; }

    private void gameOverPopup() {
        boolean winner = (NUM_ALIENS == 0);

        LEVEL = (NUM_ALIENS == 0) ? LEVEL + 1 : LEVEL;
        GameOver gameOver = new GameOver(SCORE, winner, LEVEL);

        gameRoot.getChildren().add(gameOver.getGameOver());
    }

    public void restart() {
        gameRoot.getChildren().clear();

        if (LEVEL == 4) {
            LEVEL = 1;
        }

        initialize(LEVEL, SCORE);
    }

}
