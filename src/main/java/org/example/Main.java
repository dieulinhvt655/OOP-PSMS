package org.example;

import Constants.Options;
import Constants.Options.Customer;
import Constants.Options.Product;
import controllers.CustomerController;
import controllers.OrderController;
import controllers.ProductController;
import data.Context;
import services.CustomerService;
import services.OrderService;
import services.ProductService;
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
    //main code
    int option = 0;
    do{
      option = dashboard.menu();
      switch(option){
        case Options.PRODUCT:
          int productOption = 0;
          do{
            productOption = productView.menu();
            switch(productOption){
              case Product.ADD :{
                productController.add();
              }
            }
          }while(false);
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
        case Options.ORDER:
          break;
      }
    }
    while(option != Options.EXIT);
  }
}

