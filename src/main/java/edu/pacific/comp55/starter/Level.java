package edu.pacific.comp55.starter;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.graphics.GRectangle;

//done

public class Level extends GraphicsApplication {

	private static final int DAMAGE_PER_ATTACK = 25;

	private static final Color HITBOX_COLOR = Color.RED;

	private static final int HITBOX_HEIGHT = 100;

	private static final int HITBOX_WIDTH = 60;

	private static final boolean HITBOX_IS_VISIBLE = true;

	private static final boolean BARRIER_BLOCK_VISIBLE = false;

	private static final String BARRIER_BLOCK_IMAGE = "media/" + "COMP055 - Barrier Block 2.png";
	
	public boolean hitboxHit = false;
	private int numOfTimesDamaged = 0;

	int start = 0;
	
	private static final int REC_LENGTH = 250;
	private static final int REC_WIDTH = 300;
	private static final int START_Y = 200;
	private static final int START_X = 250;
	private boolean P1Left;
	private boolean P1Right;
	private boolean P1Q;
	private boolean P2Left;
	private boolean P2Right;
	


	private MainApplication mainApp;//add constructor 
	private GOval PlayerOneHurtbox;
	private GOval PlayerTwoHurtbox;
	
	private int playerOneDefaultSpawnX = 100;
	private int playerOneDefaultSpawnY = -90;


	private int playerTwoDefaultSpawnX = 600;
	private int playerTwoDefaultSpawnY = -90;

	private Character PlayerOne;
	private Character PlayerTwo;
	public FightingMoves play1;
	public FightingMoves play2;


	private static final int WINDOW_WIDTH = 1285;
	private static final int WINDOW_HEIGHT = 690;

	private static final int SIZE = 200;

	//private static final int controlList [] = new int [] {KeyEvent.VK_Q , KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_O,
			//KeyEvent.VK_P, KeyEvent.VK_OPEN_BRACKET, KeyEvent.VK_L, KeyEvent.VK_COLON, KeyEvent.VK_COMMA};
	
	// ArrayList has search
	// Will just need to store object, GImages will not need to be accessed by any other objects
	// GImages can be manipulated within object class
	private ArrayList<Object> objList = new ArrayList<Object>();
	
	// Recommend to use HashMap to store character assets because attached GOval will have to 
	// be accessed to other classes
		// May waste memory though
//	private ArrayList<Character> charList = new ArrayList<Character>();

	private ArrayList<GRect> collisonBoxes = new ArrayList<GRect>(); 
	
	//Timer moveTime = new Timer(1000, this);
	


	private MenuScreen menu;
	private MapSelect mapSelect;
	private OptionScreen options;
	private CreditScreen credit;
	private winScreen win;
	
	private pauseGame pause;
	private gameplay gp;
	private boolean stopTime = false;
	
	private CharScreen characterSelect;
	private int checkPause = 0;
	private int score[] = {0,0};
	private int winCount[] = {0,0};
	private int winner;
	private Characters[] playcharacter = {null, null};
	
