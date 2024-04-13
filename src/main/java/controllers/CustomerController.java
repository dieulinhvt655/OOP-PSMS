package controllers;

import models.Customer;
import services.CustomerService;
import views.CustomerView;

public class CustomerController{
  CustomerView customerView;
  CustomerService customerService;

  public CustomerController(CustomerView customerView, CustomerService customerService){
    this.customerView = customerView;
    this.customerService = customerService;
  }

  public void addCustomer() {
    Customer customer = new Customer();
    customerView.enterInformation(customer);
    customerService.add(customer);

  }
}
