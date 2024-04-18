package org.example;

import Constants.Options;
import Constants.Options.Customer;
import controllers.CustomerController;
import data.Context;
import services.*;
import views.CustomerView;
import views.Dashboard;

public class Main{
  public static void main(String[] args){
    //context
    Context context = new Context();


    //view
    Dashboard dashboard = new Dashboard();
    CustomerView customerView = new CustomerView();
    //services
    CustomerService customerService = new CustomerService(context);
    OrderService orderService = new OrderService(context);
    //controller
    CustomerController customerController = new CustomerController(customerView, customerService);
    //main code

    int option = 0;
    do{
      option = dashboard.menu();
      switch(option){
        case Options.PRODUCT:
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

