package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.game.src.main.classes.EntityB;
import com.game.src.main.classes.EntityC;

public class Enemy extends GameObject implements EntityB {

	Random r = new Random();
	private Textures tex;
	private Game game;
	private Player p;
	public int numberDrop;
	
	public double speed;
	public double dashBuffer;
	double velY = 0;
	
	public Enemy (double x, double y, Textures tex, Controller c, Game game, Player p){
		super (x, y);
		this.tex = tex;
		this.game = game;
		this.p = p;
		numberDrop = 1 + r.nextInt(2);
		speed = -5;
		dashBuffer = 0;
	}
	
	public void tick(){
		
		if (dashBuffer > 20)
			dashBuffer = 20;
		else if (dashBuffer < -20)
			dashBuffer = -20;
		
		if (dashBuffer > 0)
			x += speed;
		else if (dashBuffer <= 0)
			x -= speed;
			
		//x += speed;
			
		/////////////////
		//This is with dash buffer
		
		/*if (x < p.getX())
		{
			if (dashBuffer > 30)
			{
				speed = 5;
				dashBuffer = 0;
			}
			else
			{
				dashBuffer ++;
				speed -= 0.6;
			}
			
		}
		
		else if (x > p.getX())
		{
			if (dashBuffer > 30)
			{
				speed = -5;
				dashBuffer = 0;
			}
			else
			{
				dashBuffer ++;
				speed += 0.6;
			}

		}*/
		
		/////
		//This is for non Dash Buffer
		
		if (p.getForm() == 1)
		{
		if (x < p.getX())
		{
				dashBuffer --;
				//speed = 4;
		}
		
		else if (x > p.getX())
		{
				//speed = -4;
				dashBuffer++;
		}
		}
		else if (p.getForm() == 0)
		{
			if (dashBuffer == -1)
				dashBuffer = 10;
			else if (dashBuffer == 1)
				dashBuffer = -10;
			else if (dashBuffer > 0)
			{
				dashBuffer --;
			}
			else if (dashBuffer < 0)
			{
				dashBuffer ++;
			}
		}
		
		
		//////////////////////////
		
		
		
		y += velY;
		//speed = 2;
		/*if (x >= 639)
		{
			//speed = r.nextInt(9) + 1;
			speed = 8;
		}
		else if (x <= 0)
		{
			speed = (r.nextInt(9) + 1) * (-1);
		}*/
		
		
		
		/*for (int i = 0; i < game.ea.size(); i++)
		{
			EntityA tempEnt = game.ea.get(i);
			
			if(Physics.Collision(this, tempEnt))
			{
				
				game.setEnemy_killed(game.getEnemy_killed() + 1);
				game.setPlayer_score(game.getPlayer_score() + 1);
				//c.removeEntity(tempEnt);
				//c.removeEntity(this);
				Game.MANA += .5;
			}
			
		}*/
		for (int i = 0; i < game.ec.size(); i++)
		{
			EntityC tempEnt = game.ec.get(i);
			if(Physics.Collision(this, tempEnt))
			{
				//Left
				if (x >= tempEnt.getX() && x + 64 <= tempEnt.getX() + 32 && y <= tempEnt.getY() + 64 && y >= tempEnt.getY() - 64)
				{
					x = tempEnt.getX();
				}
				//Right
				if (x >= tempEnt.getX() + 32 && x <= tempEnt.getX() + 64 && y <= tempEnt.getY() + 64 && y >= tempEnt.getY() - 64)
				{
					x = tempEnt.getX() + 45;
				}
				//up
				else if (x > tempEnt.getX() - 64 && x <= tempEnt.getX() +64 && y <= tempEnt.getY() && y >= tempEnt.getY() - 37)
				{
						y = tempEnt.getY() - 37;
						velY = 0;
				}
				else if (x > tempEnt.getX() - 64 && x <= tempEnt.getX() +64 && y <= tempEnt.getY() + 65 && y >= tempEnt.getY())
				{
					y = tempEnt.getY() + 59;
					velY = 5;
				}
			}
		}
		if(y <= 0)
			y = 0;
		if(y >= 480 - 64 -40)
			y = 480 - 64 -40;
				
		if (y == 440 - 64)
		{
			velY = r.nextInt(19) * -1;
		}
		else
		{
			velY += 1;
		}
		
	}
	
	public void render(Graphics g){
		g.drawImage(tex.enemy[0], (int)x, (int)y, null);
	}
	
	public Rectangle getBounds(){
		return new Rectangle ((int)x + 18, (int)y + 14, 34, 48);
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	public void setX(double x)
    {
    	this.x = x;
    }
    public void setY(double y)
    {
    	this.y = y;
    }
	
	public int getNumberDrop()
	{
		return numberDrop;
	}

	//public int getSpeed() {
	//	return speed;
	//}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	
	
}
