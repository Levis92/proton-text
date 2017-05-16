package edu.chl.proton.model;

import static java.awt.Color.*;

/**
 * @author Mickaela
 * Created by Mickaela on 2017-05-01.
 */
public class Char {

    private Position position;
    private enum Colors{BLACK, RED, ORANGE, BLUE, GREEN, YELLOW, PINK, GRAY;}
    private FontStyle style;
    private char c;

    public Char(char c, Position position, FontStyle style, String color){
        this.c = c;
        this.position = position;
        this.style = style;
        Colors.valueOf(color);
    }

    protected Position getPosition(){
        return position;
    }

    protected void setPosition(int x, int y){
        this.position.setX(x);
        this.position.setY(y);
    }

}