	private GImage Background;
	private GImage gpMap;

	
	@Override
	public void run() {
		
		// TODO Auto-generated method stub
		
		// (GImage, width, height)

		//MainApp Merge
		playSound(0, true);
		menu = new MenuScreen(this);
		options = new OptionScreen(this);
		credit = new CreditScreen(this);
		win = new winScreen(this);
		
		mapSelect = new MapSelect(this);
		characterSelect = new CharScreen(this);
		
		pause = new pauseGame(this);
		gp = new gameplay(this);
		
		Background = new GImage("menuBackGround.png", 0, 0);
		Background.setSize(1285, 690);
		add(Background);
		
		gpMap = new GImage("", 0, 0);

		setupInteractions();
		
		switchToMenu();
		//MainApplication Merge
		
		/*createNewChar(CharacterCommands.PLAYER1,Characters.FEMALE_TITAN);
	
		
		createNewChar(CharacterCommands.PLAYER2,Characters.FEMALE_TITAN);
		
		generateHitboxes(CharacterCommands.PLAYER1,CharacterCommands.SPECIAL);
		generateHitboxes(CharacterCommands.PLAYER2,CharacterCommands.SPECIAL);*/
		
		//add(PlayerOne.characterAnimation.get("kick").get(4));

		
		// Testing object collision
		/*createNewObj("media/" + "COMP055 - Stone Block Platform.png",0,400, 800, 200);
		createNewObj("media/" + "Thanos.png",200,251,150, 150);
		createNewObj("media/" + "aot v1 armored model.png",5,251, 150, 150);*/

		createSkyBox();
		
		play1 = new FightingMoves(this, 1, "rest");
		play2 = new FightingMoves(this, 2, "rest");
		

		
//		objList.get(2).move(Orientation.LEFT, objList);
		

	}
	


	
	// two intuitive options : a timer that calls movement every few miliseconds, or just call movement when a key is pressed.
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (!stopTime) {
		switch(e.getKeyCode()) {
			
			case KeyEvent.VK_Q :
				//P1 block
				//P1Q = true;
				//Movement(P1Q);
			
				play1.setAction("block");
				System.out.println(play1.startTimer());
				//playAnimation("block", 1);
				break;
				
			case KeyEvent.VK_W:
				//P1 punch
				play1.setAction("punch");
				play1.startTimer();
				//playAnimation("punch", 1);
				break;
				
			case KeyEvent.VK_E :
				play1.setAction("kick");
				play1.startTimer();
				//playAnimation("kick", 1);
				//System.out.println(playAnimation("kick", 1));	
				//System.out.println("Player One kick");
				break;
				
			case KeyEvent.VK_A:
				//P1 walk left
				P1Left = true;
				//Movement(P1Left);
				PlayerOne.move(Orientation.LEFT, objList);
				if(P2Left) {
					PlayerTwo.move(Orientation.LEFT, objList);
				}
				if(P2Right) {
					PlayerTwo.move(Orientation.RIGHT, objList);
				}
				PlayerOne.setOrientation(Orientation.RIGHT);

				
				break;
			case KeyEvent.VK_S:
				play1.setAction("special");
				play1.startTimer();
				//playAnimation("special", 1);
					
				break;
			case KeyEvent.VK_D : 
				PlayerOne.setOrientation(Orientation.LEFT);

				P1Right = true;
				PlayerOne.move(Orientation.RIGHT, objList);
				if(P2Left) {
					PlayerTwo.move(Orientation.LEFT, objList);
				}
				if(P2Right) {
					PlayerTwo.move(Orientation.RIGHT, objList);
				}
			
				break;
				
		
		case KeyEvent.VK_O:
			//P2 block
			play2.setAction("block");
			play2.startTimer();
			//playAnimation("block", 2);
			break;
			
		case KeyEvent.VK_P:
			//P2 punch
			play2.setAction("punch");
			play2.startTimer();
			//playAnimation("punch", 2);
			
			break;
		case KeyEvent.VK_OPEN_BRACKET:
			
			play2.setAction("kick");
			play2.startTimer();
			
			//System.out.println("Player 2 kick");
			//playAnimation("kick", 2);	
			
			break;
		case KeyEvent.VK_L:
			PlayerTwo.setOrientation(Orientation.RIGHT);
			P2Left = true;
			//Movement(P2Left);
			PlayerTwo.move(Orientation.LEFT, objList);
			if(P1Right) {
				PlayerOne.move(Orientation.RIGHT, objList);
			}
			if(P1Left) {
				PlayerOne.move(Orientation.LEFT, objList);
			}
			break;
		case KeyEvent.VK_COLON:
			//p2 special
			play2.setAction("special");
			play2.startTimer();
			
			//playAnimation("special", 2);
			break;
			
		case KeyEvent.VK_QUOTE:
			PlayerTwo.setOrientation(Orientation.LEFT);

			//p2 walk right 
			P2Right = true;
			//Movement(P2Right);
			PlayerTwo.move(Orientation.RIGHT, objList);
			if(P1Right) {
				PlayerOne.move(Orientation.RIGHT, objList);
			}
			if(P1Left) {
				PlayerOne.move(Orientation.LEFT, objList);
			}
			break;
		default: 
			break;
		}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A) P1Left = false;
        if (e.getKeyCode() == KeyEvent.VK_D) P1Right = false;
        if (e.getKeyCode() == KeyEvent.VK_L) P2Left = false;
        if (e.getKeyCode() == KeyEvent.VK_QUOTE) P2Right = false;
        if(e.getKeyCode() == KeyEvent.VK_Q) P1Q = false;
        if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) showPause();
        if(e.getKeyCode() == KeyEvent.VK_BACK_SLASH) hidePause();
	}
	
	public void createSkyBox() {
		
		System.out.println("Skybox creation process intiated.");
		
		// Left border
		createNewCollision(0,0,5,WINDOW_HEIGHT);
		// Right border
		createNewCollision(WINDOW_WIDTH-5,0,5,WINDOW_HEIGHT);
		// Top border
		createNewCollision(0,0,WINDOW_WIDTH,5);
		// Bottom border
		createNewCollision(0,WINDOW_HEIGHT-5,WINDOW_WIDTH,5);
	
		System.out.println("Skybox successfully created.");
	}
	
	
	// Draws collision boxes of objects/characters in the game for game development purposes
	public void drawCollisions() {
		
		GRectangle bounds;
		GRect visibleBounds;
		
		for (Object obj : objList) {
			bounds = obj.getBounds();
			
			visibleBounds = new GRect(bounds.getX(),bounds.getY(),bounds.getWidth(),bounds.getHeight());
			add(visibleBounds);
			collisonBoxes.add(visibleBounds);
		}
		
		bounds = PlayerOne.getBounds();
		
		visibleBounds = new GRect(bounds.getX(),bounds.getY(),bounds.getWidth(),bounds.getHeight());
		add(visibleBounds);
		collisonBoxes.add(visibleBounds);
		
		bounds = PlayerTwo.getBounds();
		
		visibleBounds = new GRect(bounds.getX(),bounds.getY(),bounds.getWidth(),bounds.getHeight());
		add(visibleBounds);
		collisonBoxes.add(visibleBounds);
		
	}
	
	// May want to create GImage subclass that adds width and height parameters
	public void createNewObj(String objImageName, int x, int y, int width, int height) {
		
		Object newObj = new Object(objImageName, x, y, width, height);
		
		add(newObj);
		
		objList.add(newObj);
	}
	
	public void createNewCollision(int x, int y, int width, int height) {
		
		Object newObj = new Object(BARRIER_BLOCK_IMAGE, x, y, width, height);
		newObj.setVisible(BARRIER_BLOCK_VISIBLE);
		
		add(newObj);
		
		objList.add(newObj);
	}
	
	
	// Citation: https://www.tutorialspoint.com/How-to-convert-string-to-array-of-integers-in-java#:~:text=You%20can%20convert%20a%20String,the%20integer%20array%20with%20them.
