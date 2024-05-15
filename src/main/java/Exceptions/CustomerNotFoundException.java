package Exceptions;

public class CustomerNotFoundException extends Exception{
  private static CustomerNotFoundException instance;

  private CustomerNotFoundException(String message){
    super(message);
  }

  private CustomerNotFoundException(){
  }

  public static CustomerNotFoundException getInstance_1(){
    if(instance == null) instance = new CustomerNotFoundException("Error: Customer doesn't exist");
    return instance;
  }
}
