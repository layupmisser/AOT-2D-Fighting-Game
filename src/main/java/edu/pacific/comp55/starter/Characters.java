package edu.pacific.comp55.starter;

enum Characters {
	BEAST_TITAN("Beast Titan"),
	ARMOR_TITAN("Armor Titan"),
	FEMALE_TITAN("Female Titan"),
	ATTACK_TITAN("Attack Titan");
	
	String charName;
	
	Characters(String charName) {
		this.charName = charName;
	}
	
	@Override
	public String toString() {
		return this.charName;
	}
}
