package recyclemug.ProjectMug.data;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class CustomerOrderIdResponse {

    private String partnerName;

    private String cupName;

    private byte[] cupImage;

    private LocalDateTime rentDateTime;
    private LocalDateTime returnDateTime;
    private LocalDateTime returnedDateTime;

}
