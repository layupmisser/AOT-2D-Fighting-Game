package edu.pacific.comp55.starter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import acm.graphics.GImage;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.graphics.GRect;


public class Character extends Object implements CharInterface {

	private static final boolean COLLISION_IS_VISIBLE = true;
	private static final Color COLLISION_BOX_COLOR = Color.ORANGE;
	private static final Color HURTBOX_COLOR = Color.cyan;
	private static final int COLLISION_BOX_HEIGHT = 350;
	private static final int COLLISION_BOX_WIDTH = 150;
	private static final int HURTBOX_WIDTH = 100;
	private static final int HURTBOX_HEIGHT = 100;
	private static final boolean HURTBOX_IS_VISIBLE = true;
	public static final String IMAGE_FILENAME_PATH = "animations/";
	public static final String IMAGE_EXTENSION = ".png";
	private static final Orientation LEFT_DIRECTION = Orientation.LEFT;
	private static final Orientation RIGHT_DIRECTION = Orientation.RIGHT;
	
	
	
	private String characterName;
	private int healthPoints = 100;
	private int moveSpeed;
	private int sprintSpeed = moveSpeed * 2;
	
	private int knockBackConstant;
	private int stunConstant;
	

	private Orientation forwardDirection;
	private ArrayList<GImage> currentState;

	private GOval hurtBox;
	private GRect collisionBox;

	
	public HashMap<String, ArrayList<GImage>> characterAnimation = new HashMap<String, ArrayList<GImage>>();
	public HashMap<String, ArrayList<GOval>> hitBoxes = new HashMap<String, ArrayList<GOval>>();
	public GOval[][] attackAnimationHitBoxes;
	
	public Character(String name, Orientation direction, int x, int y){
		
		super(IMAGE_FILENAME_PATH + name + "AnimationWalkTransparent" + direction + 1 + IMAGE_EXTENSION, x, y);
				
		characterName = name;
		forwardDirection = direction; 
		
		setCharacterAnimation();
	
		setHurtbox();
		setCollision();
	}
	
	private void setHurtbox() {
		GPoint charCenterPoint = getCenterPoint(this);
		
		hurtBox = new GOval(charCenterPoint.getX()-(HURTBOX_WIDTH/2), charCenterPoint.getY()-(HURTBOX_HEIGHT/2),HURTBOX_WIDTH,HURTBOX_HEIGHT);
		hurtBox.setColor(HURTBOX_COLOR);
		hurtBox.setVisible(HURTBOX_IS_VISIBLE);
		hurtBox.sendToFront();
		System.out.println("Hurtbox for " + characterName + " created.");
		
	}
	
	public GOval getHurtbox() {
		System.out.println("Hurtbox for " + characterName + " given to Level.");
		return hurtBox; 
	}
	
	
	
	private void setCollision() {
	//		
	//		GPoint charCenterPoint = getCenterPoint(this);
	//		
	//		this.setBounds(charCenterPoint.getX()-COLLISION_BOX_WIDTH,charCenterPoint.getY()-COLLISION_BOX_HEIGHT,COLLISION_BOX_WIDTH, COLLISION_BOX_HEIGHT);
	//	
	//		System.out.println("Collision box changed for character.");
			
			
	
			GPoint charCenterPoint = getCenterPoint(this);
			
			collisionBox = new GRect(charCenterPoint.getX()-(COLLISION_BOX_WIDTH/2),charCenterPoint.getY()-(COLLISION_BOX_HEIGHT/2),COLLISION_BOX_WIDTH, COLLISION_BOX_HEIGHT);
			collisionBox.setColor(COLLISION_BOX_COLOR);
			collisionBox.setVisible(COLLISION_IS_VISIBLE);
			collisionBox.sendToFront();
			
			System.out.println("Collision box GRect created for character.");
		}

	public GRect getCollisionBox() {
		System.out.println("collisionBox for " + characterName + " given to Level.");
		return collisionBox;
	}

	public String getCharacterName(){
		return characterName;
	}
	
	public int getHealth() {
		return healthPoints;
	}
	
	public void setOrientation(Orientation direction) {
		
		forwardDirection = direction;
		
	}
	
