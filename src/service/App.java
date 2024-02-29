package service;

import helper.Degree;
import helper.InputException;
import model.*;

public class App {
  public static void main(String[] argv) throws Exception {
    Professor prof;

    try {
      prof = new Professor("Deniss S", "Solovjovs", Degree.BACHELOR);
    } catch (InputException e) {
      System.out.println(e);
      throw e;
    }
    prof.showFullInfo();
    System.out.println(Professor.getCounter());

  }
}
