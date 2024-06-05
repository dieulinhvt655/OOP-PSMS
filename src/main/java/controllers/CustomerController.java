package controllers;
// congtroller là cầu nối để tương tác giữa giao diện người dùng, xử lý dữ liệu và xử lý các yêu cầu
import Exceptions.DuplicatedCustomerException;
import models.Customer;
import services.CustomerService;
import views.Components.Input;
import views.Components.Table;
import views.CustomerView;

import java.util.NoSuchElementException;

public class CustomerController{
  // dependency
  CustomerView customerView;
  CustomerService customerService;
// constructor để khởi tạo đối tượng
  public CustomerController(CustomerView customerView, CustomerService customerService){
    this.customerView = customerView;
    this.customerService = customerService;
  }

  public void add(){
    customerView.start();
   // Customer() này là một constructor. constructor này được gọi khi một đối tượng mới của lớp Customer được tạo
    Customer customer = new Customer(); // tạo ra một đối tượng mới của lớp Customer
    customerView.enterInformation(customer); // enterInfor(customer) là một phương thức có tham số truyền vào

    // dùng để xử lý trường hợp ngoại lệ nếu khách hàng thêm vào đã tồn tại
    try{
      customerService.add(customer);
      System.out.println("Add successfully");
    }catch(DuplicatedCustomerException exception){
      System.out.println(exception.getMessage());
    }
    customerView.end();
  }

  public void update(){
    customerView.start();
// phương thức enterPhoneNumber trên đối tượng customerView dùng để nhập số điện thoại khách hàng
    // hasCustomerPhoneNumber là một method của object customerService dùng để kiểm tra xem sđt tồn tại chưa
    String phone = customerView.enterPhoneNumber(customerService::hasCustomerPhoneNumber);
    //....
    try{
      customerService.update(customerView.updateCustomerForm(), phone);
      System.out.println("Update successfully.");
    }catch(NoSuchElementException | DuplicatedCustomerException e){
      System.out.println(e.getMessage());
    }
    customerView.end();
  }

  public void displayAll(){
    try {
      // xử lý ngoại lệ nếu danh sách trống
      Table.table(customerService.getAllCustomer()); // lấy ra tất cả in4 khách hàng tồn tại
    } catch (NoSuchElementException exception) {
      System.out.println(exception.getMessage());
    }
  }

  public void remove(){
    customerView.start();
    // gọi method enterPhoneNumber của đối tượng customerView, truyền vào một biểu thức
    // biểu thức lamda nhận 1 tham số phone2 và gọi hàm để kiểm tra xem phone2 có tồn tại hay chưa
    String phone = customerView.enterPhoneNumber((phone2) -> customerService.hasCustomerPhoneNumber(phone2));
    // gọi method enterNumber của class Input yêu cầu người dùng nhập lựa chọn
    var option = Input.enterNumber("Are you sure to remove this customer(1. Yes, 2. No)?", "Invalid. Choose again", 1, 2);
   // nếu option khác 2 thì gọi đến method remove của đối tượng customerService để xoá phone nhập vào
    if(option != 2){
      customerService.remove(phone);
      System.out.println("Customer was removed.");
      return;
    }
    System.out.println("Removal aborted");
  }
}
