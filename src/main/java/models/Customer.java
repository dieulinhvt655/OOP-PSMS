package models;

import Annotations.DisplayedField;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable{
  @Serial
  private static final long serialVersionUID = 1L;
  @DisplayedField(displayName = "Full name")
  private String fullName;
  @DisplayedField(displayName = "Phone number")
  private String phoneNumber;
  private ArrayList<Order> orders = new ArrayList<>();
  private String id;

  public Customer(){
  }

  public Customer(String fullName, String phoneNumber, ArrayList<Order> orders, String id){
    this.fullName = fullName;
    this.phoneNumber = phoneNumber;
    this.orders = orders;
    this.id = id;
  }

  public String getFullName(){
    return fullName;
  }

  public void setFullName(String fullName){
    this.fullName = fullName;
  }

  public String getPhoneNumber(){
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber){
    this.phoneNumber = phoneNumber;
  }

  public ArrayList<Order> getOrders(){
    return orders;
  }

  public void setOrders(ArrayList<Order> orders){
    this.orders = orders;
  }

  public String getId(){
    return id;
  }

  public void setId(String id){
    this.id = id;
  }

  @Override
  public String toString(){
    return "Customer{" +
            "fullName='" + fullName + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", orders=" + orders +
            ", id='" + id + '\'' +
            '}';
  }
}

