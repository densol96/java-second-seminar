package model;

import helper.InputException;
import model.base.Person;

public class Student extends Person {
  public Student() throws InputException {
    super("Student", "test");
    counter++;
  }
}