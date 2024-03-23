package Obstacles;

import java.awt.*;

import static utilz.Constants.ColorConstants.*;

public class SpecialFloor extends Platform {
    final int color;

    public SpecialFloor(int x, int y, int width, int height, int color) {
        super(x, y, width, height);
        this.color = color;
        initHitbox();
        hitbox = new Rectangle(x, y, width, height);
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

    public int getColor() {
        return color;
    }
}
