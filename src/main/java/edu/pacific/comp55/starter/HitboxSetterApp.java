package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GPoint;

public class HitboxSetterApp extends GraphicsApplication {

	
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final int SHAPE_SIZE = 60;
	
	//file editing
	static StringBuffer stringBufferOfData = new StringBuffer();
	
	// These are for mouseEvent  
	public static int lastX;
	public static int lastY;
	private MouseEvent m;
	private File file = new File("media/tempHitBoxes.txt");
	
	private GObject toDrag;
	
	private String NAME_OF_ATTACK = Attacks.values()[0].toString();
	private ArrayList<GOval> currentHitboxes = new ArrayList<GOval>();
	private LinkedHashMap<GImage, ArrayList<GOval>> imageHitboxes = new LinkedHashMap<GImage, ArrayList<GOval>>();
	
	// Complete data structure holding all images & hitboxes for the entire attack
	private LinkedHashMap<String, LinkedHashMap<GImage, ArrayList<GOval>>> attackHitboxes = new LinkedHashMap<String, LinkedHashMap<GImage, ArrayList<GOval>>>();
	
	private GImage charImage;

	
	// 	HashMap<String, ArrayList<GImage>> characterAnimation = new HashMap<String, ArrayList<GImage>>();
	// 	charac.characterAnimation.get("block");
	
	private int charIndex = 0;

	// Progress Labels
	private GLabel charLabel = new GLabel(CHARACTER_NAME.toString(), WINDOW_WIDTH-250,40);	
	private GLabel attackLabel = new GLabel(NAME_OF_ATTACK, WINDOW_WIDTH-250,50);	
	private GLabel completeLabel = new GLabel("Completed all frames!", (WINDOW_WIDTH/2) + 100,WINDOW_HEIGHT/2);
	private GLabel frameLabel= new GLabel(charIndex+1 + "/6 frames completed", WINDOW_WIDTH-250,60);	

	
	// Editting Control labels
	private GLabel SPAWN_CONTROL_LABEL = new GLabel("Press F to spawn new circle",0,40);
	private GLabel SAVE_CONTROL_LABEL = new GLabel("Press R to save and move to new frame",0,50);
	private GLabel REMOVE_CONTROL_LABEL = new GLabel("Press E to remove all hitboxes",0,60);
	private GLabel HITBOX_CONTROL_LABEL = new GLabel("Press D to remove previous hitbox",0,70);
	private GLabel MOVE_BACK_LABEL = new GLabel("Press C to move back a frame",0,80);
	private GLabel MOVE_FORWARD_LABEL = new GLabel("Press V to move forward a frame",0,90);
	private GLabel SAVE_FILE_LABEL = new GLabel("Press Z to save to file",0,100);
	
	// This is created so we can obtain all of the data structures containing all of animation images we will be adding hitboxes to
	private Character charac = new Character(CHARACTER_NAME.toString(),CHARACTER_ORIENTATION,WINDOW_WIDTH/2, WINDOW_HEIGHT/2);
	
	
	// Case and letter sensitive
		// Characters.BEAST_TITAN
		// Characters.ARMORED_TITAN
		// Characters.FEMALE_TITAN;
		// Characters.ATTACK_TITAN;
	
	private static final Characters CHARACTER_NAME = Characters.FEMALE_TITAN;
	private static final Orientation CHARACTER_ORIENTATION = Orientation.LEFT;

	enum Characters {
		BEAST_TITAN("Beast Titan"),
		ARMOR_TITAN("Armor Titan"),
		FEMALE_TITAN("Female Titan"),
		ATTACK_TITAN("Attack Titan");
		
		String charName;
		
		Characters(String charName) {
			this.charName = charName;
		}
		
		@Override
		public String toString() {
			return this.charName;
		}
	}

	enum Attacks {
		PUNCH("punchLEFT"),
		KICK("kickLEFT"),
		SPECIAL("specialLEFT");
		
		String attackName;
		
		Attacks(String attackName) {
			this.attackName = attackName;
		}
		
