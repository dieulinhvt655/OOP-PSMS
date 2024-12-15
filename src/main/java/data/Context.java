package data;

import models.Customer;
import models.Order;
import models.Product;

import java.io.*;
import java.util.ArrayList;

// Serializable được yêu cầu phỉa có với những object muốn được chuyển đổi thành bin
public class Context implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();

    public Context(){
        try{
            FileInputStream fileIn = new FileInputStream("storage/database.bin");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Context context = (Context) objectIn.readObject();
            this.customers = context.customers;
            this.products = context.products;
            this.orders = context.orders;
            objectIn.close();
            fileIn.close();
            System.out.println("load db success.");
        }catch(EOFException e){
            System.out.println("db empty");
        }catch(IOException | ClassNotFoundException e){
            System.out.println("Error: database load fail.");
        }
    }

    public void saveChange(){
        try{
            FileOutputStream fileOutputStream = new FileOutputStream("storage/database.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            fileOutputStream.close();
            objectOutputStream.close();
        }catch(FileNotFoundException e){
            System.out.println("Error: save change fail. DB not found");
        }catch(IOException e){
            System.out.println("Error: save change fail");
            System.out.println(e.getMessage());
        }
    }

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
