package edu.pacific.comp55.starter;

import java.awt.Image;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.graphics.GRectangle;

public class Object extends GImage {
	
	private String objImageName; 
	
	
	public Object(String objImageName, int x, int y) {
		super(objImageName, x, y);
		
		this.objImageName = objImageName;
		
		System.out.println("Object created: ");
		printInfo();
	}
	
	public Object(String objImageName, int x, int y, int width, int height) {
		super(objImageName, x, y);
		// TODO Auto-generated constructor stub
		
		// 
		this.setSize(width, height);
		// Function needs to center the obj image inside of the collision box
		// Function needs to automatically change the collision box to be:
		// 1. at the center of the image
		// 2. within the borders of the image
		
		this.objImageName = objImageName;
		this.setBounds(x, y, width, height);
		
		System.out.println("Object created: ");
		printInfo();
	}
	
//	
//	// Complete the below
//	
//	public void move(Orientation moveDirection, ArrayList<Object> objList) {
//		
//		double pixelsMoved = 0.5;
//		for(int i = 0; i < 20; i++) {
//		if (canMove(moveDirection,objList)) {
//			
//			switch(moveDirection) {
//				case LEFT:
//					this.move(-pixelsMoved, 0);
//					break;
//				case RIGHT: 
//					this.move(pixelsMoved, 0);
//					break;
//				case UP: 
//					this.move(0, -pixelsMoved);
//					break;
//				case DOWN: 
//					this.move(0, pixelsMoved);
//					break;
//				default: 
//					System.out.println(this.getImageName() + " move() ERROR, incorrect enumerated Orientation type given");				
//			}
//		}
//		else {
//			System.out.println(this.getImageName() + " move(): collision detected, movement rejected");				
//		}
//		
//	}
//	}
//	
//	// Use bounds to see limits of rectangle
//	public boolean canMove(Orientation moveDirection, ArrayList<Object> objList) {
//		// receives a list of all objects on level
//		// if this object is touching another, it shoudn't be able to move in that direction
//		// if it is touching another object, obtain coordinates of that object and compare it to the
//		// coordinates of this object to find the direction
//		// prohibit movement from that given direction
//		
//		int i = 0;
//		for (Object gameObj : objList) {
//			if (gameObj != this) {
//				// Checks to see the bounds of this object intersects with
//				// another object in the 
//				
//				if(this.getBounds().intersects(gameObj.getBounds())) {
//					
//					System.out.println("Two objects intersect:");
//					this.printInfo();
//					gameObj.printInfo();
//					
//					// Checks to see from which direction an object is blocked
//					// Needed for future character movement
//					
//					if (getCenterPoint(this).getX() <= getCenterPoint(gameObj).getX()) {							
//						if(!upDownThreshold(this,gameObj)) {
//							
//							System.out.println(this.getImageName() + " collides with another object named " + gameObj.getImageName() + " can not move to the right.");
//							
//							if (moveDirection == moveDirection.RIGHT) {
//								System.out.println("canMove(): False");
//								return false;
//							}
//							continue;
//						}
//					}
//					else if (getCenterPoint(this).getX() > getCenterPoint(gameObj).getX()) {
//						
//						if(!upDownThreshold(this,gameObj)) {
//
//							System.out.println(this.getImageName() + " collides with another object named " + gameObj.getImageName() + " can not move to the left.");
//					
//							if (moveDirection == moveDirection.LEFT) {
//								System.out.println("canMove(): False");
//								return false;
//							}
//							continue; // Because an object can physically collide from the left or right OR up or down, but never both at the same time
//						}
//					}
//					
//					if (this.getUpperBound() >= gameObj.getLowerBound()) {
//						System.out.println(this.getImageName() + " collides with another object named " + gameObj.getImageName() + " can not move upward.");
//					
//						if (moveDirection == moveDirection.UP) {
//							System.out.println("canMove(): False");
//							return false;
//						}
//					}
//					else if (this.getLowerBound() >= gameObj.getUpperBound()) {
//						System.out.println(this.getImageName() + " collides with another object named " + gameObj.getImageName() + " can not move down.");
//				
//						if (moveDirection == moveDirection.DOWN) {
//							System.out.println("canMove(): False");
//							return false;
//						}
//					}
//					
//					
//					System.out.println("Collision detection direction is empty.");
//				}
//				
//							
//			}
//			i++;
//		}
//		
//		System.out.println("Object does NOT collide with another object in that particular move direction");
//		
//		return true;
//	}	
//	
//	protected Boolean upDownThreshold(Object obj, Object otherObj) {
//		if ((otherObj.getUpperBound() <= obj.getLowerBound()) && (obj.getLowerBound() <= otherObj.getUpperBound()+3)) {
//			System.out.println("Within lower threshold");
//			return true; // True = within threshold obtained
//		}
//		
//		System.out.println("Not within threshold");
//		return false;
//	}
//
	public void printInfo() {
		System.out.println("Name : " + this.getImageName());
		System.out.println("Image X: " + this.getX() + " Y: " + this.getY() + " Width: " + this.getWidth() + " Height: " + this.getHeight());
		System.out.println("Collision X: " + this.getBounds().getX() + " Y: " +  this.getBounds().getY() + " Width: " +  this.getBounds().getWidth() + " Height: " +  this.getBounds().getHeight());
		System.out.println();
	}
	
	protected double getLowerBound() {		
		return this.getBounds().getY()+this.getBounds().getHeight();
	}
	
	protected double getUpperBound() {
		return this.getBounds().getY();
	}
	
	public GPoint getCenterPoint(Object obj) {
		
		double centerX = obj.getX() + (obj.getWidth()/2);
		double centerY = obj.getY() + (obj.getHeight()/2);
		
		GPoint centerPoint = new GPoint(centerX, centerY);
		
		System.out.println(obj.getImageName() + ": x " + centerX + ", y " + centerY);
		
		return centerPoint;
	}
	
	public String getImageName() {
		return objImageName;
	}
	
//	public GRect collisionBox() {
//	}
			
	public static void main() {
		
	}
}
