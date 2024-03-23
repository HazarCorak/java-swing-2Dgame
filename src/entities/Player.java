package entities;

import Obstacles.Platform;
import Obstacles.Point;
import Obstacles.Projectile;
import Obstacles.SpecialFloor;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static main.Game.*;
import static utilz.Constants.ColorConstants.*;
import static utilz.Constants.DirectionsConstants.*;
import static utilz.Constants.PlayerConstants.*;

public class Player extends Entity {
    private BufferedImage[][] blue_player_animations;
    private BufferedImage[][] red_player_animations;
    private BufferedImage[][] magenta_player_animations;
    private int aniTick, aniIndex;
    private int playerAction = IDLE;
    private boolean running = false, firing = false, jumping = false, fired = false, sliding = false, slid = false;

    private final float friction = 0.01f;
    private final float slideSpeed = 2.5f;
    private final float jumpSpeed = -4;
    private final float fallSpeedAfterHittingTop = 1;

    public Player(int x, int y) {
        super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_SPEED, BLUE);
        loadAnimations();
        initHitbox();
    }

    public void update() {
        updateAnimationTick();
        updatePos();
        checkActions();
        checkInteractions();
    }

    public void render(Graphics g) {
        switch (color) {
            case BLUE:
                g.drawImage(blue_player_animations[playerAction][aniIndex], (x - PLAYER_HITBOX_X_OFFSET) + flipX, (y - PLAYER_HITBOX_Y_OFFSET), width * flipW, height, null);
                break;
            case RED:
                g.drawImage(red_player_animations[playerAction][aniIndex], (x - PLAYER_HITBOX_X_OFFSET) + flipX, (y - PLAYER_HITBOX_Y_OFFSET), width * flipW, height, null);
                break;
            case MAGENTA:
                g.drawImage(magenta_player_animations[playerAction][aniIndex], (x - PLAYER_HITBOX_X_OFFSET) + flipX, (y - PLAYER_HITBOX_Y_OFFSET), width * flipW, height, null);
                break;
        }
        //        drawHitbox(g);
    }

    public void updatePos() {
        updateLookingDirInitRight();
        updateDir();
        running = false;
        inAir = isInAir();

        if (alive) {
            if (!sliding) {
                if (right)
                    if (xSpeed < 1.5)
                        xSpeed += speed;
                if (left)
                    if (xSpeed > -1.5)
                        xSpeed -= speed;
            }

            if (jumping)
                jump();
        }

        if (inAir) {
            if (hittingTop()) {
                hitbox.y += COLLUSION_OFFSET;
                ySpeed = fallSpeedAfterHittingTop;
            }
            updateYPos(ySpeed);
            ySpeed += gravity;
        } else if (alive) {
            ySpeed = 0;
            running = true;
            if (left) {
                if (xSpeed > 0.1)
                    xSpeed = 0;
                xSpeed += friction;
            } else if (right) {
                if (xSpeed < -0.1)
                    xSpeed = 0;
                xSpeed -= friction;
            } else {
                running = false;
                if (xSpeed > 0)
                    xSpeed -= friction;
                else if (xSpeed < 0)
                    xSpeed += friction;
            }
        }
        updateXPos(xSpeed);
    }

    private void updateDir() {
        if (left)
            direction = LEFT;
        if (right)
            direction = RIGHT;
    }

    void updateYPos(float ySpeed) {
        if (canMoveHereVertically(hitbox.x, hitbox.y - ySpeed, hitbox.width, hitbox.height, PLATFORMS)) {
            hitbox.y += (int) ySpeed;
            y = (int) hitbox.y;
        }
    }

    void updateXPos(float xSpeed) {
        if (canMoveHereHorizontally(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, PLATFORMS)) {
            hitbox.x += xSpeed;
            x = (int) hitbox.x;
        }
    }

    private void jump() {
        if (!inAir) {
            inAir = true;
            hitbox.y -= COLLUSION_OFFSET;
            ySpeed = jumpSpeed;
            jumping = false;
        }
    }

    private void slide() {
        if (!inAir) {
            if (right)
                xSpeed = slideSpeed;
            else if (left)
                xSpeed = -slideSpeed;
            slid = true;
        }
    }

    private void checkSliding() {
        if (sliding) {
            if (!slid)
                slide();
        } else
            slid = false;
    }

    private void fire() {
        if (color != MAGENTA)
            PROJECTILES.add(new Projectile(this));
        fired = true;
    }

    private void checkFiring() {
        if (firing) {
            if (!fired)
                fire();
        } else
            fired = false;
    }

    boolean hittingTop() {
        for (Platform p : PLATFORMS)
            if (hitbox.y < p.y + p.height + COLLUSION_OFFSET && hitbox.y + hitbox.height > p.y + p.height && hitbox.x + hitbox.width > p.x && hitbox.x < p.x + p.width)
                return true;
        return false;
    }

    private void checkInteractions() {
        enemyInteraction();
        pointInteraction();
        portalInteraction();
        floorInteraction();
    }

    private void checkActions() {
        checkFiring();
        checkSliding();
    }

    private void enemyInteraction() {
        for (Enemy enemy : ENEMIES)
            if (enemy != null)
                if (hitbox.intersects(enemy.hitbox)) {
//                    alive = false;
                }
    }

    private void pointInteraction() {
        for (Point point : POINTS)
            if (hitbox.intersects(point.getHitbox())) {
                point.setCollected(true);
//                score++
            }
    }

    private void floorInteraction() {
        for (SpecialFloor floor : FLOORS)
            if (hitbox.intersects(floor.getHitbox()))
                if (color != floor.getColor()) {
//                    alive = false;
                }
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= getAniSpeed(playerAction)) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getSpriteAmount(playerAction)) {
                aniIndex = 0;
                firing = false;
                sliding = false;
            }
        }
        setAnimation();
    }

    private void setAnimation() {
        int startAni = playerAction;

        if (!alive) {
            playerAction = DEAD;
        } else if (firing) {
            playerAction = FIRING;
            if (running)
                playerAction = RUNNING_FIRING;
            else if (inAir)
                playerAction = AIR_FIRING;
        } else if (inAir) {
            playerAction = AIR;
        } else if (sliding) {
            playerAction = SLIDING;
        } else if (running) {
            playerAction = RUNNING;
        } else
            playerAction = IDLE;

        if (startAni != playerAction)
            resetAniTick();
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void loadAnimations() {
        BufferedImage blue_player = LoadSave.getImage(LoadSave.BLUE_PLAYER_ATLAS);
        BufferedImage red_player = LoadSave.getImage(LoadSave.RED_PLAYER_ATLAS);
//        BufferedImage magenta_player = LoadSave.getImage(LoadSave.MAGENTA_PLAYER_ATLAS);
        blue_player_animations = new BufferedImage[8][5];
        red_player_animations = new BufferedImage[8][5];
        magenta_player_animations = new BufferedImage[8][5];
        for (int i = 0; i < blue_player_animations.length; i++)
            for (int j = 0; j < blue_player_animations[0].length; j++) {
                blue_player_animations[i][j] = blue_player.getSubimage(j * 117, i * 115, 117, 115);
                red_player_animations[i][j] = red_player.getSubimage(j * 117, i * 115, 117, 115);
//                magenta_player_animations[i][j] = magenta_player.getSubimage(j * 117, i * 115, 117, 115);
            }
    }

    @Override
    public void initHitbox() {
        hitbox = new Rectangle2D.Float(x, y, PLAYER_HITBOX_WIDTH, PLAYER_HITBOX_HEIGHT);
    }

    public void resetDir() {
        right = false;
        left = false;
    }

    public void setFiring(boolean firing) {
        this.firing = firing;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void setSliding(boolean sliding) {
        this.sliding = sliding;
    }

    @Override
    public void die() {

    }
}