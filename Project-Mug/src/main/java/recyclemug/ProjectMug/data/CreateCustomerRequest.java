package recyclemug.ProjectMug.data;

import lombok.Data;

/**
 * Customer join 의 Request Body 용 클래스
 */
@Data
public class CreateCustomerRequest {
    private String email; 	// String (email)
    private String password;		// String
    private String phoneNumber;		// String
}
