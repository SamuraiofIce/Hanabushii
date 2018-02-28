////////Hanabushii, Java Game.java////////
//Based on RealTutsGML's Java Game tutorial//
//  https://youtu.be/hXImR8Wm53M   //
//Abridged by Andy Honey (GPAL13)//


/////////Important notes://////////
//I will assume that you check what things are set to by default at the top here.
//You don't have to memorize the declarations, but be aware that most of them should be defaulted at the top
//Basically, if you don't understand a reference to an object or variable, check the defaults near the top
//I'm making things more complicated than necessary. Sorry.


//Importing all of the necessary libraries, packages, etc. Some of these could probably be cleaned up a bit.
package com.game.src.main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JFrame;
import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;
import com.game.src.main.classes.EntityC;
import com.game.src.main.classes.EntityD;
import com.game.src.main.classes.EntityE;


//Declaring the game class. Basically this just prototypes a game window and tells
//it what it will look like as a window.
public class Game extends Canvas implements Runnable{
	
    //Window Stats. Pretty self explanatory
    public static final int WIDTH = 320;
    public static final int HEIGHT = WIDTH / 12 * 9;
    public static final int SCALE = 2;
    public static String Title = "Hanabushii";
    //Actually, not 100% on what the thread does, but the running bool is for telling it when it should close the window.
    private boolean running = false;
    private Thread thread;
    
    //Delcaring our RNG
    private Random rand = new Random();
    //All of the images such as the spritesheet and such. We'll tell the code where they are later.
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
    private BufferedImage spriteSheet = null;
    private BufferedImage background = null;
    private BufferedImage titleBack = null;
    private BufferedImage groundBlock = null;
    private BufferedImage statBar = null;
    
    //A few game variables that we'll need.

	//These should be booleans, but aren't?
	public static int helpDisplay = 0;
    private int is_shooting = 0;
    
    //Keeps track of score and used for enemy spawning
    public int player_score = 0;
    public int enemy_count = 0;
    public int enemy_killed = 0;
    public String scoreString = "0";
    
    //Tells it whether or not to make new floating platforms.
    public boolean terrainGen = false;

	//Your basic player stats.
    public static double HEALTH = 100;
    public static double MANA = 116;
    
    //Used to keep track of jumps so that you only get two jumps
    public static int JUMP = 0;
    public static int slideBuffer = 0;
    
    //Initiating all of the classes that we need here.
    private Player p;
    private Maps map;
    private Controller c;
    private Textures tex;
    private Menu menu;
    private Pause_Menu pauseMenu;
    private Game_Over gameOver;
    private Cutscene cutScene;
    public TextBubble textBubble;
    
    private BufferedImageLoader loader;
    
    //I'll make my own damn orange, thank you.
    private Color custom_orange;
    
    public int textBox = 0;

    //These are lists containing all of the game objects.
    public LinkedList<EntityA> ea;
    public LinkedList<EntityB> eb;
    public LinkedList<EntityC> ec;
    public LinkedList<EntityD> ed;
    public LinkedList<EntityE> ee;
    
    
    //Camera controls
    public int cameraX = 0;
    public int cameraY = 0;
    
    
    //Ok, so these are states. This is how we make a menu system work
    public static enum STATE {
    	MENU,
    	GAME,
    	GAME_OVER,
    	CUT_SCENE,
    	PAUSE_MENU,
    }
    
    //This one keeps track of the magic in use.
    public static enum AURA {
    	DEFAULT,
    	TRIPLE_K,
    	INVINCIBILITY,
    }
    
    //Tells us how to default these states.
    public static STATE State = STATE.MENU;
    public static AURA Aura = AURA.DEFAULT;
    
    //Object prototypes for the purple fire highliting the buttons
    private Purplefire downFire;
    private Purplefire upFire;
    private Purplefire leftFire;
    private Purplefire rightFire;
    
