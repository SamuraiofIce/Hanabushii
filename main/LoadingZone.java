package com.game.src.main;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityE;
import java.awt.Rectangle;

class LoadingZone extends GameObject implements EntityE {
	
	String area;
	private Controller c;
	private Game game;
	
	public LoadingZone(double x, double y, Controller c, Game game, String area)
	{
		super(x,y);
		this.area = area;
		this.c = c;
		this.game = game;
	
	}
	
	public void tick()
	{
		
	}
	
	public void teleport()
	{
		//System.out.println("Teleporting... Please wait...");\
		game.textBox = 1;
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
	public Rectangle getBounds(){
		return new Rectangle ((int)x, (int)y, 64, 64);
	}
}
