package entities;

import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.ColorConstants.BLUE;
import static utilz.Constants.DragonConstants.*;

public class BlueDragon extends Enemy {
    public BlueDragon(int x, int y) {
        super(x, y, DRAGON_WIDTH, DRAGON_HEIGHT, DRAGON_SPEED, BLUE);
        loadAnimations();
        initDirection();
        initHitbox();
    }

    public void render(Graphics g) {
        g.drawImage(img, x + flipX, y, width * flipW, height, null);
//        drawHitbox(g);
    }

    private void loadAnimations() {
        img = LoadSave.getImage(LoadSave.BLUE_DRAGON_IMG);
    }

    public void initHitbox() {
        hitbox = new Rectangle2D.Float(x + DRAGON_HITBOX_X_OFFSET, y + DRAGON_HITBOX_Y_OFFSET, DRAGON_HITBOX_WIDTH, DRAGON_HITBOX_HEIGHT);
    }
}