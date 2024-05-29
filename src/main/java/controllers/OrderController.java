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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class OrderController{
  public final OrderService orderService;
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

  private ArrayList<Product> chooseProduct(ArrayList<Product> productStorage){
    var products = new ArrayList<Product>();
    Table.table(productStorage);
    int option;
    do{
      var productIndex = Input.enterNumber("Choose product number", "Can't find product in the list", 1, productStorage.size()) - 1;
      var product = productStorage.get(productIndex).clone();
      var productStore = productService.getProduct(product.getId());
      orderView.editProductInfo(product, productStore);
      var newProduct = new ArrayList<Product>();
      newProduct.add(product);
      mergeTwoProductList(products, newProduct);
      System.out.println("1, Keep going to choose\n2.Complete");
      option = Input.enterNumber("Enter option", "Invalid", 1, 2);
    }while(option != 2);
    return products;
  }

  private void buy(Order order){
    System.out.println("1. Buy\n2. Save order to buy later");
    int option = Input.enterNumber("Enter option", "Invalid", 1, 2);
    if(option == 1){
      try{
        orderService.buy(order);
        System.out.println("Complete");
        return;
      }catch(CustomerNotFoundException e){
        System.out.println("Error: " + e.getMessage());
      }
    }
    System.out.println("Order saved");
  }

  public void createOrder(){
    var order = new Order();
    var productStorage = productService.getAllProduct();
    try{
      mergeTwoProductList(order.getProducts(), chooseProduct(productStorage));
      var customerPhone = customerView.enterPhoneNumber(customerService::hasCustomerPhoneNumber);
      var customerId = customerService.getCustomerId(customerPhone);
      order.setCustomerId(customerId);
      orderService.createOrder(order);
      buy(order);
    }catch(CustomerNotFoundException | NoSuchElementException e){
      System.out.println(e.getMessage());
    }
  }

  public void displayPaidOrder(){
    var customerPhone = customerView.enterPhoneNumber(customerService::hasCustomerPhoneNumber);
    var customerName = customerService.getCustomer(customerPhone).getFullName();
    try{
      var orders = orderService.getOrders(customerPhone).stream()
              .filter(Order::isPaid)
              .map(order -> new PaidOrderDTO(order, customerName))
              .collect(Collectors.toList());
      Table.table(orders, "paid orders");
      var orderIndex = Input.enterNumber("Choose an order to see detail", "Order not found", 1, orders.size());
      var currentOrder = orders.get(orderIndex - 1);
      orderView.orderForm(currentOrder);
    }catch(NoSuchElementException e){
      System.out.println(e.getMessage());
    }
  }

  public void updateDraftOrder(){
    var customerPhone = customerView.enterPhoneNumber(customerService::hasCustomerPhoneNumber);
    var customerName = customerService.getCustomer(customerPhone).getFullName();
    try{
      var orders = orderService.getIsNotPaidOrders(customerPhone);
      System.out.println("Your all orders have not paid yet below: ");
      Table.table(orders.stream()
              .map(order -> new PaidOrderDTO(order, customerName))
              .collect(Collectors.toList()));
      var orderIndex = Input.enterNumber("Choose an order", "Order not found", 1, orders.size());
      var currentOrder = orders.get(orderIndex - 1);
      orderView.orderForm(new PaidOrderDTO(currentOrder, customerName));
      var option = orderView.orderFromMenu();
      switch(option){
        case Update.CHOOSE_NEW_PRODUCT:
          var productStorage = productService.getAllProduct();
          mergeTwoProductList(currentOrder.getProducts(), chooseProduct(productStorage));
          orderService.update(currentOrder);
          buy(currentOrder);
          break;
        case Update.CHANGE_PRODUCT_INFO:{
          var productIndex = Input.enterNumber("Choose product", "Product not found", 1, currentOrder.getProducts().size());
          var product = currentOrder.getProducts().get(productIndex - 1);
          var productStore = productService.getProduct(product.getId());
          Table.table(productService.getAllProduct().stream()
                          .filter(product1 -> product1.getId().equals(product.getId()))
                          .collect(Collectors.toList())
                  , "Product information in store: ");
          orderView.editProductInfo(product, productStore);
          orderService.update(currentOrder);
          buy(currentOrder);
          break;
        }
        case Update.BUY:
          buy(currentOrder);
          break;
      }
//      new ProcessBuilder("clear").inheritIO().start().waitFor();
    }catch(OrderNotFoundException | NoSuchElementException e){
      System.out.println(e.getMessage());
    }
  }

  private void mergeTwoProductList(List<Product> originList, List<Product> newList){
    if(originList.isEmpty()){
      originList.addAll(newList);
      return;
    }
    for(Product product_newList : newList){
      if(originList.stream().anyMatch(product -> product.getId().equals(product_newList.getId()))){
        originList.stream()
                .filter(product -> product.getId().equals(product_newList.getId()))
                .findFirst()
                .get()
                .setQuantity(quantity -> quantity + product_newList.getQuantity());
      }else{
        originList.add(product_newList);
      }
    }
  }

  public static void main(String[] args){
    new OrderController(new OrderService(new Context(), new CustomerService(new Context())), new OrderView(), new ProductService(new Context()), new ProductView(), new CustomerView(), new CustomerService(new Context()))
            .updateDraftOrder();
  }
}