		@Override
		public String toString() {
			return this.attackName;
		}
	}

	public void run() {
		
		setEdittingLabels();
		
		addMouseListeners();
		addKeyListeners();
		
		updateLabels();
		setChar();
	}
	
	//Link to source site: https://www.daniweb.com/programming/software-development/code/408638/read-edit-and-write-to-file
	public void saveToFile() {
		//File file = new File(filePath);
	        try {
	            BufferedWriter bufwriter = new BufferedWriter(new FileWriter(file));
	            
//	            LinkedHashMap<String, LinkedHashMap<GImage, ArrayList<GOval>>> 
	            
                bufwriter.newLine();
	            
	            for (String key : attackHitboxes.keySet()) {
	            	
	            	System.out.println("key is: " + key);
	            	
	            	LinkedHashMap<GImage, ArrayList<GOval>> specificHitboxes = attackHitboxes.get(key);
	            	
	            	System.out.println("Size of specificHitboxes for key " + key + ": " + specificHitboxes.size());
	            		            		
	            		
	            	for (GImage hitboxKey : specificHitboxes.keySet()) {

		            	bufwriter.write(CHARACTER_NAME.toString()+"," + key + "," + CHARACTER_ORIENTATION + ",");//writes the edited string buffer to the new file
	            		
	            		for (GOval hitbox : specificHitboxes.get(hitboxKey)) {
	            			System.out.println("Saving the line: " + hitbox.getX() + "," + hitbox.getY() + ",");
	            			bufwriter.write(hitbox.getX() + "," + hitbox.getY() + ",");
	            		}
	            		
    	                bufwriter.newLine();
	            	}
	            }
	            	
	            
	            System.out.println("Successfully saved to file: " + file);
	            System.out.println("Closed the file: " + file);
	            bufwriter.close();//closes the file

	        } catch (Exception e) {//if an exception occurs
	            System.out.println("Error occured while attempting to write to file: " + e.getMessage());
	        }
	}


