package views;

import Constants.Options;
import models.Customer;
import views.Components.Input;

public class CustomerView{
  public int menu(){
    System.out.println("Menu");
    System.out.println("1. Add customer.");
    System.out.println("2. Update customer.");
    System.out.println("3. Remove customer.");
    System.out.println("4. Back.");
    System.out.println("5. Exit.");
    System.out.println("-----------------------");
    return Input.enterNumber("choose option", "invalid option", Options.ADD, Options.EXIT_CHILD_MENU);
  }

  public void enterInformation(Customer customer){
    String name = Input.enterAString("Enter customer's name: ");
    String phoneNumber = Input.enterAString("Enter customer's phone number: ");
    customer.setFullName(name);
    customer.setPhoneNumber(phoneNumber);
  }
}
