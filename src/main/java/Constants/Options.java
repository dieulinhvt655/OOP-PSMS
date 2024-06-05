package Constants;

public class Options{
  public static final int PRODUCT = 1;
  public static final int CUSTOMER = 2;
  public static final int ORDER = 3;
  public static final int EXIT = 4;

  public static class Customer{
    public static final int ADD = 1;
    public static final int UPDATE = 2;
    public static final int REMOVE = 3;
    public static final int EXIT = 6;
    public static final int BACK = 5;
    public static final int DISPLAY = 4;
  }

  public static class Order{
    public static final int ADD = 1;
    public static final int UPDATE = 2;
    public static final int REMOVE = 3;
    public static final int EXIT = 6;
    public static final int BACK = 5;
    public static final int DISPLAY = 4;

    public static class Update{
      public static final int CHANGE_PRODUCT_INFO = 2;
      public static final int CHOOSE_NEW_PRODUCT = 1;
      public static final int BACK = 4;
      public static final int EXIT = 5;
      public static final int BUY = 3;
    }
  }
  public static class Product{
    public static final int ADD = 1;
    public static final int UPDATE = 2;
    public static final int REMOVE = 3;
    public static final int EXIT = 6;
    public static final int BACK = 5;
    public static final int DISPLAY = 4;
  }
  public static class Update{
    public static final int NAME = 2;
    public static final int PHONE = 1;
    public static final int BOTH = 3;
    public static final int EXIT = 4;
    public static final int NAMEP = 1;
    public static final int DESCRIPTION = 2;
    public static final int PRICE = 3;
    public static final int QUANTITY = 4;
    public static final int EXITP = 5;
  }
//  public static class OrderUpdate{
//    public static int CHANGE_PRODUCT_INFO = 1;
//    public static int CHANGE_CUSTOMER_INFO = 2;
//    public static int BACK = 3;
//    public static int EXIT = 4;
//
//
//  }
}
