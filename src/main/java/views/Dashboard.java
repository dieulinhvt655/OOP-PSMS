package views;

import views.Components.Input;

import java.util.Scanner;

public class Dashboard{

  public int menu() {
    System.out.println("---------------Menu---------------");
    System.out.println(" 1. Products management");
    System.out.println(" 2. Customers management");
    System.out.println(" 3. Orders management");
    System.out.println(" 4. Exit");
    System.out.println("-----------------------------------");

    return Input.enterNumber("choose an option: ", "Invalid value", 1, 4);

  }
}


