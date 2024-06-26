package framework.utils;

public class Product {
    private String name;

    private String price;

    public Product(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getIntPrice(){
        return Integer.parseInt(price.replace(" ",""));
    }
}
