package recyclemug.ProjectMug.data;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Customer 반환 클래스
 */
@Data
@AllArgsConstructor
public class CreateCustomerResponse {
    private String result;
    private String message;
}
