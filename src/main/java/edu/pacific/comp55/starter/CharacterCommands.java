package edu.pacific.comp55.starter;

public enum CharacterCommands {

	PLAYER1("Player 1"),
	PLAYER2("Player 2"),
	PUNCH("punch"),
	WALK(""),
	SPRINT(""),
	BLOCK(""),
	KICK("kick"),
	SPECIAL("special"),
	DAMAGE(""),
	KNOCK("");
	
	String attackName;
	
	CharacterCommands(String attackName) {
		this.attackName = attackName;
	}
	
	@Override
	public String toString() {
		return this.attackName;
	}
}
