package models;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class Order implements Serializable{
  @Serial
  private static final long serialVersionUID = 1L;
  private int Id;
  private ArrayList<Product> products;
  private Date date;
  private double tax; // %
  private String customerId;
  private boolean isPaid = false;

  public Order(){
    products = new ArrayList<>();
  }

  public Order(int Id, ArrayList<Product> products, Date date, double tax, String customerId, boolean isPaid){
    this.products = products;
    this.date = date;
    this.tax = tax;
    this.customerId = customerId;
    this.isPaid = isPaid;
    this.Id = Id;
  }

  public int getId(){
    return Id;
  }

  public void setId(int id){
    Id = id;
  }

  public boolean isPaid(){
    return isPaid;
  }

  public void setPaid(boolean paid){
    if(paid){
      setDate(new Date());
    }
    isPaid = paid;
  }

  public String getCustomerId(){
    return customerId;
  }

  public void setCustomerId(String customerId){
    this.customerId = customerId;
  }

  public ArrayList<Product> getProducts(){
    products.removeIf(product -> product.getQuantity() == 0);
    return this.products;
  }

  public void setProducts(ArrayList<Product> products){
    this.products = products;
  }

  public Date getDate(){
    return date;
  }

  public void setDate(Date date){
    this.date = date;
  }

  public double getTax(){
    return tax;
  }

  public void setTax(double tax){
    this.tax = tax;
  }

  public void addProducts(ArrayList<Product> products){
    this.products.addAll(products);
  }
}
