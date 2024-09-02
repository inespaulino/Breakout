package io.codeforall.bootcamp;

import org.academiadecodigo.simplegraphics.graphics.Color;
import java.util.ArrayList;
import java.util.List;

public class BrickLine {

    private List<Brick> bricks;
    private int lineY;

    public BrickLine(int lineY, Color color) {
        this.lineY = lineY;

        bricks = new ArrayList<>();

        int x = 80;
        int rectangleWidth = 130;
        int rectangleHeight = 60;

        for(int i = 0; i < 6; i++) {

            if(i == 0) {
                bricks.add(new Brick(x , lineY, rectangleWidth, rectangleHeight, color));
                continue;
            }

            x+=140;

            bricks.add(new Brick(x,lineY, rectangleWidth, rectangleHeight, color));
        }
    }

    public boolean itsEmpty() {
        return bricks.isEmpty();
    }

    public void removeBrick(int index) {
        bricks.remove(index);
    }

    public int getLength() {
        return bricks.size();
    }

    public Brick getBrick(int index) {
        return bricks.get(index);
    }

}
