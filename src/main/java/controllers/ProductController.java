package controllers;

import Exceptions.DuplicatedCustomerException;
import models.Product;
import services.ProductService;
import views.Components.Table;
import views.ProductView;

import java.io.Serializable;
import java.util.NoSuchElementException;

public class ProductController implements Serializable {
    ProductView productView;
    ProductService productService;
    public ProductController(ProductView productView,ProductService productService){
        this.productView=productView;
        this.productService=productService;
    }
    public void add(){
        productView.start();
        Product product= new Product();
        productView.enterInformation(product);
        try {
            productService.add(product);
            System.out.println("Add successfully");

        }catch (DuplicatedCustomerException exception){
            System.err.println(exception.getMessage());

        }
        productView.end();
    }
    public void update(){
        productView.start();
        String id =productView.enterID();
        if(productService.hasProduct(id)){
            try{
                productService.update(productView.updateProductForm(),id);
            }catch (NoSuchElementException | DuplicatedCustomerException e){
                System.err.println((e.getMessage()));
            }
        }else {
            System.out.println("Error: Product not found !");
        }
        productView.end();
    }
    public void displayAll(){
        Table.table(productService.getAllProduct());
    }
    public void remove(){
        productView.start();
        String id =productView.enterID();
        if(productService.hasProduct(id)){
            productService.remove(id);
            System.out.println("Product was removed! ");
        }else{
            System.out.println("Error: can't found product!");
        }
    }
}
