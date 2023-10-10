package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class MenuScreen extends GraphicsPane {
	private Level program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GImage titleName;
	private GButton start;
	private GButton options;
	private GButton credits;
	private GButton quit;
	
	public MenuScreen(Level app) {
		super();
		program = app;
		titleName = new GImage("titleName.png", 1285/2/2, 50);
		
		start = new GButton("START", 532, 320, 200, 50);
		start.setFillColor(Color.GRAY);
		options = new GButton("OPTIONS", 532, 380, 200, 50);
		options.setFillColor(Color.GRAY);
		credits = new GButton("CREDITS", 532, 440, 200, 50);
		credits.setFillColor(Color.GRAY);
		quit = new GButton("QUIT", 532, 500, 200, 50);
		quit.setFillColor(Color.GRAY);
	}
	
	

	@Override
	public void showContents() {
		program.add(titleName);
		program.add(start);
		program.add(options);
		program.add(credits);
		program.add(quit);
		
	}

	@Override
	public void hideContents() {
		program.remove(titleName);
		program.remove(start);
		program.remove(options);
		program.remove(credits);
		program.remove(quit);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		
		if (obj == start) start.setFillColor(Color.DARK_GRAY);
		else if (obj == options) options.setFillColor(Color.DARK_GRAY);
		else if (obj == credits) credits.setFillColor(Color.DARK_GRAY);
		else if (obj == quit) quit.setFillColor(Color.DARK_GRAY);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == start) program.switchToMapS();
		else if (obj == options) program.switchToOption();
		else if (obj == credits) program.switchToCredits();
		else if (obj == quit) System.exit(0);
		start.setFillColor(Color.GRAY);
		options.setFillColor(Color.GRAY);
		credits.setFillColor(Color.GRAY);
		quit.setFillColor(Color.GRAY);
	}
}
