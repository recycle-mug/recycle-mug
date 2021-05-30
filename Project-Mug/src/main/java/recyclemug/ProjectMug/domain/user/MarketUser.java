package recyclemug.ProjectMug.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("market")
@Getter @Setter
public class MarketUser{

    @Id
    @GeneratedValue
    @Column(name = "market_user_id")
    private Long id;

    private String userEmail;
    private String password;
    private String phoneNumber;

    private String marketName;

    private String profilePicture;

    private String zipcode;
    private String detailAddress;
    private String registrationNumber;

}
