package com.game.src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class Menu {
	
		public Rectangle playButton = new Rectangle(Game.WIDTH / 2 + 120, 150, 100, 0);
		public Rectangle helpButton = new Rectangle(Game.WIDTH / 2 + 120, 250, 100, 0);
		public Rectangle quitButton = new Rectangle(Game.WIDTH / 2 + 120, 350, 100, 0);
		
		
		public void render(Graphics g)
		{
			Graphics2D g2d = (Graphics2D) g;
			Color custom_orange = new Color (255, 128, 0);
			
			Font fnt0 = new Font("serif", Font.BOLD, 50);
			g.setFont(fnt0);
			g.setColor(custom_orange);
			g.drawString("Hanabushii", Game.WIDTH/2 + 40, 100);
			g.setColor(Color.white);
			Font fnt1 = new Font("arial", Font.BOLD, 25);
			g.setFont(fnt1);
			g2d.draw(playButton);
			g.drawString("Play", playButton.x + 19, playButton.y + 30);
			g2d.draw(helpButton);
			g.drawString("Help", helpButton.x + 19, helpButton.y + 30);
			g2d.draw(quitButton);
			g.drawString("Quit", quitButton.x + 19, quitButton.y + 30);
		}
	
}
