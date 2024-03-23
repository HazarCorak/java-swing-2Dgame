package utilz;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static final String BLUE_PLAYER_ATLAS = "blue_player.png";
    public static final String RED_PLAYER_ATLAS = "red_player.png";
    public static final String MAGENTA_PLAYER_ATLAS = "magenta_player.png";
    public static final String PLATFORMS_IMG = "platforms.png";
    public static final String BLUE_DRAGON_IMG = "blue_dragon.png";
    public static final String RED_DRAGON_IMG = "red_dragon.png";
    public static final String ORANGE_DRAGON_IMG = "orange_dragon.png";
    public static final String MAGENTA_DRAGON_IMG = "magenta_dragon.png";

    public static BufferedImage getImage(String fileName) {
        BufferedImage img;
        try {
            InputStream is = LoadSave.class.getResourceAsStream(fileName);
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }
}
