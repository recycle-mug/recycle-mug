package recyclemug.ProjectMug.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Partner {

    @Id
    @GeneratedValue
    @Column(name = "partner_id")
    private Long id;

    private String email;
    private String password;
    private String phoneNumber;

    private String partnerName;

    private String profilePicture;

    private String zipcode;
    private String detailAddress;
    private String registrationNumber;

    protected Partner(){}

    // 생성 메서드 //
    public Partner createPartner(String email, String password, String phoneNumber, String registrationNumber) {
        Partner partner = new Partner();
        partner.email = email;
        partner.password = password;
        partner.phoneNumber = phoneNumber;
        partner.registrationNumber = registrationNumber;

        return partner;
    }
}
