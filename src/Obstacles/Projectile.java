package Obstacles;

import entities.Enemy;
import entities.Entity;
import entities.Player;
import level.LevelObject;
import level.MovingObject;

import java.awt.*;
import java.util.ArrayList;

import static main.Game.*;
import static utilz.Constants.ColorConstants.BLUE;
import static utilz.Constants.ColorConstants.RED;
import static utilz.Constants.DirectionsConstants.RIGHT;
import static utilz.Constants.ProjectileConstants.PROJECTILE_HEIGHT;
import static utilz.Constants.ProjectileConstants.PROJECTILE_WIDTH;

public class Projectile extends MovingObject {
    private int color;
    private final int speed = 5;
    private final int direction;

    public Projectile(Player player) {
        x = (int) (player.getHitbox().x + (player.width / 2));
        y = (int) (player.getHitbox().y + (player.height / 3));
        width = PROJECTILE_WIDTH;
        height = PROJECTILE_HEIGHT;
        this.direction = player.getDirection();
        color = player.getColor();
        initHitbox();
    }

    public void update() {
        updatePos();
        updateHitbox();
        portalInteraction();
    }

    public void render(Graphics g) {
        switch (color) {
            case BLUE:
                g.setColor(Color.BLUE);
                break;
            case RED:
                g.setColor(Color.RED);
                break;
        }
        g.fillRect(x, y, width, height);
    }

    public void updatePos() {
        if (!hitEnemy(ENEMIES) && !hitPlatform(PLATFORMS)) {
            if (direction == RIGHT) {
                x += speed;
            } else
                x -= speed;
        } else
            hit();
    }

    @Override
    public void portalInteraction() {
        for (Portal portal : PORTALS)
            if (hitbox.intersects(portal.hitbox))
                portal.teleport(this);
    }

    private boolean hitPlatform(ArrayList<Platform> platforms) {
        for (Platform platform : platforms)
            if (this.hitbox.intersects(platform.getHitbox()))
                return true;
        return false;
    }

    private boolean hitEnemy(ArrayList<Enemy> enemies) {
        for (Enemy enemy : enemies)
            if (enemy != null) {
                if (this.hitbox.intersects(enemy.getHitbox())) {
                    if (enemy.getColor() == color) {
                        enemy.setHealth(enemy.getHealth() + 1);
                    } else if (enemy.getColor() == -color)
                        enemy.setHealth(enemy.getHealth() - 1);

                    if (enemy.getHealth() <= 0)
                        enemy.die();
                    return true;
                }
            }
        return false;
    }

    public void hit() {
        width = 0;
        height = 0;
        hitbox.width = 0;
        hitbox.height = 0;
    }

    void updateHitbox() {
        hitbox.x = x;
        hitbox.y = y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
        hitbox.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
        hitbox.y = y;
    }
}