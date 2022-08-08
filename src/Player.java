import java.awt.*;

public class Player extends Rectangle {
    private final int xVelocity = 25;

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void moveRight() {
        if (this.x + this.width < 800) this.x += xVelocity;
    }

    public void moveLeft() {
        if (this.x > 0) this.x -= xVelocity;
    }

    public int getxVelocity() {
        return xVelocity;
    }

}
