package org.example;

import Constants.Options;
import controllers.CustomerController;
import data.Context;
import services.CustomerService;
import services.OrderService;
import views.CustomerView;
import views.Dashboard;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
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
              case Options.ADD:{
                customerController.addCustomer();
              }
              break;
              case Options.UPDATE:
                break;
              case Options.EXIT_CHILD_MENU:
                System.exit(0);
                break;

            }
          }while(customerOption != Options.BACK);
          break;
        }
        case Options.ORDER:
          break;
      }
    }
    while(option != Options.EXIT);
  }
}

class Option{
  public int option;

  public Option(){
    option = 0;
  }
}
