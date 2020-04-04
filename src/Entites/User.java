package Entites;

public class User {
    public final String USERID = "user_id";
    public final String FIRSTNAME = "first_name";
    public final String LASTNAME = "last_name";
    public final String EMAILADDRESS = "email_address";
    public final String PHONENUMBER = "phone_number";
    public final String SHIPPINGADDRESS = "shipping_address";
    public final String PASSWORD = "password";
    public final String PROMOTED = "promoted";

    int userId;
    String firstName;
    String lastName;
    String emailAddress;
    String phoneNumber;
    String shippingAddress;
    String password;
    boolean promoted;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPromoted() {
        return promoted;
    }

    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }
}
