package edu.chl.proton.model;

/**
 * @author Stina Werme
 * Created by stinawerme on 01/05/17.
 */
public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;

    }

    protected void setX(int x) {
        this.x = x;
    }

    protected int getX() {
        return x;
    }

    protected void setY(int y) {
        this.y = y;
    }

    protected int getY() {
        return y;
    }



}
