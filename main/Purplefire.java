package com.game.src.main;

import com.game.src.libs.Animation;
import com.game.src.main.classes.EntityA;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Purplefire extends GameObject implements EntityA {

	public int velY = 0;
	public int lifespan = 180;
	
	Animation anim;
	
	public Purplefire (double x, double y, Textures tex, Game game, Controller c){
		super (x, y);
			
		anim = new Animation(1, tex.purpleFire[0], tex.purpleFire[1], tex.purpleFire[2]);
		
		
	}
	
	public void tick()
	{			
		anim.runAnimation();
				
	}
	
	public void render (Graphics g){
		anim.drawAnimation(g, x, y, 0);
	}

	public Rectangle getBounds(){
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
