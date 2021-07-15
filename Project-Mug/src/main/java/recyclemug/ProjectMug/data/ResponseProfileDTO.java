package recyclemug.ProjectMug.data;

import lombok.Data;

/**
 * /profile URI 의 반환 객체
 */
@Data
public class ResponseProfileDTO {
    private Long id;
    private String email;
    private String nickname;
    private String profilePicture;
    private String role;

    public ResponseProfileDTO(Long id, String email, String nickname, String profilePicture, String role) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.profilePicture = profilePicture;
        this.role = role;
    }
}
