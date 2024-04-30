package app.entities;/* @auther: Frederik Dupont */

public class User {
    private int userId;
    private String email;
    private String password;
    private String address;
    private int postalcode;
    private String city;
    private int phonenumber;
    private String role;


    public User(int userId, String email, String password, String address, int postalcode, String city, int phonenumber, String role) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.address = address;
        this.postalcode = postalcode;
        this.city = city;
        this.phonenumber = phonenumber;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(int postalcode) {
        this.postalcode = postalcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", postalcode=" + postalcode +
                ", city='" + city + '\'' +
                ", phonenumber=" + phonenumber +
                ", role='" + role + '\'' +
                '}';
    }
}
