
package com.game.src.main;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.game.src.libs.Animation;
import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;
import com.game.src.main.classes.EntityC;
import com.game.src.main.classes.EntityD;
import com.game.src.main.classes.EntityE;

public class Player extends GameObject implements EntityA {
	
	private double velX = 0;
	private double velY = 0;
	public static enum FORM{
		SAMURAI,
		FOX,
		ENEMY,
	}
	
	public static int Form;
	public String playerDirection = "Left 1";
	public boolean playerWalking = false;
	public boolean inControl = true;
	public boolean blockGrav = true;
	public boolean friction = true;
	public int stance = 1;
	public double hitStun = 0;
	public double gBlockLeft = 100;
	public double gBlockRight = 100;
	Game game;
	Controller c;
	private Random rand = new Random();
	
	Animation animL;
	Animation animR;
	Animation animJumpSpinL;
	Animation animJumpSpinR;
	
	
	private Textures tex;
	
	public Player(double x, double y, Textures tex, Game game, Controller c)
	{
		super (x, y);
		this.tex = tex;
		this.game = game;
		this.c = c;
		Form = 1;
		animL = new Animation(2, tex.playerL1[0], tex.playerL1[1], tex.playerL1[2]);
		animR = new Animation(2, tex.playerR1[0], tex.playerR1[1], tex.playerR1[2]);
		animJumpSpinL = new Animation (2, tex.jumpSpinL [0], tex.jumpSpinL [1], tex.jumpSpinL[2]);
		animJumpSpinR = new Animation (2, tex.jumpSpinR [0], tex.jumpSpinR [1], tex.jumpSpinR[2]);
	}

	public void tick()
	{
		
		
		x+=velX;
		y+=velY;
		
		if (velY > 20)
			velY = 20;
		
		hitStun --;
		
		if(velX > 0 && !playerWalking)
			velX --;
		if (velX < 0 && !playerWalking)
			velX ++;
			
		if(velX > 20)
			velX = 20;
		if(velX < -20)
			velX = -20;
		if(y > 500)
			game.HEALTH -= 500;
		
		//Friction
		if (friction)
		{
			if (velX > 0 || !playerWalking)
				velX --;
			if (velX < 0 || !playerWalking)
				velX ++;
		}
		
		//Ground Block colision
		for (int i = 0; i < game.ec.size(); i++)
		{
			EntityC tempEnt = game.ec.get(i);
			if(Physics.Collision(this, tempEnt))
			{
				//Left
				/*if (x >= tempEnt.getX() && x + 64 <= tempEnt.getX() + 32 && y <= tempEnt.getY() + 64 && y >= tempEnt.getY() - 64)
				{
					x = tempEnt.getX();
				}
				//Right
				if (x >= tempEnt.getX() + 32 && x <= tempEnt.getX() + 64 && y <= tempEnt.getY() + 64 && y >= tempEnt.getY() - 64)
				{
					x = tempEnt.getX() + 45;
				}*/
				//up
				if (x > tempEnt.getX() - 64 && x <= tempEnt.getX() +64 && y <= tempEnt.getY() && y >= tempEnt.getY() - 37)
				{
					if (blockGrav != false)
					{
						y = tempEnt.getY() - 37;
						if (velY > 0)
							velY = 0;
						Game.JUMP = 0;
						if (stance > 1)
							stance = 1;
						//hitStun = 0;
						inControl = true;
					}
				}
				
				if (x > tempEnt.getX() - 64 && x <= tempEnt.getX() +64 && y <= tempEnt.getY() + 65 && y >= tempEnt.getY())
				{
					y = tempEnt.getY() + 49;
					if (velY > 0)
						velY = 0;
					//hitStun = 0;
					inControl = true;
				}
				
				
			}
		}
		
		//Collision with Enemies
		for (int i = 0; i < game.eb.size(); i++)
		{
			EntityB tempEnt = game.eb.get(i);
			if(Physics.Collision(this, tempEnt))
			{
				
				if (Game.Aura != Game.AURA.INVINCIBILITY && hitStun <= 0 && Form != 0)
				{
					Game.HEALTH -= 10;
					hitStun = 100;
					inControl = false;
					velY = -15;
					if (x <= tempEnt.getX() + 17)
						velX = -15;
					else
						velX = 15;
					//c.removeEntity(tempEnt);
					game.eb.remove(tempEnt);
					game.setEnemy_killed(game.getEnemy_killed() + 1);
				}
				
				
			}
		}
		
		//Collion with Orbs
		for (int i = 0; i < game.ed.size(); i++)
		{
			EntityD tempEnt = game.ed.get(i);
			
			if(Physics.Collision(this, tempEnt))
			{
				
				c.removeEntity(tempEnt);
				if (tempEnt.getType() == 0)
					Game.MANA += 1;
				else if (tempEnt.getType() == 1)
					Game.HEALTH +=1;
				game.ed.remove(tempEnt);

			}

		}
		
		//Collision with Loading Zones
		for (int i = 0; i < game.ee.size(); i++)
		{
			EntityE tempEnt = game.ee.get(i);
			if(Physics.Collision(this, tempEnt))
			{
				tempEnt.teleport();
			}
			else
				game.textBox = 0;
		}
		
		
		
		if(x <= 175)
		{
			x = 175;
			game.cameraX -= velX;
			
			for (int i = 0; i < game.ea.size(); i++)
			{
				c.enta = game.ea.get(i);					
				c.enta.setX(c.enta.getX() - velX);
			}
			
			for (int i = 0; i < game.eb.size(); i++)
			{
				c.entb = game.eb.get(i);					
				c.entb.setX(c.entb.getX() - velX);
			}
			
			for (int i = 0; i < game.ec.size(); i++)
			{
				c.entc = game.ec.get(i);					
				c.entc.setX(c.entc.getX() - velX);
			}
			
			for (int i = 0; i < game.ed.size(); i++)
			{
				c.entd = game.ed.get(i);					
				c.entd.setX(c.entd.getX() - velX);
			}
			for (int i = 0; i < game.ee.size(); i++)
			{
				c.ente = game.ee.get(i);					
				c.ente.setX(c.ente.getX() - velX);
			}
			
		}
		
		if(x >= 408)
		{
			x = 408;
			game.cameraX -= velX;
			for (int i = 0; i < game.ea.size(); i++)
			{
				c.enta = game.ea.get(i);					
				c.enta.setX(c.enta.getX() - velX);
			}
			
			for (int i = 0; i < game.eb.size(); i++)
			{
				c.entb = game.eb.get(i);					
				c.entb.setX(c.entb.getX() - velX);
			}
			
			for (int i = 0; i < game.ec.size(); i++)
			{
				c.entc = game.ec.get(i);					
				c.entc.setX(c.entc.getX() - velX);
			}
			
			for (int i = 0; i < game.ed.size(); i++)
			{
				c.entd = game.ed.get(i);					
				c.entd.setX(c.entd.getX() - velX);
			}
		}
		
		if (hitStun == 1)
			velX = 0;
		if (hitStun == 40)
			inControl = true;	
		
		velY ++;
		
		animL.runAnimation();
		animR.runAnimation();
		animJumpSpinL.runAnimation();
		animJumpSpinR.runAnimation();
	}
	
