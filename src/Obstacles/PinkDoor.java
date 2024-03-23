package Obstacles;

import entities.BlueDragon;
import entities.MagentaDragon;
import entities.OrangeDragon;
import entities.RedDragon;

import java.awt.*;
import java.util.Random;

import static main.Game.ENEMIES;
import static utilz.Constants.ColorConstants.*;
import static utilz.Constants.PinkDoorConstants.*;

public class PinkDoor extends Platform {
    public PinkDoor() {
        super(PINK_DOOR_X, PINK_DOOR_Y, PINK_DOOR_WIDTH, PINK_DOOR_HEIGHT);
        initHitbox();
    }

    private void spawnEnemy() {
        Random random = new Random();
        int enemyColor = random.nextInt(3) - 1;
        switch (enemyColor) {
            case BLUE -> ENEMIES.add(new BlueDragon(x + 60, y));
            case RED -> ENEMIES.add(new RedDragon(x + 60, y));
            case ORANGE -> ENEMIES.add(new OrangeDragon(x + 60, y));
            case MAGENTA -> ENEMIES.add(new MagentaDragon(x + 60, y));
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.PINK);
        g.fillRect(x, y, width, height);
    }
}