    //CutsceneStuff
    public static int csNumber;
    
    
    //The wizard of the program.
    //init basically delcares everything that we need, and links us to all of our stuff.
    //It's like a compiler within the game, of sorts.
    public void init(){
    	
    	//So this is basically giving us an object to handle images.
    	loader = new BufferedImageLoader();
    	//So this will crash us and send us an error report telling us that it couldn't find our images
    	//Or it will load the images and work perfectly fine.
    	try{
    		spriteSheet = loader.loadImage("/res/sprite_sheet.png");
    		background = loader.loadImage("/res/Background 3.png");
    		titleBack = loader.loadImage("/res/Background 3.png");
    		statBar = loader.loadImage ("/res/StatBar2.png");	
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	
    	//This takes the image spriteSheet declared above, and makes an object to make working with images cleaner.
    	new SpriteSheet(spriteSheet);
    	
    	//This tells the window to read the mouse and keyboard input
    	this.addKeyListener(new KeyInput(this));
    	this.addMouseListener(new MouseInput());
    	
    	//Ok, so the function below will start up a song and play it, but it's commented
    	//because I don't want to screw with it right now.
    	//Sound.playSound("Someday.wav");
    	
    	//This tells us what all of these instances declared before the init mean.
    	tex = new Textures (this);
    	c = new Controller(tex, this, p);
    	p = new Player (30, 376, tex, this, c);
    	custom_orange = new Color (255, 128, 0);
    	c = new Controller(tex, this, p);
    	menu = new Menu();
    	pauseMenu = new Pause_Menu();
    	gameOver = new Game_Over();
    	ea = c.getEntityA();
    	eb = c.getEntityB();
    	ec = c.getEntityC();
    	ed = c.getEntityD();
    	ee = c.getEntityE();
    	
    	map = new Maps(1, c, this, tex);
    	
    	//This creates the first enemies, based on the enemy_count variable, which can be modded above somewhere
    	//c.createEnemy(enemy_count);
    		
    	
    }
    
    
    
    



	//Start
    private synchronized void start()
    {
       if(running)
           return;
        
        running = true;
        thread = new Thread(this);
        thread.start();
    } 
    
    //Stops the program and closes everything cleanly.
    private synchronized void stop()
    {
    	//I don't actually know if this is useful, as I don't know what the hell it is.
        if(!running)
            return;
        running = false;
        //Tries close it cleanly, or tells us that something went wrong when closing it.
        try
        {
            thread.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();   
        }
        //Basically closes things.
        System.exit(1);
    }
    
    //What to do while the program is running
    public void run()
    {
      //Tells it to run the init function so that the game can start. You could copy and paste the init function here,
      //but it looks cleaner up there.
      init(); 
      	
      //Alright, some new variables for this function.
      //Basically just finding time, and converting to 20 ticks per second.
      long lastTime = System.nanoTime();
      final double amountOfTicks = 60;
      double ns = 1000000000 / amountOfTicks;
      double delta = 0;
      int updates = 0;
      int frames = 0;
      long timer = System.currentTimeMillis();
      
      //As long as the game is running, this gets our time and number of updates
      while (running)
      {
      	long now = System.nanoTime();
      	delta += (now - lastTime) / ns;
      	lastTime = now;
      	if (delta >=1){
      		//Run the tick method
      		tick();
      		updates ++;
      		delta--;
      }
      
      //Tells the game to draw everything that needs drawing
      render();
      //If the game begins to run slowly, this won't update as fast.
      frames++;
        
      //Prints (to the console) the system ticks, fps, and player score once every second
        if(System.currentTimeMillis() - timer > 1000){
        	timer += 1000;
        	System.out.println(updates + " Ticks, FPS" + frames + "  Score: " + player_score + "       " + p.stance);
        	updates = 0;
        	frames = 0;
        	//c.addEntity(new Orb(10, 10, tex, this, c, 0));
        }
     

       }
      //Once running is no longer true, run the stop method
      stop();
    }
    
    

	//Do all of the updates and checks for certain states. It should do this 60 times per second as declared in the run method 
    private void tick(){
    	
    	//If your mana goes above 116, it becomes 116. Basically just a mana cap
    	if (MANA > 116)
    	{
    		MANA = 116;
    	}

		//Used for auto mana regen. 6 Mana per second at this rate.
		//This is also why it is declared a double even though we cast it as an int later
    	MANA += 0.01;
    	HEALTH += 0.001;
    	
    	//Ok, so here we start with game states from the enum near the top.
    	//These things will determine whether or not we're in a menu, unpaused, etc.
    	//This one is for unpaused gameplay.
    	if(State == STATE.GAME)
    	{
    	
    		//Tells the player and controller objects to update
    		p.tick();
    		c.tick();
    		
    		//Lowers the shooting buffer. Again, could be a boolean, but it isn't.
    		//Since shooting sets this to 1, and it won't shoot if it's greater than 0, it limits you to
    		//Shooting 60 times per second. Basically stops you from turbo buttoning, or holding the button to
    		//shoot at obnoxious speeds.
    		is_shooting -= 1;


			//Auto Enemy Spawn    	
    		//This is important because it tells us how to handle enemy spawns.
    		//If the number of enemies killed is equal to or greater than the number of enemies that we last spawned,
    		//This adds 2 enemies to the number that should spawn, resets kills, and spawns new ones.
    		//EnemySpawn
    		/*if(enemy_killed == enemy_count)
    		{
    			//enemy_count +=3;
    			enemy_count +=1;
    			enemy_killed = 0;
    			c.createEnemy(enemy_count);
    			//enemy_count = 0;
    		}*/
    		if (enemy_count > 4)
    		{
    			enemy_count = 4;
    			//enemy
    		}
    		
    		//If your health drops to 0 or lower, sets the game to a gameover state
    		if (HEALTH <= 0)
    		{
    			State = STATE.GAME_OVER;
    		}
    		
    		//If the terrain is not spawned, creates 4 new terrain blocks with semi random positions.
    		if (terrainGen == false)
    		{
    			map.makeMap();
    		/*
    		//Basically, the x and y coordinates of 4 prototype controlled blocks are taken, and added to by a random ammount.
    		c.addEntity(new GroundBlock(40 + rand.nextInt(200), 400 - rand.nextInt(5), tex, c, this));
    		c.addEntity(new GroundBlock(300 + rand.nextInt(200), 400 - rand.nextInt (5), tex, c, this));
    		c.addEntity(new GroundBlock(rand.nextInt(200), 200 + rand.nextInt(100), tex, c, this));
    		c.addEntity(new GroundBlock(200 + rand.nextInt(200), 200 + rand.nextInt(100), tex, c, this));
    		c.addEntity(new GroundBlock(400 + rand.nextInt(200), 200 + rand.nextInt(100), tex, c, this));
    		//Tells it that it has spawned the terrain so that it limits it to four.
    		terrainGen = true;
    		*/
    		}
    		
    		//If your mana is less than or equal to 0, it sets the player's magic state to no magic
    		if (MANA <= 0)
			{
				Aura = AURA.DEFAULT;
				
				//Removing the fires that show what aura is on.
				c.removeEntity(upFire);
				c.removeEntity(downFire);
				c.removeEntity(leftFire);
				c.removeEntity(rightFire);
				upFire = null;
				downFire = null;
				leftFire = null;
				rightFire = null;
				p.Form = 1;
			}
			
			//If the player is using invincibility, this drains their magic to prevent it from going indefinitley.
			if (Aura == AURA.INVINCIBILITY)
			{
				MANA -= 0.2;
			}
			if (p.getForm() == 0)
				MANA -= 0.8;
    		
    		
    	}
    	
    	//What happens upon gameover.
    	if (State == STATE.GAME_OVER)
    		{
    			//Sets the player's velocity back to 0 so that they don't move upon starting anew
    			p.setVelX(0);
    			p.setVelY(0);
    			//Score to 0
    			player_score = 0;
    			//Sets the player back to default position because a new game must be started.
    			p.setX(0);
    			p.setY(336);
    			
    			//Sets the enemies spawned to 0, as well as the enemies killed. Because they're equal,
    			//it'll add two to the count and then spawn 2 upon starting a new game
    			enemy_count = 0;
    			enemy_killed = 0;
    			
    			//Clears those lists of entities that we declared earlier. This will despawn all enemies, kunai, and blocks
    			eb.clear();
    			ea.clear();
    			ec.clear();
    			ed.clear();
    			
    			//Tells the game that we got rid of the terrain
    			terrainGen = false;
    			
    			//Mana full for the next game
    			MANA = 116;
    			p.stance = 1;
    			
    			//Ok, so let me explain this shit. Before I knew that you could just clear the linked lists, I tried to kill
    			//all enemies from the game by creating 600 kunai to wipe them all out. So, learn from my stupidity.
    			//This obviously didn't work.
    			/*for (int i = 0; i < 600; i++)
    			{
    			for (int s = 300; s < 324; s ++ )
    			{
    				c.addEntity(new Kunai(i, s, tex, this, 5));
    			}
    			}*/
    		}
    		
    		if(State == STATE.CUT_SCENE)
    		{
    		
    		}
    		
    		//If you aren't in one of these two states, the game does not update the player or controller. So things can't move
    		
    		
    }
    
    //Now let's get into graphics.... Ugh...
    private void render(){
    	
    	//Creates bs... yep. Remind me to figure out wtf this means.
		BufferStrategy bs = this.getBufferStrategy();
	
		if(bs == null){
		
			createBufferStrategy(3);
			return;
		
		}
		

		//Ok, so this... See last comment.
		Graphics g = bs.getDrawGraphics();
		
		//Hey, here's something fun to do. Try it. I dare you... do it.
		//comment out next line and the g.dispose and bs.show
		//Oh yeah, and this tells it to draw a blank image to draw others over.
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		
			
		
		//If the game is unpaused, draw enemies and such. Otherwise, the player and enemies will not be drawn
		//But they aren't deleted. Just invisible
    	if(State == STATE.GAME)
    	{
    		//Do not change the order of these. They're carefully positioned because layers...
    		//This one draws the background, also because the image was accidentally made to small
    		//(and i'm to lazy to change it), we draw it over 5 so that it's centered.
    		g.drawImage(background, 5 + cameraX / 2, cameraY, null);
    		g.drawImage(background, 645 + cameraX / 2, cameraY, null);
    		g.drawImage(background, -635 + cameraX / 2, cameraY, null);
    	
    		//Draw the player
    		p.render(g);
    	
    		//Draw the enemies and kunai (they're controlled by the controller class)
    		c.render(g);
    		
    		//Makes a green rectangle who's length is dependent on the current health.
    		g.setColor(Color.green);
    		g.fillRect(0, 0, (int) HEALTH * 7, 10);
    		
    		//Makes a rectangle who's length is dependent on the current mana.
    		g.setColor(Color.blue);
    		g.fillRect(414, 10, (int) MANA * 2, 30);
    		
    		//Draws the overlay over these bars to hid the fact that they're just rectangles. Makes it look nice.
			g.drawImage(statBar, 5, 0, null);
			
			//Creates and sets an new font to use for the score counter.
			Font fnt0 = new Font("serif", Font.BOLD, 35);
			g.setFont(fnt0);
			g.setColor(Color.orange);
			
			//Creates a string object equal to the score, and draws it
    		scoreString = String.valueOf(player_score);
    		g.drawString (scoreString, 330, 38);
    		
    	}
    	
    	//What to draw on the Title menu
    	else if(State == STATE.MENU)
    	{
    		//This is the background used on the title screen.
    		g.drawImage(titleBack, 5, 0, null);
    		
    		//renders the menu object.
    		menu.render(g);
    		
    		//AGAIN WITH MY USING INTEGERS INSTEAD OF BOOLEANS. GOD I'M STUPID
    		//If you click help, the help menu appears or dissapears to show the controls.
    		if (helpDisplay == 1)
    		{
    		
    		//All of this is aesthetic stuff. Just setting fonts, colors, and positions using rectangles to create a nice
    		//looking box to keep it in.
   			g.setColor(Color.black);
    		g.fillRect (175, 225, 300, 80);
    		Font fnt0 = new Font("serif", Font.BOLD, 20);
			g.setFont(fnt0);
			g.setColor(custom_orange);
    		g.drawString ("Movement: WASD (or arrows)", 195, 250);
    		g.drawString ("Kunai: SPACE", 250, 270);
    		g.drawString ("MENU: SHIFT", 250, 290);
    		
    		
    		}
    	}
    	
    	//Basically a copy of the menu
    	else if(State == STATE.PAUSE_MENU)
    	{
    		
    		g.drawImage(titleBack, 5, 0, null);
    		pauseMenu.render(g);
    		if (helpDisplay == 1)
    		{
   			g.setColor(Color.black);
    		g.fillRect (175, 225, 300, 80);
    		Font fnt0 = new Font("serif", Font.BOLD, 20);
			g.setFont(fnt0);
			g.setColor(custom_orange);
    		
    		
    		g.drawString ("Movement: WASD (or arrows)", 195, 250);
    		g.drawString ("Kunai: SPACE", 250, 270);
    		g.drawString ("MENU: SHIFT", 250, 290);
    		
    		}
    	}
    	
    	//Basically the menu, but for gameover.
    	else if(State == STATE.GAME_OVER)
    	{
    		
    		g.drawImage(titleBack, 5, 0, null);
    		gameOver.render(g);
    	}
    	
    	//renders text boxes
    	if (textBox == 1)
    	{
    		textBubble = new TextBubble();
    		textBubble.render(g);
    	}
    	
    	//Renders Cutscenes
    	else if(State == STATE.CUT_SCENE)
    	{
    	
    	}
    	
    	//No idea what these do. Sound useless, right? Besides, I told you earlier to comment them out...
    	g.dispose();
    	//Oh my god, don't you dare run it. I know you did it...
		bs.show();
		//DO
    	//NOT
    	//DO IT.
    	//IT WILL CRASH YOUR GRAPHICS CARD
    	//DON'T YOU DARE
    	//DON'T BE LIKE ME. YEP. I DID IT ON A SCHOOL COMPUTER.
    }	
    
    //This should be at the top?
    int jumpBuffer = 0;
    
    //Controls
    public void keyPressed(KeyEvent e)
	{
		//Gets keypresses
		int key = e.getKeyCode();
		
		//Can't use certain controls in certain states.
		//Also loses control when in hitstun.
    	if(State == STATE.GAME)
    	{
    		if (p.hitStun <= 240 || p.inControl)
    		{
    		//Keyboard Commands
    		//Movement
    		if(key == KeyEvent.VK_D && p.stance > 0)
    		{
    			//sets the player's direction string to right. used for animation telling it render his right facing sprite
    				p.setPlayerDirection("Right 1");
    				
    				//Set his velocity, cancelling movement, and then set it to the right at 5 speed.
    				//p.setVelX(0);
    				if(p.getVelX() < 5)
    				p.setVelX(5);
    				/*if (p.getVelX() < 8)
    					p.setVelX(8);
    				else
    					p.setVelX(p.getVelX() + 2);*/
    					
    					
    				//tells it to render his walking animation
    			p.setPlayerWalking(true);
    			p.friction = false;
    		}
    		else if (key == KeyEvent.VK_A && p.stance > 0)
    		{
    			//basically above except to the left
    			p.setPlayerDirection("Left 1");
    			//p.setVelX(0);
    			
    			if (p.getVelX() > -5)
    			p.setVelX(-5);
    			
    			/*if (p.getVelX() > -8)
    				p.setVelX(-8);
    			else
    				p.setVelX(p.getVelX() - 2);*/
    			
    			
    			p.setPlayerWalking(true);
    			p.friction = false;
    			//p.setX(p.getX() - 5);
    		}
    		
    		//Down
    		else if (key == KeyEvent.VK_S)
    		{
    			if (slideBuffer <= 0 && p.getPlayerDirection().equals("Right 1"))
    			{
    				//slideBuffer = 60;
    				p.setVelX(p.getVelX() + 25);
    			}
    			
    			else if (slideBuffer <= 0 && p.getPlayerDirection().equals("Left 1"))
    			{
    				p.setVelX(p.getVelX() - 25);
    			}
    			//sets his velocity on the y axis at 5 (which is downwards
    			//p.setVelY(5);
    			//Tells him to ignore the block collision
    			//p.setX (p.getX() + 20);
    			//p.setBlockGrav(false);
    			//p.setY(p.getY() + 5);
    			p.stance = 0;
    			//slideBuffer --;
    			if (p.hitStun < 0 && slideBuffer <= 0)
    			{
    				p.hitStun = 30;
    				slideBuffer = 60;
    			}
    			//JUMP = 0;
    			p.friction = true;
    			
    		}
    		else if (key == KeyEvent.VK_W && p.stance > 0)
    		{
    			//If he's jumped 2 times, this button does nothing
    			if (JUMP < 2)
    			{
    				//Sets his ariel speed
    				//p.setX(p.getX() + 1);
    				p.setVelY(-15);
    				//Adds to the jump count
    				JUMP ++;
    				//Tell the player object to render his jumping animation
    				p.stance += 1;
    			} 
    		}
    		//Magic
    		//TRIPLE KUNAI
    		if(key == KeyEvent.VK_RIGHT)
    		{
    			if (Aura == AURA.TRIPLE_K)
    				Aura = AURA.DEFAULT;
    			else
    				Aura = AURA.TRIPLE_K;
    			/*if (rightFire == null){
    				rightFire = new Purplefire (565, 360, tex, this, c);
    				c.addEntity(rightFire);
    			}		
    			else
    			{
    				c.removeEntity(rightFire);
    				rightFire = null;
    			}*/
    		}
    		//INVINCIBILITY
    		else if (key == KeyEvent.VK_DOWN)
    		{
    			if (Aura == AURA.INVINCIBILITY)
    				Aura = AURA.DEFAULT;
    			else
    				Aura = AURA.INVINCIBILITY;
    			/*if (downFire == null){
    				downFire = new Purplefire (531, 385, tex, this, c);
    				c.addEntity(downFire);
    			}	
    			else
    			{
    				c.removeEntity(downFire);
    				downFire = null;
    			}*/
    		}
    		else if (key == KeyEvent.VK_UP)
    		{
    			/*if (p.Form == p.FORM.SAMURAI)
    			{
    			p.Form = p.FORM.ENEMY;
    			MANA -= 10;
    			}
    			else
    			{
    				p.Form = p.FORM.SAMURAI;
    			}*/
    			if (p.getForm() == 0)
    				p.setForm(1);
    			else if (p.getForm() == 1)
    				p.setForm(0);
    			MANA -=10;
    		}
    		//Shoot
    		else if(key == KeyEvent.VK_SPACE && is_shooting <= 0){
    			is_shooting = 10;
    			if (p.playerDirection.equals("Left 1"))
    			{
    				if (Aura == AURA.TRIPLE_K)
    				{
    					c.addEntity(new Kunai(p.getX() + 0, p.getY()+10, tex, this, -20, "Left 1", c));
    					c.addEntity(new Kunai(p.getX() + 0, p.getY()-10, tex, this, -20, "Left 1",c));
    					c.addEntity(new Kunai(p.getX() + 0, p.getY(), tex, this, -20, "Left 1",c));
    					MANA -= 15;
    				}
    				else
    				 c.addEntity(new Kunai(p.getX() + 0, p.getY(), tex, this, -20, "Left 1", c));
    			}
    			else if (p.playerDirection.equals("Right 1"))
    			{	
    				if (Aura == AURA.TRIPLE_K)
    				{
    					c.addEntity(new Kunai(p.getX() + 0, p.getY() + 10, tex, this, 20, "Right 1", c));
    					c.addEntity(new Kunai(p.getX() + 0, p.getY() - 10, tex, this, 20, "Right 1", c));
    					c.addEntity(new Kunai(p.getX() + 0, p.getY(), tex, this, 20, "Right 1", c));
    					MANA -= 15;	
    				}
    				else
    					c.addEntity(new Kunai(p.getX() + 0, p.getY(), tex, this, 20, "Right 1", c));
    			
    			}
    		}
    		else if(key == KeyEvent.VK_SHIFT){
    			State = STATE.PAUSE_MENU;
    			
    		}
    		
    		//Fire
    		else if(key == KeyEvent.VK_LEFT && is_shooting <= 0 && MANA > 20){
    			is_shooting = 10;
    			if (p.playerDirection.equals("Left 1"))
    			{
    				 c.addEntity(new Fire(p.getX() + 0, p.getY(), tex, this, -20, "Left 1", c));
    			} 
    			else if (p.playerDirection.equals("Right 1"))
    			{	
    				c.addEntity(new Fire(p.getX() + 0, p.getY(), tex, this, 20, "Right 1", c));
    			
    			}
    			MANA -= 20;
    		}
    		//End of Fire Code
    		}
    	}
    	
    	//PAUSE MENU BUTTONS
    	else if (State == STATE.PAUSE_MENU)
    	{
    		if(key == KeyEvent.VK_SHIFT){
    			State = STATE.GAME;
    			
    		}
    	}
	
	}
	
	
	/*public void keyHeld(KeyEvent e)
	{
		//Gets keypresses
		int key = e.getKeyCode();
		
		//Can't use certain controls in certain states.
		//Also loses control when in hitstun.
    	if(State == STATE.GAME)
    	{
    		if (p.hitStun <= 240 || p.inControl)
    		{
    		//Keyboard Commands
    		//Movement
    		if(key == KeyEvent.VK_D && p.stance > 0)
    		{
    		
    		}
    		else if (key == KeyEvent.VK_A && p.stance > 0)
    		{
    			
    		}
    		
    		//Down
    		else if (key == KeyEvent.VK_S)
    		{
    			
    		}
    		
    		//Shoot
    		else if(key == KeyEvent.VK_SPACE && is_shooting <= 0){
    			is_shooting = 10;
    			if (p.playerDirection.equals("Left 1"))
    			{
    				if (Aura == AURA.TRIPLE_K)
    				{
    					c.addEntity(new Kunai(p.getX() + 0, p.getY()+10, tex, this, -20, "Left 1", c));
    					c.addEntity(new Kunai(p.getX() + 0, p.getY()-10, tex, this, -20, "Left 1",c));
    					c.addEntity(new Kunai(p.getX() + 0, p.getY(), tex, this, -20, "Left 1",c));
    					MANA -= 15;
    				}
    				else
    				 c.addEntity(new Kunai(p.getX() + 0, p.getY(), tex, this, -20, "Left 1", c));
    			}
    			else if (p.playerDirection.equals("Right 1"))
    			{	
    				if (Aura == AURA.TRIPLE_K)
    				{
    					c.addEntity(new Kunai(p.getX() + 0, p.getY() + 10, tex, this, 20, "Right 1", c));
    					c.addEntity(new Kunai(p.getX() + 0, p.getY() - 10, tex, this, 20, "Right 1", c));
    					c.addEntity(new Kunai(p.getX() + 0, p.getY(), tex, this, 20, "Right 1", c));
    					MANA -= 15;	
    				}
    				else
    					c.addEntity(new Kunai(p.getX() + 0, p.getY(), tex, this, 20, "Right 1", c));
    			
    			}
    		}
    		
    		
    	
    		}
    	}
	
	}*/
	
	
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if (State == STATE.GAME)
		{
			//Keyboard Commands
			if(key == KeyEvent.VK_D)
			{
				tex.direction = 1;
				if (p.getVelX() > 10)
					p.setVelX(10);
				//p.setX(p.getX() + 5);
				p.setPlayerWalking(false);
				p.friction = true;
			}
			else if (key == KeyEvent.VK_A)
			{
				tex.direction = 2;
				if (p.getVelX() < -10)
					p.setVelX(-10);
				//p.setX(p.getX() - 5);
				p.setPlayerWalking(false);
				p.friction = true;
			}
			else if (key == KeyEvent.VK_S)
			{
				//p.setVelY(0);
				p.stance = 1;
				slideBuffer = 0;
				if (p.hitStun <= 60)
					p.hitStun = 0;
				//p.setVelX(0);
				
				//p.setY(p.getY() + 5);
				//for (i = 0; i < 20)
				//{
					
				//}
			}
			else if (key == KeyEvent.VK_W)
			{
				p.setVelY(0);
				p.setBlockGrav(true);
			}
			else if (key == KeyEvent.VK_SPACE){
				//is_shooting = false;
			}
		}
	
	}
    
    public static void main(String[] args)
    {
       Game game = new Game();
        
       game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT *    SCALE));
       game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
       game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        
       JFrame frame = new JFrame(Title);
       frame.add(game);
       frame.pack();
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setResizable(false);
       frame.setLocationRelativeTo(null);
       frame.setVisible(true);
       
       game.start();
    }    
    
    public BufferedImage getSpriteSheet()
    {
    	return spriteSheet;
    }
    
    public int getEnemy_count() {
		return enemy_count;
	}



	public void setEnemy_count(int enemy_count) {
		this.enemy_count = enemy_count;
	}



	public int getEnemy_killed() {
		return enemy_killed;
	}



	public void setEnemy_killed(int enemy_killed) {
		this.enemy_killed = enemy_killed;
	}

	public int getPlayer_score() {
		return player_score;
	}



	public void setPlayer_score(int player_score) {
		this.player_score = player_score;
	}
    
    public void setBackground(String newBack)
    {
    	if (newBack.equals("Ground"))
    	{
    		try{
    		background = loader.loadImage("/res/Background 3.png");	
    		}catch(IOException e){
    		e.printStackTrace();
    	}
    	}
    		
    }
    
}





