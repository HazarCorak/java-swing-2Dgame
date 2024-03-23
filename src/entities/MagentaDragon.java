package entities;

import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.ColorConstants.RED;
import static utilz.Constants.DragonConstants.*;

public class MagentaDragon extends Enemy {
    public MagentaDragon(int x, int y) {
        super(x, y, DRAGON_WIDTH, DRAGON_HEIGHT, DRAGON_SPEED, RED);
        loadAnimations();
        initDirection();
        initHitbox();
    }

    public void render(Graphics g) {
        g.drawImage(img, x + flipX, y, width * flipW, height, null);
//        drawHitbox(g);
    }

    private void loadAnimations() {
        img = LoadSave.getImage(LoadSave.MAGENTA_DRAGON_IMG);
    }

    public void initHitbox() {
        hitbox = new Rectangle2D.Float(x + DRAGON_HITBOX_X_OFFSET, y + DRAGON_HITBOX_Y_OFFSET, DRAGON_HITBOX_WIDTH, DRAGON_HITBOX_HEIGHT);
    }
}