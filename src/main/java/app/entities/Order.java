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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getRoof() {
        return roof;
    }

    public void setRoof(String roof) {
        this.roof = roof;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", price=" + price +
                ", width=" + width +
                ", length=" + length +
                ", roof='" + roof + '\'' +
                ", status=" + status +
                ", shippingAddress='" + shippingAddress + '\'' +
                '}';
    }
}
