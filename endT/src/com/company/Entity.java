package com.company; // the package of the project

public class Entity { // parent class Entity which is needed to create sublasses such as player and pet
    private String name; // private field name (String type)
    private Point position = new Point(); // field Point which creates an object of Point class in order to get access for subclasses to x and y coordinates

    public Entity(){ // the constructor with no parameters
        name = "entity"; // by default name is equal to "entity" value
    }

    public Entity(String name) { // the constructor with parameter String name
        setName(name); // using setter to set name with argument name
    }

    public String getName() { //getter for name
        return name;
    }

    public void setName(String name) { // setter for name
        this.name = name;
    }

    public void sound(){ // method sound which will be override in subclasses
        System.out.println("some sound"); // output "some sound" by default
    }

    public Point getPosition() { //getter for position
        return position;
    }

    public void setPosition(Point position) { //setter for position
        this.position = position;
    }
    public void setPosition(int x, int y) { //setter for position with x and y arguments
        this.position.setX(x);
        this.position.setY(y);
    }
    public String methodX(){ // method which helps to get access for x position of the entity
        return "" + position.getX();
    }
    public String methodY(){ // method which helps to get access for y position of the entity
        return "" + position.getY();
    }
}
