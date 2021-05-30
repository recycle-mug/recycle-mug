package recyclemug.ProjectMug.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.domain.user.MarketUser;
import recyclemug.ProjectMug.domain.user.OrdinaryUser;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordinary_user_id")
    private OrdinaryUser ordinaryUser;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_user_id")
    private MarketUser marketUser;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cup_id")
    private Cup cup;

    private LocalDateTime orderTime;
    private LocalDateTime returnTime;


}
