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
}
