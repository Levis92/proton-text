package edu.chl.proton.model;



/**
 * @author Ludvig Ekman
 * Created by ludvig on 2017-05-01.
 */
public class Cursor{ // extends javafx.scene.Cursor ?

    private Position position;

    public void Cursor(Position position){
        this.position = position;
    }

    protected Position getPosition() {
        return position;
    }

    protected void setPosition(int x,int y) {
        position.setX(x);
        position.setY(y);
    }










}
