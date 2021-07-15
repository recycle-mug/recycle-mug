package recyclemug.ProjectMug.data;

import lombok.Data;

/**
 * Customer join 의 Request Body 용 클래스
 */
@Data
public class CreateCustomerRequest {
    private String role; // "customer" or "partner"
    private String id; 	// String (email)
    private String pw;		// String
    private String tel;		// String
    private String address;	// String (partner일경우 "")
    private String address_num;	// String (5자리숫자, partner일경우 "")
    private String address_detail;
}
