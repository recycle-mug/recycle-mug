package recyclemug.ProjectMug.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerModifyDTO {
    private String password;
    private String phoneNumber;
    private String nickname;
}