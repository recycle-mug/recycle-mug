package recyclemug.ProjectMug.domain.user;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import recyclemug.ProjectMug.domain.cup.PartnerCup;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@DiscriminatorValue("P")
public class Partner extends User{

    private String businessName;

    // 주소: 우편번호(zipcode) + 상세주소(detailAddress)
    private String zipcode;
    private String detailAddress;
    // 사업자 등록번호
    private String registrationNumber;

    private double latitude;
    private double altitude;

    @OneToMany(mappedBy = "partner")
    private List<PartnerCup> partnerCups;

    protected Partner(){}

    // 생성 메서드 //
    public static Partner createPartner(String email, String password, String phoneNumber,
                                        String zipcode, String detailAddress, double latitude, double altitude) {
        Partner partner = new Partner();
        partner.setEmail(email);
        partner.setPassword(password);
        partner.setPhoneNumber(phoneNumber);
        partner.setZipcode(zipcode);
        partner.setDetailAddress(detailAddress);
        partner.setPoint(10000000);
        partner.setSignupDateTIme(LocalDateTime.now());
        partner.setLatitude(latitude);
        partner.setAltitude(altitude);

        return partner;
    }

    public void addPartnerCup(PartnerCup partnerCup) {
        partnerCups.add(partnerCup);
        partnerCup.setPartner(this);
    }
}
