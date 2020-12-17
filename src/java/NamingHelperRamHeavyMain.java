import java.io.*;
import java.util.*;

public class NamingHelperRamHeavyMain {
  
  private static List<FullName> fullNamesList = new ArrayList<>();
  private static List<String> fileNames = new ArrayList<>();
  
  public static void main(String[] args) {
    // create List of names from namesList.csv
    List<Name> namesList = new ArrayList<>();
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("../resources/namesList.csv")))) {
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
                  fullNamesList.add(new FullName(firstName, middleName, lastName));
                  numNames++;
                  if ((fullNamesList.size() % 1000000) == 0) {
                    System.out.println("Made " + numNames + " names so far!");
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
                        fullNamesList.add(new FullName(firstName, middleName1, middleName2, lastName));
                        if ((fullNamesList.size() % 1000000) == 0) {
                          System.out.println("Made " + numNames + " names so far!");
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
    
    fillOutputFile("output.txt");
    System.out.println("Made " + numNames + " names total!");
  }
  
  private static void fillOutputFile(String outputFileName) {
    Collections.shuffle(fullNamesList);
            
    File output = new File("../resources/" + outputFileName);
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
}
