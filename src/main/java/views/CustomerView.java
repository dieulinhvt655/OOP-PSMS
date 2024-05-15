package views;

import Constants.Options;
import Constants.Options.Update;
import models.Customer;
import views.Components.Input;

import java.util.function.Function;

public class CustomerView extends BaseView{
  public int menu(){
    System.out.println("Customer Management Menu:");
    System.out.println("-----------------------");
    System.out.println("1. Add customer.");
    System.out.println("2. Update customer.");
    System.out.println("3. Remove customer.");
    System.out.println("4. Display.");
    System.out.println("5. Back.");
    System.out.println("6. Exit.");
    System.out.println("-----------------------");
    return Input.enterNumber("choose option", "invalid option", Options.Customer.ADD, Options.Customer.EXIT);
  }

  public void enterInformation(Customer customer){
    String name = Input.enterAString("Enter customer's name: ");
    String phoneNumber = Input.enterAString("Enter customer's phone number: ");
    customer.setFullName(name);
    customer.setPhoneNumber(phoneNumber);
  }

  public String enterPhoneNumber(Function<String, Boolean> condition){
    String phone;
    do{
      phone = Input.enterAString("Enter phone number: ");
      if(condition.apply(phone)){
        return phone;
      }else {
        System.out.println("No customer found, try again");
      }
    }while(true);
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
