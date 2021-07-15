package recyclemug.ProjectMug.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * /profile controller: JWT에 담겨있는 정보를 통해서 Authorize 를 상시화 하기 위해서 사용
 * 그중 JWT 의 payload(Claim) 정보를 Parsing 하기 위해서 사용
 */
@Data
public class HeaderJwtDTO {
    @JsonProperty("sub")
    private String email;
    @JsonProperty("auth")
    private String role;
    @JsonProperty("exp")
    private String expiredTime;
}
