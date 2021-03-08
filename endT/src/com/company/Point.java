package com.company; // the package of the project

public class Point { // class Point which is needed to create movement to the game
    private int x; // field x
    private int y; // field y

    public Point(){ // the constructor with no parameters which declare initial position for the object
        x = 0; // this x is equal to zero
        y = 0; // this y is equal to zero
    }

    public Point(int x, int y){ // the constructor with parameters x and y
        this.x = x; // this x will be equal to argument x
        this.y = y; // this y will be equal to argument y
    }
            // encapsulation part
    public int getX() { // getter for x
        return x;
    }

    public void setX(int x) { // setter for x
        this.x = x;
    }

    public int getY() { // getter for y
        return y;
    }

    public void setY(int y) { // setter for y
        this.y = y;
    }
}
