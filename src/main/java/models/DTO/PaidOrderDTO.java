package models.DTO;

import Annotations.DisplayedField;
import models.Order;
import models.Product;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;

public class PaidOrderDTO{
  private int Id;
  private String customerId;
  private ArrayList<Product> products;
  @DisplayedField(displayName = "Customer name")
  private String customerName;
  @DisplayedField(displayName = "Total")
  private double totalPrice;
  @DisplayedField(displayName = "Date")
  private Date date;

  public PaidOrderDTO(){
  }

  public PaidOrderDTO(Order order, String customerName){
    this.Id = order.getId();
    this.customerId = order.getCustomerId();
    this.customerName = customerName;
    this.date = order.getDate();
    this.products = order.getProducts();
    this.totalPrice = products.stream().map(product -> product.getPrice()*product.getQuantity()).reduce(0.0, Double::sum);
  }

  public PaidOrderDTO(int id, String customerId, ArrayList<Product> products, String customerName, Double totalPrice, Date date){
    Id = id;
    this.customerId = customerId;
    this.products = products;
    this.customerName = customerName;
    this.totalPrice = totalPrice;
    this.date = date;
  }

  public int getId(){
    return Id;
  }

  public void setId(int id){
    Id = id;
  }

  public String getCustomerId(){
    return customerId;
  }

  public void setCustomerId(String customerId){
    this.customerId = customerId;
  }

  public ArrayList<Product> getProducts(){
    return products;
  }

  public void setProducts(ArrayList<Product> products){
    this.products = products;
  }

  public String getCustomerName(){
    return customerName;
  }

  public void setCustomerName(String customerName){
    this.customerName = customerName;
  }

  public Double getTotalPrice(){
    return totalPrice;
  }

  public void setTotalPrice(Double totalPrice){
    this.totalPrice = totalPrice;
  }

  public Date getDate(){
    return date;
  }

  public void setDate(Date date){
    this.date = date;
  }
}
