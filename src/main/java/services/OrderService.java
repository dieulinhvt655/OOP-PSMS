package services;

import Exceptions.CustomerNotFoundException;
import Exceptions.OrderNotFoundException;
import data.Context;
import models.Order;
import models.Product;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService implements BaseService{
  private final Context context;
  private final CustomerService customerService;

  public OrderService(Context context, CustomerService customerService){
    this.context = context;
    this.customerService = customerService;
  }

  public List<Order> getOrders(String phoneNumber){
    var customer = context.getCustomers().stream()
            .filter(cus -> cus.getPhoneNumber().equals(phoneNumber)).findFirst().get();
    var orderList = context.getOrders().stream()
            .filter(order -> order.getCustomerId().equals(customer.getId()));
    return orderList.collect(Collectors.toList());
  }

  public List<Order> getIsNotPaidOrders(String phoneNumber){
    var customer = context.getCustomers().stream()
            .filter(cus -> cus.getPhoneNumber().equals(phoneNumber)).findFirst().get();
    var orderList = context.getOrders().stream()
            .filter(order -> order.getCustomerId().equals(customer.getId()) && !order.isPaid());
    return orderList.collect(Collectors.toList());
  }

  public void createOrder(Order order) throws CustomerNotFoundException{
    var generatedId = generateIntId();
    order.setDate(new Date());
    order.setId(generatedId);
    if(order.getCustomerId() == null || !customerService.hasCustomer(order.getCustomerId()))
      throw CustomerNotFoundException.getInstance_1();
    context.getOrders().add(order);
    context.saveChange();
  }

  public void update(Order order) throws OrderNotFoundException{
    var updatedOrder = context.getOrders().stream().filter(order1 -> order1.getId() == order.getId() && !order1.isPaid()).findFirst().get();
    if(updatedOrder == null) throw OrderNotFoundException.getInstance_1();
    updatedOrder.setProducts(order.getProducts());
    updatedOrder.setCustomerId(order.getCustomerId());
    context.saveChange();
  }

  public Order getCurrentOrder(String phoneNumber) throws CustomerNotFoundException{
    var customerId = customerService.getCustomerId(phoneNumber);
    return context.getOrders().stream()
            .filter(order ->
                    !order.isPaid() &&
                            order.getCustomerId().equals(customerId))
            .findFirst().get();
  }

  public void buy(Order order) throws CustomerNotFoundException{
    var updatedOrder = context.getOrders().stream()
            .filter(order_1 -> (order_1.getId() == order.getId() && !order.isPaid()))
            .findFirst().get();
    updatedOrder.setPaid(true);
    updatedOrder.setDate(new Date());
    var products = context.getProducts();
    for(Product product : order.getProducts()){
      products.stream()
              .filter(product1 -> product1.getId().equals(product.getId())).findFirst()
              .get()
              .setQuantity(quantity -> quantity - product.getQuantity());
    }
    context.saveChange();
  }

  @Override
  public int generateIntId(){
    try{
      var id = context.getOrders().stream().max(Comparator.comparing(Order::getId)).get().getId() + 1;
      return id;
    }catch(Exception e){
      return 0;
    }
  }
  public void deleteAll(){
    context.getOrders().removeIf(order -> order.getId()==0);
    context.saveChange();
  }

}

