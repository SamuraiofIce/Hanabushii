package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import com.game.src.main.classes.EntityC;

//NO GROUND BLOCKS GENERATED


public class GroundBlock extends GameObject implements EntityC {
	
	private Textures tex;

	
	public GroundBlock (double x, double y, Textures tex, Controller c, Game game)
	{
		super (x, y);
		this.tex = tex;
		
	}
	public void tick()
	{
		
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
    public void render (Graphics g){
		g.drawImage(tex.groundBlock[0], (int) x, (int) y, null);
	}
    
	public Rectangle getBounds(){
		return new Rectangle ((int)x, (int)y, 64, 64);
	}
}
