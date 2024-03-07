package service;

import java.util.ArrayList;
import java.util.Arrays;

import helper.Degree;
import helper.InputException;
import model.*;
import model.base.Person;

public class App {
  private static ArrayList<Grade> grades = new ArrayList<>();
  private static Professor profOne, profTwo;
  private static Course courseOne, courseTwo;
  private static Student studOne, studTwo, studThree, studFour;
  private static ArrayList<Person> people = new ArrayList<>();
  private static ArrayList<Student> students = new ArrayList<>();
  private static ArrayList<Student> sortedStudents;

  public static void main(String[] argv) throws InputException {
    initObjects();
    // STUD ONE    
    Grade grOne = new Grade(7, studOne, courseOne);
    Grade grTwo = new Grade(8, studOne, courseTwo);
    // STUD TWO
    Grade grThree = new Grade(9, studTwo, courseOne);
    Grade grFour = new Grade(9, studTwo, courseTwo);
    // STUD THREE
    Grade grFive = new Grade(9, studThree, courseOne);
    Grade grSix = new Grade(10, studThree, courseTwo);
    // STUD FOUR
    Grade grSeven = new Grade(10, studFour, courseOne);
    Grade grEight = new Grade(10, studFour, courseTwo);
    
    // ADD GRADES    
    addGrade(grOne);
    addGrade(grTwo);
    addGrade(grThree);
    addGrade(grFour);
    addGrade(grFive);
    addGrade(grSix);
    addGrade(grSeven);
    addGrade(grEight);
    System.out.println("=============================================================================");
    printAllGrades();
    System.out.println("Testing the expected rejected addition of the new grade in the list..");
    Grade erraGrade = new Grade(3, studTwo, courseTwo);
    addGrade(erraGrade);
    System.out.println("=============================================================================");
    System.out.println("Testing the polymorphism via the array of people (students and professors): ");
    people.add(profOne);
    people.add(studOne);
    people.add(profTwo);
    people.add(studTwo);
    for (Person p : people) {
      System.out.println(p);
    }
    System.out.println("======= TESTING CALC BY STUDENT =======");
    System.out.println("Deniss's AVG grade ---> " + calcAvg(studOne));
    System.out.println("Misha's AVG grade ---> " + calcAvg(studTwo));
    System.out.println("David AVG grade ---> " + calcAvg(studThree));
    System.out.println("Edik's AVG grade ---> " + calcAvg(studFour));

    System.out.println("======= TESTING CALC BY COURSE =======");
    System.out.println("COURSE ONE -- AVG grade ---> " + calcAvg(courseOne));
    System.out.println("COURSE TWO -- AVG grade ---> " + calcAvg(courseTwo));

    System.out.println("======= TESTING TAUGHT BY PROFESSOR =======");
    System.out.println("Taught by profOne ---> " + taughtBy(profOne));
    
    System.out.println("=============================================================================");
    System.out.println("======================== SORTING ================================");
    System.out.println("Before sorting: ");
    for(Student s: students) {
    	System.out.print(s);
    	System.out.println(" ----> " + calcAvg(s));
    }
    
    System.out.println("After sorting: ");
    sortArray();
    
    for(Student s: sortedStudents) {
    	System.out.print(s);
    	System.out.println(" ----> " + calcAvg(s));
    }
  }
  
  // CRUD

  // create
  public static void createStudent(String name, String surname) throws Exception {
    if (name == null || surname == null) {
      throw new Exception("Empty parameters");
    }
    for (Student student: students) {
      if (student.getName().equals(name) && student.getSurname().equals(surname)) {
        throw new Exception("Student already exists");

      }
    }
    Student student = new Student(name, surname);
    students.add(student);
  }

  public static Student retrieveStudentBySurname(String surname) throws Exception {
    if (surname == null) throw new Exception("Empty surname");

    for(Student student: students) {
      if (student.getSurname().equals(surname)) {
        return student;
      }
    }
    throw new Exception("Student not found");
  }

