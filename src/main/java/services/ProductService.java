package services;

import Exceptions.DuplicatedCustomerException;
import data.Context;
import models.Product;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;

public class ProductService{
  Context context;

  public ProductService(Context context){

    this.context = context;
  }

  public void add(Product product) throws DuplicatedCustomerException{
    if(hasProduct(product.getId())){
      throw new DuplicatedCustomerException("Error: ID is existed");
    }
    product.setId(UUID.randomUUID().toString());
    context.getProducts().add(product);
    context.saveChange();
  }

  public void update(Product product) throws NoSuchElementException, DuplicatedCustomerException{
    if(!hasProduct(product.getId())){
      throw new NoSuchElementException("Error: Product is not found. ");
    }
    try{
      var term = context.getProducts().stream()
              .filter(product1 -> product1.getId().equals(product.getId()))
              .findFirst().get();
      if(product.getDescription() != null){
        term.setDescription(product.getDescription());
      }
      if(product.getDescription() != null){
        term.setDescription(product.getDescription());
      }
      if(product.getName() != null){
        term.setName(product.getName());
      }
      if(product.getPrice() != 0){
        term.setPrice((product.getPrice()));
      }
      if(product.getQuantity() != 0){
        term.setQuantity(product.getQuantity());
      }
      context.saveChange();
    }catch(NoSuchElementException e){
      throw e;
    }
  }

  public boolean hasIDExitedExcepItsOwner(String id, String oldD){
    return context.getProducts().stream().filter(product -> !product.getId().equals(oldD)).anyMatch(product -> product.getId().equals(id));
  }

  public boolean hasProduct(String id){
    return context.getProducts().stream().anyMatch(product -> product.getId().equals(id));
  }

  public ArrayList<Product> getAllProduct(){
    return context.getProducts();
  }

  public void remove(String id){
    context.getProducts().removeIf(product -> product.getId().equals(id));
    context.saveChange();
  }

  public Product getProduct(String id){
    return context.getProducts().stream()
            .filter(product -> id.equals(product.getId()))
            .findFirst()
            .get();
  }
}
