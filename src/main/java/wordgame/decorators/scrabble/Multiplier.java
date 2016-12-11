package wordgame.decorators.scrabble;

public class Multiplier implements PointModifier {
	
	public enum MultiplierType {
		LETTER, WORD;
	}
	
	private int times;
	private MultiplierType type;
	
	public Multiplier(int times, MultiplierType type) {
		this.times = times;
		this.type = type;
	}
	
	public MultiplierType getType() {
		return this.type;
	}
	
	public int apply(int point) {
		return point * this.times;
	}
	
}
