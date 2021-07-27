package recyclemug.ProjectMug.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PartnerModifyDTO {
    private String password;
    private String phoneNumber;
    private String nickname;

    private String address;

    private String registrationNumber;
    private String businessName;

    private double latitude;
    private double longitude;
}
