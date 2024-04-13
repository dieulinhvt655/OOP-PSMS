package views;

import views.Components.Input;

public class ProductView{
  public int menu() {
    System.out.println("Menu");
    System.out.println("1. Add product.");
    System.out.println("2. Update product.");
    System.out.println("3. Remove productr.");
    System.out.println("4. Back.");
    System.out.println("5. Exit.");
    System.out.println("-----------------------");

    return Input.enterNumber("choose option", "invalid option", 1, 4);

  }
}
