package com.game.src.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener{
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		int mx = e.getX();
		int my = e.getY();
		
		if (Game.State == Game.STATE.MENU)
		{
		
			if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220 && my >= 150 && my<=200)
			{
				//cutscene
				Game.csNumber = 1;
				Game.State = Game.STATE.GAME;
				
				
				Game.helpDisplay = 0;
				//c.createEnemy(3);
			}
			if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220 && my >= 350 && my<=400)
			{
				System.exit(1);
			}
			if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220 && my >= 250 && my<=300)
			{
				if (Game.helpDisplay == 0)
					Game.helpDisplay = 1;
				else if (Game.helpDisplay == 1)
					Game.helpDisplay = 0;
				
			}
	
		}
		else if (Game.State == Game.STATE.PAUSE_MENU)
		{
		
			if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220 && my >= 150 && my<=200)
			{
				Game.State = Game.STATE.GAME;
				Game.helpDisplay = 0;
			}
			if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220 && my >= 350 && my<=400)
			{
				System.exit(1);
			}
			if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220 && my >= 250 && my<=300)
			{
				if (Game.helpDisplay == 0)
					Game.helpDisplay = 1;
				else if (Game.helpDisplay == 1)
					Game.helpDisplay = 0;
				
			}
	
		}
		else if (Game.State == Game.STATE.GAME_OVER)
			{
		
				//PlayButton
				if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220 && my >= 150 && my<=200)
				{
					Game.State = Game.STATE.MENU;
					Game.HEALTH = 100;
					//Player.setVelX(0);
					//Player.setVelY(0);
				}
			}
		
		else if (Game.State == Game.STATE.CUT_SCENE)
		{
	

				Game.State = Game.STATE.GAME;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}