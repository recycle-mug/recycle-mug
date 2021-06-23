package recyclemug.ProjectMug.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter
@DiscriminatorValue("C")
public class Customer extends User{

    @Enumerated(EnumType.STRING)
    private CustomerState customerState;

    protected Customer() { }

    // 생성 메서드 //
    public static Customer createCustomer(String email, String password, String phoneNumber) {
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setPhoneNumber(phoneNumber);
        customer.setPoint(0);
        customer.setCustomerState(CustomerState.NONE);

        return customer;
    }
}
