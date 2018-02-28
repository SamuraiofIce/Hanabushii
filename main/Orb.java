package com.game.src.main;

import com.game.src.main.classes.EntityC;
import com.game.src.main.classes.EntityD;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Orb extends GameObject implements EntityD {
	
	private Game game;
	private Controller c;
	public int lifespan = 1000;
	private Textures tex;
	private Random r = new Random();
	
	private double x;
	private double y;
	public double velX;
	public double velY;
	
	private int orbType;
	
	public Orb (double x, double y, Textures tex, Game game, Controller c, int orbType)
	{
		super (x, y);
		this.x = x;
		this.y = y;
		this.velY = -20;
		if (r.nextInt(2) == 1)
			this.velX = 5;
		else
			this.velX = -5;
		this.tex = tex;
		this.game = game;
		this.c = c;
		this.orbType = r.nextInt(2);
	}
	
	public void tick()
	{
		x += velX;
		y += velY;
		velY ++;
		
		//Floor Collisions
		if (y > 376)
		{
			y = 376;
			if (velX > 0)
				velX --;
			if (velX < 0)
				velX ++;
		}
		
		for (int i = 0; i < game.ec.size(); i++)
		{
			EntityC tempEnt = game.ec.get(i);
			if(Physics.Collision(this, tempEnt))
			{
				//Left
				if (x >= tempEnt.getX() && x + 64 <= tempEnt.getX() + 32 && y <= tempEnt.getY() + 64 && y >= tempEnt.getY() - 64)
				{
					x = tempEnt.getX();
					velY += 3;
					velX = -5;
				}
				//Right
				if (x >= tempEnt.getX() + 32 && x <= tempEnt.getX() + 64 && y <= tempEnt.getY() + 64 && y >= tempEnt.getY() - 64)
				{
					x = tempEnt.getX() + 45;
					velY += 3;
					velX = 5;
				}
				//up
				else if (x > tempEnt.getX() - 64 && x <= tempEnt.getX() +64 && y <= tempEnt.getY() && y >= tempEnt.getY() - 37)
				{
						y = tempEnt.getY() - 37;
						//velY = 0;
						if (velY > 1)
							velY = velY/2*-1;
						if (velX > 0)
							velX --;
						if (velX < 0)
							velX ++;
						
				}
				//Down
				else if (x > tempEnt.getX() - 64 && x <= tempEnt.getX() +64 && y <= tempEnt.getY() + 65 && y >= tempEnt.getY())
				{
					y = tempEnt.getY() + 59;
					velY = 5;
					if (velX > 0)
						velX --;
					if (velX < 0)
						velX ++;
				}
			}
		}
			
		
		//EntA collisons
		
		/*for (int boCanTroll = 0; boCanTroll < game.ea.size(); boCanTroll++)
		{
			EntityA tempEnt = game.ea.get(boCanTroll);
			if(Physics.Collision(this, tempEnt))
			{
				if (this.getType() == 0)
					Game.MANA += 10;
				else if (this.getType() == 1)
					Game.HEALTH +=10;
				c.removeEntity(this);
			}
		}*/
		//More player collision?
		/*for (int i = 0; i < game.ed.size(); i++)
		{
			EntityA tempEnt = game.ea.get(i);
			
			if(Physics.Collision(this, tempEnt))
			{
				
				
				if (this.getType() == 0)
					Game.MANA += 1;
				else if (this.getType() == 1)
					Game.HEALTH +=1;
				c.removeEntity(tempEnt);

			}

		}*/
		
		//lifespan Stuff
		lifespan -= 1;
		if (lifespan <= 0)
			c.removeEntity(this);
	}
	public void render (Graphics g){
		g.drawImage(tex.orb[0], (int) x, (int) y, null);
	}
    //GET THE X BOUNDS
	public Rectangle getBounds(){
		return new Rectangle ((int)x + 27, (int)y + 51, 13, 13);
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
    
    public int getType()
    {
    	return orbType;
    }
	
}
