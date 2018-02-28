package com.game.src.main;

import java.util.Random;

public class Maps {
	
	private Controller c;
	private Game game;
	private int mapNumber;
	private Random rand = new Random();
	private Textures tex;
	
	public Maps(int mapNumber, Controller c, Game game, Textures tex)
	{
		this.game = game;
		this.c = c;
		this.mapNumber = mapNumber;
		this.tex = tex;
	}
	
	public void makeMap()
	{
	if (game.terrainGen == false)
	{
		if (mapNumber == 0)
		{
			c.addEntity(new GroundBlock(40 + rand.nextInt(200), 400 - rand.nextInt(5), tex, c, game));
    		c.addEntity(new GroundBlock(300 + rand.nextInt(200), 400 - rand.nextInt (5), tex, c, game));
    		c.addEntity(new GroundBlock(rand.nextInt(200), 200 + rand.nextInt(100), tex, c, game));
    		c.addEntity(new GroundBlock(200 + rand.nextInt(200), 200 + rand.nextInt(100), tex, c, game));
    		c.addEntity(new GroundBlock(400 + rand.nextInt(200), 200 + rand.nextInt(100), tex, c, game));
    		game.terrainGen = true;
		}
		if (mapNumber == 1)
		{
			c.addEntity(new GroundBlock(380, 100, tex, c, game));
			c.addEntity(new GroundBlock(320, 200, tex, c, game));
			c.addEntity(new GroundBlock(230, 300, tex, c, game));
			c.addEntity(new LoadingZone(380, 310, c, game, "Sky"));

			for (int i = 0; i < 120; i ++)
			{
				///impliment ground blocks for actual ground
				c.addEntity(new GroundBlock(-3840 + (i * 64), 414, tex, c, game));
			}
			
			game.terrainGen = true;
		}
	}
	}
}
