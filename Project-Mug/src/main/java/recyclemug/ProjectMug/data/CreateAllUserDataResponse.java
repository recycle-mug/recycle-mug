package recyclemug.ProjectMug.data;

import lombok.Builder;
import lombok.Data;
import recyclemug.ProjectMug.domain.user.CustomerState;

@Data
@Builder
public class CreateAllUserDataResponse {
    private Long id;
    private String email;
    private String phoneNumber;
    private byte[] profilePicture;
    private int point;
    private String nickname;
    private CustomerState customerState;


}