	@Override
	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode()) {
			
			case KeyEvent.VK_F :
				System.out.println("Spawn new circle");
				addCircle();
				break;
			case KeyEvent.VK_R :
				System.out.println("Save and move to new frame");
				
				saveCurrentHitboxes();
				clearCurrentHitboxes();
				moveForwardFrame();
				
				if (charIndex >= 6) {
					moveForwardAttack();
				}
				
				updateCharImage();
				generateSavedCircles();
				
				// use only for test purposes
				// O(n)
//				printSize();
				break;
			case KeyEvent.VK_E :
				System.out.println("Remove all hitboxes");
				clearCurrentHitboxes();
				break;
			case KeyEvent.VK_D :
				System.out.println("Remove previous hitbox");
				clearLastHitbox();
				break;
				
			case KeyEvent.VK_C :
				System.out.println("Move to left frame");
				saveCurrentHitboxes();
				clearCurrentHitboxes();
				moveBackFrame();
				updateCharImage();
				generateSavedCircles();

				break;
			case KeyEvent.VK_V :
				System.out.println("Move to right frame");
				saveCurrentHitboxes();
				clearCurrentHitboxes();
				moveForwardFrame();
				updateCharImage();
				generateSavedCircles();

				break;
				
			case KeyEvent.VK_B :
				System.out.println("Move to right attack");
				saveCurrentHitboxes();
				clearCurrentHitboxes();
				moveForwardAttack();
				updateCharImage();
				generateSavedCircles();

				break;
				
			case KeyEvent.VK_X :
				System.out.println("Move to left attack");
				saveCurrentHitboxes();
				clearCurrentHitboxes();
				moveBackAttack();
				updateCharImage();
				generateSavedCircles();
				updateLabels();

				break;
				
			case KeyEvent.VK_Z :
				System.out.println("Save to File");
				saveToFile();

				break;
				
			default: 
				break;
		
		}
	}


	private void updateLabels() {
		attackLabel.setLabel(NAME_OF_ATTACK);
		frameLabel.setLabel(charIndex+1 + "/6 frames completed for: " + NAME_OF_ATTACK);

	}
	
	private void initializeAttackHitboxes() {
		
		for (Attacks attack : Attacks.values())  {
			attackHitboxes.put(attack.toString(), null);
		}
		
		NAME_OF_ATTACK = Attacks.values()[0].toString();
		System.out.println(NAME_OF_ATTACK);
		
	}
	
	private void setEdittingLabels() {
		add(SPAWN_CONTROL_LABEL);
		add(SAVE_CONTROL_LABEL);
		add(REMOVE_CONTROL_LABEL);
		add(HITBOX_CONTROL_LABEL);
		add(MOVE_BACK_LABEL);
		add(MOVE_FORWARD_LABEL);
		add(SAVE_FILE_LABEL);
		
		add(charLabel); 
		add(attackLabel);
		add(frameLabel);
		
	}
	
	private void addCircle() {
		
		if (m != null) {
		
			GOval oval = new GOval(m.getX()-(SHAPE_SIZE/2), m.getY()-(SHAPE_SIZE/2), SHAPE_SIZE, SHAPE_SIZE);
			oval.setColor(Color.RED);
			oval.setFilled(false);
			add(oval);
			
			currentHitboxes.add(oval);
			
			System.out.println("New circle added.");
		}
	}


	private void clearCurrentHitboxes() {
			
		if (!currentHitboxes.isEmpty()) {
			for (GOval hitbox : currentHitboxes) {
				remove(hitbox);
			}
			
			currentHitboxes.clear();
		}
	}
	
	private void clearLastHitbox() {
		if (!currentHitboxes.isEmpty()) {
			remove(currentHitboxes.get(currentHitboxes.size()-1));
			currentHitboxes.remove(currentHitboxes.size()-1);	
			System.out.println("Successfully removed latest hitbox.");
		}
	}
	
	private void saveAttackHitboxes() {
		System.out.println("imageHitbox added to attackHitboxes hashmap");
		attackHitboxes.put(NAME_OF_ATTACK, imageHitboxes);
		
	}
	
	
	private void saveCurrentHitboxes() {
		
		ArrayList<GOval> savedHitboxes = new ArrayList<GOval>();
		
		if (charImage == null) {
			System.out.println("Error with saving current hitboxes: charImage is null");
			return;
		}
		if (currentHitboxes.isEmpty()) {
			System.out.println("Error with saving current hitboxes: currentHitboxes is empty");
			return;
		}
		
		for (GOval hitbox : currentHitboxes) {
			GOval savedHitbox = new GOval(hitbox.getX(), hitbox.getY(),hitbox.getWidth(), hitbox.getHeight());
			changeToOffsetCoor(savedHitbox);
			savedHitbox.setColor(Color.RED);
			savedHitboxes.add(savedHitbox);
		}
		
		imageHitboxes.put(charImage, savedHitboxes);
		System.out.println("There were " + savedHitboxes.size() + " hitboxes saved!");
		System.out.println("Successfully saved " + charIndex + " out of 6 frames.");
		completeLabel.setVisible(false);	
		

	}

	private void generateSavedCircles() {
			
			ArrayList<GOval> savedHitboxes = new ArrayList<GOval>();
			if (imageHitboxes != null) {
				System.out.println("Generating hit boxes from current imageHitboxes");
				savedHitboxes = imageHitboxes.get(charImage);
			}
			else if (imageHitboxes == null) {
				System.out.println("Generating hit boxes from attackHitboxes");
				savedHitboxes = attackHitboxes.get(NAME_OF_ATTACK).get(charImage);
				
				
			}
			if (savedHitboxes == null) {
				System.out.println("savedHitboxes is empty");
			}
			else {
				System.out.println("savedHitboxes size: " + savedHitboxes.size());

			}
			
			
			
			if (attackHitboxes.get(NAME_OF_ATTACK) == null ) {
				System.out.println("imageHitboxes is of size: " + 0);
			}
			else {
				System.out.println("imageHitboxes is of size: " + attackHitboxes.get(NAME_OF_ATTACK).size());
			}
			
			if (savedHitboxes != null) {
				System.out.println("generateSavedCircles(): Accessing saved circles from charImage key of reference: " + charImage);				
				
				// Test:
	//			charImage.move(300, 0);
	//			add(charImage);
	//			test reveals that the image is the correct one
								
				int i = 0;
				for (GOval hitbox : savedHitboxes) {
					changeFromOffsetCoor(hitbox);
					add(hitbox);
					currentHitboxes.add(hitbox);
				
					System.out.println("generateSavedCircles(): added " + i + " circle." );
					i++;
				}
			}
			else {
				System.out.println("generateSavedCircles(): Current animation does not have any saved frames yet.");
				return;
			}
		}


	// to
	private void changeToOffsetCoor(GOval hitbox) {
		
		GPoint centerPoint = getCenterPoint(charImage);
			
		// Right facing: hitboxX - centerX = positive X offset
	
		double offsetX = hitbox.getX()-centerPoint.getX();
		double offsetY = hitbox.getY()-centerPoint.getY();
	
		hitbox.setLocation(offsetX, offsetY);
		
	}


	// from
	private void changeFromOffsetCoor(GOval hitbox) {
		
		GPoint centerPoint = getCenterPoint(charImage);
		
		// Right facing: hitboxX - centerX = X positive offset
	
		double offsetX = hitbox.getX()+centerPoint.getX();
		double offsetY = hitbox.getY()+centerPoint.getY();
	
		hitbox.setLocation(offsetX, offsetY);
	}


	private GPoint getCenterPoint(GImage gObj) {
		
		double centerX = gObj.getX() + (gObj.getWidth()/2);
		double centerY = gObj.getY() + (gObj.getHeight()/2);
		
		GPoint centerPoint = new GPoint(centerX, centerY);		
		
		return centerPoint;
	}


	private void setChar() {
		if (charIndex == 0) {
			System.out.println("Name of attack was: " + NAME_OF_ATTACK);
			charImage = charac.characterAnimation.get(NAME_OF_ATTACK).get(charIndex);
			charImage.setLocation((WINDOW_WIDTH/2)-(charImage.getWidth()/2), (WINDOW_HEIGHT/2)-(charImage.getHeight()/2));
			add(charImage);
			
			System.out.println("Set base image for the character");			
		}		
	}
	
	private void updateCharImage() {

		System.out.println("current charImage reference is: " + charImage);
		
		System.out.println("charIndex is: " + charIndex);
		
		if (charIndex < charac.characterAnimation.get(NAME_OF_ATTACK).size()) {
			
			if (charImage != null) {
				remove(charImage);
			}
			
			charImage = charac.characterAnimation.get(NAME_OF_ATTACK).get(charIndex);
			charImage.setLocation((WINDOW_WIDTH/2)-(charImage.getWidth()/2), (WINDOW_HEIGHT/2)-(charImage.getHeight()/2));
			add(charImage);
			
			frameLabel.setLabel(charIndex+1 + "/" +  charac.characterAnimation.get(NAME_OF_ATTACK).size() + " frames completed for: " + NAME_OF_ATTACK);
			
			System.out.println("Updated character image");
		}		
		else {
			
//			if (charImage != null) {
//				remove(charImage);
//			}
			
			System.out.println("Finished all frames for this animation");

			// Updates frames completed counter
			add(completeLabel);
			completeLabel.setVisible(true);
		}
	}
	
	private void moveForwardFrame() {
		System.out.println("movedForwardFrame() before incrementing charIndex is: " + charIndex);
		charIndex++;
		System.out.println("movedForwardFrame() after incrementing charIndex is: " + charIndex);
	
		if (charIndex > charac.characterAnimation.get(NAME_OF_ATTACK).size()) {
			charIndex = 6;
			System.out.println("No more frames to move forward to. charIndex returned to 0.");
		}
		
	}


	private void moveBackFrame() { 
	
	//		ArrayList<GImage> keys = new ArrayList(imageHitboxes.keySet());
	//		
	//		if(imageHitboxes.isEmpty()) {
	//			System.out.println("imageHitbox hashmap is empty: can't move back a frame");
	//			return;
	//		}
			
			System.out.println("movedBackFrame() before decrementing charIndex is: " + charIndex);
			charIndex--;
			System.out.println("movedBackFrame() after decrementing charIndex is: " + charIndex);
						
			if (charIndex < 0) {
				// change to previous key & arraylist value in the hashmap
				
				charIndex = 0;
				System.out.println("No more frames to move back from. charIndex returned to 0.");
	//			for (int i = 0; i < keys.size(); i++) {
	//			    GImage image = keys.get(i);
	//			    
	//			    if(image == charImage) {
	//			    	i--;
	//			    	
	//			    	// Set previous image arraylist<GOvals> hitboxes to current hitboxes
	//			    	currentHitboxes = imageHitboxes.get(keys.get(i));
	//			    	System.out.println("Moved to previous keyset");
	//			    	break;
	//			    }
	//			    // do stuff here
	//			}
	//			
	//			System.out.println("Moved back arraylist index");
	//			
	//			charIndex = 6;
	//			
	//			addNewChar();
	//			return;
			}				
		}


	private void moveForwardAttack() {
		
		// Find out which linkedList we are at rn
		
		Attacks[] attacks = Attacks.values();
		
		for (int i = 0; i < attacks.length; i++) {
			if (attacks[i].toString().equals(NAME_OF_ATTACK)) {
				if ((i+1) < attacks.length) {
					saveAttackHitboxes();
					NAME_OF_ATTACK = attacks[i+1].toString();
					imageHitboxes = attackHitboxes.get(NAME_OF_ATTACK);
					
					if (imageHitboxes == null) {
						imageHitboxes = new LinkedHashMap<GImage, ArrayList<GOval>>();
					}
					
					System.out.println("NAME_OF_ATTACK successfully moved forward to: " + NAME_OF_ATTACK);
					
					charIndex = 0;
					
					updateLabels();

					completeLabel.setVisible(false);

					return;
				}
				else {
					System.out.println("NAME_OF_ATTACK NOT moved forward, no more to move forward to.");
					saveAttackHitboxes();
					return;
				}
			}
		}
		
		
		
	}


	private void moveBackAttack() {
		Attacks[] attacks = Attacks.values();
		
		for (int i = 0; i < attacks.length; i++) {
			if (attacks[i].toString().equals(NAME_OF_ATTACK)) {
				if ((i-1) >= 0) {
					saveAttackHitboxes();

					NAME_OF_ATTACK = attacks[i-1].toString();
					imageHitboxes = attackHitboxes.get(NAME_OF_ATTACK);
					
					if (imageHitboxes == null) {
						imageHitboxes = new LinkedHashMap<GImage, ArrayList<GOval>>();
					}
					System.out.println("NAME_OF_ATTACK successfully moved backward to: " + NAME_OF_ATTACK);
					
					charIndex = 0;

					updateLabels();

					return;
				}
				else {
					System.out.println("NAME_OF_ATTACK NOT moved backward, no more to move backward to.");
					saveAttackHitboxes();
					return;
				}
			}
		}
		
	}
	
	private void printSize() {
		
		for (int i = 0; i < imageHitboxes.size(); i++) {
			
			System.out.println("Size of Arraylists in imageHitboxes:" + imageHitboxes.get(i).size());
			
		}

	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
		if (getElementAt(e.getX(), e.getY()) != null) {
			toDrag = getElementAt(e.getX(), e.getY());
		}
		
		lastX = e.getX();
		lastY = e.getY();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {

		if(toDrag != null && toDrag instanceof GOval) {
			toDrag.setLocation(toDrag.getX() + (e.getX() - lastX), toDrag.getY() + (e.getY()-lastY));
		}
		
		lastX = e.getX();
		lastY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		m = e;
	}
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		requestFocus();
	}
	
	public static void main(String[] args) {
		new HitboxSetterApp().start();
	}
}
