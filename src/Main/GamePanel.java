package Main;

import Entity.Player;
import java.awt.Color;
import java.awt.Dimension; // Corrected the spelling from "Dimention" to "Dimension"
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel  implements Runnable{
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;
    //FPS
    int FPS = 60;
    public x =0;

    KeyHandler keyH = new KeyHandler(); 
    Thread gameThread; 
    Player player= new Player(this, keyH);
    //Set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Fixed "Dimention" typo
        this.setBackground(Color.BLACK); // Best practice to use uppercase for color constants
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
   
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta>=1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
        if (timer >= 1000000000) {
            System.out.println("FPS:" + drawCount);
            drawCount = 0;
            timer = 0;
        }

        }
    }
    public void update() {
        player.update();
      
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        player.draw(g2);
        g2.dispose();
    }
    public void draw(Entity.Player player, Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fillRect(player.x, player.y, tileSize, tileSize);
        
    }
}

