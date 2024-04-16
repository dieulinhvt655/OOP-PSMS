package services;

import data.Context;
import models.Customer;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;

public class CustomerService{
  Context context;

  public CustomerService(Context context){
    this.context = context;
  }

  public void add(Customer customer){
    customer.setId(UUID.randomUUID().toString());
    context.getCustomers().add(customer);
    context.saveChange();
  }

  public void update(Customer customer) throws NoSuchElementException{
    try{
      Customer term = context.getCustomers().stream()
              .filter(customer1 -> customer1.getPhoneNumber().equals(customer.getPhoneNumber()))
              .findFirst().get();
      if(customer.getPhoneNumber() != null){
        term.setPhoneNumber(customer.getPhoneNumber());
      }
      if(customer.getFullName() != null){
        term.setFullName(customer.getFullName());
      }
      context.saveChange();
    }catch(NoSuchElementException e){
      throw new NoSuchElementException("Customer is not found");
    }
  }

  public boolean hasCustomer(String phoneNumber){
    return context.getCustomers().stream().anyMatch(customer -> customer.getPhoneNumber().equals(phoneNumber));
  }
  public ArrayList<Customer> getAllCustomer(){
    return context.getCustomers();
  }
}
