package controllers;
// congtroller là cầu nối để tương tác giữa giao diện người dùng, xử lý dữ liệu và xử lý các yêu cầu
import Exceptions.DuplicatedCustomerException;
import models.Customer;
import services.CustomerService;
import views.Components.Input;
import views.Components.Table;
import views.CustomerView;

import java.util.NoSuchElementException;

public class CustomerController{
  // dependency
  CustomerView customerView;
  CustomerService customerService;

  public CustomerController(CustomerView customerView, CustomerService customerService){
    this.customerView = customerView;
    this.customerService = customerService;
  }

  public void add(){
    customerView.start();
    Customer customer = new Customer();
    customerView.enterInformation(customer);

    try{
      customerService.add(customer);
      System.out.println("Add successfully");
    }catch(DuplicatedCustomerException exception){
      System.out.println(exception.getMessage());
    }
    customerView.end();
  }

  public void update(){
    customerView.start();
    String phone = customerView.enterPhoneNumber(customerService::hasCustomerPhoneNumber);

    try{
      customerService.update(customerView.updateCustomerForm(), phone);
      System.out.println("Update successfully.");
    }catch(NoSuchElementException | DuplicatedCustomerException e){
      System.out.println(e.getMessage());
    }
    customerView.end();
  }

  public void displayAll(){
    try {
      Table.table(customerService.getAllCustomer());
    } catch (NoSuchElementException exception) {
      System.out.println(exception.getMessage());
    }
  }

  public void remove(){
    customerView.start();
    String phone = customerView.enterPhoneNumber((phone2) -> customerService.hasCustomerPhoneNumber(phone2));
    var option = Input.enterNumber("Are you sure to remove this customer(1. Yes, 2. No)?", "Invalid. Choose again", 1, 2);
    if(option != 2){
      customerService.remove(phone);
      System.out.println("Customer was removed.");
      return;
    }
    System.out.println("Removal aborted");
  }
}
