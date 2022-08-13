package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import javax.scene.Scene;
import java.net.URL;


public class RichardJerry extends Application implements EventHandler<InputEvent> {
    GraphicsContext gc;
    Group root;

    int x = 25;
    int xVel = 0;
    int xBall = 25;
    int yBall = 600;
    int velBallY = 2;
    int velBallX = 2;
    int tempBallX;
    int tempBallY;
    AnimateObjects animate;
    Canvas canvas;
    Brick bricks[][] = new Brick[4][10];
    Image paddle = new Image("file:/Users/Richard/IdeaProjects/Atari Breakout/src/sample/BreakoutPaddle.jpeg");
    Image red = new Image("file:/Users/Richard/IdeaProjects/Atari Breakout/src/sample/redRect.jpeg");
    Image orange = new Image("file:/Users/Richard/IdeaProjects/Atari Breakout/src/sample/orangeRect.png");
    Image green = new Image("file:/Users/Richard/IdeaProjects/Atari Breakout/src/sample/greenRect.png");
    Image yellow = new Image("file:/Users/Richard/IdeaProjects/Atari Breakout/src/sample/yellowRect.png");
    Image blank = new Image("file:/Users/Richard/IdeaProjects/Atari Breakout/src/sample/blankRec.jpg");
    Image ball = new Image("file:/Users/Richard/IdeaProjects/Atari Breakout/src/sample/Ball.png");
    Image crackedRed = new Image("file:/Users/Richard/IdeaProjects/Atari Breakout/src/sample/redRectCracked.jpeg");
    Image crackedOrange = new Image("file:/Users/Richard/IdeaProjects/Atari Breakout/src/sample/orangeRectCracked.png");
    Image crackedGreen = new Image("file:/Users/Richard/IdeaProjects/Atari Breakout/src/sample/greenRectCracked.png");
    Image crackedYellow = new Image("file:/Users/Richard/IdeaProjects/Atari Breakout/src/sample/yellowRectCracked.png");


    int xRed = 22;
    int xOrange = 22;
    int xGreen = 22;
    int xYellow = 22;
    int xCoordinate = 22;
    int yCoordinate = 100;

    int score = 0;
    int lives = 3;
    int hits = 0;
    int crackedHits = 0;
    int firstHits = 0;

    boolean gameValid = true;
    boolean pause = true;
    boolean isPaused = false;
    boolean levelOne = true;
    boolean levelOneSound = true;
    boolean gameOverSound = true;
    boolean gameStart = false;
    boolean canCrack = false;
    boolean canBounce = true;
    boolean levelOneScreen = true;

    Rectangle2D ballRect;


    AudioClip brickClip;
    AudioClip paddleClip;
    AudioClip levelUpClip;
    AudioClip regenerationClip;
    AudioClip gameOverClip;


    public static void main(String[] args) {
        launch();

    }

    public void start(Stage stage) {
        stage.setTitle("Breakout");
        root = new Group();
        canvas = new Canvas(1445, 700);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        gc = canvas.getGraphicsContext2D();
        animate = new AnimateObjects();
        animate.start();
        stage.show();
        scene.addEventHandler(KeyEvent.KEY_PRESSED, this);
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, this);
        URL brickResource = getClass().getResource("brickSound.wav");
        brickClip = new AudioClip(brickResource.toString());
        URL paddleResource = getClass().getResource("paddleSound.wav");
        paddleClip = new AudioClip(paddleResource.toString());
        URL levelResource = getClass().getResource("levelUpSound.wav");
        levelUpClip = new AudioClip(levelResource.toString());
        URL regenerationResource = getClass().getResource("regenerationSound.wav");
        regenerationClip = new AudioClip(regenerationResource.toString());
        URL gameOverResource = getClass().getResource("gameOverSound.wav");
        gameOverClip = new AudioClip(gameOverResource.toString());


