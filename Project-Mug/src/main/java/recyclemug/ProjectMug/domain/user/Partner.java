package recyclemug.ProjectMug.domain.user;

import lombok.Getter;
import lombok.Setter;
import recyclemug.ProjectMug.domain.cup.PartnerCup;

import javax.persistence.*;
import java.util.List;

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

    // 주소: 우편번호(zipcode) + 상세주소(detailAddress)
    private String zipcode;
    private String detailAddress;
    // 사업자 등록번호
    private String registrationNumber;

    @OneToMany(mappedBy = "partner")
    private List<PartnerCup> partnerCups;

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

    public void addPartnerCup(PartnerCup partnerCup) {
        partnerCups.add(partnerCup);
        partnerCup.setPartner(this);
    }
}
