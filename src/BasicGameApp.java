//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable, KeyListener, MouseListener, MouseMotionListener {

   //Variable Definition Section
   //Declare the variables used in the program 
   //You can set their initial values too

   //Sets the width and height of the program window
	final int WIDTH = 1000;
	final int HEIGHT = 700;

   //Declare the variables needed for the graphics
	public JFrame frame;
	public Canvas canvas;
   public JPanel panel;
   
	public BufferStrategy bufferStrategy;
	public Image astroPic;

	public Image salmonPic;

	public Image elkpic;

   //Declare the objects used in the program
   //These are things that are made up of more than one variable type
	private Astronaut astro;
	private Astronaut jack;

	private Astronaut salmon;

	private Astronaut elk[]; //declared an array of elk



	public Image background;
	public Image winBackground;



	// Main method definition
   // This is the code that runs first and automatically
	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method  
	}


   // Constructor Method
   // This has the same name as the class
   // This section is the setup portion of the program
   // Initialize your variables and construct your program objects here.
	public BasicGameApp() {



      setUpGraphics();
	  canvas.addKeyListener(this);
      //variable and objects
      //create (construct) the objects needed for the game and load up 
		astroPic = Toolkit.getDefaultToolkit().getImage("bearpic.jpg"); //load the picture
		salmonPic = Toolkit.getDefaultToolkit().getImage("salmon.jpeg");
		elkpic = Toolkit.getDefaultToolkit().getImage("elk.jpg");
		winBackground = Toolkit.getDefaultToolkit().getImage("victorypic.jpeg");
		astro = new Astronaut(10,500);
		jack = new Astronaut(100,50);
		salmon = new Astronaut(40,300);
		elk = new Astronaut[10];
		// constructed array for the elks
		for (int x=0; x<elk.length;x++) {
			elk[x] = new Astronaut(50*x, 300-(10*x)); // filled each spot
		}
		jack.dx = 2;
		jack.dy = 3;
		salmon.dx = 2;
		salmon.dy = 4;
		for (int x=0; x<elk.length;x++) {
			elk[x].dx = 3;
			elk[x].dy = 3;
		}
		astro.dx = 5;
		astro.dy = 4;





	}// BasicGameApp()

   
