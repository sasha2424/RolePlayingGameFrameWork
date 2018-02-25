package main;

import java.awt.Graphics2D;
import java.util.ArrayList;

import entitiesHandling.Entity;
import entitiesHandling.Player;

public class EventHandler {
	private ArrayList<Entity> toRender;
	
	public EventHandler(){
		toRender = new ArrayList<Entity>();
	}
	
	public void addEntity(Entity e){
		toRender.add(e);
	}


}
