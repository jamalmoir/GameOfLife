package com.antonio.jamal.gameOfLife.gui;
import java.awt.Dimension;
import java.awt.Rectangle;

public class DeadOrAlive extends Rectangle {
	private boolean isAlive;
	
	public DeadOrAlive() {
		super();
		
		isAlive = false;
	}	
	
	public DeadOrAlive(Dimension d) {
		super(d);
		
		isAlive = false;
	}
	
	public DeadOrAlive(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		isAlive = false;
	}
	
	public DeadOrAlive(int width, int height) {
		super(width, height);
		
		isAlive = false;
	}
	
	public DeadOrAlive(DeadOrAlive o) {
		super((int) o.getX(), (int) o.getY(), (int) o.getWidth(), (int) o.getHeight());
		
		this.isAlive = o.isAlive();
	}
	
	public boolean isAlive() {
		return isAlive;	
	}
	
	public void changeState() {
		isAlive = !isAlive;
	}
	
	public void makeAlive() {
		isAlive = true;
	}
	
	public void makeDead() {
		isAlive = false;
	}
}
