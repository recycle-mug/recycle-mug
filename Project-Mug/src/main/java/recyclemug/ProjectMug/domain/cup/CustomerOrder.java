package recyclemug.ProjectMug.domain.cup;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import recyclemug.ProjectMug.domain.user.Customer;
import recyclemug.ProjectMug.domain.user.Partner;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class CustomerOrder {

    @Id @GeneratedValue
    @Column(name = "customer_order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Partner partner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cup_id")
    private Cup cup;

    // 빌린시간, 반납 해야하는 시간, 실제 반납 시간
    private LocalDateTime rentDateTime;
    private LocalDateTime returnDateTime;
    private LocalDateTime returnedDateTime;

    protected CustomerOrder(){}

    public CustomerOrder(Customer customer, Cup cup) {
        this.customer = customer;
        this.cup = cup;
        this.rentDateTime = LocalDateTime.now();
        this.returnDateTime = LocalDateTime.now().plusWeeks(1);
    }
}
