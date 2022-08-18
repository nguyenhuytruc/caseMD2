package vn.htn.htsport.model;

public class Product {
    private Long id;
    private String title;
    private String size;
    private Double price;
    private Integer quantity;

    public Product() {
    }

    public Product(Long id, String title, String size, Integer quantity, Double price) {
        this.id = id;
        this.title = title;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
    }

    public static Product parse(String record){
        String[] fields = record.split(",");
        long id = Long.parseLong(fields[0]);
        String title = fields[1];
        String size = fields[2];
        Integer quantiy = Integer.parseInt(fields[3]);
        double price = Double.parseDouble(fields[4]);
        return new Product(id, title, size, quantiy, price);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s",id, title, size, quantity, price);
    }
}
