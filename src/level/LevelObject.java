package level;

import java.awt.*;

public abstract class LevelObject extends Rectangle {
    public Rectangle hitbox;

    public abstract void render(Graphics g);

    public void initHitbox() {
        hitbox = new Rectangle(x, y, width, height);
    }

    public void drawHitbox(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }
}
