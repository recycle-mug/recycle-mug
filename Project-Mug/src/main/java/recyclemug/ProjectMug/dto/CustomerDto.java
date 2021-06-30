package recyclemug.ProjectMug.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class CustomerDto {

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String nickname;
}
