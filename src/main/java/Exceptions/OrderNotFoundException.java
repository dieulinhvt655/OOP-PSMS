package Exceptions;

public class OrderNotFoundException extends Exception{
  private static OrderNotFoundException instance;

  private OrderNotFoundException(){
  }

  private OrderNotFoundException(String message){
    super(message);
  }

  public static OrderNotFoundException getInstance_1(){
    if(instance == null){
      instance = new OrderNotFoundException("Order doesn't exist");
    }
    return instance;
  }
}
