public class FullName {
  String fullName;
  String originAndMeaning;
  
  FullName(firstName, middleName, lastName) {
    this.fullName = firstName.name + " " + middleName.name + " " + lastName.name;
    this.originAndMeaning = firstName.originAndMeaning + ", " + middleName.originAndMeaning + ", " + lastName.originAndMeaning;
  }
  
  FullName(firstName, middleName1, middleName2, lastName) {
    this.fullName = firstName.name + " " + middleName1.name + " " + middleName2.name + " " + lastName.name;
    this.originAndMeaning = firstName.originAndMeaning + ", " + middleName1.originAndMeaning + ", " + middleName2.originAndMeaning + ", " + lastName.originAndMeaning;
  }
  
  @Override
    public String toString() { 
        return fullName + " ::: " + originAndMeaning; 
    }
}
