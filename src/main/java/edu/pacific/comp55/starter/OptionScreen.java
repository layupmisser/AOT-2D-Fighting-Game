package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;

public class OptionScreen extends GraphicsPane {
	private Level program;
	private GObject toDrag;
	private GButton back;
	
	private GButton volMas;
	private GRect volRect;
	private GOval volCir;
	
	private GButton Mus;
	private GRect musRect;
	private GOval musCir;
	
	private GButton Sou;
	private GRect souRect;
	private GOval souCir;
	
	private GParagraph control1;
	private GParagraph control2;
	
	public OptionScreen(Level app) {
		this.program = app;	
		back = new GButton("Back", 10, 10, 100, 50);
		back.setFillColor(Color.GRAY);
		
		volMas = new GButton("Master Volume", 350, 205, 300, 50);
		volMas.setColor(Color.WHITE);
		volMas.setFillColor(new Color(0, 0, 0, 0));
		
		volRect = new GRect(600, 220, 200, 20);
		volRect.setFilled(true);
		volRect.setFillColor(Color.GRAY);
		
		volCir = new GOval(700, 218, 25, 25);
		volCir.setFilled(true);
		volCir.setFillColor(Color.GRAY);
		
		Mus = new GButton("Music Volume", 350, 255, 300, 50);
		Mus.setColor(Color.WHITE);
		Mus.setFillColor(new Color(0, 0, 0, 0));
		
		musRect = new GRect(600, 270, 200, 20);
		musRect.setFilled(true);
		musRect.setFillColor(Color.GRAY);
		
		musCir = new GOval(700, 268, 25, 25);
		musCir.setFilled(true);
		musCir.setFillColor(Color.GRAY);
		
		Sou = new GButton("Sound Volume", 350, 305, 300, 50);
		Sou.setColor(Color.WHITE);
		Sou.setFillColor(new Color(0, 0, 0, 0));
		
		souRect = new GRect(600, 320, 200, 20);
		souRect.setFilled(true);
		souRect.setFillColor(Color.GRAY);
		
		souCir = new GOval(700, 318, 25, 25);
		souCir.setFilled(true);
		souCir.setFillColor(Color.GRAY);
		
		control1 = new GParagraph("Player 1 \r\n"
				+ "Q - block \r\n"
				+ "W - punch\r\n"
				+ "E - punch\r\n"
				+ "A - left move\r\n"
				+ "S - special attack\r\n"
				+ "D - right move\r\n"
				+ "pause - backspace\r\n"
				+ "unpause - backslash", 100, 175);	
		control1.setFont(new Font(Font.SERIF,  Font.PLAIN, 30));
		control1.setColor(Color.WHITE);

		control2 = new GParagraph("Player 2 \r\n"
				+ "O - block\r\n"
				+ "P - punch\r\n"
				+ "[ - kick\r\n"
				+ "L - move left\r\n"
				+ "; - special\r\n"
				+ "“ - move right\r\n"
				+ "pause - backspace\r\n"
				+ "unpause - backslash", 900, 175);	
		control2.setFont(new Font(Font.SERIF,  Font.PLAIN, 30));
		control2.setColor(Color.WHITE);
	}
		

	@Override
	public void showContents() {
		program.add(back);
		program.add(control1);
		program.add(control2);
		
		program.add(volMas);
		program.add(volRect);
		program.add(volCir);
		program.add(Mus);
		program.add(musRect);
		program.add(musCir);
		program.add(Sou);
		program.add(souRect);
		program.add(souCir);
		
	}

	@Override
	public void hideContents() {
		program.remove(back);
		program.remove(control1);
		program.remove(control2);
		
		program.remove(volMas);
		program.remove(volRect);
		program.remove(volCir);
		program.remove(Mus);
		program.remove(musRect);
		program.remove(musCir);
		program.remove(Sou);
		program.remove(souRect);
		program.remove(souCir);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		toDrag = program.getElementAt(e.getX(), e.getY());
		if (obj == back) back.setFillColor(Color.DARK_GRAY);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		double numMas = (volCir.getX()- 25/2 - 600) / 200;
		double numMus = ((musCir.getX() - 600) / 200) * numMas;
		if (toDrag == volCir) {
			if (e.getX() > 600 && e.getX() < 800) {
				volCir.setLocation(e.getX() - 25/2, volCir.getY());
				program.changeMusic(numMus);
			}
		}
		else if (toDrag == musCir) {
			if (e.getX() > 600 && e.getX() < 800) {
				musCir.setLocation(e.getX() - 25/2, musCir.getY());
				program.changeMusic(numMus);
			}
		}
		else if (toDrag == souCir) {
			if (e.getX() > 600 && e.getX() < 800) {
				souCir.setLocation(e.getX() - 25/2, souCir.getY());
			}
		}
		
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == back) program.switchToMenu();
		back.setFillColor(Color.GRAY);
	}

}
