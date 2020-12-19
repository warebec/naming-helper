import java.io.*;
import java.util.*;

public class ReduceListMain {

	private static final String INPUT_TXT = "src/resources/BatchedOutput.txt";
	private static final String OUTPUT_TXT = "src/resources/ReducedList.txt";
	
	public static void main(String[] args) {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(INPUT_TXT)));
				Scanner scanner = new Scanner(System.in);
				FileWriter fileWriter = new FileWriter(new File(OUTPUT_TXT))) {
			
			List<String> names = new ArrayList<>();
			String line = "";
			int count = 1;

			while ((line = bufferedReader.readLine()) != null) {
				names.add(line);
				if (names.size() >= 5) {
					// show random 2 to user
					Random random = new Random();
					int index1 = random.nextInt(names.size()-1);
					int index2 = random.nextInt(names.size()-1);
					while (index2 == index1) {
						index2 = random.nextInt(names.size()-1);
					}
					String name1 = names.get(index1);
					String name2 = names.get(index2);
					
					String displayOutput = name1 + "\n" + name2 + "\n"
							+ "Please pick from the following options:\n"
							+ "1. keep only the first\n"
							+ "2. keep only the second\n"
							+ "3. keep both\n"
							+ "4. discard both";
					System.out.println(displayOutput);
					
					// read input of which to keep, remove the other from the set
					String choice = scanner.nextLine();
					boolean choiceProcessed = false;
					while (!choiceProcessed) {
						choiceProcessed = true;
						switch (choice) {
						case "1":
							names.remove(name2);
							break;
						case "2":
							names.remove(name1);
							break;
						case "3":
							break;
						case "4":
							names.remove(name1);
							names.remove(name2);
							break;
						default:
							choiceProcessed = false;
							displayOutput = name1 + "\n" + name2 + "\n"
									+ "Incorrect input. Please pick from the following options:\n"
									+ "1. keep only the first\n"
									+ "2. keep only the second\n"
									+ "3. keep both\n"
									+ "4. discard both";
							System.out.println(displayOutput);
							choice = scanner.nextLine();
						}
					}
					
					// if size > 10, put random 5 in output file and remove from list
					if (names.size() > 10) {
						for (int i = 1; i <= 5; i++) {
							int index = random.nextInt(names.size()-1);
							fileWriter.write(names.get(index) + "\n");
							names.remove(index);
						}
					}
					System.out.println("Have read " + count + " lines from source; names list has " + names.size() + " options.\n");
				}
				count++;
			}
			for (String name : names) {
				fileWriter.write(name + "\n");
			}
			System.out.println("Who cares? Progress is being made!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
