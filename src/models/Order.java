package models;

import java.util.ArrayList;

public class Order {
    private String uid;
    private int tableNumber;
    private ArrayList<Product> products;
    private User waitress;
    private int percentDiscount;

    public Order(String uid, int tableNumber, User waitress) {
        this.uid = uid;
        this.tableNumber = tableNumber;
        this.products = new ArrayList<>();
        this.waitress = waitress;
    }

    public String getTotalPrice(boolean withDiscount){
        double sum = 0;
        for(Product product : products){
            sum += (product.getPrice() * product.getQuantity());
        }
        if(withDiscount){
            double discount = sum * (percentDiscount / 100);
            sum = sum - discount;
        }
        return String.format("%.2f лв.", sum);
    }
    public String getProductsCount(){
        int count = 0;
        for(Product product : products){
            count += product.getQuantity();
        }
        return Integer.toString(count);

    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public User getWaitress() {
        return waitress;
    }

    public void setWaitress(User waitress) {
        this.waitress = waitress;
    }

    public int getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(int percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "uid='" + uid + '\'' +
                ", tableNumber=" + tableNumber +
                ", products=" + products +
                ", waitress=" + waitress +
                ", percentDiscount=" + percentDiscount +
                '}';
    }
}
