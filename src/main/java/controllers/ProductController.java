package controllers;

import Exceptions.DuplicatedCustomerException;
import models.Product;
import services.ProductService;
import views.Components.Input;
import views.Components.Table;
import views.ProductView;

import java.io.Serializable;
import java.util.NoSuchElementException;

public class ProductController implements Serializable{
  // dependency
  ProductView productView;
  ProductService productService;

  // controller để khởi tạo ra các đối tượng
  public ProductController(ProductView productView, ProductService productService){
    this.productView = productView;
    this.productService = productService;
  }

  public void add(){
    productView.start();
    Product product = new Product(); // tạo ra một đối tượng mới của lớp Product
    productView.enterInformation(product); // gọi method enterInformation của productView để truyền vào một đối tượng product
    // xử lý ngoại lệ nếu sản phẩm đã tồn tại
    try{
      productService.add(product); // gọi method add của productService để thêm product mới
      System.out.println("Add successfully");
    }catch(DuplicatedCustomerException exception){
      System.out.println(exception.getMessage());
    }
    productView.end();
  }

  public void update(){
    productView.start();
    displayAll();
    var products = productService.getAllProduct();// hiển thị ra danh sách sản phẩm
    int option;
    // gọi method enterNumber của lớp Input yêu cầu nhập vào 1 lựa chọn
    var productIndex = Input.enterNumber("Choose a product", "Product not found", 1, products.size());
    // trong mảng các chỉ số bắt đầu bằng 0 nên khi muốn lấy ra sản phẩm phải lấy index nhập vào - 1 đv
    var productInStore = productService.getAllProduct().get(productIndex - 1);
    System.out.println("-- Update product: " + productInStore.getName() + " --");
    // vòng lặp bắt người dùng nhập đến khi nào đúng thì thôi =))))
    do{
      // xử lý ngoại lệ nếu không có sản phẩm trong danh sách hoặc trùng
      try{
        // method updateProductForm được dùng để cập nhật thông tin sp dựa trên dữ liệu người dùng nhập và trả về một đối tượng Product đc cập nhật
        var productUpdated = productView.updateProductForm();
        // gán lại ID cho sản phẩm vừa cập nhật để đảm bảo trùng với ID sản phẩm cũ
        productUpdated.setId(productInStore.getId());
        productService.update(productUpdated);
        System.out.println("Update successfully");
      }catch(NoSuchElementException | DuplicatedCustomerException e){
        System.out.println((e.getMessage()));
      }
      System.out.println();
      // hỏi xem người dùng có muốn tiếp tục cập nhật gì nữa không
      option = Input.enterNumber("Do you want to continue update " + productInStore.getName() + "?(1. Yes, 2. No) ", "Invalid", 1, 2);
    }while(option != 2);
    productView.end();
  }

  public void displayAll(){
    // lấy ra và hiển thị tất cả các sp có
    // bắt ngoại lệ nếu không có sản phẩm
    try {
      Table.table(productService.getAllProduct());
    }
    catch (NoSuchElementException exception) {
      System.out.println("Error: " + exception.getMessage());
    }
  }

  public void remove(){
    productView.start();
    displayAll(); // hiển thị all sp thông qua gọi phương thức getAllProduct
    var products = productService.getAllProduct();
    var id = Input.enterNumber("Choose a product", "Product not found", 1, products.size());
    var option = Input.enterNumber("Are you sure to remove this product(1. Yes, 2. No)?", "Invalid. Choose again", 1, 2);
    if(option == 1){
      productService.remove(products.get(id - 1).getId());
      System.out.println("Product was removed! ");
    }else{
      System.out.println("Aborted removal");
    }
    productView.end();
  }
}
