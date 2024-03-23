package utilz;

public class Constants {
    public static class PlayerConstants {
        public static final int PLAYER_WIDTH = 80;
        public static final int PLAYER_HEIGHT = 80;
        public static final float PLAYER_SPEED = 0.02f;
        public static final int PLAYER_HITBOX_WIDTH = 50;
        public static final int PLAYER_HITBOX_HEIGHT = 60;
        public static final int PLAYER_HITBOX_X_OFFSET = 9;
        public static final int PLAYER_HITBOX_Y_OFFSET = 18;
        public static final int COLLUSION_OFFSET = 10;
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int RUNNING_FIRING = 2;
        public static final int FIRING = 3;
        public static final int AIR = 4;
        public static final int AIR_FIRING = 5;
        public static final int SLIDING = 6;
        public static final int DEAD = 7;

        public static int getSpriteAmount(int player_action) {
            return switch (player_action) {
                case IDLE -> 5;
                case RUNNING, RUNNING_FIRING -> 4;
                case FIRING, AIR, AIR_FIRING, SLIDING, DEAD -> 1;
                default -> 0;
            };
        }

        public static int getAniSpeed(int player_action) {
            return switch (player_action) {
                case IDLE, FIRING, AIR_FIRING, SLIDING -> 100;
                case RUNNING, RUNNING_FIRING -> 30;
                default -> 60;
            };
        }
    }

    public static class DragonConstants {
        public static final int DRAGON_WIDTH = 65;
        public static final int DRAGON_HEIGHT = 70;
        public static final float DRAGON_SPEED = 1f;
        public static final float DRAGON_HITBOX_WIDTH = 60f;
        public static final float DRAGON_HITBOX_HEIGHT = 60f;
        public static final float DRAGON_HITBOX_X_OFFSET = 20f;
        public static final float DRAGON_HITBOX_Y_OFFSET = 20f;
    }

    public static class ProjectileConstants {
        public static final int PROJECTILE_WIDTH = 7;
        public static final int PROJECTILE_HEIGHT = 5;
    }

    public static class DirectionsConstants {
        public static final int LEFT = 0;
        public static final int RIGHT = 1;
    }

    public static class PointConstants {
        public static final int POINT_WIDTH = 10;
        public static final int POINT_HEIGHT = 20;
    }

    public static class PortalConstants {
        public static final int PORTAL_WIDTH = 40;
        public static final int PORTAL_HEIGHT = 100;
    }

    public static class ColorConstants {
        public static final int BLUE = -1;
        public static final int MAGENTA = 0;
        public static final int RED = 1;
        public static final int ORANGE = 2;
    }

    public static class PinkDoorConstants {
        public static final int PINK_DOOR_X = 960;
        public static final int PINK_DOOR_Y = 100;
        public static final int PINK_DOOR_WIDTH = 70;
        public static final int PINK_DOOR_HEIGHT = 70;
    }
}
