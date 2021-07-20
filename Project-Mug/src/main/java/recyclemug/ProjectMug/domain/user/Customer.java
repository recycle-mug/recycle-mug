package recyclemug.ProjectMug.domain.user;

import lombok.Getter;
import lombok.Setter;
import recyclemug.ProjectMug.domain.cup.CustomerOrder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@DiscriminatorValue("C")
public class Customer extends User{

    @Enumerated(EnumType.STRING)
    private CustomerState customerState;

    @OneToMany(mappedBy = "customer",orphanRemoval = true)
    private List<CustomerOrder> customerOrders;

    protected Customer() { }

    // 생성 메서드 //
    public static Customer createCustomer(String email, String password, String phoneNumber, String profilePictureAddress) {
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setPhoneNumber(phoneNumber);
        customer.setPoint(10000000);
        customer.setProfilePictureAddress(profilePictureAddress);
        customer.setCustomerState(CustomerState.NONE);
        customer.setSignupDateTIme(LocalDateTime.now());

        return customer;
    }
}
