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
    private int price;

    private String profilePicture;

}
