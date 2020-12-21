public class Name {
	String name;
	String originAndMeaning;
	boolean onlyLast;
	boolean neverLast;

	Name(String name, String origin, String meaning, boolean onlyLast, boolean neverLast) {
		this.name = name;
		this.originAndMeaning = "(" + origin + ") " + meaning;
		this.onlyLast = onlyLast;
		this.neverLast = neverLast;
	}

	@Override
	public String toString() { 
		return name + ", " + originAndMeaning + ", " + onlyLast + ", " + neverLast; 
	}
}