	public void knockback(Orientation direction, ArrayList<Object> objList) {
		for (int i = 0 ; i < 20; i++) {
			
			if (direction.equals(Orientation.RIGHT)) {
				move(Orientation.LEFT, objList);				
			}
			else {
				move(Orientation.RIGHT, objList);				
			}
		}
	}
	
	public void move(Orientation moveDirection, ArrayList<Object> objList) {
		
		double pixelsMoved = 1;
		for(int i = 0; i < 10; i++) {
		if (canMove(moveDirection,objList)) {
			
			switch(moveDirection) {
				case LEFT:
					this.move(-pixelsMoved, 0);
					hurtBox.move(-pixelsMoved, 0);
					collisionBox.move(-pixelsMoved, 0);

					break;
				case RIGHT: 
					this.move(pixelsMoved, 0);
					hurtBox.move(pixelsMoved, 0);
					collisionBox.move(pixelsMoved, 0);
					break;
				case UP: 
					this.move(0, -pixelsMoved);
					hurtBox.move(0, -pixelsMoved);
					collisionBox.move(0, -pixelsMoved);
					break;
				case DOWN: 
					this.move(0, pixelsMoved);
					hurtBox.move(0, pixelsMoved);
					collisionBox.move(0, pixelsMoved);
					break;
				default: 
					System.out.println(this.getImageName() + " move() ERROR, incorrect enumerated Orientation type given");				
			}
		}
		else {
			System.out.println(this.getImageName() + " move(): collision detected, movement rejected");				
		}
		
		}
	}
	
	public boolean canMove(Orientation moveDirection, ArrayList<Object> objList) {
		// receives a list of all objects on level
		// if this object is touching another, it shoudn't be able to move in that direction
		// if it is touching another object, obtain coordinates of that object and compare it to the
		// coordinates of this object to find the direction
		// prohibit movement from that given direction
		
		int i = 0;
		for (Object gameObj : objList) {
			if (gameObj != this) {
				// Checks to see the bounds of this object intersects with
				// another object in the 
				
				if(this.getCollisionBox().getBounds().intersects(gameObj.getBounds())) {
					
					System.out.println("Two objects intersect:");
					this.printInfo();
					gameObj.printInfo();
					
					// Checks to see from which direction an object is blocked
					// Needed for future character movement
					
					if (getCenterPoint(this).getX() <= getCenterPoint(gameObj).getX()) {							
						if(!upDownThreshold(gameObj)) {
							
							System.out.println(this.getImageName() + " collides with another object named " + gameObj.getImageName() + " can not move to the right.");
							
							if (moveDirection == moveDirection.RIGHT) {
								System.out.println("canMove(): False");
								return false;
							}
							continue;
						}
					}
					else if (getCenterPoint(this).getX() > getCenterPoint(gameObj).getX()) {
						
						if(!upDownThreshold(gameObj)) {

							System.out.println(this.getImageName() + " collides with another object named " + gameObj.getImageName() + " can not move to the left.");
					
							if (moveDirection == moveDirection.LEFT) {
								System.out.println("canMove(): False");
								return false;
							}
							continue; // Because an object can physically collide from the left or right OR up or down, but never both at the same time
						}
					}
					
					if (this.getUpperBound() >= gameObj.getLowerBound()) {
						System.out.println(this.getImageName() + " collides with another object named " + gameObj.getImageName() + " can not move upward.");
					
						if (moveDirection == moveDirection.UP) {
							System.out.println("canMove(): False");
							return false;
						}
					}
					else if (this.getLowerBound() >= gameObj.getUpperBound()) {
						System.out.println(this.getImageName() + " collides with another object named " + gameObj.getImageName() + " can not move down.");
				
						if (moveDirection == moveDirection.DOWN) {
							System.out.println("canMove(): False");
							return false;
						}
					}
					
					
					System.out.println("Collision detection direction is empty.");
				}
				
							
			}
			i++;
		}
		
		System.out.println("Object does NOT collide with another object in that particular move direction");
		
		return true;
	}	
	
	private Boolean upDownThreshold(Object otherObj) {
		if ((otherObj.getUpperBound() <= this.getLowerBound()) && (this.getLowerBound() <= otherObj.getUpperBound()+3)) {
			System.out.println("Within lower threshold");
			return true; // True = within threshold obtained
		}
		
		System.out.println("Not within threshold");
		return false;
	}
	
