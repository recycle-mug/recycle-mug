package recyclemug.ProjectMug.dto;

import lombok.Data;

@Data
public class CustomerResponseDto {
    private Long userId;
    private String email;
    private String nickname;
    public CustomerResponseDto(Long userId,String email,String nickname){
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
    }
}
