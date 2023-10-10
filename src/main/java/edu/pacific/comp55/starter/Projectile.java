package edu.pacific.comp55.starter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


public class Projectile{

	public static final String IMAGE_FILENAME_PATH = "animations/";
	public static final String IMAGE_EXTENSION = ".png";
	public static final int MOVE_SPEED = 10;
	public static final int GRAVITY_FALL_CONSTANT = 5;
	
	private FightingMoves app;
	private Timer timer = new Timer(40, (ActionListener) this);
	private double positionX;
	private double positionY;
	
	public Projectile(FightingMoves a) {

		app = a;
		positionX = app.rockX;
		positionY = app.rockY;
		
	}
	
	public void actionPerformed (ActionEvent e){
		
		positionX += MOVE_SPEED;
		positionY += GRAVITY_FALL_CONSTANT;
		app.projectile.setLocation(positionX, positionY);
		
		if(app.projectile.getY() == 500) {
			timer.stop();
			
		}
		
	}
	
	public void startTimer() {
		timer.start();
	}
	
	
	public void setPositionX(double x) {
		positionX = x;
	}
	
	public void setPositionY(double y) {
		positionY = y;
	}
	
	public static void main(String[] args) {
		
		
		
		
		
		
	}
}
