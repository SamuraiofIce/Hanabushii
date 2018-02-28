package com.game.src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class Game_Over {
	
		public Rectangle menuButton = new Rectangle(Game.WIDTH / 2 + 120, 150, 100, 0);
		
		public void render(Graphics g)
		{
			Graphics2D g2d = (Graphics2D) g;
			
			Font fnt0 = new Font("serif", Font.BOLD, 50);
			g.setFont(fnt0);
			g.setColor(Color.orange);
			g.drawString("Game Over", Game.WIDTH/2 + 30, 100);
			
			g.setColor(Color.white);
			Font fnt1 = new Font("arial", Font.BOLD, 25);
			g.setFont(fnt1);
			g2d.draw(menuButton);
			g.drawString("Main Menu", menuButton.x -19, menuButton.y + 30);
			
		}
	
}
