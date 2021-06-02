package recyclemug.ProjectMug.domain.user;

import lombok.Getter;
import lombok.Setter;
import recyclemug.ProjectMug.domain.cup.Cup;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Customer {

    @Id @GeneratedValue
    @Column(name = "customer_id")
    private Long id;

    private String email;
    private String password;
    private String phoneNumber;

    // 이미지
    private String profilePicture;

    private int point = 0;

    private String nickName;

    @Enumerated(EnumType.STRING)
    private CustomerState customerState;

    protected Customer() {
    }

    // 생성 메서드 //
    public static Customer createCustomer(String email, String password, String phoneNumber) {
        Customer customer = new Customer();
        customer.email = email;
        customer.password = password;
        customer.phoneNumber = phoneNumber;
        customer.setPoint(0);
        customer.setCustomerState(CustomerState.NONE);

        return customer;
    }
}
