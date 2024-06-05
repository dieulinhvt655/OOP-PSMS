package models;

import Annotations.DisplayedField;

import java.io.Serial;
import java.io.Serializable;
import java.util.function.Function;

public class Product implements Serializable, Cloneable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    @DisplayedField(displayName = "Name")
    private String name;
    private String description;
    @DisplayedField(displayName = "Price")//  mo ta
    private double price; // gia
    @DisplayedField(displayName = "Available")
    private int quantity; // so luong

    public Product() {
    }

    public Product(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Product(String id, String name, String description, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setQuantity(Function<Integer, Integer> set) {
        this.quantity = set.apply(this.quantity);
    }

    @Override
    public Product clone() {
        try {
            return (Product) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
