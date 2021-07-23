package recyclemug.ProjectMug.domain.cup;

import lombok.Getter;
import lombok.Setter;
import recyclemug.ProjectMug.domain.order.OrderState;
import recyclemug.ProjectMug.domain.user.Partner;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class PartnerOrder {

    @Id @GeneratedValue
    @Column(name = "partner_order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cup_id")
    private Cup cup;

    private int orderQuantity;

    private LocalDateTime orderDateTime;
    private LocalDateTime deliveryCompleteDateTime;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "order_state")
    private OrderState orderState;

    protected PartnerOrder() {
    }

    public PartnerOrder(Partner partner, Cup cup, int orderQuantity) {
        this.partner = partner;
        this.cup = cup;
        this.orderQuantity = orderQuantity;
        this.orderDateTime = LocalDateTime.now();
        this.orderState = OrderState.DELIVERY_WAITING;
    }
}
