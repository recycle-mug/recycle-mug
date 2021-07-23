package recyclemug.ProjectMug.domain.cup;

import lombok.Getter;
import lombok.Setter;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.exception.NotEnoughStockException;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class PartnerCup {

    @Id @GeneratedValue
    @Column(name = "partner_cup_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Partner partner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cup_id")
    private Cup cup;

    private int stockQuantity;

    private LocalDateTime lastModifiedTime;

    protected PartnerCup() {
    }

    public PartnerCup(Partner partner, Cup cup, int stockQuantity) {
        this.partner = partner;
        this.cup = cup;
        this.stockQuantity = stockQuantity;
        this.lastModifiedTime = LocalDateTime.now();
    }

    // 비즈니스 로직

    /**
     * customer 가 성공적으로 PartnerCup 주문에 성공했을때 재고 수량을 줄어들게 하는 비즈니스 로직.
     * 참고: 현재 로직에선 1개씩밖에 주문하지 못하지만 언젠가 몇잔씩 주문 가능하게 될 경우를 가정해 orderQuantity 부여.
     */
    public void customerOrderComplete(int orderQuantity) {
        stockQuantity = stockQuantity - orderQuantity;
    }
}
