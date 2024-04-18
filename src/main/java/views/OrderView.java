package views;

import Constants.Options;
import Constants.Options.Order;
import views.Components.Input;

public class OrderView{

  public int menu() {
    System.out.println("Order Management Menu");
    System.out.println("-----------------------");
    System.out.println("1. Add order.");
    System.out.println("2. Update order.");
    System.out.println("3. Remove order.");
    System.out.println("4. Display.");
    System.out.println("5. Back.");
    System.out.println("6. Exit.");
    System.out.println("-----------------------");

    return Input.enterNumber("choose option", "invalid option", Options.Order.ADD, Order.EXIT);

  }
}
