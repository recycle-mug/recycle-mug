package recyclemug.ProjectMug.domain.user;

import lombok.Getter;
import lombok.Setter;
import recyclemug.ProjectMug.domain.cup.Cup;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrdinaryUser{

    @Id @GeneratedValue
    @Column(name = "ordinary_user_id")
    private Long id;

    private String userEmail;
    private String password;
    private String phoneNumber;

    // 이미지
    private String profilePicture;

    private int point;

    private String nickName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cup_id")
    private Cup useCup;

    @Enumerated(EnumType.STRING)
    private UserState userState;

}