        for (int row = 0; row < bricks.length; row++) {
            for (int col = 0; col < bricks[0].length; col++) {
                if (row == 0) {
                    bricks[row][col] = new Brick(red, new Rectangle2D(xRed, 100, red.getWidth(), red.getHeight()), 2);
                    xRed += 140;
                }
                if (row == 1) {
                    bricks[row][col] = new Brick(orange, new Rectangle2D(xOrange, 171, orange.getWidth(), orange.getHeight()), 2);
                    xOrange += 140;
                }
                if (row == 2) {
                    bricks[row][col] = new Brick(green, new Rectangle2D(xGreen, 242, green.getWidth(), green.getHeight()), 2);
                    xGreen += 140;
                }
                if (row == 3) {
                    bricks[row][col] = new Brick(yellow, new Rectangle2D(xYellow, 313, yellow.getWidth(), yellow.getHeight()), 2);
                    xYellow += 140;
                }


            }

        }


        regenerationClip.play();


    }

    public class AnimateObjects extends AnimationTimer {
        public void handle(long now) {


            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());


            Rectangle2D leftBorder = new Rectangle2D(0, 0, 22, 700);
            gc.fillRect(0, 0, 22, 700);


            Rectangle2D topBorder = new Rectangle2D(0, 0, 1445, 55);
            gc.fillRect(0, 0, 1445, 55);

            Rectangle2D rightBorder = new Rectangle2D(1417, 0, 28, 700);
            gc.fillRect(1417, 0, 28, 700);


            if (yBall < 700 && hits < 40) {


                for (int row = 0; row < bricks.length; row++) {
                    for (int col = 0; col < bricks[0].length; col++) {

                        gc.drawImage(bricks[row][col].getImage(), xCoordinate, yCoordinate);
                        xCoordinate += 140;

                    }

                    yCoordinate += 71;
                    xCoordinate = 22;

                }

                xCoordinate = 22;
                yCoordinate = 100;

                if (gameStart == true) {


                    if (x + xVel <= 22)
                        x = 22;
                    else if (x + xVel >= 1248)
                        x = 1248;
                    else
                        x += xVel;

                    Rectangle2D paddleRect = new Rectangle2D(x, 650, paddle.getWidth(), paddle.getHeight());
                    gc.drawImage(paddle, x, 650);


                    yBall -= velBallY;
                    xBall += velBallX;
                    gc.drawImage(ball, xBall, yBall);
                    ballRect = new Rectangle2D(xBall, yBall, ball.getWidth(), ball.getHeight());


                    for (int row = 0; row < bricks.length; row++) {
                        for (int col = 0; col < bricks[0].length; col++) {

                            if (ballRect.intersects(bricks[row][col].getRect()) && (bricks[row][col].getImage() == crackedYellow || bricks[row][col].getImage() == crackedGreen || bricks[row][col].getImage() == crackedOrange || bricks[row][col].getImage() == crackedRed)) {

                                if (canCrack) {

                                    crackedHits++;

                                    brickClip.play();

                                    if (bricks[row][col].getImage() == crackedYellow)
                                        score++;
                                    if (bricks[row][col].getImage() == crackedGreen)
                                        score += 3;
                                    if (bricks[row][col].getImage() == crackedOrange)
                                        score += 5;
                                    if (bricks[row][col].getImage() == crackedRed)
                                        score += 7;

                                    bricks[row][col] = new Brick(blank, new Rectangle2D(bricks[row][col].getX(), bricks[row][col].getY(), 135, 66), 2);

                                    /*if (ballRect.getMaxX() <= bricks[row][col].getRect().getMinX() && velBallX > 0 && ballRect.getMaxY() < bricks[row][col].getRect().getMaxY() && ballRect.getMaxY() > bricks[row][col].getRect().getMinY())
                                        velBallX = - velBallX;
                                    if (ballRect.getMaxX() <= bricks[row][col].getRect().getMinX() && velBallX > 0 && ballRect.getMinY() < bricks[row][col].getRect().getMaxY() && ballRect.getMinY() > bricks[row][col].getRect().getMinY())
                                        velBallX = - velBallX;
                                    if (ballRect.getMinX() >= bricks[row][col].getRect().getMaxX() && velBallX < 0 && ballRect.getMaxY() < bricks[row][col].getRect().getMaxY() && ballRect.getMaxY() > bricks[row][col].getRect().getMinY())
                                        velBallX = - velBallX;
                                    if (ballRect.getMinX() >= bricks[row][col].getRect().getMaxX() && velBallX < 0 && ballRect.getMinY() < bricks[row][col].getRect().getMaxY() && ballRect.getMinY() > bricks[row][col].getRect().getMinY())
                                        velBallX = - velBallX;*/

                                    velBallY = -velBallY;
                                }
                                canBounce = true;
                                break;
                            }

                        }
                    }


                    for (int row = 0; row < bricks.length; row++) {
                        for (int col = 0; col < bricks[0].length; col++) {

                            if (ballRect.intersects(bricks[row][col].getRect()) && bricks[row][col].getImage() != blank && bricks[row][col].getImage() != crackedGreen && bricks[row][col].getImage() != crackedOrange && bricks[row][col].getImage() != crackedRed && bricks[row][col].getImage() != crackedYellow) {

                                if (levelOne == true) {

                                    hits++;

                                    brickClip.play();

                                    if (bricks[row][col].getImage() == yellow)
                                        score++;
                                    if (bricks[row][col].getImage() == green)
                                        score += 3;
                                    if (bricks[row][col].getImage() == orange)
                                        score += 5;
                                    if (bricks[row][col].getImage() == red)
                                        score += 7;

                                    bricks[row][col] = new Brick(blank, new Rectangle2D(bricks[row][col].getX(), bricks[row][col].getY(), 135, 66), 2);

                                } else if (levelOne == false) {


                                    hits++;

                                    firstHits++;

                                    brickClip.play();

                                    if (bricks[row][col].getImage() == yellow)
                                        bricks[row][col] = new Brick(crackedYellow, new Rectangle2D(bricks[row][col].getX(), bricks[row][col].getY(), 135, 66), 2);
                                    if (bricks[row][col].getImage() == green)
                                        bricks[row][col] = new Brick(crackedGreen, new Rectangle2D(bricks[row][col].getX(), bricks[row][col].getY(), 135, 66), 2);
                                    if (bricks[row][col].getImage() == orange)
                                        bricks[row][col] = new Brick(crackedOrange, new Rectangle2D(bricks[row][col].getX(), bricks[row][col].getY(), 135, 66), 2);
                                    if (bricks[row][col].getImage() == red)
                                        bricks[row][col] = new Brick(crackedRed, new Rectangle2D(bricks[row][col].getX(), bricks[row][col].getY(), 135, 66), 2);

                                    canCrack = false;


                                }


                                if (hits >= 2 && hits < 4) {
                                    if (velBallX < 0)
                                        velBallX = -4;
                                    else
                                        velBallX = 4;
                                    if (velBallY < 0)
                                        velBallY = -4;
                                    else
                                        velBallY = 4;
                                }

                                if (hits >= 4 && hits < 6) {
                                    if (velBallX < 0)
                                        velBallX = -6;
                                    else
                                        velBallX = 6;
                                    if (velBallY < 0)
                                        velBallY = -6;
                                    else
                                        velBallY = 6;
                                }

                                if (hits >= 6 && hits < 10) {
                                    if (velBallX < 0)
                                        velBallX = -8;
                                    else
                                        velBallX = 8;
                                    if (velBallY < 0)
                                        velBallY = -8;
                                    else
                                        velBallY = 8;
                                }

                                if (hits >= 10 && hits < 16) {
                                    if (velBallX < 0)
                                        velBallX = -10;
                                    else
                                        velBallX = 10;
                                    if (velBallY < 0)
                                        velBallY = -10;
                                    else
                                        velBallY = 10;
                                }

                                if (hits >= 16 && hits < 20) {
                                    if (velBallX < 0)
                                        velBallX = -12;
                                    else
                                        velBallX = 12;
                                    if (velBallY < 0)
                                        velBallY = -12;
                                    else
                                        velBallY = 12;
                                }

                                /*if (ballRect.getMaxX() <= bricks[row][col].getRect().getMinX() && velBallX > 0 && ballRect.getMaxY() < bricks[row][col].getRect().getMaxY() && ballRect.getMaxY() > bricks[row][col].getRect().getMinY())
                                    velBallX = - velBallX;
                                if (ballRect.getMaxX() <= bricks[row][col].getRect().getMinX() && velBallX > 0 && ballRect.getMinY() < bricks[row][col].getRect().getMaxY() && ballRect.getMinY() > bricks[row][col].getRect().getMinY())
                                    velBallX = - velBallX;
                                if (ballRect.getMinX() >= bricks[row][col].getRect().getMaxX() && velBallX < 0 && ballRect.getMaxY() < bricks[row][col].getRect().getMaxY() && ballRect.getMaxY() > bricks[row][col].getRect().getMinY())
                                    velBallX = - velBallX;
                                if (ballRect.getMinX() >= bricks[row][col].getRect().getMaxX() && velBallX < 0 && ballRect.getMinY() < bricks[row][col].getRect().getMaxY() && ballRect.getMinY() > bricks[row][col].getRect().getMinY())
                                    velBallX = - velBallX;*/


                                velBallY = -velBallY;
                                canBounce = true;

                                break;


                            }

                        }
                    }


                    if (ballRect.intersects(paddleRect)) {

                        if (yBall < paddleRect.getMaxY()) {


                            if (canBounce) {

                                paddleClip.play();

                                if (ballRect.getMaxX() <= paddleRect.getMinX() && velBallX > 0 && ballRect.getMaxY() > paddleRect.getMinY())
                                    velBallX = -velBallX;

                                if (ballRect.getMinX() >= paddleRect.getMaxX() && velBallX < 0 && ballRect.getMaxY() > paddleRect.getMinY())
                                    velBallX = -velBallX;

                                velBallY = -velBallY;
                            }


                        }
                        canCrack = true;
                        canBounce = false;

                    } else if (ballRect.intersects(topBorder)) {

                        velBallY = -velBallY;
                        canCrack = true;
                        canBounce = true;
                    } else if (ballRect.intersects(rightBorder) || ballRect.intersects(leftBorder)) {
                        velBallX = -velBallX;
                        canCrack = true;
                        canBounce = true;
                    }


                } else {

                    gc.drawImage(paddle, 22, 650);
                    gc.drawImage(ball, 25, 600);
                }

            } else {

                if (yBall >= 700) {


                    if (lives > 1) {

                        lives--;
                        yBall = 600;
                        xBall = 25;
                        x = 25;
                        velBallY = -velBallY;
                        if (velBallX < 2)
                            velBallX = 2;
                        gc.drawImage(ball, xBall, yBall);
                        gc.drawImage(paddle, x, 650);
                        gameStart = false;
                        canCrack = true;

                    } else {

                        if (gameOverSound == true)
                            gameOverClip.play();
                        gameOverSound = false;
                        lives = 0;
                        gameValid = false;
                        pause = false;
                        gameStart = false;
                        if (!levelOne)
                            levelOne = true;


                        Font font = Font.font("Arial", FontWeight.NORMAL, 70);
                        gc.setFont(font);
                        gc.fillText("Game Over", 500, 350); //draws the yellow part of the text
                        gc.strokeText("Game Over", 500, 350);
                        gc.fillText("Press Space to Restart", 300, 500); //draws the yellow part of the text
                        gc.strokeText("Press Space to Restart", 300, 500);

                        hits = 0;


                    }

                }

                if (crackedHits == 40 && score > 160) {

                    if (levelOneSound == true)
                        levelUpClip.play();
                    levelOneSound = false;
                    levelOne = true;
                    levelOneScreen = true;
                    lives = 0;
                    hits = 0;
                    gameValid = false;
                    pause = false;
                    gameStart = false;
                    Font font = Font.font("Arial", FontWeight.NORMAL, 70);
                    gc.setFont(font);
                    gc.fillText("Level 2 Completed!", 425, 350); //draws the yellow part of the text
                    gc.strokeText("Level 2 Completed!", 425, 350);
                    gc.fillText("Press Space to Continue", 320, 500); //draws the yellow part of the text
                    gc.strokeText("Press Space to Continue", 320, 500);


                }

                if (hits == 40 && hits < 41 && levelOneScreen == true) {

                    if (levelOneSound == true)
                        levelUpClip.play();
                    levelOneSound = false;
                    levelOne = false;
                    lives = 0;
                    gameValid = false;
                    pause = false;
                    gameStart = false;
                    Font font = Font.font("Arial", FontWeight.NORMAL, 70);
                    gc.setFont(font);
                    gc.fillText("Level 1 Completed!", 425, 150); //draws the yellow part of the text
                    gc.strokeText("Level 1 Completed!", 425, 150);
                    gc.fillText("Press Space to Continue", 320, 250); //draws the yellow part of the text
                    gc.strokeText("Press Space to Continue", 320, 250);
                    gc.fillText("-random inter-brick reflections", 300, 450); //draws the yellow part of the text
                    gc.strokeText("-random inter-brick reflections", 300, 450); //draws the yellow part of the text
                    gc.fillText("-faster approach", 300, 525); //draws the yellow part of the text
                    gc.strokeText("-faster approach", 300, 525); //draws the yellow part of the text
                    gc.fillText("-brick Hp", 300, 600); //draws the yellow part of the text
                    gc.strokeText("-brick Hp", 300, 600); //draws the yellow part of the text


                }

            }


            gc.setStroke(Color.WHITE); //Changes the outline the black
            gc.setLineWidth(1); //How big the black lines will be
            Font font = Font.font("Arial", FontWeight.NORMAL, 48);
            gc.setFont(font);
            gc.fillText("Score: " + score, 1190, 45); //draws the yellow part of the text
            gc.strokeText("Score: " + score, 1190, 45);

            gc.fillText("Lives: " + lives, 50, 45); //draws the yellow part of the text
            gc.strokeText("Lives: " + lives, 50, 45);


        }


    }


    public void handle(final InputEvent event) {

        if (event instanceof KeyEvent) {

            if (event.getEventType().toString().equals("KEY_PRESSED")) {

                if (((KeyEvent) event).getCode() == KeyCode.LEFT) {
                    if (gameStart == false)
                        gameStart = true;
                    xVel = -25;
                }
                if (((KeyEvent) event).getCode() == KeyCode.RIGHT) {
                    if (gameStart == false)
                        gameStart = true;
                    xVel = 25;
                }
                if (((KeyEvent) event).getCode() == KeyCode.SPACE) {

                    if (pause == true) {

                        tempBallX = velBallX;
                        tempBallY = velBallY;
                        velBallY = 0;
                        velBallX = 0;
                        isPaused = true;
                        pause = false;
                    } else if (isPaused == true) {

                        velBallY = tempBallY;
                        velBallX = tempBallX;
                        isPaused = false;
                        pause = true;

                    } else {
                        gameValid = true;
                        isPaused = false;
                        pause = true;
                        gameOverSound = true;
                        levelOneSound = true;
                        if (levelOne == false)
                            levelOneScreen = false;
                        xBall = 25;
                        yBall = 600;
                        velBallY = 2;
                        velBallX = 2;
                        lives = 3;
                        if (levelOne == true || crackedHits > 0) {
                            score = 0;
                            levelOneScreen = true;
                        }
                        xRed = 22;
                        xOrange = 22;
                        xGreen = 22;
                        xYellow = 22;
                        xCoordinate = 22;
                        yCoordinate = 100;
                        x = 25;
                        hits = 0;
                        crackedHits = 0;
                        for (int row = 0; row < bricks.length; row++) {
                            for (int col = 0; col < bricks[0].length; col++) {
                                if (row == 0) {
                                    bricks[row][col] = new Brick(red, new Rectangle2D(xRed, 100, red.getWidth(), red.getHeight()), 2);
                                    xRed += 140;
                                }
                                if (row == 1) {
                                    bricks[row][col] = new Brick(orange, new Rectangle2D(xOrange, 171, orange.getWidth(), orange.getHeight()), 2);
                                    xOrange += 140;
                                }
                                if (row == 2) {
                                    bricks[row][col] = new Brick(green, new Rectangle2D(xGreen, 242, green.getWidth(), green.getHeight()), 2);
                                    xGreen += 140;
                                }
                                if (row == 3) {
                                    bricks[row][col] = new Brick(yellow, new Rectangle2D(xYellow, 313, yellow.getWidth(), yellow.getHeight()), 2);
                                    xYellow += 140;
                                }


                            }

                        }

                        regenerationClip.play();

                    }


                }
            }

            if (event.getEventType().toString().equals("KEY_RELEASED")) {

                if (((KeyEvent) event).getCode() == KeyCode.LEFT)
                    xVel = 0;

                if (((KeyEvent) event).getCode() == KeyCode.RIGHT)
                    xVel = 0;


            }


        }
    }


}


