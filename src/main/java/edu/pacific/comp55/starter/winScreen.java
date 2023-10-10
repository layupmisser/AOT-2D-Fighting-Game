package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class winScreen extends GraphicsPane{
	private Level program;
	
	private GImage player1Name;
	private GImage player2Name;
	private GLabel youwin;
	private GParagraph score1;
	private GParagraph score2;
	
	private GButton again;
	private GButton map;
	private GButton character;
	private GButton quit;
	
	public winScreen(Level app) {
		this.program = app;	
		
		player1Name = new GImage("player1Text.png", 75, 125);
		player1Name.setSize(200, 50);
		player2Name = new GImage("player2Text.png", 1000, 125);
		player2Name.setSize(200, 50);
		
		youwin = new GLabel("" , 350, 100);
		youwin.scale(8, 7);
		youwin.setColor(Color.WHITE);
		youwin.setFont(Font.SERIF);
		
		score1 = new GParagraph("",  25, 250);
		score1.setFont(new Font(Font.SERIF,  Font.PLAIN, 40));
		
		score2 = new GParagraph("",  950, 250);
		score2.setFont(new Font(Font.SERIF,  Font.PLAIN, 40));
		
		again = new GButton("PLAY AGAIN", 532, 320, 200, 50);
		again.setFillColor(Color.GRAY);
		map = new GButton("MAP SELECT", 532, 380, 200, 50);
		map.setFillColor(Color.GRAY);
		character = new GButton("CHARACTER SELECT", 532, 440, 200, 50);
		character.setFillColor(Color.GRAY);
		quit = new GButton("QUIT", 532, 500, 200, 50);
		quit.setFillColor(Color.GRAY);
	}

	@Override
	public void showContents() {
		program.add(player1Name);
		program.add(player2Name);
		program.add(youwin);
		youwin.setLabel("Player " + program.getWinner() + " WINS");
		
		program.add(score1);
		program.add(score2);
		score1.setText("       Score - " + program.getscore(1) + "\n" +
						"Number of Wins - "  + program.getwinCount(1));
		score2.setText("       Score - " + program.getscore(2) + "\n" +
						"Number of Wins - " + program.getwinCount(2));
		score1.setColor(Color.WHITE);
		score2.setColor(Color.WHITE);
		
		program.add(again);
		program.add(map);
		program.add(character);
		program.add(quit);
		
	}

	@Override
	public void hideContents() {
		program.remove(player1Name);
		program.remove(player2Name);
		program.remove(youwin);
		
		program.remove(score1);
		program.remove(score2);
		
		program.remove(again);
		program.remove(map);
		program.remove(character);
		program.remove(quit);
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		
		if (obj == again) again.setFillColor(Color.DARK_GRAY);
		else if (obj == map) map.setFillColor(Color.DARK_GRAY);
		else if (obj == character) character.setFillColor(Color.DARK_GRAY);
		else if (obj == quit) quit.setFillColor(Color.DARK_GRAY);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		
		if (obj == again) program.switchToGame();
		else if (obj == map) program.switchToMapS();
		else if (obj == character) program.switchToChar();
		else if (obj == quit) System.exit(0);
		
		again.setFillColor(Color.GRAY);
		map.setFillColor(Color.GRAY);
		character.setFillColor(Color.GRAY);
		quit.setFillColor(Color.GRAY);
	}

}
