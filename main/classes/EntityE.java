package com.game.src.main.classes;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface EntityE {

	public void tick();
	public Rectangle getBounds();
		
	public double getX();
	public double getY();
	public void setX(double x);
	public void setY(double y);
	public void teleport();
	
}
