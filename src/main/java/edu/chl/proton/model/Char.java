package edu.chl.proton.model;

import static java.awt.Color.*;

/**
 * @author Mickaela
 * Created by Mickaela on 2017-05-01.
 */
public class Char {

    private Position position;
    private enum Colors{BLACK, RED, ORANGE, BLUE, GREEN, YELLOW, PINK, GRAY;}
    private Fontstyle style;

    public Char(Position position, Fontstyle style, String color){
        this.position = position;
        this.style = style;
        //color?
    }

    protected Position getPosition(){
        return position;
    }

    protected void setPosition(int x, int y){
        this.position.getX() = x;
        this.position.getY() = y;
    }

}