	public void setPlayerDirection(String playerDirection)
	{
		this.playerDirection = playerDirection;
	}
	
	public void setPlayerWalking(boolean playerWalking)
	{
		this.playerWalking = playerWalking;
	}
	
	public String getPlayerDirection()
	{
		return playerDirection;
	}
	
	public void render(Graphics g)
	{
		if (Form == 1)
		{
		if (hitStun%2 != 1)
		{	
			if (stance == 0)
			{
				if (playerDirection.equals("Left 1"))
				{
					g.drawImage(tex.playerCrouchL[0], (int) x, (int) y, null);
				}
		
				else if (playerDirection.equals("Right 1"))
				{
					g.drawImage(tex.playerCrouchR[0], (int) x, (int) y, null);
				}
			}	
			
			else if (stance == 2)
			{
				if (playerDirection.equals("Left 1"))
				{
					g.drawImage(tex.playerJumpL[0], (int) x, (int) y, null);
				}
		
				else if (playerDirection.equals("Right 1"))
				{
					g.drawImage(tex.playerJumpR[0], (int) x, (int) y, null);
				}
			}
		
			else if (stance == 1)
			{
				if (playerWalking == true)
				{
					if (playerDirection.equals("Left 1"))
					{
						animL.drawAnimation(g, x, y, 0);
					}
					else if (playerDirection.equals("Right 1"))
					{
						animR.drawAnimation(g, x, y, 0);
					}
				}
		
				else 
				{
					if (playerDirection.equals("Left 1"))
					{
						g.drawImage(tex.playerL1[0], (int) x, (int) y, null);
					}
					else if (playerDirection.equals("Right 1"))
					{
						g.drawImage(tex.playerR1[0], (int) x, (int) y, null);
					}
				}
			}
		
			else if (stance >= 3)
			{
				if (playerDirection.equals("Left 1"))
				{
					animJumpSpinL.drawAnimation(g, x, y, 0);
				}
				else if (playerDirection.equals("Right 1"))
				{
					animJumpSpinR.drawAnimation(g, x, y, 0);
				}
			}
		}
		}
		else if (Form == 0)
		{
			if (playerDirection.equals("Left 1"))
					{
						g.drawImage(tex.enemy[0], (int) x, (int) y, null);
					}
					else if (playerDirection.equals("Right 1"))
					{
						g.drawImage(tex.enemy[1], (int) x, (int) y, null);
					}
		}
	
	}
	
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	
	public Rectangle getBounds()
	{
		if (stance == 1 || stance == 2)
			if (playerDirection == "Right 1")
				return new Rectangle ((int)x + 23, (int)y + 15, 28, 49);
			else //if (playerDirection == "Left 1")
				return new Rectangle ((int)x + 15, (int)y + 15, 28, 49);
		else if (stance == 3)
			if (playerDirection == "Right 1")
				return new Rectangle ((int)x + 19, (int)y + 21, 30, 28);
			else //if (playerDirection == "Left 1")
				return new Rectangle ((int)x + 16, (int)y + 21, 30, 28);		
		else
			return new Rectangle ((int)x + 15, (int)y + 15, 28, 49);
	}
	
	public void setX(double x){
		this.x = x;
	}
	public void setY(double y){
		this.y = y;
	}
	public void setVelX(double velX){
		this.velX = velX;
	}
	public void setVelY(double velY){
		this.velY = velY;
	}
	public double getVelX(){
		return velX;
	}
	public double getVelY(){
		return velY;
	}
	
	public void setForm(int asdf)
	{
		Form = asdf;
	}
	public int getForm()
	{
		return Form;
	}
	
	
	public void setBlockGrav(boolean blockGrav)
	{
		this.blockGrav = blockGrav;
	}
}
