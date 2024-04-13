package data;

import models.Customer;
import models.Order;
import models.Product;

import java.util.ArrayList;

public class Context{

  ArrayList<Customer> customers = new ArrayList<>();
  ArrayList<Product> products = new ArrayList<>();
  ArrayList<Order> orders = new ArrayList<>();

  public Context(){

  }

  public Context(ArrayList<Customer> customers, ArrayList<Product> products, ArrayList<Order> orders){
    this.customers = customers;
    this.products = products;
    this.orders = orders;
  }

  public ArrayList<Customer> getCustomers(){
    return customers;
  }

  public void setCustomers(ArrayList<Customer> customers){
    this.customers = customers;
  }

  public ArrayList<Product> getProducts(){
    return products;
  }

  public void setProducts(ArrayList<Product> products){
    this.products = products;
  }

  public ArrayList<Order> getOrders(){
    return orders;
  }

  public void setOrders(ArrayList<Order> orders){
    this.orders = orders;
  }
}
