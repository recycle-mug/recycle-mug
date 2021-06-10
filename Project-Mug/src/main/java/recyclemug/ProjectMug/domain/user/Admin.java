package recyclemug.ProjectMug.domain.user;

import lombok.Getter;
import lombok.Setter;
import recyclemug.ProjectMug.domain.cup.Cup;

import javax.persistence.*;

@Entity
@DiscriminatorValue("A")
@Getter @Setter
public class Admin extends User{

    private String adminName;

}
