package com.company; // the package of the project
// inheritance starts here
public class Player extends Entity { // class Player which is subclass of Entity
    public Player(String name){ // constructor with the name which is declaired in parent class
        super(name);
    }
    // polymorphism starts here
    @Override //overriding parent's method sound
    public void sound(){
        System.out.println("Hey dog!"); // calling for your dog
    }
}
