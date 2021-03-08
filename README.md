# EndT-project
Diyar Mukhidenov IT-2004

This game about user and pet. Your pet is lost, and you must find it.
The dog spawns randomly and can go through the map randomly.
Your initial position is x = 0 and y = 0.
On every position you can call your pet, and if it is near to you will get response with “GAV GAV” which means that your dog is close.
There is a score table, and you should find your pet as fast as possible in order to get first place.

The code also includes classes such as Point, Entity, Player and Pet.

The main code is executed in class main with database, and gameitself.
Class Point is created in order to create x y coordinate plain where player and dog can move.
Class Entity is parent class of subclasses Player and Pet. This class also helps us to make polymorphism and inheritance.
Class Player is needed to create player object and play the game. It is subclass of Entity.
Class Pet is needed to create dog object and create your target which is needed to be catch. Your dog is walking randomly. 
