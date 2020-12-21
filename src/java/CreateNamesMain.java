import java.io.*;
import java.util.*;

public class CreateNamesMain {

	private static List<FullName> fullNamesList = new ArrayList<>();
	private static List<String> fileNames = new ArrayList<>();

	public static void main(String[] args) {

		// create List of names from namesList.csv
		List<Name> namesList = new ArrayList<>();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("src/resources/reducedNamesListv2.csv")))) {
			String line = "";
			String csvBreakBy = ",";

			while ((line = bufferedReader.readLine()) != null && namesList.size() < 50) {
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
				boolean neverLast = false;
				if ("true".equals(nameProperties[4])) {
					neverLast = true;
				}

				namesList.add(new Name(nameProperties[0], nameProperties[1], nameProperties[2], onlyLast, neverLast));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Integer fileNum = null;
		int numNames = 0;

		System.out.println("Making full name combinations...");
		List<String> neverFirstList = new ArrayList<>(List.of("Waterfall", "Phoenix", "Emeterio", "Herodes", "Androcles",
				"Percival", "Quigley", "Fileas", "Yorick", "Bonito", "Philander", "Felix", "Renatus"));
		for (int i = 0; i < namesList.size(); i++) {
			Name firstName = namesList.get(i);
			if (!firstName.onlyLast && !neverFirstList.contains(firstName.name)) {
				for (int j = 0; j < namesList.size(); j++) {
					if (i != j) {
						Name middleName1 = namesList.get(j);
						if (!middleName1.onlyLast) {
							for (int k = 0; k < namesList.size(); k++) {
								if (i != k && j != k) {
									Name middleName2 = namesList.get(k);
									if (!middleName2.neverLast) {
										fullNamesList.add(new FullName(firstName, middleName1, middleName2));
										if ((fullNamesList.size() % 1000000) == 0) {
											makeFile(fileNum);
											if (fileNum == null) {
												fileNum = 0;
											}
											fileNum++;
										}
										numNames++;
									}
									for (int l = 0; l < namesList.size(); l++) {
										if (i != l && j != l && k != l) {
											Name lastName = namesList.get(l);
											if (!(middleName2.onlyLast && !lastName.onlyLast) && !lastName.neverLast) {
												fullNamesList.add(new FullName(firstName, middleName1, middleName2, lastName));
												if ((fullNamesList.size() % 1000000) == 0) {
													makeFile(fileNum);
													if (fileNum == null) {
														fileNum = 0;
													}
													fileNum++;
												}
												numNames++;
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
		makeFile(fileNum);
		System.out.println("Made " + numNames + " names in " + fileNames.size() + " files.");

		if (fileNames.size() > 1) {
			mergeFilesIntoOne();
		}
	}

	private static void makeFile(Integer fileNum) {
		String fileName = "output" + fileNum + ".txt".replace("null", "");
		fileName = fileName.replace("null", "");
		fillOutputFile(fileName);
		fileNames.add(fileName);
	}

	private static void fillOutputFile(String outputFileName) {
		Collections.shuffle(fullNamesList);

		File output = new File("src/resources/" + outputFileName);
		try {
			if (output.exists()) {
				output.delete();
			}
			output.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (FileWriter fileWriter = new FileWriter(output)) {
			for (FullName fullName : fullNamesList) {
				fileWriter.write(fullName.toString() + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(outputFileName + " completed! Contains " + fullNamesList.size() + " names.");
		fullNamesList.clear();
	}

	private static void mergeFilesIntoOne() {
		Collections.shuffle(fileNames);

		FileWriter fileWriter = null;
		try {
			List<BufferedReader> bufferedReaders = new ArrayList<>();
			for (String fileName : fileNames) {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("src/resources/" + fileName)));
				bufferedReaders.add(bufferedReader);
			}
			System.out.println("Successfully made " + bufferedReaders.size() + " readers");

			File output = new File("src/resources/FullOutput.txt");
			if (output.exists()) {
				output.delete();
			}
			output.createNewFile();
			fileWriter = new FileWriter(output);

			int numNulls = 0;
			int linesWritten = 0;
			while (numNulls < bufferedReaders.size()) {
				numNulls = 0;
				for (BufferedReader br : bufferedReaders) {
					String line = br.readLine();
					if (line != null) {
						fileWriter.write(line + "\n");
						linesWritten++;
						if ((linesWritten % 1000000) == 0) {
							System.out.println("Wrote " + linesWritten + " names to combo mega file!");
						}
					} else {
						numNulls++;
					}
				}
			}
			System.out.println("Wrote " + linesWritten + " total names to combo mega file!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
