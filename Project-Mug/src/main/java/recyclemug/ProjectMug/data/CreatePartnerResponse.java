package recyclemug.ProjectMug.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import recyclemug.ProjectMug.domain.cup.PartnerCup;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CreatePartnerResponse {
    private Long id;
    private String email;
    private String phoneNumber;
    private byte[] profilePicture;
    private int point;
    private String nickname;
    private LocalDateTime lastLoginDateTime;
    private LocalDateTime signupDateTIme;

    // Partner only val
    private String businessName;
    private String address;
    private String registrationNumber;
    private double latitude;
    private double longitude;
}
