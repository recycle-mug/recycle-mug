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
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cup_id")
    private Cup cup;

    private int stockQuantity;

    private LocalDateTime lastModifiedTime;


    // 비즈니스 로직
    /*
        컵을 요청하여 더 받아오는 메서드
        partner 가 가지고 있는 Cup count 가 줄어드는 메서드
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0){
            throw new NotEnoughStockException("컵이 충분하지 않습니다.");
        }
    }
}
