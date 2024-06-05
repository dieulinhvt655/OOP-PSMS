package controllers;

import Constants.Options.Order.Update;
import Exceptions.CustomerNotFoundException;
import Exceptions.OrderNotFoundException;
import data.Context;
import models.DTO.PaidOrderDTO;
import models.Order;
import models.Product;
import services.CustomerService;
import services.OrderService;
import services.ProductService;
import views.Components.Input;
import views.Components.Table;
import views.CustomerView;
import views.OrderView;
import views.ProductView;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderController {
    // dependency
    public final OrderService orderService;
    private final OrderView orderView;
    private final ProductService productService;
    private final ProductView productView;
    private final CustomerView customerView;
    private final CustomerService customerService;

    // constructor khởi tạo các đối tượng
    public OrderController(OrderService orderService, OrderView orderView, ProductService productService, ProductView productView, CustomerView customerView, CustomerService customerService) {
        this.orderService = orderService;
        this.orderView = orderView;
        this.productService = productService;
        this.productView = productView;
        this.customerView = customerView;
        this.customerService = customerService;
    }

    // dùng để lựa chọn 1 thằng sản phẩm từ trong kho
    private ArrayList<Product> chooseProduct(ArrayList<Product> productStorage) {
        var products = new ArrayList<Product>();
        // xử lý ngoại lệ nếu không có sp trong kho
        try {
            Table.table(productStorage);
            int option;
            do {
                var productIndex = Input.enterNumber("Choose product number", "Can't find product in the list", 1, productStorage.size()) - 1;
                //.clone(); sao chép các thuộc tính của instance hiện tại trả ra một instance khác với thuộc tính giống instance hiện tại
                var product = productStorage.get(productIndex).clone();
                var productStore = productService.getProduct(product.getId());
                // hiển thị thông tin chi tiết của sp và cho phép chỉnh sửa nếu cần, sau khi chỉnh sửa sp sẽ đc thêm vào danh sách products
                orderView.editProductInfo(product, productStore);
                var newProduct = new ArrayList<Product>(); // khởi tạo ra một product mới
                newProduct.add(product);
                // nếu newProduct trùng với products thì merge
                mergeTwoProductList(products, newProduct);
                System.out.println("1, Keep going to choose\n2.Complete");
                option = Input.enterNumber("Enter option", "Invalid", 1, 2);
            } while (option != 2);
            return products;
        } catch (NoSuchElementException e) {
            // không muốn in ra trực tiếp, khi nào chooseProduct sd mới in ra thì dùng throw
            throw new NoSuchElementException("Error: Has no product available");
        }
    }


    private void buy(Order order) {
        System.out.println("1. Buy\n2. Save order to buy later");
        int option = Input.enterNumber("Enter option", "Invalid", 1, 2);
        if (option == 1) {

            try {
                orderService.buy(order);
                System.out.println("Complete");
                return;
            } catch (CustomerNotFoundException e) { // xử lý ngoại lệ không tìm thấy khách hàng
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Order saved");
    }

    // tạo ra đơn hàng
    public void createOrder() {
        var order = new Order(); // khởi tạo ra thằng đơn hàng mới
        // Storage: kho
        var productStorage = productService.getAllProduct();
        try {
            // gọi pthuc chooseProduct ra để chọn danh sách từ trong kho
            List<Product> products = chooseProduct(productStorage);
            // merge sp cũ với sp mới nhập vào
            mergeTwoProductList(order.getProducts(), products);
            var customerPhone = customerView.enterPhoneNumber(customerService::hasCustomerPhoneNumber);
            var customerId = customerService.getCustomerId(customerPhone); // lấy id của khách hàng thông qua nhập sđt
            order.setCustomerId(customerId); // thiết lập id của khách hàng cho đơn hàng
            orderService.createOrder(order); // tạo ra đơn hàng
            buy(order); // xác nhận đơn hàng đã mua
        } catch (CustomerNotFoundException | NoSuchElementException e) {
            System.out.println(e.getMessage()); // xử lý ngoại lệ không tìm thấy khách hàng hoặc không có phẩn tử (sp) nào trong mảng
        }
    }

    // hiển thị đơn hàng đã mua
    public void displayPaidOrder() {
        // nhập sđt và ktra sđt có tồn tại hay chưa
        var customerPhone = customerView.enterPhoneNumber(customerService::hasCustomerPhoneNumber);
        // lấy in4 khách thông qua sđt
        var customerName = customerService.getCustomer(customerPhone).getFullName();
        try {
            // lấy danh sách đơn đặt hàng dựa trên sđt của khách hàng
            // stream dùng để chuyển danh sách các đơn đặt hàng thành chuỗi dữ liệu
            var orders = orderService.getOrders(customerPhone).stream()
                    .filter(Order::isPaid) // sd phương thức filter để lọc ra các order đã thanh toán
                    // map để chuyển đổi mỗi đơn hàng thành 1 đối tượng
                    //
                    .map(order -> new PaidOrderDTO(order, customerName))
                    .collect(Collectors.toList()); // collect dùng để tạo thành 1 danh sách đơn hàng mới từ các đối tượng
            // DTO là Data Transfer Object dùng để chuyển đổi dữ liệu mà không làm thay đổi dữ liệu gốc
            Table.table(orders, "paid orders");

            var orderIndex = Input.enterNumber("Choose an order to see detail", "Order not found", 1, orders.size());
            // current hiện tại đang nói chính xác/ cụ thể về 1 cái gì đó hiện hữu
            var currentOrder = orders.get(orderIndex - 1);
            orderView.orderForm(currentOrder);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage()); // xử lý ngoại lệ không tìm thấy sp
        }
    }

    public void updateDraftOrder() {
        var customerPhone = customerView.enterPhoneNumber(customerService::hasCustomerPhoneNumber);
        var customerName = customerService.getCustomer(customerPhone).getFullName();
        try {
            // lấy ra đơn hàng chưa thanh toán thông qua nhập số điện thoại người dùng
            var orders = orderService.getIsNotPaidOrders(customerPhone);
            System.out.println("Your all orders have not paid yet below: ");
            Table.table(orders.stream() // hiển thị danh sách đơn hàng chưa thanh toán
                    .map(order -> new PaidOrderDTO(order, customerName)) // map để chuyển đổi đơn hàng thành 1 đối tượng
                    .collect(Collectors.toList())); // collect dùng để tạo thành 1 danh sách đơn hàng mới
            var orderIndex = Input.enterNumber("Choose an order", "Order not found", 1, orders.size());
            var currentOrder = orders.get(orderIndex - 1);

            // new PaidOrderDTO là tạo ra một đối tượng mới với thông tin currentOrder và
            //currentOrder là đơn đặt hàng hiện tại
            // customerName là tên của khách hàng
            orderView.orderForm(new PaidOrderDTO(currentOrder, customerName));
            var option = orderView.orderFromMenu();
            switch (option) {
                case Update.CHOOSE_NEW_PRODUCT: // chọn thêm một product vào order
                    var productStorage = productService.getAllProduct(); // lấy tất cả product cũ gán vào productStorage
                    // merger 2 thằng product đang có trong order với thằng product mới chọn thêm vào order
                    mergeTwoProductList(currentOrder.getProducts(), chooseProduct(productStorage));
                    orderService.update(currentOrder); // cập nhật thông tin thằng order hiện tại
                    buy(currentOrder); // chọn mua hoặc không
                    break;
                case Update.CHANGE_PRODUCT_INFO: // thay đổi thông tin của product có trong order
                {
                    var productIndex = Input.enterNumber("Choose product", "Product not found", 1, currentOrder.getProducts().size());
                    var product = currentOrder.getProducts().get(productIndex - 1);
                    // lấy ra id của thằng product để xác định product đó cần lấy thông tin
                    var productStore = productService.getProduct(product.getId());
                    //hiển thị thông tin của sản phẩm trong cửa hàng sử dụng phương thức table và
                    Table.table(productService.getAllProduct().stream()
                                    // danh sách sản phẩm được lọc dựa trên ID của sản phẩm đã chọn
                                    .filter(product1 -> product1.getId().equals(product.getId()))
                                    .collect(Collectors.toList()) // collect dùng để tạo thành 1 danh sách đơn hàng mới
                            , "Product information in store: ");
                    // chỉnh sửa thông tin sản phẩm thông qua phương thức editProductInfo
                    orderView.editProductInfo(product, productStore);
                    // cập nhật order hiện tại
                    orderService.update(currentOrder);
                    // chọn mua hoặc không
                    buy(currentOrder);
                    break;
                }
                case Update.BUY:
                    // chọn mua hoặc không
                    buy(currentOrder);
                    break;
            }
//      new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (OrderNotFoundException | NoSuchElementException e) {
            System.out.println(e.getMessage()); // xử lý ngoại lệ không tìm thấy đơn hàng hoặc đơn hàng trống
        }
    }

    // hàm để kiểm tra xem Product List gốc đã tồn tại 1 product nào đó trong list product mới thì sẽ cộng dồn quantity lại, nếu không
    // add product vào list cũ
    private void mergeTwoProductList(List<Product> originList, List<Product> newList) {
        if (originList.isEmpty()) {
            originList.addAll(newList);
            return;
        }
        for (Product product_newList : newList) {
            if (originList.stream().anyMatch(product -> product.getId().equals(product_newList.getId()))) {
                originList.stream()
                        .filter(product -> product.getId().equals(product_newList.getId()))
                        .findFirst()
                        .get()
                        .setQuantity(quantity -> quantity + product_newList.getQuantity());
            } else {
                originList.add(product_newList);
            }
        }
    }

    public void removeOrder() {
        orderView.start();
        // chỉ remove đơn hàng chưa được thanh toán
        String phone = customerView.enterPhoneNumber(phone1 -> customerService.hasCustomerPhoneNumber(phone1));
        String customerName = customerService.getCustomer(phone).getFullName();
        List<Order> orders = orderService.getIsNotPaidOrders(phone);
        try {
            Table.table(orders.stream().map(order -> new PaidOrderDTO(order, customerName)).toList());
            int orderIndex = Input.enterNumber("Choose an order", "Order not found", 1, orders.size());
            int option = Input.enterNumber("Are you sure to remove this order? (1. Yes/ 2. No)", "Invalid. Choose again:", 1, 2);
            if (option == 1) {
                orderService.remove(orders.get(orderIndex - 1));
                System.out.println("Order was removed!");
            } else {
                System.out.println("Removal aborted!");
            }
        } catch (NoSuchElementException exception) { // xử lý ngoại lệ không có đơn hàng chưa thanh toán
            System.out.println("Error:" + exception.getMessage());
        }
    }


    public static void main(String[] args) {
        var instance = new OrderController(new OrderService(new Context(), new CustomerService(new Context())), new OrderView(), new ProductService(new Context()), new ProductView(), new CustomerView(), new CustomerService(new Context()));
        var list1 = new ArrayList<Product>();
        var list2 = new ArrayList<Product>();
        list1.add(new Product("1", "abc", "", 2, 10));
        list1.add(new Product("2", "bcd", "", 8, 14));
        list1.add(new Product("3", "cdf", "", 4, 13));
        list1.add(new Product("4", "des", "", 6, 19));
        list1.add(new Product("7", "ebs", "", 1, 12));
        list1.add(new Product("8", "fwa", "", 9, 11));
        list1.add(new Product("5", "gad", "", 5, 17));
        list1.add(new Product("6", "has", "", 7, 18));

//    List<Product> stream1 = list1.stream().filter(product -> product.getQuantity() > 13 && product.getQuantity() < 19).sorted((p1, p2) -> p2.getPrice() > p1.getPrice()?-1:1).collect(Collectors.toList());
//
//    List<Product> stream2 = list1.stream().filter(product -> product.getName().contains("b")).toList();
//
//    list1.removeIf(product -> product.getPrice() >= 2 && product.getPrice() <= 6 || product.getQuantity() <13);

//    instance.removeOrder();
//    System.out.println();

    }
}
