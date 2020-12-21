import java.io.*;
import java.util.*;

public class ReduceListMain {

	private static final String INPUT_TXT = "src/resources/output.txt";
	private static final String OUTPUT_TXT = "src/resources/ReducedList.txt";
	private static final int START_AT_LINE = 0;
	private static final List<String> DISCARD_BEGINNING_WITH = new ArrayList<>(List.of());
	private static final List<String> DISCARD_CONTAINING = new ArrayList<>(List.of());
	
	public static void main(String[] args) {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(INPUT_TXT)));
				Scanner scanner = new Scanner(System.in);
				FileWriter fileWriter = new FileWriter(new File(OUTPUT_TXT), true)) {
			
			List<String> names = new ArrayList<>();
			String line = "";
			int countFromSource = 0;
			int countToFile = 0;

			while ((line = bufferedReader.readLine()) != null) {
				countFromSource++;
				if (countFromSource <= START_AT_LINE || isDiscardForBeginning(line) || isDiscardForContaining(line)) {
					continue;
				}

				names.add(line);
				if (names.size() >= 7) {
					String displayOutput = "Please pick from the following options:\n";
					int num = 0;
					for (String name : names) {
						displayOutput = displayOutput + num + ". " + name + "\n";
						num++;
					}
					displayOutput = displayOutput + num + ". discard all";
					System.out.println(displayOutput);
					
					// read input of which to keep, handle accordingly
					while (true) {
						String choice = scanner.nextLine();
						int choiceInt = -1;
						try {
							choiceInt = Integer.parseInt(choice);
						} catch (NumberFormatException nfe) {
							System.out.println("Incorrect input. " + displayOutput);
							continue;
						}
						
						if (choiceInt < 0 || choiceInt > num) {
							System.out.println("Incorrect input. " + displayOutput);
							continue;
						}
						if (choiceInt < num) {
							fileWriter.write(names.get(choiceInt) + "\n");
							countToFile++;
						}
						names.clear();
						break;
					}
					System.out.println("Have read " + countFromSource + " lines from source and printed " + countToFile + " lines to destination.\n");
				}
			}
			for (String name : names) {
				fileWriter.write(name + "\n");
			}
			System.out.println("Who cares? Progress is being made!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean isDiscardForContaining(String line) {
		for (String containing : DISCARD_CONTAINING) {
			if (line.contains(containing)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isDiscardForBeginning(String line) {
		for (String beginning : DISCARD_BEGINNING_WITH) {
			if (line.startsWith(beginning)) {
				return true;
			}
		}
		return false;
	}

}
