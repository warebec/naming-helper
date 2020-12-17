import java.io.*;
import java.util.*;

public class NamingHelperMain {
  public static void main(String[] args) {
    // first, create List of names from namesList.csv
    List<Name> namesList = new ArrayList<>();
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("../resources/namesList.csv")))) {
      String line = "";
      String csvBreakBy = ",";
      int count = 0;
      
      while ((line = bufferedReader.readLine()) != null && count < 50) {
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
    
    // next, create List of full names based on that list
    List<FullName> fullNamesList = new ArrayList<>();
            
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
                }
              }
            }
          }
        }
      }
    }
            
    // 4 name combinations
    System.out.println("Done with 3, beginning 4 name combinations...");
    int num = 1;
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
                        Name lastName = namesList.get(k);
                        fullNamesList.add(new FullName(firstName, middleName1, middleName2, lastName));
                        System.out.println("Added name #" + num++);
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
    
    // next, shuffle the full names list to somewhat randomize the output
    System.out.println("About to shuffle fullNamesList of size: " + fullNamesList.size());
    Collections.shuffle(fullNamesList);
    System.out.println("Done shuffling");
            
    // finally, print full names to output.txt in format:
    //   - full name ::: origin and meaning of name
    File output = new File("../resources/output.txt");
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
  }
}
