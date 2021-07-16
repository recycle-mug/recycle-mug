package recyclemug.ProjectMug.data;

import lombok.Data;

/**
 * Partner Request Body 클래스
 */
@Data
public class CreatePartnerRequest {
    private String id;
    private String pw;
    private String tel;
    private String address_num;
    private String address;
    private String address_detail;
}
