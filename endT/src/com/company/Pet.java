package com.company; // the package of the project

public class Pet extends Entity { // class Pet which is subclass of Entity
    public Pet(String name){ // constructor with the name which is declaired in parent class
        super(name);
    }

    @Override //overriding parent's method sound
    public void sound(){
        System.out.println("GAV GAV!"); // it is needed when you call your dog and if it is next to you
    }
}
