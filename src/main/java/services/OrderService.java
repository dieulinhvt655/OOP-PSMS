package services;

import Exceptions.CustomerNotFoundException;
import Exceptions.OrderNotFoundException;
import data.Context;
import models.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderService{
  Context context;
  CustomerService customerService;

  public OrderService(Context context, CustomerService customerService){
    this.context = context;
    this.customerService = customerService;
  }

  public List<Order> getOrders(String phoneNumber){
    var customer = context.getCustomers().stream().filter(cus -> cus.getPhoneNumber().equals(phoneNumber)).findFirst().get();
    var orderList = context.getOrders().stream().filter(order -> order.getCustomerId().equals(customer.getId()));
    return orderList.collect(Collectors.toList());
  }
  public void createOrder(Order order) throws CustomerNotFoundException{
    if(order.getCustomerId() == null || !customerService.hasCustomer(order.getCustomerId()))
      throw CustomerNotFoundException.getInstance_1();
    context.getOrders().add(order);
    context.saveChange();
  }
  public void update(Order order) throws OrderNotFoundException{
    var updatedOrder = context.getOrders().stream().filter(order1 -> order1.getId() == order.getId() && !order1.isPaid()).findFirst().get();
    if(updatedOrder == null) throw OrderNotFoundException.getInstance_1();
    updatedOrder.setProducts(order.getProducts());
  }

}
