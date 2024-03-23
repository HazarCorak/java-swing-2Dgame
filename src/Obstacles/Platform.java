package Obstacles;

import level.LevelObject;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Platform extends LevelObject {
    public Platform(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        initHitbox();
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }
}