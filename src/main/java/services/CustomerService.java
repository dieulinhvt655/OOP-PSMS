package services;

import data.Context;
import models.Customer;

import java.util.UUID;

public class CustomerService{
  Context context;

  public CustomerService(Context context){
    this.context = context;
  }

  public void add(Customer customer){
    customer.setId(UUID.randomUUID().toString());
    context.getCustomers().add(customer);
  }
}
