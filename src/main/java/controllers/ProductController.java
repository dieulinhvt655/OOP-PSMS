package controllers;

import Exceptions.DuplicatedCustomerException;
import models.Product;
import services.ProductService;
import views.Components.Input;
import views.Components.Table;
import views.ProductView;

import java.io.Serializable;
import java.util.NoSuchElementException;

public class ProductController implements Serializable{
  ProductView productView;
  ProductService productService;

  public ProductController(ProductView productView, ProductService productService){
    this.productView = productView;
    this.productService = productService;
  }

  public void add(){
    productView.start();
    Product product = new Product();
    productView.enterInformation(product);
    try{
      productService.add(product);
      System.out.println("Add successfully");
    }catch(DuplicatedCustomerException exception){
      System.out.println(exception.getMessage());
    }
    productView.end();
  }

  public void update(){
    productView.start();
    displayAll();
    var products = productService.getAllProduct();
    int option;
    var productIndex = Input.enterNumber("Choose a product", "Product not found", 1, products.size());
    var productInStore = productService.getAllProduct().get(productIndex - 1);
    System.out.println("-- Update product: " + productInStore.getName() + " --");
    do{
      try{
        var productUpdated = productView.updateProductForm();
        productUpdated.setId(productInStore.getId());
        productService.update(productUpdated);
        System.out.println("Update successfully");
      }catch(NoSuchElementException | DuplicatedCustomerException e){
        System.out.println((e.getMessage()));
      }
      System.out.println();
      option = Input.enterNumber("Do you want to continue update " + productInStore.getName() + "?(1. Yes, 2. No) ", "Invalid", 1, 2);
    }while(option != 2);
    productView.end();
  }

  public void displayAll(){
    Table.table(productService.getAllProduct());
  }

  public void remove(){
    productView.start();
    displayAll();
    var products = productService.getAllProduct();
    var id = Input.enterNumber("Choose a product", "Product not found", 1, products.size());
    var option = Input.enterNumber("Are you sure to remove this product(1. Yes, 2. No)?", "Invalid. Choose again", 1, 2);
    if(option == 1){
      productService.remove(products.get(id - 1).getId());
      System.out.println("Product was removed! ");
    }else{
      System.out.println("Aborted removal");
    }
    productView.end();
  }
}
