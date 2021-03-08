package com.company; // the package of the project

import java.sql.*; // importing sql libraries to use database
import java.util.Scanner; // importing scanner to have an opportunity to input data

public class Main { // class main

    public static void menu(){ //static (without object) method which outputs the main menu of the game
        System.out.println("1.Play"); // 1 to play
        System.out.println("2.Score table"); // 2 to see score table
        System.out.println("3.Exit"); // 3 to exit from the game
    }

    public static void main(String[] args) { // the program starts here, method main

        Scanner scanner = new Scanner(System.in); // creating scanner object
        boolean isStarted = false; // bool type variable which is needed to restart timer
        long beginning = 0; // a variable for the timer

        Connection connection = null; // connection for dbms
        Statement stmt = null; // statement for dbms
        ResultSet rs = null; // result set
        ResultSet rss = null;// result set

        try { // exception handling
            Class.forName("org.postgresql.Driver"); /////////////
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "12345"); ///////////
            if (connection != null){ ////// this part of code is needed to connect to the postgre database
                stmt = connection.createStatement(); // statement
                // System.out.println("connected");
            }
            else {
                System.out.println("no connection"); // if there is no connection
            }
        }catch (Exception e) { // if there is an exception we catch it and output
            System.out.println(e); ////////
        }

        while (true) { // infinite loop
            try{ // exception handling
                menu(); // calling for method menu
                int choice = scanner.nextInt(); // variable choice which is equal to input data
                scanner.nextLine(); //////

                if (choice == 1) { // if user wants to play then
                    System.out.println("This is the game where should find your dog, when you call it and the dog is next to you, you will hear its sound.");
                    System.out.println("Enter your name:"); // background information and inserting user's name and dog name
                    String playerName = scanner.nextLine(); // variable playerName is equal to input data
                    Player player = new Player(playerName); // creating player object with playerName name
                    System.out.println("Enter pet's name:"); ////
                    Pet dog = new Pet(scanner.nextLine()); // creating dog object with inserted name

                    PreparedStatement prst = connection.prepareStatement("insert into player(player_name) values(?)"); // creating a prepared statement with inserting data
                    prst.setString(1,playerName); // inserting name of the player into player table
                    prst.executeUpdate(); // execute the prepared statement

                    System.out.println("Timer starts now, you should find your pet as fast as possible!"); // about timer
                    player.setPosition(0, 0); // initializing player's initial position as x=0 and y=0

                    int positive = 1; // int variable positive which is equal to 1
                    if ((int)(Math.random() * 2) == positive) { // this part of code  initialize the initial position of your dog by random
                        dog.getPosition().setX((int)(Math.random() * 5)); /////////// random x from 0 to 4
                    }
                    else {
                        dog.getPosition().setX((int)(Math.random() * -5)); ///////////// random x from -4 to 0
                    }
                    if ((int)(Math.random() * 2) == positive) { /////////////
                        dog.getPosition().setY((int)(Math.random() * 5)); ///////////// random y from 0 to 4
                    }
                    else {
                        dog.getPosition().setY((int)(Math.random() * -5)); ////////////////// random y from -4 to 0
                    }

                    while(true){ //infinite loop
                        if (!isStarted){ // if isStarted is not false
                            isStarted = true; //then it is true
                            beginning = System.currentTimeMillis(); // a variable for timer
                        }
                        System.out.println("Your position: " + player.methodX() + ", " + player.methodY()); // output your current position
                        System.out.println("1.Go up"); // north way
                        System.out.println("2.Go down"); // south way
                        System.out.println("3.Go left"); // west way
                        System.out.println("4.Go right"); // east way
                        System.out.println("5.Call your pet (say Hey body!)"); // optionally calling your dog
                      //System.out.println("INFORMATION FOR DEVELOPER " + dog.getPosition().getX() + " " + dog.getPosition().getY());
                        int move = scanner.nextInt(); //variable move is equal to input data
                        scanner.nextLine(); ///
                        if (move == 1 && player.getPosition().getY() != 4){         //////////// this part of code is needed to make movements for a player
                            player.getPosition().setY(player.getPosition().getY() + 1); //going top
                        }
                        else if (move == 2 && player.getPosition().getY() != -4){ // going down
                            player.getPosition().setY(player.getPosition().getY() - 1); //////
                        }
                        else if (move == 3 && player.getPosition().getX() != -4){ // goind left
                            player.getPosition().setX(player.getPosition().getX() - 1); ///////
                        }
                        else if (move == 4 && player.getPosition().getX() != 4){  // going right
                            player.getPosition().setX(player.getPosition().getX() + 1); /////////
                        }
                        else if (move == 5){ //calling your dog
                            player.sound(); // saying HEY DOG!
                            if ((player.getPosition().getY() == dog.getPosition().getY() && (player.getPosition().getX() - dog.getPosition().getX() == 1)) || (player.getPosition().getY() == dog.getPosition().getY() && (dog.getPosition().getX() - player.getPosition().getX() == 1)) || (player.getPosition().getX() == dog.getPosition().getX() && (player.getPosition().getY() - dog.getPosition().getY() == 1)) || (player.getPosition().getX() == dog.getPosition().getX() && (dog.getPosition().getY() - player.getPosition().getY() == 1))){
                                dog.sound(); // if your dog is next to you, your dog will say gav gav
                            }
                            continue; // continue the loop
                        }
                        int player_id = 0; // this part of code is end when you find your pet
                        rss = stmt.executeQuery("select player_id from player where player.player_id = player_id"); //executing query
                        while (rss.next()){
                            player_id = rss.getInt("player_id"); // getting player's current id
                        }
                        if (player.getPosition().getX() == dog.getPosition().getX() && player.getPosition().getY() == dog.getPosition().getY()){ // if player and dog positions are the same
                            System.out.println("Your position: " + player.methodX() + ", " + player.methodY()); // output your position
                            System.out.println("You found your pet!"); // finding your pet
                            long time = (System.currentTimeMillis() - beginning)/1000; // score time
                            System.out.println("Your time is: " + time + " seconds"); // showing your time
                            PreparedStatement prst1 = connection.prepareStatement("insert into score(player_id, player_time) values(?,?)"); ///
                            prst1.setInt(1,player_id); ////
                            prst1.setLong(2,time); //and inserting this score into table score
                            prst1.executeUpdate(); ////
                            isStarted = false; ///
                            break; // stop the loop
                        }
                        int randomMove = (int)(Math.random() * 4); ///////// this part of code is needed to make random move for your dog
                        if (randomMove == 0 && dog.getPosition().getY() != 4){ // it depends on random move variable which is equal to from 0 to 3
                            dog.getPosition().setY(dog.getPosition().getY() + 1); // in this case going top
                        }
                        else if (randomMove == 1 && dog.getPosition().getY() != -4){ // in this case going down
                            dog.getPosition().setY(dog.getPosition().getY() - 1); /////
                        }
                        else if (randomMove == 2 && dog.getPosition().getX() != -4){ // in this case going left
                            dog.getPosition().setX(dog.getPosition().getX() - 1); ///////
                        }
                        else if (randomMove == 3 && dog.getPosition().getX() != 4){ // in this case going right
                            dog.getPosition().setX(dog.getPosition().getX() + 1); ///////
                        }
                        player_id = 0; // this part of code is the end when your pet find you
                        rss = stmt.executeQuery("select player_id from player where player.player_id = player_id"); //executing query
                        while (rss.next()){
                            player_id = rss.getInt("player_id"); // getting current player's id
                        }
                        if (player.getPosition().getX() == dog.getPosition().getX() && player.getPosition().getY() == dog.getPosition().getY()){ // if player and dog positions are the same
                            System.out.println("Your position: " + player.methodX() + ", " + player.methodY()); // output your position
                            System.out.println("You found your pet!"); // finding your pet
                            long time = (System.currentTimeMillis() - beginning)/1000; // score time
                            System.out.println("Your time is: " + time + " seconds"); // showing your time
                            PreparedStatement prst1 = connection.prepareStatement("insert into score(player_id, player_time) values(?,?)"); ////
                            prst1.setInt(1,player_id); ////
                            prst1.setLong(2,time); //and inserting this score into table score
                            prst1.executeUpdate(); ////
                            isStarted = false; ///
                            break; // stop the loop
                        }
                    }
            }
                else if (choice == 2){ // condition, if choice is equal to 2
                    rs = stmt.executeQuery("select player.player_id, player_name, player_time from player\n" +
                            "full outer join score on player.player_id = score.player_id order by player_time asc"); //executing sql query
                    while (rs.next()){
                        System.out.println("ID: " + rs.getInt("player_id") + "     Name: " + rs.getString("player_name") + "     Time: " + rs.getFloat("player_time") + " seconds."); // output these statements
                    }
                }
                else if (choice == 3){ // otherwise when it is 3
                    System.exit(0); // then exit the program
                }
            }catch (Exception e){ // if there is an exception then catch and output it
                System.out.println(e); /////
            }
        }
    }
}
