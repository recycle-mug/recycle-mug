package recyclemug.ProjectMug.domain;

import lombok.Getter;
import lombok.Setter;
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.domain.user.Admin;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.domain.user.Customer;

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

    private int count;

    private LocalDateTime lastModifiedTime;


    // 비즈니스 로직
    /*
        컵을 요청하여 더 받아오는 메서드
        partner 가 가지고 있는 Cup count 가 줄어드는 메서드
     */

}
