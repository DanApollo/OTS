package data.users;

import domain.users.ICustomerUser;

public class CustomerUser extends User implements ICustomerUser {

    record Address(String addressLine1, String addressLine2, String city, String county, String postcode) {
        public String getAddress() {
            return addressLine1 + addressLine2 + city + county + postcode;
        }
    }

    private Address address;

    public CustomerUser(String firstName, String secondName, String username, String password, String addressLine1, String addressLine2, String city, String county, String postcode) {
        super(firstName, secondName, username, password);
        this.address = new Address(addressLine1, addressLine2, city, county, postcode);
    }

    @Override
    public String getAddress() {
        return this.address.getAddress();
    }

    @Override
    public void setAddress(String addressLine1, String addressLine2, String city, String county, String postcode) {
        this.address = new Address(addressLine1, addressLine2, city, county, postcode);
    }
}