	protected double getLowerBound() {		
		return this.getCollisionBox().getBounds().getY()+this.getCollisionBox().getBounds().getHeight();
	}
	

	protected double getUpperBound() {
		return this.getCollisionBox().getBounds().getY();
	}
	
	public Orientation getOrientation () {
		
		return forwardDirection;
	}
	
	
	
	public void setCharacterAnimation() {
		
		kickAttack();
		punchAttack();
		block();
		restAnimation();
		knockBack();
		sprintOrWalk();
		tookDamage();
		specialAttack();
		
		System.out.println("Set all character animations.");
		
	}

	@Override
	public void sprintOrWalk() {
		// TODO Auto-generated method stub
		ArrayList<GImage> sprintAnimationL = new ArrayList<GImage>();
		ArrayList<GImage> sprintAnimationR = new ArrayList<GImage>();
		GImage image;
		
		for(int i = 1; i < 14; i++) {
			image = new GImage(IMAGE_FILENAME_PATH + characterName + "AnimationWalkTransparent" + LEFT_DIRECTION + i + IMAGE_EXTENSION);
			sprintAnimationL.add(image);
		}
		
		characterAnimation.put("walkLEFT",sprintAnimationL);
		
		for(int i = 1; i < 14; i++) {
			image = new GImage(IMAGE_FILENAME_PATH + characterName + "AnimationWalkTransparent" + RIGHT_DIRECTION + i + IMAGE_EXTENSION);
			sprintAnimationR.add(image);
		}
		
		characterAnimation.put("walkRIGHT",sprintAnimationR);
		
	}

	@Override
	public void block() {
		// TODO Auto-generated method stub
		ArrayList<GImage> blockAnimationL = new ArrayList<GImage>();
		ArrayList<GImage> blockAnimationR = new ArrayList<GImage>();
		GImage image;
		
		for(int i = 1; i < 7; i++) {
			image = new GImage(IMAGE_FILENAME_PATH + characterName + "AnimationBlockTransparent" + LEFT_DIRECTION + i + IMAGE_EXTENSION);
			blockAnimationL.add(image);
		}
		
		characterAnimation.put("blockLEFT",blockAnimationL);
		
		for(int i = 1; i < 7; i++) {
			image = new GImage(IMAGE_FILENAME_PATH + characterName + "AnimationBlockTransparent" + RIGHT_DIRECTION + i + IMAGE_EXTENSION);
			blockAnimationR.add(image);
		}
		
		characterAnimation.put("blockRIGHT",blockAnimationR);
		
	}

	@Override
	public void punchAttack() {
		// TODO Auto-generated method stub
		ArrayList<GImage> punchAnimationL = new ArrayList<GImage>();
		ArrayList<GImage> punchAnimationR = new ArrayList<GImage>();
		GImage image;
		
		for(int i = 1; i < 7; i++) {
			image = new GImage(IMAGE_FILENAME_PATH + characterName + "AnimationPunchTransparent" + LEFT_DIRECTION + i + IMAGE_EXTENSION);
			punchAnimationL.add(image);
		}
		
		characterAnimation.put("punchLEFT",punchAnimationL);
		
		for(int i = 1; i < 7; i++) {
			image = new GImage(IMAGE_FILENAME_PATH + characterName + "AnimationPunchTransparent" + RIGHT_DIRECTION + i + IMAGE_EXTENSION);
			punchAnimationR.add(image);
		}
		
		characterAnimation.put("punchRIGHT",punchAnimationR);
	}

	@Override
	public void kickAttack() {
		// TODO Auto-generated method stub
		
		ArrayList<GImage> kickAnimationL = new ArrayList<GImage>();
		ArrayList<GImage> kickAnimationR = new ArrayList<GImage>();
		GImage image;
		
		for(int i = 1; i < 7; i++) {
			image = new GImage(IMAGE_FILENAME_PATH + characterName + "AnimationKickTransparent" + LEFT_DIRECTION + i + IMAGE_EXTENSION);
			kickAnimationL.add(image);
		}
		
		characterAnimation.put("kickLEFT",kickAnimationL);
		
		for(int i = 1; i < 7; i++) {
			image = new GImage(IMAGE_FILENAME_PATH + characterName + "AnimationKickTransparent" + RIGHT_DIRECTION + i + IMAGE_EXTENSION);
			kickAnimationR.add(image);
		}
		
		characterAnimation.put("kickRIGHT",kickAnimationR);
		
	}

