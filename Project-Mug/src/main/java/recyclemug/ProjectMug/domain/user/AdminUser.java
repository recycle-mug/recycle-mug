package recyclemug.ProjectMug.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("admin")
@Getter @Setter
public class AdminUser{

    @Id
    @GeneratedValue
    @Column(name = "admin_user_id")
    private Long id;

    private String userEmail;
    private String password;
    private String phoneNumber;

    private String name;
}
