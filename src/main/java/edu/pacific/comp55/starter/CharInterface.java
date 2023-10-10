package edu.pacific.comp55.starter;

import java.util.ArrayList;
import acm.graphics.GOval;

public interface CharInterface {

	void sprintOrWalk();
	void block();
	void punchAttack();
	void kickAttack();
	void specialAttack();
	void tookDamage();
	void knockBack();//animation one is kick, animation two is punch
	void restAnimation();
	//boolean playAnimation(String name);
	ArrayList<GOval> getHitBox();
	
}
