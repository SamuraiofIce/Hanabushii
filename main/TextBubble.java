package com.game.src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class TextBubble {
	
		 private Color custom_orange = new Color (255, 128, 0);
	
		public TextBubble()
		{
			
		}
	
		public void render(Graphics g)
		{
			g.setColor(Color.black);
    		g.fillRect (175, 225, 300, 80);
    		Font fnt0 = new Font("serif", Font.BOLD, 20);
			g.setFont(fnt0);
			g.setColor(custom_orange);
    		
    		
    		g.drawString ("Movement: WASD (or arrows)", 195, 250);
    		g.drawString ("Kunai: SPACE", 250, 270);
    		g.drawString ("MENU: SHIFT", 250, 290);
		}
	
}