//	private double[] convertStringArrayToDouble(String[] str) {
//	     int size = str.length;
//	     double[] arr = new double [size];
//	  
//	     for(int i=0; i<size; i++) {
//	        arr[i] = Double.parseDouble(str[i]);
//	     }
//	     return arr;
//	}
//	
	private void changeFromOffsetCoor(GOval hitbox, GPoint charCenterPoint, Orientation charOrientation) {
		
		// Right facing: hitboxX - centerX = X positive offset
		System.out.println("Raw character center point is: x: " + charCenterPoint.getX() + ", y: " + charCenterPoint.getY());
		
		double offsetX = hitbox.getX();
		
		// Flips the hitbox spawn location if character is facing a different direction
		if (charOrientation.equals(Orientation.RIGHT)) {
			offsetX = offsetX * -1;
			System.out.println("offsetX mirrored for orientation right");
		}
		
		double absX = offsetX+charCenterPoint.getX();
		double absY = hitbox.getY()+charCenterPoint.getY();
	
		hitbox.setLocation(absX, absY);
		
		System.out.println("Hitbox changed to absolute coordinates at: x: " + absX + " y: " + absY);
	}
//	
//	public ArrayList<double[]> getHitboxesData(String characterName, CharacterCommands attack) {
//			
//		 ArrayList<double[]> attackHitbox = new ArrayList<double[]>(); 
//		
//		try {			
//			  File myObj = new File("media/hitBoxes.txt");
//			  Scanner myReader = new Scanner(myObj);
//			  while (myReader.hasNextLine()) {
//			    String data = myReader.nextLine();
//			    
//			    String[] rawHitBoxData = data.split(",");
//			    double[] intHitBoxData;
//			    
//			    if (rawHitBoxData[0].equals(characterName) && rawHitBoxData[1].equals(attack.toString())) {
//			    	rawHitBoxData = Arrays.copyOfRange(rawHitBoxData,3,rawHitBoxData.length);
//			    	intHitBoxData = convertStringArrayToDouble(rawHitBoxData);
//			    	attackHitbox.add(intHitBoxData);
//			    }
// 			  }
//			  myReader.close();			  
//			} catch (FileNotFoundException e) {
//			  System.out.println("An error occurred.");
//				      e.printStackTrace();
//			}
//		
//		
//		  return attackHitbox;
//	}
//	
	
	public void generateHitboxes(Character player, int j, ArrayList<double[]> attackHitboxData) {
		
		System.out.println("Hitboxes set visible: " + HITBOX_IS_VISIBLE);
		
		Orientation charOrientation; // This needs to obtain the character's current orientation
		
		GPoint characterCenter;
		String playerCharName;
		GOval enemyHurtbox;
		CharacterCommands enemyPlayer;
		
		ArrayList<GOval> hitboxGOvals = new ArrayList<GOval>();
		double[] frameData = attackHitboxData.get(j);	
	
		System.out.println("generateHitboxes index: " + j);
		
		if (PlayerOne == player) {
			
			characterCenter = PlayerOne.getCenterPoint(PlayerOne);
			playerCharName = PlayerOne.getCharName();
			charOrientation = PlayerOne.getOrientation();
			enemyPlayer = CharacterCommands.PLAYER2;
			
			enemyHurtbox = PlayerTwoHurtbox;
			
		}
		
		else if (PlayerTwo == player) {
			
			characterCenter = PlayerTwo.getCenterPoint(PlayerTwo);
			playerCharName = PlayerTwo.getCharName();
			charOrientation = PlayerTwo.getOrientation();
			enemyPlayer = CharacterCommands.PLAYER1;

			enemyHurtbox = PlayerOneHurtbox;

			
		}
		else {
			System.out.println("Failed to generate hitboxes. Invalid player given.");
			return;
		}
		
				
		
		// Goes through every frame in an attack
		// Remove all the hitbox GOvals that were just made			
		
		if (hitboxHit && (numOfTimesDamaged == 0)) {
			if(play1.getAction() == "block" && play2.getAction() != "block") {
				return;
			}
			if(play1.getAction() != "block" && play2.getAction() == "block") {
				return;
			}
			
			gp.tookDamage(enemyPlayer, DAMAGE_PER_ATTACK);
			System.out.println(enemyPlayer.toString() + " took damage from " + player.toString());
			
			if (player == PlayerOne) {
				play2.setAction("knock");
				PlayerTwo.knockback(PlayerOne.getOrientation(),objList);
			}
			else if (player == PlayerTwo) {
				play1.setAction("knock");
				PlayerOne.knockback(PlayerTwo.getOrientation(),objList);
			}
			
			numOfTimesDamaged++;
			return;
		}
		else if (hitboxHit) {
			System.out.println("Enemy character already took damage from hitboxes. No more hitboxes generated.");
			return;
		}
		// Remove all the hitbox GOvals that were just made			
				
		
		// Spawns hitboxes for one frame
		for (int i = 0; i < frameData.length; i = i + 2) {
			
			GOval tempGOval = new GOval(frameData[i],frameData[i+1],HITBOX_WIDTH, HITBOX_HEIGHT);
			changeFromOffsetCoor(tempGOval, characterCenter, charOrientation);
			tempGOval.setColor(HITBOX_COLOR);
			tempGOval.sendToFront();
			hitboxGOvals.add(tempGOval);
			add(tempGOval);
			System.out.println("Spawned a hitbox");
			
			if (tempGOval.getBounds().intersects(enemyHurtbox.getBounds())) {
				System.out.println(player.toString() + " hurts other player's hurtbox!");
				System.out.println("Stopped generating hitboxes.");
				
				hitboxHit = true; 
				break;
			}
		}
		
		for (GOval hitbox : hitboxGOvals) {
			remove(hitbox);
		}
		hitboxGOvals.clear();
		return;
	}
	
	public void resetNumOfTimesDamaged() {
		hitboxHit = false;
		numOfTimesDamaged = 0;
	}
	
	public void createNewChar(CharacterCommands playerNumber, Characters charName) {
				
		switch(playerNumber) {
		
			case PLAYER1:
				PlayerOne = new Character(charName.toString(), Orientation.LEFT, playerOneDefaultSpawnX, playerOneDefaultSpawnY); // Change orientation of this
								
				PlayerOneHurtbox = PlayerOne.getHurtbox();
				add(PlayerOne);
				add(PlayerOneHurtbox);
				add(PlayerOne.getCollisionBox());
				
//				objList.add(PlayerOne);
				
				
				break;
			case PLAYER2:
				PlayerTwo = new Character(charName.toString(), Orientation.RIGHT, playerTwoDefaultSpawnX, playerTwoDefaultSpawnY);
				
				PlayerTwoHurtbox = PlayerTwo.getHurtbox();
				add(PlayerTwo.getCollisionBox());
				
				add(PlayerTwo);
				add(PlayerTwoHurtbox);
//				objList.add(PlayerTwo);
				break;
			default:
				break;
		}
		
		// Complete character method
	}
	
	//MainApplication Merge
	public void showPause() {
		if (checkPause == 0) {
			checkPause++;
			stopTime = true;
			showScreen(pause);
		}
	}
	public void hidePause() {
		if (checkPause > 0) {
			checkPause = 0;
			stopTime = false;
			hideScreen();
		}
	}
	
	public void setMap(String map) {
		this.gpMap.setImage(map);
	}
	
	public boolean getPause() {
		return stopTime;
	}
	
	public void addscore(int num, int points) {
		score[num - 1] += points;
	}
	
	public int getscore(int num) {
		return score[num - 1];
	}
	
	public void addwinCount(int num) {
		winCount[num - 1] += 1;
	}
	
	public int getwinCount(int num) {
		return winCount[num - 1];
	}
	
	public void setWinner(int num) {
		winner = num;
	}
	
	public int getWinner() {
		return winner;
	}
	
	public void setCharacter(int num, Characters name) {
		if (num == 1) playcharacter[0] = name;
		else playcharacter[1] = name;
	}

	public Characters getCharacter(int num) {
		if (num == 1) return playcharacter[0]; else return playcharacter[1];
	}
	
	public Character getPlayer(int num) {
		if (num == 1) return PlayerOne; else return PlayerTwo;
	}
	
	public void switchToMenu() {
		if (checkPause != 1) switchToScreen(menu);
	}

	public void switchToMapS() {
		if (checkPause != 1) switchToScreen(mapSelect);
	}
	
	public void switchToOption() {
		if (checkPause != 1) switchToScreen(options);
	}
	
	public void switchToCredits() {
		if (checkPause != 1) switchToScreen(credit);
	}
	
	public void switchToChar() {
		if (checkPause != 1) switchToScreen(characterSelect);
	}
	
	public void switchToGame() {
		if (checkPause != 1) {
		remove(Background);
		add(gpMap);
		gpMap.setSize(1285, 690);
		stopSound(0);
		switchToScreen(gp);
		}
	}
	
	public void switchToWin() {
		if (checkPause != 1) {
			remove(gpMap);
			add(Background);
			stopSound(1);
			playSound(0, true);
			switchToScreen(win);
		}
	}
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	public static void main(String[] args) {
		new Level().start();
	}

}
