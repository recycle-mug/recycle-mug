package recyclemug.ProjectMug.data;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Partner Join 성공/실패 반환 클래스
 */
@Data
@AllArgsConstructor
public class CreatePartnerJoinResponse {
    private String result;
    private String message;
}
