package com.game.src.main;

import java.util.LinkedList;
import java.util.Random;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;
import com.game.src.main.classes.EntityC;
import com.game.src.main.classes.EntityD;
import com.game.src.main.classes.EntityE;

import java.awt.Graphics;

public class Controller {
	private LinkedList<EntityA> ea = new LinkedList<EntityA>();
	private LinkedList<EntityB> eb = new LinkedList<EntityB>();
	private LinkedList<EntityC> ec = new LinkedList<EntityC>();
	private LinkedList<EntityD> ed = new LinkedList<EntityD>();
	private LinkedList<EntityE> ee = new LinkedList<EntityE>();
	
    EntityA enta;
    EntityB entb;
    EntityC entc;
    EntityD entd;
    EntityE ente;
    private Textures tex;
    Random r = new Random();
    private Game game;
    private Player p;
	public int enemySpeed;
    
    public Controller(Textures tex, Game game, Player p)
    {
    	this.tex = tex;
    	this.game = game;
    	this.p = p;
    }
    
    public void createEnemy(int enemy_count)
    {
    	for (int i = 0; i < enemy_count; i ++)
    	{
    		enemySpeed = r.nextInt(1);
    		if (enemySpeed == 0)
    			addEntity(new Enemy(640 + r.nextInt(30), 376, tex, this, game, p));
    		else if (enemySpeed == 1)
    			addEntity(new Enemy(0 - r.nextInt(300), 376, tex, this, game, p));
    	}
    }
    
	public void tick()
	{
		
		//A Class Entity
		for (int i = 0; i < ea.size(); i++)
		{
			enta = ea.get(i);
			enta.tick();

			
		}
		
		for (int i = 0; i < ed.size(); i++)
		{
			entd = ed.get(i);
			entd.tick();

			
		}
		
	
		for (int i = 0; i < eb.size(); i++)
		{
			entb = eb.get(i);					
			entb.tick();
		}
		
		for (int i = 0; i < ec.size(); i++)
			{
				entc = ec.get(i);
							
					entc.tick();
			}
		for (int i = 0; i < ed.size(); i++)
		{
			entd = ed.get(i);
						
				entd.tick();
		}
		for (int i = 0; i < ee.size(); i++)
		{
			ente = ee.get(i);
						
				ente.tick();
		}
		}
	
	




	public void render(Graphics g)
	{
		for (int i = 0; i < ec.size(); i++)
			{
				entc = ec.get(i);
							
				entc.render(g);
			}
		
		//A Class Entity
		for (int i = 0; i < ea.size(); i++)
		{
			enta = ea.get(i);
					
			enta.render(g);
		}
		
		//B Class Entity
				for (int i = 0; i < eb.size(); i++)
				{
					entb = eb.get(i);
							
					entb.render(g);
				}
		
		for (int i = 0; i < ed.size(); i++)
			{
				entd = ed.get(i);
							
				entd.render(g);
			}
		
		
	}
    
	public void addEntity(EntityA block)
	{
		ea.add(block);
	}
	
	public void removeEntity(EntityA block)
	{
		ea.remove(block);
	}
	
	public void addEntity(EntityB block)
	{
		eb.add(block);
	}
	
	public void removeEntity(EntityB block)
	{
		eb.remove(block);
	}

	public void addEntity(EntityC block)
	{
		ec.add(block);
	}
	
	public void removeEntity(EntityC block)
	{
		ec.remove(block);
	}
	
	public void addEntity(EntityD block)
	{
		ed.add(block);
	}
	
	public void removeEntity(EntityD block)
	{
		ed.remove(block);
	}
	
	public void addEntity(EntityE block)
	{
		ee.add(block);
	}
	
	public void removeEntity(EntityE block)
	{
		ee.remove(block);
	}
	
	//Linked List Return
	public LinkedList<EntityA> getEntityA()
	{
		return ea;
	}
	public LinkedList<EntityB> getEntityB()
	{
		return eb;
	}
	public LinkedList<EntityC> getEntityC()
	{
		return ec;
	}
	
	public LinkedList<EntityD> getEntityD()
	{
		return ed;
	}
	public LinkedList<EntityE> getEntityE()
	{
		return ee;
	}

}
