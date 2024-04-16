package controllers;

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

  public void addCustomer(){
    customerView.start();
    Customer customer = new Customer();
    customerView.enterInformation(customer);
    customerService.add(customer);
    customerView.end();
  }

  public void updateCustomer(){
    customerView.start();
    String phone = customerView.enterPhoneNumber();
    if(customerService.hasCustomer(phone)){
      try{
        customerService.update(customerView.updateCustomerForm());
      }catch(NoSuchElementException e){

      }
    }
    customerView.end();
  }
  public void displayAll(){
    Table.table(customerService.getAllCustomer());
  }
}
