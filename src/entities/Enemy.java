package entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static main.Game.ENEMIES;
import static main.Game.PLATFORMS;
import static utilz.Constants.DirectionsConstants.*;

public abstract class Enemy extends Entity {
    int health = 3;
    BufferedImage img;

    public Enemy(int x, int y, int width, int height, float speed, int color) {
        super(x, y, width, height, speed, color);
    }

    public void update() {
        updatePos();
        updateLookingDirInitLeft();
        checkInteractions();
    }

    public void render(Graphics g) {
        g.drawImage(img, x + flipX, y - 5, width * flipW, height, null);
//            drawHitbox(g);
    }

    public void updatePos() {
        if (direction == RIGHT)
            xSpeed = speed;
        else
            xSpeed = -speed;

        if (!canMoveHereHorizontally(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, PLATFORMS)) {
            if (direction == RIGHT) {
                direction = LEFT;
            } else
                direction = RIGHT;
        }
        updateXPos(xSpeed);

        inAir = isInAir();
        if (inAir) {
            updateYPos(ySpeed);
            ySpeed += gravity;
        } else
            ySpeed = 0;
    }

    void updateYPos(float ySpeed) {
        if (canMoveHereVertically(hitbox.x, hitbox.y - ySpeed, hitbox.width, hitbox.height, PLATFORMS)) {
            hitbox.y += ySpeed;
            y = (int) hitbox.y;
        }
    }

    void updateXPos(float xSpeed) {
        if (canMoveHereHorizontally(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, PLATFORMS)) {
            hitbox.x += xSpeed;
            x = (int) hitbox.x;
        }
    }

    public void die() {
//        ENEMIES.set(ENEMIES.indexOf(this), null);
        ENEMIES.remove(this);
    }

    void checkInteractions() {
        portalInteraction();
    }

    void initDirection() {
        Random r = new Random();
        direction = r.nextInt(2);
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
