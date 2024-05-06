package app.entities;

public class Order {
    private int orderId;
    private double price;
    private int width;
    private int length;
    private String roof;
    private String shippingAddress;
    private int statusId;

    public Order(int orderId, double price, int width, int length, String roof, String shippingAddress, int statusId) {
        this.orderId = orderId;
        this.width = width;
        this.length = length;
        this.roof = roof;
        this.shippingAddress = shippingAddress;
        this.statusId = statusId;
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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


    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", price=" + price +
                ", width=" + width +
                ", length=" + length +
                ", roof='" + roof + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", statusId=" + statusId +
                '}';
    }
}
