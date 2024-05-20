package views;

import Annotations.DisplayedField;
import Constants.Options;
import Constants.Options.Order;
import models.DTO.Anonymous;
import models.DTO.PaidOrderDTO;
import models.Product;
import views.Components.Input;
import views.Components.Table;

import java.util.stream.Collectors;

public class OrderView extends BaseView{
  public int menu(){
    System.out.println("Order Management Menu");
    System.out.println("-----------------------");
    System.out.println("1. Add order.");
    System.out.println("2. Update order.");
    System.out.println("3. Remove order.");
    System.out.println("4. Display.");
    System.out.println("5. Back.");
    System.out.println("6. Exit.");
    System.out.println("-----------------------");
    return Input.enterNumber("choose option", "invalid option", Options.Order.ADD, Order.EXIT);
  }

  public void orderForm(PaidOrderDTO order){
    start();
    Table.table(order.getProducts().stream().map(ord -> new Anonymous(){
      @DisplayedField(displayName = "Name")
      String name = ord.getName();
      @DisplayedField(displayName = "Price ($)")
      double price = ord.getPrice();
      @DisplayedField(displayName = "Quantity")
      int quantity = ord.getQuantity();
    }).collect(Collectors.toList()));
    System.out.println("Customer: " + order.getCustomerName());
    System.out.println("Total: $" + order.getTotalPrice());
    end();
  }

  public int orderFromMenu(){
    System.out.println("1. Change product info, 2. Change buyer info, 3.Back, 4. Exit");
    var option = Input.enterNumber("Choose a option", "Invalid option", 1, 4);
    return option;
  }

  public void editProductInfo(Product product, Product productInStorage){
    System.out.println("1. Change product quantity. To delete, set quantity to 0");
    var quantity = Input.enterNumber("Enter quantity", "Out of stock", 0, productInStorage.getQuantity());
    product.setQuantity(quantity);
  }
}
