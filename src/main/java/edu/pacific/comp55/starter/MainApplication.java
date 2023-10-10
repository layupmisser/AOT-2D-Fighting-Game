package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.graphics.GResizable;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 1920;
	public static final int WINDOW_HEIGHT = 1080;
	
	private MenuScreen menu;
	private MapSelect mapSelect;
	private OptionScreen options;
	private CreditScreen credit;
	private winScreen win;
	
	private pauseGame pause;
	private gameplay gp;
	private boolean stopTime = false;
	
	private CharScreen character;
	private int count = 0;
	private int score[] = {0,0};
	private int winCount[] = {0,0};
	private int winner;
	
	private GImage Background;
	private GImage gpMap;
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() {
		playSound(0, true);
		menu = new MenuScreen(this);
		options = new OptionScreen(this);
		credit = new CreditScreen(this);
		win = new winScreen(this);
		
		mapSelect = new MapSelect(this);
		character = new CharScreen(this);
		
		pause = new pauseGame(this);
		gp = new gameplay(this);
		
		Background = new GImage("menuBackGround.png", 0, 0);
		Background.setSize(1285, 690);
		add(Background);
		
		gpMap = new GImage("", 0, 0);

		setupInteractions();
		switchToMenu();
	}
	
	public void setMap(String map) {
		this.gpMap.setImage(map);
	}
	
	public boolean getPause() {
		return stopTime;
	}
	
	public void addscore(int num, int points) {
		score[num - 1] += points;
	}
	
	public int getscore(int num) {
		return score[num - 1];
	}
	
	public void addwinCount(int num) {
		winCount[num - 1] += 1;
	}
	
	public int getwinCount(int num) {
		return winCount[num - 1];
	}
	
	public void setWinner(int num) {
		winner = num;
	}
	
	public int getWinner() {
		return winner;
	}
	
	public void switchToMenu() {
		if (count != 1) {
		switchToScreen(menu);
	
		}
	}
	public void switchToMapS() {
		if (count != 1) {
		switchToScreen(mapSelect);
		}
	}
	
	public void switchToOption() {
		if (count != 1) {
		switchToScreen(options);
		}
	}
	
	public void switchToCredits() {
		if (count != 1) {
		switchToScreen(credit);
		}
	}
	
	public void switchToChar() {
		if (count != 1) {
		switchToScreen(character);
		}
	}
	
	public void switchToGame() {
		if (count != 1) {
		remove(Background);
		add(gpMap);
		gpMap.setSize(1285, 690);
		stopSound(0);
		switchToScreen(gp);
		}
	}
	
	public void switchToWin() {
		if (count != 1) {
			remove(gpMap);
			add(Background);
			stopSound(1);
			playSound(0, true);
			switchToScreen(win);
		}
	}
	
	
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_BACK_SLASH) {
			if (count == 0) {
				count++;
				stopTime = true;
				showScreen(pause);
			}
			else if (count > 0) {
				count = 0;
				stopTime = false;
				hideScreen();
			}
		}
	}
	
	
	public static void main(String[] args) {
		new MainApplication().start();
	}
}
