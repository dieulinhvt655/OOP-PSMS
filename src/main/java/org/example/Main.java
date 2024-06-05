package org.example;

import Constants.Options;
import Constants.Options.Customer;
import Constants.Options.Order;
import Constants.Options.Product;
import controllers.CustomerController;
import controllers.OrderController;
import controllers.ProductController;
import data.Context;
import models.DTO.Anonymous;
import services.CustomerService;
import services.OrderService;
import services.ProductService;
import views.Components.Table;
import views.CustomerView;
import views.Dashboard;
import views.OrderView;
import views.ProductView;

public class Main{
  public static void main(String[] args){
    //Config dependency injection
    //context
    Context context = new Context();
    //view
    Dashboard dashboard = new Dashboard();
    CustomerView customerView = new CustomerView();
    OrderView orderView = new OrderView();
    ProductView productView = new ProductView();
    //services
    CustomerService customerService = new CustomerService(context);
    OrderService orderService = new OrderService(context, customerService);
    ProductService productService = new ProductService(context);
    //controller
    CustomerController customerController = new CustomerController(customerView, customerService);
    ProductController productController = new ProductController(productView, productService);
    OrderController orderController = new OrderController(orderService, orderView, productService, productView, customerView, customerService);
//    //main code
    int option = 0;
    do{
      option = dashboard.menu();
      switch(option){
        case Options.PRODUCT:
          int productOption = 0;
          do{
            productOption = productView.menu();
            switch(productOption){
              case Product.ADD:{
                productController.add();
                break;
              }
              case Product.EXIT:{
                System.exit(0);
              }
              case Product.DISPLAY:{
                productController.displayAll();
                break;
              }
              case Product.REMOVE:{
                productController.remove();
                break;
              }
              case Product.UPDATE:{
                productController.update();
                break;
              }
            }
          }while(productOption != Product.BACK);
          break;
        case Options.CUSTOMER:{
          int customerOption = 0;
          do{
            customerOption = customerView.menu();
            switch(customerOption){
              case Options.Customer.ADD:{
                customerController.add();
              }
              break;
              case Options.Customer.UPDATE:
                customerController.update();
                break;
              case Customer.REMOVE:
                customerController.remove();
                break;
              case Options.Customer.EXIT:
                System.exit(0);
                break;
              case Options.Customer.DISPLAY:
                customerController.displayAll();
                break;
            }
          }while(customerOption != Options.Customer.BACK);
          break;
        }
        case Options.ORDER:{
          int orderOption = 0;
          do{
            orderOption = orderView.menu();
            switch(orderOption){
              case Order.ADD:{
                orderController.createOrder();
                break;
              }
              case Order.UPDATE:{
                orderController.updateDraftOrder();
                break;
              }
              case Order.REMOVE:{
                orderController.removeOrder();
                break;
              }
              case Order.DISPLAY:{
                orderController.displayPaidOrder();
                break;
              }
              case Order.EXIT:{
                System.exit(0);
              }
            }
          }while(orderOption != Order.BACK);
          break;
        }
      }
    }
    while(option != Options.EXIT);

  }
}

