package controllers;

import Constants.Options.Order.Update;
import Exceptions.CustomerNotFoundException;
import Exceptions.OrderNotFoundException;
import data.Context;
import models.DTO.PaidOrderDTO;
import models.Order;
import models.Product;
import services.CustomerService;
import services.OrderService;
import services.ProductService;
import views.Components.Input;
import views.Components.Table;
import views.CustomerView;
import views.OrderView;
import views.ProductView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class OrderController{
  private final OrderService orderService;
  private final OrderView orderView;
  private final ProductService productService;
  private final ProductView productView;
  private final CustomerView customerView;
  private final CustomerService customerService;

  public OrderController(OrderService orderService, OrderView orderView, ProductService productService, ProductView productView, CustomerView customerView, CustomerService customerService){
    this.orderService = orderService;
    this.orderView = orderView;
    this.productService = productService;
    this.productView = productView;
    this.customerView = customerView;
    this.customerService = customerService;
  }

  public void createOrder(){
    var order = new Order();
    var products = new ArrayList<Product>();
    var productStorage = productService.getAllProduct();
    try{
      Table.table(productStorage);
      var productIndex = Input.enterNumber("Choose product number", "Can't find product in the list", 1, productStorage.size()) - 1;
      products.add(productStorage.get(productIndex));
      order.setProducts(products);
      var customerPhone = customerView.enterPhoneNumber(customerService::hasCustomerPhoneNumber);
      var customerId = customerService.getCustomerId(customerPhone);
      order.setCustomerId(customerId);
      orderService.createOrder(order);
    }catch(CustomerNotFoundException | NoSuchElementException e){
      System.out.println(e.getMessage());
    }
  }

  public void displayPaidOrder(){
    var customerPhone = customerView.enterPhoneNumber(customerService::hasCustomerPhoneNumber);
    var customerName = customerService.getCustomer(customerPhone).getFullName();
    try{
      Table.table(orderService.getOrders(customerPhone).stream()
              .filter(Order::isPaid)
              .map(order -> new PaidOrderDTO(order, customerName))
              .collect(Collectors.toList()));
    }catch(NoSuchElementException e){
      System.out.println(e.getMessage());
    }
  }

  public void updateDraftOrder(){
    var customerPhone = customerView.enterPhoneNumber(customerService::hasCustomerPhoneNumber);
    var customerName = customerService.getCustomer(customerPhone).getFullName();
    try{
      var currentOrder = orderService.getCurrentOrder(customerPhone);
      orderView.orderForm(new PaidOrderDTO(currentOrder, customerName));
      var option = orderView.orderFromMenu();
      switch(option){
        case Update.CHANGE_PRODUCT_INFO:{
          var productIndex = Input.enterNumber("Choose product", "Product not found", 1, currentOrder.getProducts().size());
          var product = currentOrder.getProducts().get(productIndex - 1);
          var productStore = productService.getProduct(product.getId());
          orderView.editProductInfo(product, productStore);
        }
      }
      orderService.update(currentOrder);
      new ProcessBuilder("clear").inheritIO().start().waitFor();
    }catch(CustomerNotFoundException | OrderNotFoundException | IOException | InterruptedException e){
      System.out.println(e.getMessage());
    }
  }

  public static void main(String[] args){
    new OrderController(new OrderService(new Context(), new CustomerService(new Context())), new OrderView(), new ProductService(new Context()), new ProductView(), new CustomerView(), new CustomerService(new Context()))
            .updateDraftOrder();
  }
}
