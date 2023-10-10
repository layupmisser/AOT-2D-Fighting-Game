package edu.pacific.comp55.starter;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class MapSelect extends GraphicsPane {
	private Level program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GImage mapText;
	private GButton back;
	
	private GImage mapRand;
	private GImage mapWall;
	private GImage mapCastle;
	private GImage mapCrystal;
	private GImage mapCity;
	private GImage mapForrest;
	private String[] maps = {"map_Wall.png", "map_Castle.png", "map_Crystal.png", "map_City.png", "map_Forrest.png"};
	
	private GLabel confirmMap;
	public String mapBG;
	private GButton confirm;

	public MapSelect(Level app) {
		this.program = app;
		mapText = new GImage("mapMenu.png", 320, 20);
		back = new GButton("Back", 10, 10, 100, 50);
		back.setFillColor(Color.GRAY);
		
		mapRand = new GImage("map_Random.png", 155, 175);
		mapRand.setSize(247.5 , 150);
		
		mapWall = new GImage(maps[0], 505, 175);
		mapWall.setSize(247.5, 150);
		
		mapCastle = new GImage(maps[1], 850, 175);
		mapCastle.setSize(247.5, 150);
		
		mapCrystal = new GImage(maps[2], 155, 375);
		mapCrystal.setSize(247.5, 150);
		
		mapCity = new GImage(maps[3], 505, 375);
		mapCity.setSize(247.5, 150);
		
		mapForrest = new GImage(maps[4], 850, 375);
		mapForrest.setSize(247.5, 150);
		
		confirm = new GButton("CONFRIM", 532, 575, 200, 50);
		confirm.setFillColor(Color.GRAY);
		confirmMap = new GLabel("", 540, 150);
		confirmMap.setFont(new Font(Font.SERIF,  Font.PLAIN, 40));
		confirmMap.setColor(Color.WHITE);
	}
	
	String getRandomNumber(String[] arr) {
	  return arr[(new Random()).nextInt(arr.length)];
	}

	@Override
	public void showContents() {
		program.add(mapText);
		program.add(back);

		program.add(mapRand);
		program.add(mapWall);
		program.add(mapCastle);
		program.add(mapCrystal);
		program.add(mapCity);
		program.add(mapForrest);
		
		program.add(confirmMap);
		program.add(confirm);
	}

	@Override
	public void hideContents() {
		program.remove(mapText);
		program.remove(back);
		
		program.remove(mapRand);
		program.remove(mapWall);
		program.remove(mapCastle);
		program.remove(mapCrystal);
		program.remove(mapCity);
		program.remove(mapForrest);
		
		program.remove(confirmMap);
		program.remove(confirm);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == back) back.setFillColor(Color.DARK_GRAY);
		else if (obj == confirm) confirm.setFillColor(Color.DARK_GRAY);
		else if (obj == mapWall) {
			confirmMap.setLabel("   WALL");
			mapBG = maps[0];
		}
		else if (obj == mapCastle) {
			confirmMap.setLabel("CASTLE");
			mapBG = maps[1];
		}
		else if (obj == mapCrystal) {
			confirmMap.setLabel("CRYSTAL");
			mapBG = maps[2];
		}
		else if (obj == mapCity) {
			confirmMap.setLabel("   CITY");
			mapBG = maps[3];
		}
		else if (obj == mapForrest) {
			confirmMap.setLabel("FORREST");
			mapBG = maps[4];
		}
		else if (obj == mapRand) {
			confirmMap.setLabel("RANDOM");
			mapBG = getRandomNumber(maps);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == back) {
			confirmMap.setLabel("");
			mapBG = "";
			program.setMap(mapBG);
			program.switchToMenu();
		}
		else if (obj == confirm) {
			if (confirmMap.getLabel() != "" ) {
				program.setMap(mapBG);
				confirmMap.setLabel("");
				program.switchToChar();
			}
		}
		back.setFillColor(Color.GRAY);
		confirm.setFillColor(Color.GRAY);
	}
}
