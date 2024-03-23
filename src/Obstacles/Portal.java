package Obstacles;

import level.LevelObject;
import level.MovingObject;

import java.awt.*;

import static utilz.Constants.PortalConstants.*;

public class Portal extends LevelObject {
    private Portal pair;
    private final int direction;

    public Portal(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        width = PORTAL_WIDTH;
        height = PORTAL_HEIGHT;
        this.direction = direction;
        initHitbox();
    }

    public void teleport(MovingObject movingObject) {
        if (movingObject instanceof Projectile)
            movingObject.setY(pair.y + 40);
        else
            movingObject.setY(pair.y);

        if (pair.direction == 1)
            movingObject.setX(pair.x + 50);
        else
            movingObject.setX(pair.x - 10);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.magenta);
        g.fillOval(x, y, width, height);
//        drawHitbox(g);
    }

    @Override
    public void initHitbox() {
        hitbox = new Rectangle(x, y, width / 4, height);
        if (direction == 1) {
            hitbox.x = x;
        } else
            hitbox.x = x + (width * 3 / 4);
    }

    public void drawHitbox(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    public void setPair(Portal pair) {
        this.pair = pair;
    }
}
