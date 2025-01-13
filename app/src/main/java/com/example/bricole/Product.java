package com.example.bricole;

import java.util.List;

public class Product {
    private String id, name, description, product_user, statute, created_at, updated_at;
    private double price;
    private int quantity;
    //The First Constructor without update_at => for the first time create
    public Product(String id, String product_user, String name, String description, double price, int quantity, String statute, List<String> product_images,String created_at) {
        this.id = id;
        this.product_user = product_user;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.statute = statute;
        this.product_images = product_images;
        this.created_at = created_at;
    }
    public Product(String id, String product_user, String name, String description, double price, int quantity) {
        this.id = id;
        this.product_user = product_user;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
    public List<String> getProduct_images() {
        return product_images;
    }

    public void setProduct_images(List<String> product_images) {
        this.product_images = product_images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProduct_user() {
        return product_user;
    }

    public void setProduct_user(String product_user) {
        this.product_user = product_user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatute() {
        return statute;
    }

    public void setStatute(String statute) {
        this.statute = statute;
    }

    private List<String> product_images;


}
