package views;

import Constants.Options;
import models.Product;
import views.Components.Input;

public class ProductView extends BaseView{
  public int menu() {
    System.out.println("Product Management Menu");
    System.out.println("-----------------------");
    System.out.println("1. Add product.");
    System.out.println("2. Update product.");
    System.out.println("3. Remove productr.");
    System.out.println("4. Display.");
    System.out.println("5. Back.");
    System.out.println("6. Exit.");
    System.out.println("-----------------------");

    return Input.enterNumber("choose option", "invalid option", Options.Product.ADD, Options.Product.EXIT);

  }
  public void enterInformation(Product product){
    String name =Input.enterAString("Enter product's name: ");
    String description=Input.enterAString("Enter product's description: ");
    double price=Input.enterDNumber("Enter product's price ($) (>0 || <10000","ERROR",0.0,10000.0);
    int quantity=Input.enterNumber("Enter product's quantity( >0|| <1000)","ERROR",0,1000);
    product.setName(name);
    product.setDescription(description);
    product.setPrice(price);
    product.setQuantity(quantity);
  }
  public String enterID(){
    return Input.enterAString("Enter product's ID: ");

  }
  public Product updateProductForm(){
    Product product =new Product();
    System.out.println("---------------------------");
    System.out.println("1, update name ");
    System.out.println("2, update description");
    System.out.println("3, update price");
    System.out.println("4, update quantity ");
    System.out.println("5, EXIT ");
    System.out.println("---------------------------");
    int option=Input.enterNumber("choose option","invalid option", Options.Update.NAMEP, Options.Update.EXITP);
    switch (option){
      case Options.Update.NAMEP :
        String name =Input.enterAString("Enter product's name: ");
        product.setName(name);
        break;
      case Options.Update.DESCRIPTION:
        String description =Input.enterAString("Enter product's desciption:");
        product.setDescription(description);
        break;
      case Options.Update.PRICE:
        double price=Input.enterDNumber("Enter product's price ($) (>0 || <10000","ERROR",0.0,10000.0);
        product.setPrice(price);
        break;
      case Options.Update.QUANTITY:
        int quantity=Input.enterNumber("Enter product's quantity( >0|| <1000)","ERROR",0,1000);
        break;
      case Options.Update.EXITP:
        break;
    }
    return product;

  }
}
