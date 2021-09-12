package recyclemug.ProjectMug.data;

import lombok.Data;

/**
 * Partner Request Body 클래스
 */
@Data
public class CreatePartnerRequest {
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String businessName;
    private double altitude;
    private double longitude;
}
