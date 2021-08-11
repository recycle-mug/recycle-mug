package recyclemug.ProjectMug.domain.cup;

import lombok.Getter;
import lombok.Setter;
import recyclemug.ProjectMug.domain.user.Partner;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class ReturnedCup {
    @Id @GeneratedValue
    @Column(name = "returned_cup_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Partner partner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cup_id")
    private Cup cup;

    private LocalDateTime returnedTime;

    protected ReturnedCup(){};

    public ReturnedCup(Partner partner, Cup cup) {
        this.cup = cup;
        this.partner = partner;
        returnedTime = LocalDateTime.now();
    }
}
