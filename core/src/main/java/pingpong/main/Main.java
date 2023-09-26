package pingpong.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {


    private ShapeRenderer shape;
    private Paddle player1;
    private Paddle player2;
    private Ball ball;
    private Random rng;
    private State state;
    private SpriteBatch batch;
    private BitmapFont fontMessage;
    private BitmapFont fontScoreMessage;
    private String testMessage;
    private BitmapFont fontFpsMetter;
    private int p1Score;
    private int p2Score;

    private boolean continueCooldown = false;
    private long cooldownStartTime = 0;
    private long cooldownDuration = 1200000000L;

    private boolean gameOver;

    private boolean startGame;

    private Sound paddleHit;
    private Sound wallHit;
    private Sound score;


    @Override
    public void create() {
        batch = new SpriteBatch();
        fontMessage = new BitmapFont(Gdx.files.internal("arcadeFont.fnt"));
        fontMessage.setColor(Color.WHITE); // Configura el color del texto
        fontMessage.getData().setScale(2);// Configura el tamaño de la fuente

        fontScoreMessage = new BitmapFont(Gdx.files.internal("arcadeFont.fnt"));
        fontScoreMessage.setColor(Color.WHITE); // Configura el color del texto
        fontScoreMessage.getData().setScale(4);// Configura el tamaño de la fuente


        fontFpsMetter = new BitmapFont(Gdx.files.internal("arcadeFont.fnt"));
        fontFpsMetter.setColor(Color.WHITE); // Configura el color del texto
        fontFpsMetter.getData().setScale(1.2f);// Configura el tamaño de la fuente

        testMessage = "";


        state = State.PAUSED;

        p1Score = 0;
        p2Score = 0;

        player1 = new Paddle();
        player2 = new Paddle();
        ball = new Ball();

        shape = new ShapeRenderer();

        player1.setX(50f);
        player1.setY(Gdx.graphics.getHeight() / 2);

        player2.setX(Gdx.graphics.getWidth() - 75f);
        player2.setY(Gdx.graphics.getHeight() / 2);
        player2.setySpeed(10f);


        ball.setX(Gdx.graphics.getWidth() / 2);
        ball.setY(Gdx.graphics.getHeight() / 2);

        continueCooldown = false;
        cooldownStartTime = 0;
        gameOver = false;
        startGame = true;

        paddleHit = Gdx.audio.newSound(Gdx.files.internal("paddle_hit.wav"));
        wallHit = Gdx.audio.newSound(Gdx.files.internal("wall_hit.wav"));
        score = Gdx.audio.newSound(Gdx.files.internal("score.wav"));


    }


    public void update() {


        initialEvent();
        getBallMoves();
        getPlayer1Moves();
        getPlayer2Moves();
        scoreEvent();


    }

    private void initialEvent() {
        if (startGame) {
            testMessage = "The game has begun!\nTap screen to start.";
            startGame = false;
        }


    }


    private void getBallMoves() {
        rng = new Random();
        float xSpeedRng = 14f + rng.nextFloat() * (24f - 14f);
        ball.setX(ball.getX() + ball.getxSpeed());
        ball.setY(ball.getY() + ball.getySpeed());

        //Collision with player 1
        if (ball.getX() < player1.getX() + player1.getWidth() &&
            ball.getX() + ball.getWidth() > player1.getX() &&
            ball.getY() < player1.getY() + player1.getHeight() &&
            ball.getY() + ball.getHeight() > player1.getY()) {
            ball.setxSpeed(xSpeedRng);
            paddleHit.play();
        }
        if (ball.getY() < 0f || ball.getY() + ball.getHeight() > Gdx.graphics.getHeight()) {
            ball.setySpeed(-ball.getySpeed());
            wallHit.play();
        }


        //Collision with player 2
        if (ball.getX() < player2.getX() + player2.getWidth() &&
            ball.getX() + ball.getWidth() > player2.getX() &&
            ball.getY() < player2.getY() + player2.getHeight() &&
            ball.getY() + ball.getHeight() > player2.getY()) {
            ball.setxSpeed(-xSpeedRng);
            paddleHit.play();
        }


    }

    private void scoreEvent() {
        //Player 2 score
        if (ball.getX() < 0f) {
            testMessage = "   Player 2 scored!!\nTap screen to continue";
            p2Score++;
            state = State.PAUSED;
            cooldownStartTime = TimeUtils.nanoTime();
            score.play();
        }
        //Player 1 score
        if (ball.getX() + ball.getWidth() > Gdx.graphics.getWidth()) {
            testMessage = "   Player 1 scored!!\nTap screen to continue";
            p1Score++;
            state = State.PAUSED;
            cooldownStartTime = TimeUtils.nanoTime();
            score.play();
        }
        if (p1Score == 10 || p2Score == 10) {
            gameOverEvent();
        }


    }

    private void gameOverEvent() {
        gameOver = true;

        if (p1Score == 10 && gameOver) {
            testMessage = "   Player 1 has won!!\nTap screen to play again";
            state = State.PAUSED;
            cooldownStartTime = TimeUtils.nanoTime();
            gameOver = false;

        }
        if (p2Score == 10 && gameOver) {
            testMessage = "   Player 2 has won!!\nTap screen to play again";
            state = State.PAUSED;
            cooldownStartTime = TimeUtils.nanoTime();
            gameOver = false;

        }


    }

    private void getPlayer1Moves() {

        float invertedY = Gdx.graphics.getHeight() - Gdx.input.getY();
        player1.setY(invertedY);

        if (player1.getY() <= 0f) {
            player1.setY(Gdx.graphics.getHeight() / 2);
        }
        if (player1.getY() + player1.getHeight() >= Gdx.graphics.getHeight()) {
            player1.setY(Gdx.graphics.getHeight() - 150f);
        }
    }

    private void getPlayer2Moves() {
        player2.setY((player2.getY() + player2.getySpeed()));
        if (player2.getY() < 0 || player2.getY() + player2.getHeight() > Gdx.graphics.getHeight()) {
            player2.setySpeed(-player2.getySpeed());
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        switch (state) {
            case RUNNING:
                update();
                break;
            case PAUSED:
                reset();
                break;
        }


        batch.begin();
        fontMessage.draw(batch, testMessage, 375f, 1050f);
        fontFpsMetter.draw(batch, "FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()), Gdx.graphics.getWidth() - 375f, 1050f);
        fontScoreMessage.draw(batch, String.valueOf(p1Score), (Gdx.graphics.getWidth() / 2) - 400f, (Gdx.graphics.getHeight() / 2f) + 50f);
        fontScoreMessage.draw(batch, String.valueOf(p2Score), (Gdx.graphics.getWidth() / 2) + 300f, (Gdx.graphics.getHeight() / 2f) + 50f);
        batch.end();

        shape.setAutoShapeType(true);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(player2.getX(), player2.getY(), player2.getWidth(), player2.getHeight());
        shape.rect(player1.getX(), player1.getY(), player1.getWidth(), player1.getHeight());
        shape.rect(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
        shape.end();

    }

    private void reset() {

        initialEvent();

        ball.setX(Gdx.graphics.getWidth() / 2);
        ball.setY(Gdx.graphics.getHeight() / 2);
        long currentTime = TimeUtils.nanoTime();


        if (Gdx.input.isTouched() && (currentTime - cooldownStartTime >= cooldownDuration || !continueCooldown)) {
            testMessage = "";
            state = State.RUNNING;
            cooldownStartTime = currentTime; // Restablecer el tiempo de inicio del cooldown
            continueCooldown = true; // Habilitar el cooldown nuevamente
            if (gameOver == false && p1Score == 10 || p2Score == 10) {
                p1Score = 0;
                p2Score = 0;
            }
        }

    }

    @Override
    public void dispose() {
        shape.dispose();
        fontMessage.dispose();

    }

}
