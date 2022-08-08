import java.awt.*;

public class Ball extends Rectangle {
    int xVelocity = 0;
    int yVelocity = 10;

    public Ball(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void moveBall() {
        this.x += xVelocity;
        this.y += yVelocity;
    }

    public void invertXVelocity () {
        this.xVelocity *= -1;
    }

    public void invertYVelocity() {
        this.yVelocity *= -1;
    }

    public void setXVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setYVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }
}
