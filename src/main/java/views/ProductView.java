package views;

import Constants.Options;
import views.Components.Input;

public class ProductView{
  public int menu() {
    System.out.println("Product Management Menu");
    System.out.println("-----------------------");
    System.out.println("1. Add product.");
    System.out.println("2. Update product.");
    System.out.println("3. Remove productr.");
    System.out.println("4. Display.");
    System.out.println("5. Back.");
    System.out.println("6. Exit.");
    System.out.println("-----------------------");
    return Input.enterNumber("choose option", "invalid option", Options.Product.ADD, Options.Product.EXIT);
  }

}
