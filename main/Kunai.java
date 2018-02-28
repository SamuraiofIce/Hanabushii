package com.game.src.main;

import java.awt.image.BufferedImage;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Kunai extends GameObject implements EntityA {

	private Game game;
	private Controller c;
	public double throwDirection = 5;
	public BufferedImage kunaiSprite;
	
	private Textures tex;
	public Kunai (double x, double y, Textures tex, Game game, double throwDirection, String playerDirection, Controller c){
		super (x, y);
		if (playerDirection.equals("Right 1"))
			kunaiSprite = tex.kunaiR[0];
		else if (playerDirection.equals("Left 1"))
			kunaiSprite = tex.kunaiL[0];
			
		
		this.tex = tex;
		this.game = game;
		this.throwDirection = throwDirection;
		this.c = c;
	}
	
	public void tick()
	{
		
		
		x += this.throwDirection;
		
		for (int i = 0; i < game.eb.size(); i++)
		{
			EntityB tempEnt = game.eb.get(i);
			
			if(Physics.Collision(this, tempEnt))
			{
				
				game.setEnemy_killed(game.getEnemy_killed() + 1);
				game.setPlayer_score(game.getPlayer_score() + 1);
				//Game.MANA += .5;
				for (int n = 0; n < tempEnt.getNumberDrop(); n ++)
				{
					c.addEntity(new Orb(tempEnt.getX(), tempEnt.getY(), tex, game, c, 0));
				}
				c.removeEntity(tempEnt);
				c.removeEntity(this);
			}
			
		}
	}
	
	public void render (Graphics g){
		g.drawImage(kunaiSprite, (int) x, (int) y, null);
	}
    //GET THE X BOUNDS
	public Rectangle getBounds(){
		return new Rectangle ((int)x + 6, (int)y + 44, 21, 4);
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
