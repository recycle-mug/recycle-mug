package recyclemug.ProjectMug.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import recyclemug.ProjectMug.domain.cup.Cup;

import javax.persistence.*;
import java.util.Set;

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

    private boolean activated;

    // 예제 코드
    @ManyToMany
    @JoinTable(name = "customer_authority",
            joinColumns = {@JoinColumn(name = "customer_id", referencedColumnName = "customer_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

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
