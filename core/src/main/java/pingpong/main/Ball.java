package pingpong.main;

public class Paddle {
    private float height;
    private float width;
    private float x;
    private float y;
    private float xSpeed;
    private float ySpeed;

    //Constructor without parameters
    public Paddle() {
        this.height = 64f;
        this.width = 12f;
        this.x = 0.0f;
        this.y = 0.0f;
        this.xSpeed = 16f;
        this.ySpeed = 4f;
    }


}
