package Obstacles;

import level.LevelObject;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.PointConstants.*;

public class Point extends LevelObject {
    private boolean collected = false;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        width = POINT_WIDTH;
        height = POINT_HEIGHT;
        initHitbox();
    }

    public void update() {
        if (collected)
            collect();
    }

    private void collect() {
        width = 0;
        height = 0;
        hitbox.height = 0;
        hitbox.width = 0;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        if (!collected)
            g.fillRect(x, y, width, height);
    }

    public void initHitbox() {
        hitbox = new Rectangle(x, y, width, height);
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
