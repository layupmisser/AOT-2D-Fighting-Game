package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class CreditScreen extends GraphicsPane{
	private Level program;
	private GButton back;
	private GParagraph creditText;
	public CreditScreen(Level app) {
		this.program = app;
		back = new GButton("Back", 10, 10, 100, 50);
		back.setFillColor(Color.GRAY);
		creditText = new GParagraph(
				  "Richard Hull\n"
				+ "Raymond Lee\n"
				+ "Tyler Truong\n"
				+ "Vincent von Tersch\r\n"
				+ "Isayama, Hajime and Sheldon Drzka. Attack On Titan. New York, N.Y.,\n "
				+ "     Kodansha Comics, 2012.\n"
				+ "COMP 055 - Application Development"
		, 75, 100);
		creditText.setFont(new Font(Font.SERIF,  Font.PLAIN, 40));
		creditText.setColor(Color.WHITE);
		
		
	}

	@Override
	public void showContents() {
		program.add(back);
		program.add(creditText);
		
	}

	@Override
	public void hideContents() {
		program.remove(creditText);
		program.remove(back);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == back) back.setFillColor(Color.DARK_GRAY);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == back) program.switchToMenu();
		back.setFillColor(Color.GRAY);
	}

}
