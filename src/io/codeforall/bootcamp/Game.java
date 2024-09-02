package io.codeforall.bootcamp;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Ellipse;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.pictures.Picture;

import java.io.IOException;

public class Game {

    private Picture background;
    private Bar bar;
    private Ball ball;
    private ListLines listLines;
    private double velocity = 40;

    private Picture loseBackground;
    private Picture winBackground;

    private Sound backgroundSound;
    private Sound destroyingBrick;
    private Sound endingSound;
    private Sound winningSound;


    public Game() throws InterruptedException {
        background = new Picture(10, 10, "resources/images/gameImage.png");
        bar = new Bar(new Rectangle(410, 800, 120, 20));
        ball = new Ball(new Ellipse(440, 700, 40, 60));
        loseBackground = new Picture(10,10,"resources/images/Level.png");
        winBackground = new Picture(10,10,"resources/images/Win_Level.png");
        backgroundSound = new Sound();
        destroyingBrick = new Sound();
        endingSound = new Sound();
        winningSound = new Sound();
    }

    public void prepare() {
        background.draw();

        listLines = new ListLines();

        ball.setColor(new Color(255,255,255));
        ball.fill();

        bar.setColor(new Color(200, 33, 41));
        bar.fill();

        new Handler(bar, background.getWidth(), background.getHeight(), ball, background.getX());

    }

    public void start() throws InterruptedException, IOException {
        prepare();

        boolean gameEnd = false;

        backgroundSound.playSound("resources/sounds/backgroundSound.wav");
        backgroundSound.loopSound(3);

        while (!gameEnd) {

            while (ballValidPosition(ball.getNextDirection())) {

                Thread.sleep((int) velocity);

                if (ball.getNextDirection() == Direction.UP) {
                    ball.moveUp();
                } else if (ball.getNextDirection() == Direction.DIAGONAL_DOWN_RIGHT) {
                    ball.moveDiagonalDownRight();
                } else if (ball.getNextDirection() == Direction.DIAGONAL_DOWN_LEFT) {
                    ball.moveDiagonalDownLeft();
                } else if (ball.getNextDirection() == Direction.DIAGONAL_UP_LEFT) {
                    ball.moveDiagonalUpLeft();
                } else {
                    ball.moveDiagonalUpRight();
                }

            }

            gameEnd = checkGameStatus();
            }
        backgroundSound.stopSound();
        winBackground.draw();
        winningSound.playSound("resources/sounds/winningSound.wav");
        Thread.sleep(4000);
        System.exit(0);

        }

    private boolean ballValidPosition(Direction d) throws InterruptedException, IOException {

        if (hitUp() && (d == Direction.DIAGONAL_UP_RIGHT || d == Direction.DIAGONAL_UP_LEFT || d == Direction.UP)) {
            destroyingBrick.playSound("resources/sounds/brickSound.wav");
            if (d == Direction.DIAGONAL_UP_LEFT) {
                ball.setNextDirection(Direction.DIAGONAL_DOWN_LEFT);
            } else {
                ball.setNextDirection(Direction.DIAGONAL_DOWN_RIGHT);
            }
            return false;
        }
        else if(hitRight() && (d == Direction.DIAGONAL_UP_RIGHT || d == Direction.DIAGONAL_DOWN_RIGHT)) {
            destroyingBrick.playSound("resources/sounds/brickSound.wav");
            if(d == Direction.DIAGONAL_UP_RIGHT) {
                ball.setNextDirection(Direction.DIAGONAL_UP_LEFT);
            }
            else {
                ball.setNextDirection(Direction.DIAGONAL_DOWN_LEFT);
            }
            return false;
        }
        else if (hitLeft() && (d == Direction.DIAGONAL_UP_LEFT || d == Direction.DIAGONAL_DOWN_LEFT)) {
            destroyingBrick.playSound("resources/sounds/brickSound.wav");
            if(d == Direction.DIAGONAL_UP_LEFT) {
                ball.setNextDirection(Direction.DIAGONAL_UP_RIGHT);
            }
            else {
                ball.setNextDirection(Direction.DIAGONAL_DOWN_RIGHT);
            }
            return false;
        }else if(hitBar() && (d == Direction.DIAGONAL_DOWN_LEFT || d == Direction.DIAGONAL_DOWN_RIGHT)) {
            if(d == Direction.DIAGONAL_DOWN_RIGHT) {
                ball.setNextDirection(Direction.DIAGONAL_UP_RIGHT);
            } else {
                ball.setNextDirection(Direction.DIAGONAL_UP_LEFT);
            }
            destroyingBrick.playSound("resources/sounds/brickSound.wav");
            return false;
        } else if(hitDown() && (d == Direction.DIAGONAL_DOWN_LEFT || d == Direction.DIAGONAL_DOWN_RIGHT)) {
            backgroundSound.stopSound();
            endingSound.playSound("resources/sounds/endingSound.wav");
            Thread.sleep(200);
            ball.delete();
            loseBackground.draw();
            Thread.sleep(5000);
            System.exit(0);
        } else if(hitDownBrick() && (d == Direction.UP || d == Direction.DIAGONAL_UP_LEFT || d == Direction.DIAGONAL_UP_RIGHT)) {
            if (d == Direction.DIAGONAL_UP_LEFT) {
                ball.setNextDirection(Direction.DIAGONAL_DOWN_LEFT);
            } else {
                ball.setNextDirection(Direction.DIAGONAL_DOWN_RIGHT);
            }
            return false;
        }   else if(hitRightBrick() && (d == Direction.DIAGONAL_UP_LEFT || d == Direction.DIAGONAL_DOWN_LEFT)) {
                if(d == Direction.DIAGONAL_UP_LEFT) {
                    ball.setNextDirection(Direction.DIAGONAL_UP_RIGHT);
                } else {
                    ball.setNextDirection(Direction.DIAGONAL_DOWN_RIGHT);
                }
                return false;
        } else if(hitUpBrick() && (d == Direction.DIAGONAL_DOWN_LEFT || d == Direction.DIAGONAL_DOWN_RIGHT)) {
            if(d == Direction.DIAGONAL_DOWN_LEFT) {
                ball.setNextDirection(Direction.DIAGONAL_UP_LEFT);
            }else {
                ball.setNextDirection(Direction.DIAGONAL_UP_RIGHT);
            }
            return false;
        } else if(hitLeftBrick() && (d == Direction.DIAGONAL_UP_RIGHT || d == Direction.DIAGONAL_DOWN_RIGHT)) {
            if(d == Direction.DIAGONAL_UP_RIGHT) {
                ball.setNextDirection(Direction.DIAGONAL_UP_LEFT);
            }  else {
                ball.setNextDirection(Direction.DIAGONAL_DOWN_LEFT);
            }
            return false;
        }

        return true;
    }

