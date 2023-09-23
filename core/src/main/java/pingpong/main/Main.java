package pingpong.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private float x;
    private float y;
    private float xSpeed;
    private float ySpeed;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("roca.png");
        x = 0;
        y = 0;
        xSpeed = 14;
        ySpeed = 14;
    }

    public void update() {
        x += xSpeed;
        y += ySpeed;
        if (x < 0 || x  > Gdx.graphics.getWidth()) {
            xSpeed = -xSpeed;
        }
        if (y < 0 || y > Gdx.graphics.getHeight()) {
            ySpeed = -ySpeed;
        }

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update();
        batch.begin();
        batch.draw(image, x, y);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
