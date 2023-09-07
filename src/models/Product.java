package models;

public class Product {
    private String uid;
    private ProductType type;
    private String subtype;
    private String brand;
    private double price;
    private int quantity;

    public Product(String uid, ProductType type, String subtype, String brand, double price, int quantity) {
        this.uid = uid;
        this.type = type;
        this.subtype = subtype;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
    }
    public String getTotalPrice() {
        double result = this.quantity * this.price;
        return String.format("%.2f лв.", result);
    }
    public void increaseQuantity() {
        this.quantity = this.quantity + 1;
    }
    public void decreaseQuantity() {
        this.quantity = this.quantity - 1;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