//*******************************************************************************
//User Method Section
//
// put your code to do things here.

   // main thread
   // this is the code that plays the game after you set things up
	public void run() {

      //for the moment we will loop things forever.
		while (true) {

         moveThings();  //move all the game objects
         render();  // paint the graphics
         pause(20); // sleep for 10 ms


		}
	}


	public void moveThings()
	{
      //calls the move( ) code in the objects
	//	astro.move();
//		jack.move();
//		salmon.move();
		astro.wrap();
//		System.out.println(salmon.dx +",  "+ salmon.dy + " x" + salmon.xpos +" y " + salmon.ypos);

		jack.bounce();
		salmon.bounce();
		for (int x=0; x<elk.length;x++) {
			elk[x].bounce();  // put [x] everywhere
		}
		crash();
		
		//System.out.println(salmon.dx +",  "+ salmon.dy + " x" + salmon.xpos +" y " + salmon.ypos);

	}
	
   //Pauses or sleeps the computer for the amount specified in milliseconds
   public void pause(int time ){
   		//sleep
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {

			}
   }

   //Graphics setup method
   private void setUpGraphics() {
      frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.
   
      panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
      panel.setLayout(null);   //set the layout
   
      // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
      // and trap input events (Mouse and Keyboard events)
      canvas = new Canvas();  
      canvas.setBounds(0, 0, WIDTH, HEIGHT);
      canvas.setIgnoreRepaint(true);
	  canvas.addMouseListener(this);
	  canvas.addMouseMotionListener(this);
   
      panel.add(canvas);  // adds the canvas to the panel.
	    background = Toolkit.getDefaultToolkit().getImage("wildernessareas.jpg"); //load the picture

	   // frame operations
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
      frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
      frame.setResizable(false);   //makes it so the frame cannot be resized
      frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!
      
      // sets up things so the screen displays images nicely.
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      canvas.requestFocus();
      System.out.println("DONE graphic setup");
   
   }


	//paints things on the screen using bufferStrategy
	private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);

		g.drawImage(background,0,0,1000,700,null);

      //draw the image of the astronaut
		g.drawImage(astroPic, astro.xpos, astro.ypos, astro.width, astro.height, null);
		g.drawImage(astroPic, jack.xpos, jack.ypos, astro.width, astro.height, null);
		g.drawImage(salmonPic,salmon.xpos,salmon.ypos,astro.width,astro.height,null);
		for (int x=0; x<elk.length;x++) {
			g.drawImage(elkpic, elk[x].xpos, elk[x].ypos, astro.width, astro.height, null);
		}
		g.draw(new Rectangle(jack.xpos,jack.ypos,jack.width,jack.height));
		g.draw(new Rectangle(astro.xpos,astro.ypos,astro.width,jack.height));
		g.draw(new Rectangle(salmon.xpos,salmon.ypos,jack.width,jack.height));
		for (int x=0; x<elk.length;x++) {
			g.draw(new Rectangle(elk[x].xpos, elk[x].ypos, jack.width, jack.height));
		}
		g.drawString("First Bear to 10 Wins!!!",375,200);
		g.drawString("Jack Food Level Is:",375,500);
		g.drawString(Integer.toString(jack.foodLevel),500,500);
		g.drawString("Astro Food Level Is:",375,650);
		g.drawString(Integer.toString(astro.foodLevel),500,650);


		if (jack.foodLevel > 10){
			System.out.println("The Bears Win!!!");
			//g.clearRect(0, 0, WIDTH, HEIGHT);
			g.drawImage(winBackground,0,0,1000,700,null);
		}
		if (astro.foodLevel > 10){
			System.out.println("The Bears Win!!!");
		//	g.clearRect(0, 0, WIDTH, HEIGHT);
			g.drawImage(winBackground,0,0,1000,700,null);
		}







		g.dispose();

		bufferStrategy.show();
	}
	public void crash(){

		if(jack.rec.intersects(salmon.rec)){

			jack.dx = -1*jack.dx;
			jack.dy = -1* jack.dy;
			salmon.dx = -1*salmon.dx;
			salmon.dy = -1* salmon.dy;
			jack.foodLevel = jack.foodLevel+1;
//			System.out.println(jack.foodLevel);



		}
		if(astro.rec.intersects(salmon.rec)){

			astro.dx = -1*astro.dx;
			astro.dy = -1* astro.dy;
			astro.dx = -1*astro.dx;
			astro.dy = -1* astro.dy;
			astro.foodLevel = astro.foodLevel+1;
//			System.out.println(astro.foodLevel);



		}
		for (int x=0; x<elk.length;x++) {
			if (jack.rec.intersects(elk[x].rec)) {

				elk[x].dx = -1 * elk[x].dx;
				elk[x].dy = -1 * elk[x].dy;
				jack.dx = -1 * jack.dx;
				jack.dy = -1 * jack.dy;
				jack.foodLevel = jack.foodLevel + 1;


			}
		}
		for (int x=0; x<elk.length;x++) {
			if (astro.rec.intersects(elk[x].rec)) {

				elk[x].dx = -1 * elk[x].dx;
				elk[x].dy = -1 * elk[x].dy;
				astro.dx = -1 * astro.dx;
				astro.dy = -1 * astro.dy;
				astro.foodLevel = astro.foodLevel + 1;

			}
		}
		if(astro.rec.intersects(jack.rec)){

			jack.dx = -1*jack.dx;
			jack.dy = -1* jack.dy;
			astro.dx = -1*astro.dx;
			astro.dy = -1* astro.dy;


		}


	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		System.out.println(code);
		if (code == 83) {
			jack.dy = jack.dy*-1;
		}
		if (code == 87) {
			jack.dy = Math.abs(jack.dy)*-1;
		}
		if (code == 65) {
			jack.dx = jack.dx*-1;
		}
		if (code == 68) {
			jack.dx = jack.dx*-1;
		}
		if (code == 71) {
			jack.height = jack.height+10;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}






	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getX());
		astro.xpos=e.getX();
		astro.ypos=e.getY();
		astro.width = astro.width+10;

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
//		astro.xpos=e.getX();
//		astro.ypos=e.getY();
		if (e.getX()-astro.xpos>50) {
			astro.xpos=e.getX();
		}
		if (e.getY()-astro.ypos>50) {
			astro.ypos=e.getY();
		}
		if (e.getX()-jack.xpos>50) {
			jack.xpos=e.getX();
		}
		if (e.getY()-jack.ypos>50) {
			jack.ypos=e.getY();
		}

	}
}