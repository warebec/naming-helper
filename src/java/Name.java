public class Name {
	String name;
	String originAndMeaning;
	boolean onlyLast;

	Name(String name, String origin, String meaning, boolean onlyLast) {
		this.name = name;
		this.originAndMeaning = "(" + origin + ") " + meaning;
		this.onlyLast = onlyLast;
	}

	@Override
	public String toString() { 
		return name + ", " + originAndMeaning + ", " + onlyLast; 
	}
}
