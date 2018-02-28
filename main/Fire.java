package com.game.src.main;

import com.game.src.libs.Animation;
import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;
import com.game.src.main.classes.EntityC;


import java.awt.Graphics;
import java.awt.Rectangle;

public class Fire extends GameObject implements EntityA {

	private Game game;
	private Controller c;
	public double throwDirection = 5;
	public int velY = 0;
	public int lifespan = 180;
	
	Animation anim;
	
	public Fire (double x, double y, Textures tex, Game game, double throwDirection, String playerDirection, Controller c){
		super (x, y);
			
		this.game = game;
		this.throwDirection = throwDirection;
		this.c = c;
		
		anim = new Animation(2, tex.fire[0], tex.fire[1], tex.fire[2]);
		
		
	}
	
	public void tick()
	{
		
		
		x += this.throwDirection;
		y -= velY;
		if (this.throwDirection < 0)
			this.throwDirection += 1;
		else if (this.throwDirection > 0)
			this.throwDirection -= 1;
			
		anim.runAnimation();
		
		for (int i = 0; i < game.eb.size(); i++)
		{
			EntityB tempEnt = game.eb.get(i);
			
			if(Physics.Collision(this, tempEnt))
			{
				
				game.setEnemy_killed(game.getEnemy_killed() + 1);
				game.setPlayer_score(game.getPlayer_score() + 1);
				c.removeEntity(tempEnt);
			}
			
		}
		
		if (y > 376)
			y = 376;
		velY -=1;
		
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
		
		lifespan -= 1;
		if (lifespan <= 0)
			c.removeEntity(this);
			
	}
	
	public void render (Graphics g){
		anim.drawAnimation(g, x, y, 0);
	}
    //GET THE X BOUNDS
	public Rectangle getBounds(){
		/*//Rectangle kunaiRect = new Rectangle ((int)x + 36, (int)y + 44, 21, 4);
		if (p.playerDirection.equals("Right 1"))
			//kunaiRect = new Rectangle ((int)x + 6, (int)y + 44, 21, 4);
			return new Rectangle ((int)x + 6, (int)y + 44, 21, 4);
		else if (p.playerDirection.equals("Left 1"))
			//kunaiRect = new Rectangle ((int)x + 36, (int)y + 44, 21, 4);
			return new Rectangle ((int)x + 36, (int)y + 44, 21, 4);
		
		//return kunaiRect;*/
		return new Rectangle ((int)x + 20, (int)y - 34, 24, 30);
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
}
