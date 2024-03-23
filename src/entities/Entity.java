package entities;

import Obstacles.Portal;
import Obstacles.Platform;
import level.MovingObject;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static main.Game.PLATFORMS;
import static main.Game.PORTALS;
import static utilz.Constants.DirectionsConstants.LEFT;
import static utilz.Constants.DirectionsConstants.RIGHT;
import static utilz.Constants.PlayerConstants.COLLUSION_OFFSET;

public abstract class Entity extends MovingObject {
    Rectangle2D.Float hitbox;
    int color;
    float speed;
    boolean inAir, left, right;
    int direction = -1;
    boolean alive = true;
    float xSpeed = 0f;
    float ySpeed = 0f;
    final float gravity = 0.06f;
    int flipX = 0;
    int flipW = 1;

    public Entity(int x, int y, int width, int height, float speed, int color) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.speed = speed;
        this.color = color;
        initHitbox();
    }

    public abstract void die();

    void updateLookingDirInitRight() {
        if (direction == LEFT) {
            flipX = width;
            flipW = -1;
        }
        else {
            flipX = 0;
            flipW = 1;
        }
    }

    void updateLookingDirInitLeft() {
        if (direction == RIGHT) {
            flipX = width;
            flipW = -1;
        } else {
            flipX = 0;
            flipW = 1;
        }
    }

//    public boolean intersects(Rectangle hb) {
//        float hbX = hb.x + (hb.width / 3);
//        float hbY = hb.y + (hb.height / 3);
//        float hbW = hb.width / 3;
//        float hbH = hb.height / 3;
//
//        return  ((hitbox.x + hitbox.width > hbX && hitbox.x < hbX + hbW && hitbox.y + hitbox.height > hbY && hitbox.y < hbY + hbH)
//                || (hitbox.x < hbX + hbW && hitbox.x + hitbox.width > hbX && hitbox.y + hitbox.height > hbY && hitbox.y < hbY + hbH)
//                || (hitbox.y + hitbox.height > hbY && hitbox.y < hbY + hbH && hitbox.x + hitbox.width > hbX && hitbox.x < hbX + hbW)
//                || (hitbox.y < hbY + hbH && hitbox.y + hitbox.height > hbY && hitbox.x + hitbox.width > hbX && hitbox.x < hbX + hbW));
//    }

    boolean canMoveHereHorizontally(float x, float y, float w, float h, ArrayList<Platform> platforms) {
        if (x < 0 || x + w > Game.GAME_WIDTH)
            return false;

        for (Rectangle i : platforms)
            if ((x + w > i.x && x + w < i.x + i.width && y + h > i.y + COLLUSION_OFFSET && y < i.y + i.height - COLLUSION_OFFSET)
                    || (x < i.x + i.width && x > i.x && y + h > i.y + COLLUSION_OFFSET && y < i.y + i.height - COLLUSION_OFFSET))
                return false;
        return true;
    }

    boolean canMoveHereVertically(float x, float y, float w, float h, ArrayList<Platform> platforms) {
        if (y < 0 || y + h > Game.GAME_HEIGHT)
            return false;

        for (Rectangle i : platforms)
            if ((y + h > i.y + COLLUSION_OFFSET && y + h < i.y + i.height && x + w > i.x && x < i.x + i.width)
                    || (y < i.y + i.height && y > i.y && x + w > i.x && x < i.x + i.width))
                return false;
        return true;
    }

    boolean isInAir() {
        for (Rectangle i : PLATFORMS)
            if (hitbox.y + hitbox.height > i.y && hitbox.x + hitbox.width > i.x && hitbox.x < i.x + i.width && hitbox.y < i.y + i.height)
                return false;
        return true;
    }

    public void portalInteraction() {
        for (Portal portal : PORTALS)
            if (hitbox.intersects(portal.hitbox))
                portal.teleport(this);
    }

    public void drawHitbox(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }


    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public int getDirection() {
        return direction;
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

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}