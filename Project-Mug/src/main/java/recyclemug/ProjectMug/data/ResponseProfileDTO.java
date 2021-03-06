package recyclemug.ProjectMug.data;

import lombok.Data;
import recyclemug.ProjectMug.domain.user.CustomerState;

import java.time.LocalDateTime;

/**
 * /profile URI 의 반환 객체
 */
@Data
public class ResponseProfileDTO {
    private Long id;
    private String email;
    private String nickname;
    private byte[] profilePicture;
    private String role;
    private LocalDateTime lastLogin;
    private LocalDateTime signupDateTime;
    private CustomerState customerState;
    private LocalDateTime returnDateTime;

    public ResponseProfileDTO(Long id, String email, String nickname, byte[] profilePicture, String role, LocalDateTime signupDateTime,CustomerState customerState,LocalDateTime returnDateTime) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.profilePicture = profilePicture;
        this.role = role;
        this.lastLogin = LocalDateTime.now();
        this.signupDateTime = signupDateTime;
        this.customerState = customerState;
        this.returnDateTime = returnDateTime;
    }
}
