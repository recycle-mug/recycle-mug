package recyclemug.ProjectMug.domain.user;

import lombok.Getter;
import lombok.Setter;
import recyclemug.ProjectMug.domain.cup.Cup;

import javax.persistence.*;

@Entity
@DiscriminatorValue("A")
@Getter @Setter
public class Admin extends User{

    @Column(name = "admin_name", length = 15)
    private String adminName;

}
