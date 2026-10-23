package buagas;

class Mage extends GameCharacter {
   public Mage (String name){
	super(name);
	
   }
   
public String attack() {
	return name + " casts a fireball!";
	
   }

public String specialMove() {
	return name + " uses Lightning Storm!";
	
   }
}
