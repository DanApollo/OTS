import data.users.CustomerUser;
import domain.users.ICustomerUser;
import domain.users.IUser;

public class Main {
    public static void main(String[] args) {
        ICustomerUser user2 = new CustomerUser("Jane", "Doe", "JaneDoe", "Jane", "12 Dave Street", "", "London", "London", "asdf asd");
        System.out.println(user2.getAddress());
    }
}
