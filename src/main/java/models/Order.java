package models;

import javax.xml.crypto.Data;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable{
  @Serial
  private static final long serialVersionUID = 1L;

  private ArrayList<Product> products;
  private Data date;
  private double tax; // %

  public Order(){
  }

  public Order(ArrayList<Product> products, Data date, double tax){
    this.products = products;
    this.date = date;
    this.tax = tax;
  }

  public ArrayList<Product> getProducts(){
    return products;
  }

  public void setProducts(ArrayList<Product> products){
    this.products = products;
  }

  public Data getDate(){
    return date;
  }

  public void setDate(Data date){
    this.date = date;
  }

  public double getTax(){
    return tax;
  }

  public void setTax(double tax){
    this.tax = tax;
  }
}
