package domain.users;

public interface ICustomerUser {
    String getAddress();

    void setAddress(String addressLine1, String addressLine2, String city, String county, String postcode);
}
