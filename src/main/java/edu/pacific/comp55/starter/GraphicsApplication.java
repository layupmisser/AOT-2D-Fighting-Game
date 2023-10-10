package edu.pacific.comp55.starter;


import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import acm.program.GraphicsProgram;

public abstract class GraphicsApplication extends GraphicsProgram {
	private GraphicsPane curScreen;
	private GraphicsPane oldScreen;
	public static final String MUSIC_FOLDER = "sounds";
	private static final String[] SOUND_FILES = { "menuSong.mp3", "gameplaySong.mp3"};
	public AudioPlayer audio = AudioPlayer.getInstance();
	
	public GraphicsApplication() {
		super();
	}
	
	/* Method: setupInteractions
	 * -------------------------
	 * must be called before switching to another
	 * pane to make sure that interactivity
	 * is setup and ready to go.
	 */
	protected void setupInteractions() {
		requestFocus();
		addKeyListeners();
		addMouseListeners();
	}
	
	/* switchToScreen(newGraphicsPane)
	 * -------------------------------
	 * will simply switch from making one pane that was currently
	 * active, to making another one that is the active class.
	 */
	protected void switchToScreen(GraphicsPane newScreen) {
		if(curScreen != null) {
			curScreen.hideContents();
		}
		newScreen.showContents();
		curScreen = newScreen;
	}
	
	protected void showScreen(GraphicsPane newScreen) {
		newScreen.showContents();
		oldScreen = curScreen;
		curScreen = newScreen;
	}
	
	protected void hideScreen() {
		curScreen.hideContents();
		curScreen = oldScreen;
		curScreen.showContents();
	}
	
	protected void playSound(int num, Boolean loop) {
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[num], loop);
	}
	
	protected void stopSound(int num) {
		audio.stopSound(MUSIC_FOLDER, SOUND_FILES[num]);
	}
	

	protected void changeSound(double num) {
		for (int i = 0; i <  SOUND_FILES.length; i++) {
			audio.setSound(MUSIC_FOLDER, SOUND_FILES[i], num);
		}
	}
	
	protected void changeMusic(double num) {
		for (int i = 0; i <  SOUND_FILES.length; i++) {
			audio.setSound(MUSIC_FOLDER, SOUND_FILES[i], num);
		}
	}
	
	/*
	 * These methods just override the basic
	 * mouse listeners to pass any information that
	 * was given to the application to a particular screen.
	 */
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(curScreen != null) curScreen.mousePressed(e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(curScreen != null) curScreen.mouseReleased(e);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(curScreen != null) curScreen.mouseClicked(e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(curScreen != null) curScreen.mouseDragged(e);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if(curScreen != null) curScreen.mouseMoved(e);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		if(curScreen != null) curScreen.mouseEntered(e);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(curScreen != null) curScreen.keyPressed(e);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(curScreen != null) curScreen.keyReleased(e);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if(curScreen != null) curScreen.keyTyped(e);
	}
	public void keyPressed2(KeyEvent q) {
		if(curScreen != null) curScreen.keyPressed(q);
	}
	
}
