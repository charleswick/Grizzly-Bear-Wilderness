import java.awt.*;

/**
 * Created by chales on 11/6/2017.
 */
public class Astronaut {

    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public String name;                //holds the name of the hero
    public int xpos;                //the x position
    public int ypos;                //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;
    public int height;
    public boolean isAlive;   //a boolean to denote if the hero is alive or dead.

    public boolean isCrashing;
    public Rectangle rec;

    public int foodLevel;



    // METHOD DEFINITION SECTION

    // Constructor Definition
    // A constructor builds the object when called and sets variable values.


    //This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.
    public Astronaut(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        dx =1;
        dy =1;
        width = 60;
        height = 60;
        foodLevel = 0;
        isAlive = true;
        isCrashing = false;
        rec = new Rectangle(xpos,ypos,height,width);



    } // constructor

    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {
        xpos = xpos + dx;
        ypos = ypos + dy;

        rec = new Rectangle(xpos,ypos,height,width);


    }
    public void wrap(){
        xpos = xpos + dx;
        ypos = ypos + dy;
        if (xpos > 1000){
            xpos = 0;}
        if (xpos < 0){
            xpos = 999;}
        if  (ypos > 700){
            ypos = 0;}

        rec = new Rectangle(xpos,ypos,height,width);


    }
    public void bounce(){

        if  (ypos > 500){
//            System.out.println(dy);

            dy = -1*dy;
//            System.out.println(dy);

        }
        if  (ypos < 1){
//            System.out.println("ji1");
            dy = -1*dy;
        }
        if  (xpos > 500){
            dx = -1*dx;
        }
        if  (xpos < 1){
            dx = -1*dx;
        }

        xpos = xpos + dx;
        ypos = ypos + dy;
        rec = new Rectangle(xpos,ypos,height,width);

    }
}






