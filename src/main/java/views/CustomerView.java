package views;

import Constants.Options;
import Constants.Options.Update;
import models.Customer;
import views.Components.Input;

public class CustomerView extends BaseView{
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

  public String enterPhoneNumber(){
    return Input.enterAString("Enter phone number");
  }

  public Customer updateCustomerForm(){
    Customer customer = new Customer();
    System.out.println("---------------------------");
    System.out.println("1, update phone number");
    System.out.println("2, update name");
    System.out.println("3, update both");
    System.out.println("4, exit");
    System.out.println("---------------------------");
    int option = Input.enterNumber("choose option", "invalid option", Update.PHONE, Update.EXIT);
    switch(option){
      case Update.PHONE:
        String phoneNumber = Input.enterAString("Enter customer's phone number: ");
        customer.setPhoneNumber(phoneNumber);
        break;
      case Update.NAME:
        String name = Input.enterAString("Enter customer's name: ");
        customer.setFullName(name);
        break;
      case Update.BOTH:
        enterInformation(customer);
        break;
      case Update.EXIT:
        break;
    }
    return customer;
  }


}
