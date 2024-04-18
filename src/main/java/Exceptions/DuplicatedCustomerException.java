package Exceptions;

public class DuplicatedCustomerException extends Exception {
  public DuplicatedCustomerException(String message){
    super(message);
  }
}
