package recyclemug.ProjectMug.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Cup {

    @Id @GeneratedValue
    @Column(name = "cup_id")
    private Long id;

    private LocalDateTime lentDate;
    private LocalDateTime returnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

}
