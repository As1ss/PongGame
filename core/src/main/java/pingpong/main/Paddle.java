package pingpong.main;

public class Paddle {
    private float height;
    private float width;
    private float x;
    private float y;
    private float ySpeed;

    //Constructor without parameters
    public Paddle() {
        this.height =128f;
        this.width = 24f;
        this.x = 0.0f;
        this.y = 0.0f;
        this.ySpeed = 4f;
    }

    public float getX() {
        return x;
    }

    public void setX(float newX) {
        this.x = newX;
    }

    public float getY() {
        return y;
    }

    public void setY(float newY) {
        this.y = newY;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float newHeight) {
        this.height = newHeight;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float newWidth) {
        this.width = newWidth;
    }

    public float getySpeed() {
        return ySpeed;
    }

    public void setySpeed(float newYSpeed) {
        this.ySpeed = newYSpeed;
    }
}
