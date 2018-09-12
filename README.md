# Robot Simulator
    * The application is a simulation of a toy robot moving on a square tabletop, of dimensions 5 units x 5 units.
    * There are no other obstructions on the table surface.
    * The robot is free to roam around the surface of the table, but must be prevented from falling to destruction. Any movement that would result in the robot falling from the table must be prevented, however further valid movement commands must still be allowed.
    
    Application accepts following commands:-

    PLACE X,Y,DIRECTION
    MOVE
    LEFT
    RIGHT
    REPORT
        
    Assumuptions:-
    * PLACE will put the toy robot on the table in position X,Y and facing NORTH, SOUTH, EAST or WEST.
    * The origin (0,0) can be considered to be the SOUTH WEST most corner
    * The first valid command to the robot is a PLACE command, after that, any sequence of commands may be issued, in any order, including another PLACE command. The application should discard all commands in the sequence until a valid PLACE command has been executed.
    * MOVE will move the toy robot one unit forward in the direction it is currently facing.
      
    Example:
    * PLACE 0,0,NORTH
      MOVE
      REPORT
      Expected output:
          0,1,NORTH
    * PLACE 1,2,EAST
      MOVE 
      MOVE
      LEFT
      MOVE
      REPORT
      Expected output:
          3,3 NORTH
    

## Usage

    You need to install (https://leiningen.org/) and clojure (https://clojure.org/)
    
    To run application
        
        lein run 
    
    To run tests
        
        lein tests 
        
    $ java -jar robo-move-0.1.0-standalone.jar [args]


### Bugs

...



## License

Copyright Anish Patil © 2018 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