    public boolean hitUpBrick() {

        for(int lineIndex = 0; lineIndex < listLines.getLength(); lineIndex++) {

            BrickLine line = listLines.getLine(lineIndex);

            for(int brickIndex = 0; brickIndex < line.getLength(); brickIndex++) {

                Brick brick = line.getBrick(brickIndex);

                if(ball.getMaxY() == brick.getY() && ball.getX() >= brick.getX() && ball.getX() <= brick.getMaxX()

                || ball.getMaxY() == brick.getY() && ball.getMaxX() <= brick.getMaxX() && ball.getMaxX() >= brick.getX()) {

                    brick.destroyBrick();
                    deleteBrick(brickIndex, line);
                    return true;

                }
            }
        }

        return false;
    }

    public boolean hitRightBrick() {

        for(int lineIndex = 0; lineIndex < listLines.getLength(); lineIndex++) {

            BrickLine line = listLines.getLine(lineIndex);

            for(int brickIndex = 0; brickIndex < line.getLength(); brickIndex++) {

                Brick brick = line.getBrick(brickIndex);

                    if(ball.getX() == brick.getMaxX() && ball.getY() >= brick.getY() && ball.getY() <= brick.getMaxY()

                    || ball.getX() == brick.getMaxX() && ball.getMaxY() <= brick.getMaxY() && ball.getMaxY() >= brick.getY()) {

                        brick.destroyBrick();
                        deleteBrick(brickIndex, line);
                        return true;

                    }
                }
            }

        return false;
    }

    public boolean hitLeftBrick() {

        for(int lineIndex = 0; lineIndex < listLines.getLength(); lineIndex++) {

            BrickLine line = listLines.getLine(lineIndex);

            for(int brickIndex = 0; brickIndex < line.getLength(); brickIndex++) {

                Brick brick = line.getBrick(brickIndex);


                    if (ball.getMaxX() == brick.getX() && ball.getY() >= brick.getY() && ball.getY() <= brick.getMaxY()

                            ||

                            ball.getMaxX() == brick.getX() && ball.getMaxY() <= brick.getMaxY() && ball.getMaxY() >= brick.getY()) {


                        brick.destroyBrick();
                        deleteBrick(brickIndex, line);
                        return true;

                    }

            }

        }

        return false;
    }

    public boolean hitDownBrick() {

        for(int lineIndex = 0; lineIndex < listLines.getLength(); lineIndex++) {

            BrickLine line = listLines.getLine(lineIndex);

            for(int brickIndex = 0; brickIndex < line.getLength(); brickIndex++) {

                Brick brick = line.getBrick(brickIndex);

                if((ball.getY() == brick.getMaxY() && ball.getX() <= brick.getMaxX() && ball.getX() >= brick.getX())
                ||
                        (ball.getY() == brick.getMaxY()) && ball.getMaxX() <= brick.getMaxX() && ball.getMaxX() >= brick.getX()) {
                    brick.destroyBrick();
                    deleteBrick(brickIndex, line);
                    return true;
                }

            }

        }

        return false;
    }

    public boolean hitLeft() {
       return  ball.getX() == (background.getX()+10);
    }

    public boolean hitRight() {

        return ball.getX() + ball.getWidth() > background.getWidth();
    }

    public boolean hitDown() {
       return  (ball.getY() + ball.getHeight()) > background.getHeight();
    }

    public boolean hitUp() {
        return  ball.getY() == (background.getX()+10);
    }

    public boolean hitBar() {

        return (ball.getMaxY() == bar.getY() && ball.getX() >= bar.getX() && ball.getX() <= bar.getMaxX())

                || ball.getMaxY() == bar.getY() && ball.getMaxX() >= bar.getX() && ball.getMaxX() <= bar.getMaxX();

    }

    public boolean checkGameStatus() {
        int countEmpty = 0;

        for(int i = 0; i < listLines.getLength(); i++) {
            if(listLines.getLine(i).itsEmpty()) {
                countEmpty++;
            }
        }

        return countEmpty == listLines.getLength();
    }


    public static boolean barValidPosition(Direction direction, Bar bar, int fieldX, int fieldWidth) {

        switch (direction) {
            case RIGHT:
                if (bar.getX() + bar.getWidth() > fieldWidth - 10) {
                    return false;
                }
                break;
            case LEFT:
                if (bar.getX() < (fieldX+20)) {
                    return false;
                }
                break;
        }

        return true;

    }

    private void deleteBrick(int index, BrickLine line) {
        velocity-=1.2;
        destroyingBrick.playSound("resources/sounds/brickSound.wav");
        line.removeBrick(index);
    }

}
