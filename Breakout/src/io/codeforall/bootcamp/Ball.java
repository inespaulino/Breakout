package io.codeforall.bootcamp;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Ellipse;

public class Ball {
    private Ellipse ellipse;
    private Direction nextDirection;

    public Ball(Ellipse ellipse) {
        this.ellipse = ellipse;
        int randomNumber = (int) (Math.random() * (3 - 1 + 1)) + 1;
        this.nextDirection = Direction.values()[randomNumber];
    }

    public int getX() {
        return ellipse.getX();
    }

    public int getY(){
        return ellipse.getY();
    }

    public void fill() {
        ellipse.fill();
    }

    public void moveUp() {
        ellipse.translate(0, -10);
    }

    public void moveDiagonalDownLeft() {
        ellipse.translate(-10,10);
    }

    public void moveDiagonalDownRight() {
        ellipse.translate(10,10);
    }

    public void moveDiagonalUpRight() {
        ellipse.translate(10,-10);
    }

    public void moveDiagonalUpLeft() {
        ellipse.translate(-10,-10);
    }

    public void setNextDirection(Direction nextDirection) {
        this.nextDirection = nextDirection;
    }

    public int getMaxY() {
        return ellipse.getY() + ellipse.getHeight();
    }

    public int getMaxX() {
        return ellipse.getX() + ellipse.getWidth();
    }

    public Direction getNextDirection() {
        return nextDirection;
    }

    public int getWidth() {
        return ellipse.getWidth();
    }

    public int getHeight() {
        return ellipse.getHeight();
    }

    public void delete() {
        ellipse.delete();
    }

    public void setColor(Color color) {
        ellipse.setColor(color);
    }
}
