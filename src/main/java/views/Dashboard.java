package views;

import java.util.Scanner;

public class Dashboard{

  public static void menu() {
    System.out.println("---------------Menu---------------");
    System.out.println("| 1. Products management     |");
    System.out.println("| 2. Customers management | ");
    System.out.println("| 3. Orders management        |");
    System.out.println("-------------------------------------");

    int option = enterNumber("choose an option: ", "Invalid value", 1, 3);

  }
  public static int enterNumber(String message, String error, int min, int max) {
    System.out.print(message + ": ");
    Scanner scanner = new Scanner(System.in);

    try{
      int option = scanner.nextInt();
      if ( option < min || option > max) {
        return enterNumber(message, error, min, max);
      }
      return option;
    }
    catch(Exception exception) {

      System.out.println("Error: " + error);
      return enterNumber(message, error, min, max);

    }
  }
}
