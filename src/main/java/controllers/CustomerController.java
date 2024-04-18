package controllers;

import Exceptions.DuplicatedCustomerException;
import models.Customer;
import services.CustomerService;
import views.Components.Table;
import views.CustomerView;

import java.util.NoSuchElementException;

public class CustomerController{
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
    }
    catch(DuplicatedCustomerException exception) {
      System.err.println(exception.getMessage());
    }
    customerView.end();

  }

  public void update(){
    customerView.start();
    String phone = customerView.enterPhoneNumber();
    if(customerService.hasCustomer(phone)){
      try{
        customerService.update(customerView.updateCustomerForm(), phone);
        System.out.println("Update successfully.");
      }catch(NoSuchElementException | DuplicatedCustomerException e ){
        System.err.println(e.getMessage());
      }
    }
    else {
      System.out.printf("Error: Customer not found.");
    }
    customerView.end();
  }

  public void displayAll(){
    Table.table(customerService.getAllCustomer());
  }
  public void remove() {
    customerView.start();
    String phone = customerView.enterPhoneNumber();
    if (customerService.hasCustomer(phone)) {
      customerService.remove(phone);
      System.out.println("Customer was removed.");
    }
    else {
      System.out.println("Error: not found");
    }
  }
}