  public static void updateStudentSurname(String name, String surname, String newSurname) throws Exception {
    if (name == null || surname == null) {
      throw new Exception("Empty parameters");
    }

    for(Student student: students) {
      if (student.getName().equals(name) && student.getSurname().equals(surname)) {
        student.setSurname(newSurname);
        return;
      }
    }

    throw new Exception("Student not found");
  }

  public static void deleteStudent(String name, String surname) throws Exception {
    if (name == null || surname == null) {
      throw new Exception("Empty parameters");
    }

    for(Student student: students) {
      if (student.getName().equals(name) && student.getSurname().equals(surname)) {
        students.remove(student);
        return;
      }
    }

    throw new Exception("Student not found");
  }

  // can add asc/desc at home
  private static void sortArray() {
	  // edit to check if these students have grades
	  sortedStudents = (ArrayList)students.clone();
	  for(int i = 0; i < sortedStudents.size() - 1; i++) {
		  for(int j = i+ 1; j < sortedStudents.size(); j++) {
			  if(calcAvg(sortedStudents.get(j)) < calcAvg(sortedStudents.get(i))) {
				  Student temp = sortedStudents.get(i);
				  sortedStudents.set(i, sortedStudents.get(j));
				  sortedStudents.set(j, temp);
			  }
		  }
		  
	  }
  }

  private static int taughtBy(Professor p) {
    ArrayList<Course> uniqCourses = new ArrayList<>();
    int total = 0;
    for (Grade g : grades) {
      if (g.getCourse().getProfessors().contains(p) && !uniqCourses.contains(g.getCourse())) {
        total++;
        uniqCourses.add(courseOne);
      }
    }
    return total;
  }

  private static double calcAvg(Student st) {
    double sum = 0;
    int total = 0;

    for (Grade g : grades) {
      if (g.getStudent().equals(st)) {
        sum += g.getValue();
        total++;
      }
    }

    return (total != 0) ? (sum / total) : 0;
  }

  private static double calcAvg(Course c) {
    double sum = 0;
    int total = 0;

    for (Grade g : grades) {
      if (g.getCourse().equals(c)) {
        sum += g.getValue();
        total++;
      }
    }

    return (total != 0) ? (sum / total) : 0;
  }

  private static void initObjects() throws InputException {
    try {
      profOne = new Professor("Jack", "One", Degree.POSTDOCTORAL);
      profTwo = new Professor("Peter", "Two", Degree.PROFESSIONAL);
      //
      courseOne = new Course("JAVA", 4, new ArrayList<Professor>(Arrays.asList(profOne)));
      courseTwo = new Course("Math Analysis 2", 2, new ArrayList<Professor>(Arrays.asList(profTwo)));
      //
      studOne = new Student("Deniss", "Solovjovs");
      studTwo = new Student("Mihails", "Kostjuks");
      studThree = new Student("Davids", "Solovjovs");
      studFour = new Student("Ediks", "Bazbauers");
      students.add(studFour);
      students.add(studThree);
      students.add(studTwo);
      students.add(studOne);
    } catch (InputException e) {
      System.out.println(e);
      throw e;
    }
    System.out.println("There are 2 professors: ");
    System.out.println(profOne);
    System.out.println(profTwo);

    System.out.println("There are 2 courses: ");
    System.out.println(courseOne);
    System.out.println(courseTwo);

    System.out.println("And there are 4 students: ");
    printStudents(students);
  }
  
  private static void printStudents(ArrayList<Student> stds) {
	  for (Student student: stds) {
		  System.out.println(student);
	  }
  }

  private static void addGrade(Grade newGrade) {
    for (Grade grade : grades) {
      if (grade.equals(newGrade)) {
        Student student = newGrade.getStudent();
        String fullName = student.getName() + student.getSurname();

        System.out.println(
            fullName + " already has a grade for " + newGrade.getCourse().getTitle() + " in the list of grades.");
        System.out.println("Edit the existing grade ---> " + grade.getId());
        return;
      }
    }
    grades.add(newGrade);
  }

  private static void printAllGrades() {
    for (Grade g : grades) {
      System.out.println(g);
    }
  }
}
