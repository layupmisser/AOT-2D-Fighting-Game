package edu.pacific.comp55.starter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.Timer;

import acm.graphics.GImage;

public class FightingMoves implements ActionListener {

	private static final int ATTACK_MS_DELAY = 40;

	private Level program;
	
	private Timer timer = new Timer(100, this);
	private int animationCount = 0;
	private int p;
	private String characterAction;
	private Projectile rock;
	public double rockX;
	public double rockY;
	public GImage projectile;
	
	private ArrayList<double[]> attackHitboxData = new ArrayList<double[]>();

	
	
	public FightingMoves(Level app, int p, String action) {
		program = app;
		this.p = p;
		characterAction = action;
	}
	
	public void actionPerformed (ActionEvent e){
		
		if ((characterAction == "kick" || characterAction == "special" || characterAction == "punch")) {
				attackHitboxData = getHitboxesData(program.getPlayer(p).getCharacterName(), characterAction);
				System.out.println("attackHitboxData set");
		}
	
		if(characterAction == "rest") timer.setDelay(200); else timer.setDelay(ATTACK_MS_DELAY);
		program.getPlayer(p).setImage(program.getPlayer(p).characterAnimation.get(characterAction + program.getPlayer(p).getOrientation()).get(animationCount).getImage());
		
		// Spawn hitboxes && despawn them
		
		if (attackHitboxData != null && (characterAction == "kick" || characterAction == "special" || characterAction == "punch")) {
			program.generateHitboxes(program.getPlayer(p), animationCount, attackHitboxData);
		
			System.out.println("generateHitbox() called");
			System.out.println("Did attack");
		}

		animationCount++;
		System.out.println("played animation, count: " + animationCount);
				
		if(animationCount == program.getPlayer(p).characterAnimation.get(characterAction + program.getPlayer(p).getOrientation()).size()) {
			animationCount = 0;
			setAction("rest");
			program.resetNumOfTimesDamaged();
			//timer.stop();
				
		}
		
	}
	
	public void setAction(String action) {
		animationCount = 0;
		characterAction = action;
	}
	
	public String getAction() {
		return characterAction;
	}
	
	public boolean startTimer() {
		timer.start();
		return true;
	}
	
	private ArrayList<double[]> getHitboxesData(String characterName, String action) {
		
		 ArrayList<double[]> attackHitbox = new ArrayList<double[]>(); 
		
		try {			
			  File myObj = new File("media/hitBoxes.txt");
			  Scanner myReader = new Scanner(myObj);
			  while (myReader.hasNextLine()) {
			    String data = myReader.nextLine();
			    
			    String[] rawHitBoxData = data.split(",");
			    double[] intHitBoxData;
			    
			    if (rawHitBoxData[0].equals(characterName) && rawHitBoxData[1].equals(action)) {
			    	rawHitBoxData = Arrays.copyOfRange(rawHitBoxData,3,rawHitBoxData.length);
			    	intHitBoxData = convertStringArrayToDouble(rawHitBoxData);
			    	attackHitbox.add(intHitBoxData);
			    }
			  }
			  myReader.close();			  
			} catch (FileNotFoundException e) {
			  System.out.println("An error occurred.");
				      e.printStackTrace();
			}
		
		
		  return attackHitbox;
	}
	
	private double[] convertStringArrayToDouble(String[] str) {
	     int size = str.length;
	     double[] arr = new double [size];
	  
	     for(int i=0; i<size; i++) {
	        arr[i] = Double.parseDouble(str[i]);
	     }
	     return arr;
	}

	
}
