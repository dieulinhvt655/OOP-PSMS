package views.Components;

import java.util.Scanner;

public class Input{
  public static int enterNumber(String message, String error, int min, int max) {
    System.out.print(message + ": ");
    Scanner scanner = new Scanner(System.in);

    try{
      int option = scanner.nextInt();
      if ( option < min || option > max) {
        System.out.println("Error: "+error);
        return enterNumber(message, error, min, max);
      }
      return option;
    }
    catch(Exception exception) {

      System.out.println("Error: " + error);
      return enterNumber(message, error, min, max);

    }
  }

  public static String enterAString(String message) {
    Scanner scanner = new Scanner(System.in);
    System.out.print(message);
    return scanner.nextLine();
  }
  public static double enterDNumber(String message, String error, double min, double max) {
    System.out.print(message + ": ");
    Scanner scanner = new Scanner(System.in);

    try{
      int option = scanner.nextInt();
      if ( option < min || option > max) {
        System.out.println("Error: "+error);
        return enterDNumber(message, error, min, max);
      }
      return option;
    }
    catch(Exception exception) {

      System.out.println("Error: " + error);
      return enterDNumber(message, error, min, max);

    }
  }

}
