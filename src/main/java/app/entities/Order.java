package app.entities;

public class Order {
    private int orderId;
    private int price;
    private int width;
    private int length;
    private String roof;
    private boolean status;
    private String shippingAddress;

    public Order(int orderId, int price, int width, int length, String roof, boolean status, String shippingAddress) {
        this.orderId = orderId;
        this.price = price;
        this.width = width;
        this.length = length;
        this.roof = roof;
        this.status = status;
        this.shippingAddress = shippingAddress;
    }
}
