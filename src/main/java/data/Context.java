package data;

import models.Customer;
import models.Order;
import models.Product;

import java.io.*;
import java.util.ArrayList;

// Serializable được yêu cầu phải có với những object muốn được chuyển đổi thành bin
public class Context implements Serializable{
  @Serial
  private static final long serialVersionUID = 1L;
  private ArrayList<Customer> customers = new ArrayList<>();
  private ArrayList<Product> products = new ArrayList<>();
  private ArrayList<Order> orders = new ArrayList<>();

  public Context(){
    try{
      FileInputStream fileIn = new FileInputStream("storage/database.bin"); // tạo đt để mở tập "" để đọc dữ liệu từ đó
      ObjectInputStream objectIn = new ObjectInputStream(fileIn); // tạo đt để đọc các đt từ fileinput
      Context context = (Context) objectIn.readObject(); // giả định đtuong đc lưu trong tập tin là một đối tượng của lớp Context
      this.customers = context.customers; // sao chép dữ liệu
      this.products = context.products;
      this.orders = context.orders;
      objectIn.close(); // đóng "objectInputStream" và " FileInputStream" để giải phóng tài nguyên
      fileIn.close(); // ~~~~~~~
      System.out.println("load db success.");
    }
    //tập tin kh có lỗi gì
    catch(EOFException e){
      System.err.println("database empty");
    }
    // tập tin trống
    catch(IOException | ClassNotFoundException e){
      System.err.println("Error: database load fail.");
    }
  }
// lưu trữ thay đổi
  public void saveChange(){
    try{
      // tạo một đối tượng FileOutputStream để mở tập tin "storage/database.bin" để ghi dữ liệu vào đó.
      FileOutputStream fileOutputStream = new FileOutputStream("storage/database.bin");
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
      objectOutputStream.writeObject(this); // ghi đối tượng hiện tại vào vào bin
      fileOutputStream.close(); // đóng để ghi và giải phóng tài nguyên
      objectOutputStream.close();
    } catch(FileNotFoundException e){
      System.err.println("Error: save change fail. Database not found");
    }catch(IOException e){
      System.err.println("Error: save change fail");
      System.out.println(e.getMessage());
    }
  }
//
  public ArrayList<Customer> getCustomers(){
    return customers;
  }

  public ArrayList<Product> getProducts(){
    return products;
  }

  public ArrayList<Order> getOrders(){
    return orders;
  }
}
