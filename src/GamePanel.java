import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
    Player player;
    Ball ball;
    private final Random random;
    private Timer timer;
    private boolean isRunning = false;
    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 600;
    private final int DELAY = 20;
    private final int rows = 15;
    private final int columns = 6;
    private final ArrayList <Block> blocksList = new ArrayList<>();
    boolean isGoingRight = false;
    boolean isGoingLeft = false;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        this.setBackground(Color.BLACK);
        player = new Player(SCREEN_WIDTH / 2 - 150 / 2,SCREEN_HEIGHT - 25,150,25);
        ball = new Ball(SCREEN_WIDTH / 2 - 30 / 2,SCREEN_HEIGHT / 2 - 50 / 2,30,30);
        startGame();
    }


    private void startGame() {
        isRunning = true;
        timer = new Timer(DELAY,this);
        timer.start();
        spawnBlock();
    }

    public void spawnBlock() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int columnCounter = j + 1;
                int rowCounter = i + 1;
                blocksList.add(new Block(150 + 30 * rowCounter,30 * columnCounter, 30,30));
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        drawPlayer(g);
        drawBall(g);
        drawBlocks(g);

        if (!isRunning) {
            g.setColor(Color.BLACK);
            g.fillRect(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);
            g.setColor(Color.white);
            g.drawString("GAME OVER",SCREEN_WIDTH / 2,SCREEN_HEIGHT / 2);
        }
    }

    private void drawPlayer(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(player.x,player.y,player.width,player.height);
    }

    public void movePlayer() {
        if (isGoingRight) player.moveRight();
        if (isGoingLeft) player.moveLeft();
    }

    public void drawBall(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(ball.x,ball.y,ball.width,ball.height);
    }

    public void drawBlocks(Graphics g) {
        for (Block block: blocksList) {
            g.setColor(Color.white);
            g.fillRect((int) block.getX(), (int) block.getY(), (int) block.getWidth(), (int) block.getHeight());
            g.setColor(Color.BLACK);
            g.drawRect((int) block.getX(), (int) block.getY(), (int) block.getWidth(), (int) block.getHeight());
        }
    }


    public void checkBallCollision() {
        // ball collide witth the player
        if (ball.intersects(player.x,player.y,player.width,player.height)) {
            ball.invertYVelocity();
            if (ball.x + ball.getWidth() / 2 > player.getX() + player.getWidth() / 2)
                ball.setXVelocity(random.nextInt(2,5));
            else
                ball.setXVelocity(random.nextInt(2,5) * -1);
        }
        // ball colide with the screen side
        if (ball.getX() < 0 || ball.getX() > SCREEN_WIDTH) ball.invertXVelocity();
        if (ball.getY() < 0) ball.invertYVelocity();
        // ball collide with the blocks
        for (int i = 0; i < blocksList.size(); i++) {
            if (ball.intersects(blocksList.get(i).getX(),blocksList.get(i).getY(),blocksList.get(i).getWidth(), blocksList.get(i).getHeight())) {
                blocksList.remove(i);
                ball.invertYVelocity();
                break;
            }
        }
    }

    public void checkGameOver() {
        if (ball.y > SCREEN_HEIGHT || blocksList.size() == 0) isRunning = false;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            ball.moveBall();
            movePlayer();
            checkBallCollision();
            checkGameOver();
        }
        repaint();
    }

    private class myKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_D -> isGoingRight = true;
                case KeyEvent.VK_A -> isGoingLeft = true;
            }
        }

        public void keyReleased(KeyEvent e) {
            isGoingRight = false;
            isGoingLeft = false;
        }
    }
}
