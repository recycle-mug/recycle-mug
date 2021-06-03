package recyclemug.ProjectMug.domain.user;

import lombok.Getter;
import lombok.Setter;
import recyclemug.ProjectMug.domain.cup.Cup;

import javax.persistence.*;

@Entity
@DiscriminatorValue("admin")
@Getter @Setter
public class Admin {

    @Id
    @GeneratedValue
    @Column(name = "admin_id")
    private Long id;

    private String email;
    private String password;
    private String phoneNumber;

    private String name;




}
