package edu.pacific.comp55.starter;

import acm.graphics.GImage;
import acm.program.GraphicsProgram;

public class Screen extends GraphicsProgram{

	private static final int WINDOW_WIDTH = 1920;
	private static final int WINDOW_HEIGHT = 1080;
	private String titleName = "titleName.png";
	
	@Override
	public void run() {
		
		
	}

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
	}
	
	public static void main(String[] args) {
		new Screen().start();
	}
}
