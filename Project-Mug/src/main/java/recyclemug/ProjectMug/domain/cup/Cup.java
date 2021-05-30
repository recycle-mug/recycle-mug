package recyclemug.ProjectMug.domain.cup;

import lombok.Getter;
import lombok.Setter;
import recyclemug.ProjectMug.domain.user.AdminUser;
import recyclemug.ProjectMug.domain.user.MarketUser;
import recyclemug.ProjectMug.domain.user.OrdinaryUser;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Cup {

    @Id @GeneratedValue
    @Column(name = "cup_id")
    private Long id;

    private String name;

    private int stockQuantity;

    private String profilePicture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_user_id")
    private MarketUser marketUser;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordinary_user_id")
    private OrdinaryUser ordinaryUser;
}
