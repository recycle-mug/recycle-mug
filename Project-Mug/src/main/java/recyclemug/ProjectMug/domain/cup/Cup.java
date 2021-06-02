package recyclemug.ProjectMug.domain.cup;

import lombok.Getter;
import lombok.Setter;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.domain.user.Customer;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Cup {

    @Id @GeneratedValue
    @Column(name = "cup_id")
    private Long id;

    private String name;

    private String profilePicture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
