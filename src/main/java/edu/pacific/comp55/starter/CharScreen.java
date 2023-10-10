package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Random;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class CharScreen extends GraphicsPane{
	private Level program;
	private GButton back;
	private GImage charText;
	private GImage play1Text;
	private GImage play2Text;
	private GButton playerSwitch;
	private int num = 1;
	
	private GImage char_Rand;
	private GImage char_Attack;
	private GImage char_Armored;
	private GImage char_Female;
	private GImage char_Beast;
	
	private String[] charModels = {"modelattackR.png", "modelarmoredR.png", "modelfemaleR.png", "modelbeastR.png",
									"modelattackL.png", "modelarmoredL.png", "modelfemaleL.png", "modelbeastL.png"};
	private Characters[] charNames = {Characters.ATTACK_TITAN, Characters.ARMOR_TITAN, Characters.FEMALE_TITAN, Characters.BEAST_TITAN};
	
	private GImage play1Char;
	private GImage play2Char;
	
	private GButton charName1;
	private GButton charName2;
	
	private GLabel map;
	private GButton confirm;

	Color myColor = new Color(0, 0, 0, 0);
	
	public CharScreen(Level app) {
		this.program = app;
		back = new GButton("Back", 10, 10, 100, 50);
		back.setFillColor(Color.GRAY);
		charText = new GImage("charMenu.png", 200, 20);
		
		play1Text = new GImage("player1Text.png", 50, 125);
		play1Text.setSize(200, 50);
		play2Text = new GImage("player2Text.png", 1025, 125);
		play2Text.setSize(200, 50);
		
		char_Rand = new GImage("selectRand.png", 590, 250);
		char_Rand.setSize(102.17  , 125);
		char_Attack = new GImage("selectAttack.png", 460, 200);
		char_Attack.setSize(102.17, 125);
		char_Armored = new GImage("selectArmored.png", 720, 200);
		char_Armored.setSize(102.17, 125);
		char_Female = new GImage("selectFemale.png", 720, 350);
		char_Female.setSize(102.17, 125);
		char_Beast = new GImage("selectBeast.png", 460, 350);
		char_Beast.setSize(102.17, 125);
		
		play1Char = new GImage("", 50, 200);
		play2Char = new GImage("", 1050, 200);
		
		charName1 = new GButton("", 0, 550, 25, 100);
		charName2 = new GButton("", 950, 550, 25, 100);
		charName1.setFillColor(myColor);
		charName1.setColor(Color.WHITE);
		charName2.setFillColor(myColor);
		charName2.setColor(Color.WHITE);
		
		playerSwitch = new GButton("Player " + num + " Selection", 565, 135, 150, 50);
		playerSwitch.setFillColor(Color.GRAY);
		confirm = new GButton("CONFIRM", 532, 575, 200, 50);
		confirm.setFillColor(Color.GRAY);
	}
	
	Characters getRandomNumber(Characters[] charNames2) {
		  return charNames2[(new Random()).nextInt(charNames2.length)];
	}

	@Override
	public void showContents() {
		program.add(back);
		program.add(charText);
		program.add(play1Text);
		program.add(play2Text);
		program.add(playerSwitch);
		program.add(confirm);
		
		program.add(char_Rand);
		program.add(char_Attack);
		program.add(char_Armored);
		program.add(char_Female);
		program.add(char_Beast);
		
		program.add(play1Char);
		program.add(play2Char);
		program.add(charName1);
		program.add(charName2);
	}

	@Override
	public void hideContents() {
		program.remove(back);
		program.remove(charText);
		program.remove(play1Text);
		program.remove(play2Text);
		program.remove(playerSwitch);
		program.remove(confirm);
		
		program.remove(char_Rand);
		program.remove(char_Attack);
		program.remove(char_Armored);
		program.remove(char_Female);
		program.remove(char_Beast);
		
		program.remove(play1Char);
		program.remove(play2Char);
		program.remove(charName1);
		program.remove(charName2);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		
		if (obj == back) back.setFillColor(Color.DARK_GRAY);
		else if (obj == playerSwitch) playerSwitch.setFillColor(Color.DARK_GRAY);
		else if (obj == confirm) confirm.setFillColor(Color.DARK_GRAY);
		
		if (num == 1) {
			if (obj == char_Attack) {
				play1Char.setImage(charModels[0]);
				charName1.setLabel(" ATTACK"); 
				program.setCharacter(1, charNames[0]);
			}
			else if (obj == char_Armored) {
				play1Char.setImage(charModels[1]);
				charName1.setLabel(" ARMORED");
				program.setCharacter(1, charNames[1]);
			}
			else if (obj == char_Female) {
				play1Char.setImage(charModels[2]);
				charName1.setLabel(" FEMALE"); 
				program.setCharacter(1, charNames[2]);
			}
			else if (obj == char_Beast) {
				play1Char.setImage(charModels[3]);
				charName1.setLabel(" BEAST"); 
				program.setCharacter(1, charNames[3]);
			}
			else if (obj == char_Rand) {
				play1Char.setImage("modelrandom.png");
				charName1.setLabel(" RANDOM"); 
				play1Char.setSize(170, 348.32);
				program.setCharacter(1, getRandomNumber(charNames)); 
			}
			
			play1Char.setSize(170, 348.32);
		}
		else if (num == 2) {
			if (obj == char_Attack) {
				play2Char.setImage(charModels[4]);
				charName2.setLabel("   ATTACK"); 
				program.setCharacter(2, charNames[0]);
			}
			else if (obj == char_Armored) {
				play2Char.setImage(charModels[5]);
				charName2.setLabel("ARMORED"); 
				program.setCharacter(2, charNames[1]);
			}
			else if (obj == char_Female) {
				play2Char.setImage(charModels[6]);
				charName2.setLabel("  FEMALE"); 
				program.setCharacter(2, charNames[2]);
			}
			else if (obj == char_Beast) {
				play2Char.setImage(charModels[7]);
				charName2.setLabel("      BEAST"); 
				program.setCharacter(2, charNames[3]);
			}
			else if (obj == char_Rand) {
				play2Char.setImage("modelrandom.png");
				charName2.setLabel("  RANDOM"); 
				play2Char.setSize(170, 348.32);
				play2Char.setLocation(1050, 200);
				program.setCharacter(2, getRandomNumber(charNames)); 
			}
			
			
			
			play2Char.setSize(170, 348.32);
		}
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == back) {
			program.setMap("");
			play1Char.setImage("");;
			play2Char.setImage("");
			charName1.setLabel("");;
			charName2.setLabel("");;
			num = 1;
			program.switchToMapS();
		}
		else if (obj == playerSwitch) {
			if (num == 1) {
				num = 2;
			}
			else if (num == 2){
				num = 1;
			}
			playerSwitch.setLabel("Player " + num + " Selection");
		}
		else if (obj == confirm) {
				if (charName1.getLabel() != "" && charName2.getLabel() != "") program.switchToGame();
		}
		
		back.setFillColor(Color.GRAY);
		playerSwitch.setFillColor(Color.GRAY);
		confirm.setFillColor(Color.GRAY);
	}

}
