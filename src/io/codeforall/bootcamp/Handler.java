package io.codeforall.bootcamp;

import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

public class Handler implements KeyboardHandler {

    public Keyboard keyboard;
    public Bar bar;
    public Ball ball;
    public int fieldWidth;
    public int fieldHeight;

    public int backgroundX;

    public Handler(Bar bar, int fieldX, int fieldY, Ball ball, int backgroundX) {
        this.bar = bar;
        keyboard = new Keyboard(this);
        this.fieldWidth = fieldX;
        this.fieldHeight = fieldY;
        this.ball = ball;
        this.backgroundX = backgroundX;
        createKeyboardEvents();
    }

    public void createKeyboardEvents() {
        KeyboardEvent keyboardEventRight = new KeyboardEvent();
        keyboardEventRight.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboardEventRight.setKey(KeyboardEvent.KEY_RIGHT);
        keyboard.addEventListener(keyboardEventRight);

        KeyboardEvent keyboardEventLeft = new KeyboardEvent();
        keyboardEventLeft.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboardEventLeft.setKey(KeyboardEvent.KEY_LEFT);
        keyboard.addEventListener(keyboardEventLeft);

        KeyboardEvent keyboardEventSpace = new KeyboardEvent();
        keyboardEventSpace.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboardEventSpace.setKey(KeyboardEvent.KEY_SPACE);
        keyboard.addEventListener(keyboardEventSpace);
    }


    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {

        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_RIGHT:
                if(Game.barValidPosition(Direction.RIGHT, bar, backgroundX, fieldWidth)) {
                    bar.moveRight();
                }
                break;

                case KeyboardEvent.KEY_LEFT:
                    if(Game.barValidPosition(Direction.LEFT, bar, backgroundX, fieldWidth)) {
                        bar.moveLeft();
                    }
                    break;

                    case KeyboardEvent.KEY_SPACE:
                        System.exit(1);
                        break;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {

    }
}
