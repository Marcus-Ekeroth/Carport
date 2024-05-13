package app.entities;

public class Order {
    private int orderId;
    private double price;
    private int width;
    private int length;
    private String roof;
    private String shippingAddress;
    private int statusId;
    private String status;
    private int userId;

    public Order(int orderId, double price, int width, int length, String roof, String shippingAddress, int userId, String status) {
        this.orderId = orderId;
        this.width = width;
        this.price=price;
        this.length = length;
        this.roof = roof;
        this.shippingAddress = shippingAddress;
        this.status=status;
        this.userId=userId;
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

    public int getUserId(){
        return userId;
    }
    public void setUserId(int userId){
        this.userId=userId;
    }
    public String getStatus(){
        return status;
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
