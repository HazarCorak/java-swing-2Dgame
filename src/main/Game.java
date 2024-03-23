package main;

import Obstacles.*;
import Obstacles.Point;
import entities.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static utilz.Constants.ColorConstants.*;
import static utilz.Constants.DirectionsConstants.*;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 75;
    private final int UPS_SET = 200;
    private Player player;
    private PinkDoor pinkDoor;
    public static ArrayList<Enemy> ENEMIES = new ArrayList<>();
    public static ArrayList<Projectile> PROJECTILES = new ArrayList<>();
    public static ArrayList<Platform> PLATFORMS = new ArrayList<>();
    public static ArrayList<Point> POINTS = new ArrayList<>();
    public static ArrayList<Portal> PORTALS = new ArrayList<>();
    public static ArrayList<SpecialFloor> FLOORS = new ArrayList<>();

    public static final int TILES_DEFAULT_SIZE = 32;
    public static final float SCALE = 1.5f;
    public static final float TILES_IN_WIDTH = 40.0f;
    public static final float TILES_IN_HEIGHT = 21f;
    public static final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public static final int GAME_WIDTH = (int) (TILES_SIZE * TILES_IN_WIDTH);
    public static final int GAME_HEIGHT = (int) (TILES_SIZE * TILES_IN_HEIGHT);

    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void initClasses() {
        player = new Player(25, 600);
        pinkDoor = new PinkDoor();
        PLATFORMS = new ArrayList<>();
        createPlatforms();
        ENEMIES = new ArrayList<>();
        createEnemies();
        PROJECTILES = new ArrayList<>();
        POINTS = new ArrayList<>();
        createPoints();
        PORTALS = new ArrayList<>();
        createPortals();
        FLOORS = new ArrayList<>();
        createSpecialFloors();
    }

    private void createPlatforms() {
        PLATFORMS.add(new Platform(0, 800, 1920, 20));
//        PLATFORMS.add(new Platform(600, 725, 100, 75));
        PLATFORMS.add(new Platform(775, 625, 100, 25));
        PLATFORMS.add(new Platform(900, 170, 200, 25));
    }

    private void createEnemies() {
        ENEMIES.add(new BlueDragon(650, 600));
        ENEMIES.add(new RedDragon(1000, 600));
        ENEMIES.add(new OrangeDragon(1500, 600));
        ENEMIES.add(new MagentaDragon(1800, 600));
    }

    private void createPoints() {
        POINTS.add(new Point(650, 675));
    }

    private void createPortals() {
        Portal portal1 = new Portal(1820, 700, LEFT);
        Portal portal2 = new Portal(50, 200, RIGHT);
        portal1.setPair(portal2);
        portal2.setPair(portal1);
        PORTALS.add(portal1);
        PORTALS.add(portal2);
    }

    private void createSpecialFloors() {
        FLOORS.add(new SpecialFloor(900, 800, 200, 10, BLUE));
        FLOORS.add(new SpecialFloor(400, 800, 200, 10, RED));
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        player.update();
        for (Enemy enemy : ENEMIES)
            if (enemy != null)
                enemy.update();
        for (Projectile projectile : PROJECTILES)
            projectile.update();
        for (Point point : POINTS)
            point.update();
    }

    public void render(Graphics g) {
        player.render(g);
        pinkDoor.render(g);
        for (Platform platform : PLATFORMS)
            platform.render(g);
        for (Enemy enemy : ENEMIES)
            if (enemy != null)
                enemy.render(g);
        for (Projectile projectile : PROJECTILES)
            projectile.render(g);
        for (Point point : POINTS)
            point.render(g);
        for (Portal portal : PORTALS)
            portal.render(g);
        for (SpecialFloor floor : FLOORS)
            floor.render(g);
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    public void windowFocusLost() {
        player.resetDir();
    }

    public Player getPlayer() {
        return player;
    }
}
