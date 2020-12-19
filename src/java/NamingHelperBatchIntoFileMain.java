import java.io.*;
import java.util.*;

public class NamingHelperBatchIntoFileMain {

	private static final int BATCH_SIZE = 2500000;
	private static List<String> fullNamesList = new ArrayList<>();

	public static void main(String[] args) {

		System.out.println("Working Directory = " + System.getProperty("user.dir"));

		// create List of names from csv
		List<Name> namesList = new ArrayList<>();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("src/resources/reducedNamesList.csv")))) {
			String line = "";
			String csvBreakBy = ",";
			int count = 0;

			while ((line = bufferedReader.readLine()) != null && count < 500) {
				String[] nameProperties = line.split(csvBreakBy);

				while (!("true".equals(nameProperties[3]) || "false".equals(nameProperties[3]))) {
					nameProperties[2] = nameProperties[2] + ", " + nameProperties[3];
					int index = 4;
					while (index < nameProperties.length) {
						nameProperties[index-1] = nameProperties[index];
						index++;
					}
				}

				boolean onlyLast = false;
				if ("true".equals(nameProperties[3])) {
					onlyLast = true;
				}
				namesList.add(new Name(nameProperties[0], nameProperties[1], nameProperties[2], onlyLast));
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		int numNames = 0;
		File output = new File("src/resources/BatchedOutput.txt");
		try {
			if (output.exists()) {
				output.delete();
			}
			output.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (FileWriter fileWriter = new FileWriter(output)) {

			// 3 name combinations
			System.out.println("Beginning 3 name combinations...");
			for (int i = 0; i < namesList.size(); i++) {
				Name firstName = namesList.get(i);
				if (!firstName.onlyLast) {
					for (int j = 0; j < namesList.size(); j++) {
						if (i != j) {
							Name middleName = namesList.get(j);
							if (!middleName.onlyLast) {
								for (int k = 0; k < namesList.size(); k++) {
									if (i != k && j != k) {
										Name lastName = namesList.get(k);
										fullNamesList.add((new FullName(firstName, middleName, lastName)).toString());
										numNames++;
										if ((fullNamesList.size() % BATCH_SIZE) == 0) {
											writeOutput(fileWriter, numNames);
										}
									}
								}
							}
						}
					}
				}
			}

			// 4 name combinations
			System.out.println("Done with 3, beginning 4 name combinations...");
			for (int i = 0; i < namesList.size(); i++) {
				Name firstName = namesList.get(i);
				if (!firstName.onlyLast) {
					for (int j = 0; j < namesList.size(); j++) {
						if (i != j) {
							Name middleName1 = namesList.get(j);
							if (!middleName1.onlyLast) {
								for (int k = 0; k < namesList.size(); k++) {
									if (i != k && j != k) {
										Name middleName2 = namesList.get(k);
										if (!middleName2.onlyLast) {
											for (int l = 0; l < namesList.size(); l++) {
												if (i != l && j != l && k != l) {
													Name lastName = namesList.get(l);
													fullNamesList.add((new FullName(firstName, middleName1, middleName2, lastName)).toString());
													numNames++;
													if ((fullNamesList.size() % BATCH_SIZE) == 0) {
														writeOutput(fileWriter, numNames);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			writeOutput(fileWriter, numNames);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void writeOutput(FileWriter fileWriter, int numNames) throws IOException {
		Collections.shuffle(fullNamesList);
		for (String fullName : fullNamesList) {
			fileWriter.write(fullName.toString() + "\n");
		}

		System.out.println("Added " + fullNamesList.size() + 
				" names to file for a total of " + numNames + ".");
		fullNamesList.clear();
	}

}
