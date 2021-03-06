package recyclemug.ProjectMug.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role")
public abstract class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(length = 30)
    private String email;
    @Column(length = 30)
    private String password;
    @Column(length = 13)
    private String phoneNumber;

    // Customer -> 개인사진, Partner -> 매장사진
    @Column(length = 200)
    private String profilePictureAddress;

    @Column(length = 9)
    private int point;
    @Column(length = 10)
    private String nickname;

    private boolean activated;

    // 생성자 처리 해줘야함
    private LocalDateTime lastLoginDateTIme;
    private LocalDateTime signupDateTIme;

    @ManyToMany
    @JoinTable(name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    protected User() { }


}
