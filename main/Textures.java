package com.game.src.main;

import java.awt.image.BufferedImage;

public class Textures {
	
	public BufferedImage[] playerL1 = new BufferedImage[3];
	public BufferedImage[] playerR1 = new BufferedImage[3];
	public BufferedImage[] playerJumpL = new BufferedImage [1];
	public BufferedImage[] playerJumpR = new BufferedImage [1];
	public BufferedImage[] playerCrouchL = new BufferedImage [1];
	public BufferedImage[] playerCrouchR = new BufferedImage [1];
	public BufferedImage[] kunaiL = new BufferedImage[3];
	public BufferedImage[] kunaiR = new BufferedImage[3];
	public BufferedImage[] enemy = new BufferedImage[3];
	public BufferedImage[] petal = new BufferedImage[3];
	public BufferedImage[] groundBlock = new BufferedImage[1];
	public BufferedImage[] fire = new BufferedImage[3];
	public BufferedImage[] purpleFire = new BufferedImage[3];
	public BufferedImage[] jumpSpinL = new BufferedImage[3];
	public BufferedImage[] jumpSpinR = new BufferedImage[3];
	public BufferedImage[] orb = new BufferedImage[1];
	
	//Copy line above with stuff below.
	
	private SpriteSheet ss;
	
	int direction = 1;
	
	public Textures (Game game){
		ss = new SpriteSheet(game.getSpriteSheet());
		
		getTextures();
		
	}
	
	private void getTextures(){
		playerL1[0] = ss.grabImage(1, 2, 64, 64);
		playerL1[1] = ss.grabImage(2, 2, 64, 64);
		playerL1[2] = ss.grabImage(3, 2, 64, 64);
		playerR1[0] = ss.grabImage(1, 1, 64, 64);
		playerR1[1] = ss.grabImage(2, 1, 64, 64);
		playerR1[2] = ss.grabImage(3, 1, 64, 64);
		enemy[0] = ss.grabImage(1, 3, 64, 64);
		enemy[1] = ss.grabImage(2, 3, 64, 64);
		enemy[2] = ss.grabImage(3, 3, 64, 64);
		kunaiL[0] = ss.grabImage(4, 2, 64, 64);
		kunaiL[1] = ss.grabImage(4, 2, 64, 64);
		kunaiL[2] = ss.grabImage(4, 2, 64, 64);
		kunaiR[0] = ss.grabImage(4, 1, 64, 64);
		kunaiR[1] = ss.grabImage(4, 1, 64, 64);
		kunaiR[2] = ss.grabImage(4, 1, 64, 64);
		petal[0] = ss.grabImage (1, 5, 64, 64);
		petal[1] = ss.grabImage (1, 5, 64, 64);
		petal[2] = ss.grabImage (1, 5, 64, 64);
		groundBlock[0] = ss.grabImage (1, 4, 64, 64);
		fire[0] = ss.grabImage (2, 5, 64, 64);
		fire[1] = ss.grabImage (3, 5, 64, 64);
		fire[2] = ss.grabImage (4, 5, 64, 64);
		purpleFire[0] = ss.grabImage (5, 5, 64, 64);
		purpleFire[1] = ss.grabImage (6, 5, 64, 64);
		purpleFire[2] = ss.grabImage (7, 5, 64, 64);
		playerJumpL[0] = ss.grabImage(5, 2, 64, 64);
		playerJumpR[0] = ss.grabImage(5, 1, 64, 64);
		jumpSpinL[0] = ss.grabImage(6, 2, 64, 64);
		jumpSpinL[1] = ss.grabImage(7, 2, 64, 64);
		jumpSpinL[2] = ss.grabImage(8, 2, 64, 64);
		jumpSpinR[0] = ss.grabImage(6, 1, 64, 64);
		jumpSpinR[1] = ss.grabImage(7, 1, 64, 64);
		jumpSpinR[2] = ss.grabImage(8, 1, 64, 64);
		playerCrouchL[0] = ss.grabImage(8, 4, 64, 64);
		playerCrouchR[0] = ss.grabImage (8, 3, 64, 64);
		orb[0] = ss.grabImage (2, 4, 64, 64);
	
	}
}