	@Override
	public void specialAttack() {
		// TODO Auto-generated method stub
		ArrayList<GImage> specialAnimationL = new ArrayList<GImage>();
		ArrayList<GImage> specialAnimationR = new ArrayList<GImage>();
		GImage image;
		
		for(int i = 1; i < 11; i++) {
			image = new GImage(IMAGE_FILENAME_PATH + characterName + "AnimationSpecialTransparent" + LEFT_DIRECTION + i + IMAGE_EXTENSION);
			specialAnimationL.add(image);
		}
		
		characterAnimation.put("specialLEFT",specialAnimationL);

		for(int i = 1; i < 11; i++) {
			image = new GImage(IMAGE_FILENAME_PATH + characterName + "AnimationSpecialTransparent" + RIGHT_DIRECTION + i + IMAGE_EXTENSION);
			specialAnimationR.add(image);
		}
		
		characterAnimation.put("specialRIGHT",specialAnimationR);
	}

	@Override
	public void tookDamage() {
		// TODO Auto-generated method stub
		ArrayList<GImage> tookDamageAnimationL = new ArrayList<GImage>();
		ArrayList<GImage> tookDamageAnimationR = new ArrayList<GImage>();
		GImage image;
		
		for(int i = 1; i < 4; i++) {
			image = new GImage(IMAGE_FILENAME_PATH + characterName + "AnimationDamageTransparent" + LEFT_DIRECTION + i + IMAGE_EXTENSION);
			tookDamageAnimationL.add(image);
		}
		
		characterAnimation.put("damageLEFT", tookDamageAnimationL);
		
		for(int i = 1; i < 4; i++) {
			image = new GImage(IMAGE_FILENAME_PATH + characterName + "AnimationDamageTransparent" + RIGHT_DIRECTION + i + IMAGE_EXTENSION);
			tookDamageAnimationR.add(image);
		}
		
		characterAnimation.put("damageRIGHT", tookDamageAnimationR);
	}

	@Override
	public void knockBack() {
		// TODO Auto-generated method stub
		ArrayList<GImage> knockBackAnimationL = new ArrayList<GImage>();
		ArrayList<GImage> knockBackAnimationR = new ArrayList<GImage>();
		GImage image;
		
		for(int i = 1; i < 3; i++) {
			image = new GImage(IMAGE_FILENAME_PATH + characterName + "AnimationKnockTransparent" + LEFT_DIRECTION + i + IMAGE_EXTENSION);
			knockBackAnimationL.add(image);
		}
		
		characterAnimation.put("knockLEFT",knockBackAnimationL);
		
		for(int i = 1; i < 3; i++) {
			image = new GImage(IMAGE_FILENAME_PATH + characterName + "AnimationKnockTransparent" + RIGHT_DIRECTION + i + IMAGE_EXTENSION);
			knockBackAnimationR.add(image);
		}
		
		characterAnimation.put("knockRIGHT",knockBackAnimationR);
	}

	@Override
	public void restAnimation() {
		// TODO Auto-generated method stub
		ArrayList<GImage> restAnimationL = new ArrayList<GImage>();
		ArrayList<GImage> restAnimationR = new ArrayList<GImage>();
		GImage image;
		
		for(int i = 1; i < 7; i++) {
			image = new GImage(IMAGE_FILENAME_PATH + characterName + "AnimationRestTransparent" + LEFT_DIRECTION + i + IMAGE_EXTENSION);
			restAnimationL.add(image);
		}
		
		characterAnimation.put("restLEFT",restAnimationL);
		
		for(int i = 1; i < 7; i++) {
			image = new GImage(IMAGE_FILENAME_PATH + characterName + "AnimationRestTransparent" + RIGHT_DIRECTION + i + IMAGE_EXTENSION);
			restAnimationR.add(image);
		}
		
		characterAnimation.put("restRIGHT",restAnimationR);
	}
	
	@Override
	public ArrayList<GOval> getHitBox() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public String getCharName() {
		return characterName;
	}
	
	

	
	
	public static void main(String[] args) {
		
		
		
		Character player = new Character("Beast Titan", Orientation.LEFT, 100, 100);
		
		System.out.println("kick" + player.getOrientation());
		
		
		
	}


	
	
}
