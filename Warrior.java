package buagas;

class Warrior extends GameCharacter {
  public Warrior (String name){
	super(name);
	
     }

  public String attack() {
	return name + " swings a mighty sword!";
	
     }

  public String specialMove() {
	return name + " uses Shield Bash!";
}
	
}
