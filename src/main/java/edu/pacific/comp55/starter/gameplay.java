package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;

public class gameplay extends GraphicsPane implements ActionListener {
	
	
	
	
	private Level program;
	private int damage1 = 12;
	private int damage2 = 100;
	private boolean pauseStart = false;
	
	
	Timer timeRemain = new Timer(1000, this);
	private GLabel clock;
	private int time = 250;
	
	private GLabel fightCount;
	private int count = 3;
	
	private GImage UI;
	private GRect bar1;
	private GRect bar2;
	private int hp1 = 523;
	private int hp2 = 523;
	private int move2 = 713;
	

	public gameplay(Level app) {
		this.program = app;

		
		clock = new GLabel("" + time, 605, 73);
		clock.scale(4, 4);
		clock.setColor(Color.WHITE);
		clock.setFont(Font.SERIF);
		
		fightCount = new GLabel("       " + count, 250, 400);
		fightCount.setFont(new Font(Font.SERIF,  Font.PLAIN, 40));
		fightCount.setColor(Color.WHITE);
		fightCount.scale(5, 5);
		
		UI = new GImage("gameUI.png", 40, 10);
		UI.setSize(1200, 105);
		
		bar1 = new GRect(45, 33, 523 ,45);
		bar1.setFilled(true);
		bar1.setFillColor(Color.YELLOW);
		
		bar2 = new GRect(713, 33, 523 ,45);
		bar2.setFilled(true);
		bar2.setFillColor(Color.YELLOW);
		
		//program.setupInteractions();
		
	}
	
	public void tookDamage(CharacterCommands player, int damage) {
		if (player.equals(CharacterCommands.PLAYER1)) {
			hp1 -= damage;
			program.addscore(2, damage);
		}
		else if (player.equals(CharacterCommands.PLAYER2)) {
			hp2 -= damage;
			move2 += damage;
			program.addscore(1, damage);
		}
		
		bar1.setSize(hp1, 45);
		bar2.setSize(hp2, 45);
		bar2.setLocation(move2, 33);
	}


	@Override
	public void showContents() {
		if (!pauseStart) {
			timeRemain.start();
			hp1 = 523;
			hp2 = 523;
			move2 = 713;
			bar1.setSize(hp1, 45);
			bar2.setSize(hp2, 45);
			bar2.setLocation(713, 33);
			program.addscore(1, -program.getscore(1));
			program.addscore(2, -program.getscore(2));
			program.playSound(1, true);
			count = 3;
			fightCount.setLabel("       " + count);
			program.createNewChar(CharacterCommands.PLAYER1, program.getCharacter(1));
			program.createNewChar(CharacterCommands.PLAYER2, program.getCharacter(2));
			program.play1.startTimer();
			program.play2.startTimer();
			program.add(fightCount);
			
			pauseStart = true;
			
			

		}
		
		program.add(UI);
		program.add(clock);
		program.add(bar1);
		program.add(bar2);
		
	}

	@Override
	public void hideContents() {
		program.remove(clock);
		program.remove(UI);
		program.remove(bar1);
		program.remove(bar2);
		
	}
	
	
	public void actionPerformed(ActionEvent e) {		
		if (!program.getPause()) {
			if (count > 0) {
				count -= 1;
				if (count == 0) {
					fightCount.setLabel("RUMBLE");
				}
				else {
					fightCount.setLabel("       " + count);
				}
			}
			else {
				program.remove(fightCount);
				time -= 1;
			}
		}
		
		
		
		if (time >= 100) clock.setLabel("" + time); else if (time >= 10) clock.setLabel(" " + time); else if (time >= 0) clock.setLabel("  " + time);
		
		if (time == 0 || hp1 <= 0 || hp2 <= 0) {
			program.stopSound(1);
			pauseStart = false;
			if (hp1 > hp2) {
				program.addwinCount(1);
				program.setWinner(1);
			}
			else if (hp1 < hp2){
				program.addwinCount(2);
				program.setWinner(2);
			}
			time = 250;
			hp1 = 523;
			hp2 = 523;
			move2 = 713;
			clock.setLabel("" + time);
			
			program.switchToWin();
			timeRemain.stop();
			
		}
	}
}
