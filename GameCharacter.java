package buagas;

abstract class GameCharacter {
	protected String name;
	
	public GameCharacter(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public abstract String attack();
	public abstract String specialMove();
	
	public String showName() {
		return "Character: " + name;
	}
}
