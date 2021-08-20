package recyclemug.ProjectMug.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerTradeDto {
    private String email;
    private String nickname;
    private LocalDateTime rentDateTime;
    private LocalDateTime returnDateTime;
    private LocalDateTime returnedDateTime;

    public CustomerTradeDto(String email, String nickname, LocalDateTime rentDateTime, LocalDateTime returnDateTime, LocalDateTime returnedDateTime){
        this.email = email;
        this.nickname = nickname;
        this.rentDateTime = rentDateTime;
        this.returnDateTime = returnDateTime;
        this.returnedDateTime = returnedDateTime;
    }
}
