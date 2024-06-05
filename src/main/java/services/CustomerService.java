package services;
// service dùng để tương tác với dữ liệu truy vấn, chỉnh sửa (update/remove/create)
import Exceptions.CustomerNotFoundException;
import Exceptions.DuplicatedCustomerException;
import data.Context;
import models.Customer;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;
// instance
public class CustomerService{
  private final Context context;

  public CustomerService(Context context){
    this.context = context;

  }
  // thêm 1 khách hàng mới
  public void add(Customer customer) throws DuplicatedCustomerException{
    if(hasCustomerPhoneNumber(customer.getPhoneNumber())){
      throw new DuplicatedCustomerException("Error: Phone number is existed.");
    }
    customer.setId(UUID.randomUUID().toString());
    context.getCustomers().add(customer);
    context.saveChange();
  }
  public void update(Customer customer, String oldPhone) throws NoSuchElementException, DuplicatedCustomerException{
    if(hasPhoneNumberExitedExceptItsOwner(customer.getPhoneNumber(), oldPhone)){
      throw new DuplicatedCustomerException("Error: Phone number is existed.");
    }
    try{
      var term = context.getCustomers().stream()
              .filter(customer1 -> customer1.getPhoneNumber().equals(oldPhone))
              .findFirst().get();
      if(customer.getPhoneNumber() != null){
        term.setPhoneNumber(customer.getPhoneNumber());
      }
      if(customer.getFullName() != null){
        term.setFullName(customer.getFullName());
      }
      context.saveChange();
    }catch(NoSuchElementException e){
      throw e;
//      throw new NoSuchElementException("Customer is not found");
    }
  }

  public boolean hasPhoneNumberExitedExceptItsOwner(String phoneNumber, String oldNumber){
    return context.getCustomers().stream().filter(customer -> !customer.getPhoneNumber().equals(oldNumber)).anyMatch(customer -> customer.getPhoneNumber().equals(phoneNumber));
  }

  public boolean hasCustomerPhoneNumber(String phoneNumber){
    return context.getCustomers().stream().anyMatch(customer -> customer.getPhoneNumber().equals(phoneNumber));
  }

  public boolean hasCustomer(String id){
    return context.getCustomers().stream().anyMatch(customer -> customer.getId().equals(id));
  }

  public String getCustomerId(String phoneNumber) throws CustomerNotFoundException{
    try{
      return context.getCustomers().stream().filter(customer -> customer.getPhoneNumber().equals(phoneNumber)).findFirst().get().getId();
    }catch(NoSuchElementException e){
      throw CustomerNotFoundException.getInstance_1();
    }
  }

  public ArrayList<Customer> getAllCustomer(){
    return context.getCustomers();
  }

  public void remove(String phone){
    context.getCustomers().removeIf(customer -> customer.getPhoneNumber().equals(phone));
    context.saveChange();
  }

  public Customer getCustomer(String idOrPhone){
    return context.getCustomers().stream().filter(customer -> customer.getId().equals(idOrPhone)||customer.getPhoneNumber().equals(idOrPhone)).findFirst().get();
  }
}
