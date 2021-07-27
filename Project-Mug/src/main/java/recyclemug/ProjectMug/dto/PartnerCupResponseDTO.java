package recyclemug.ProjectMug.dto;

import lombok.Builder;
import lombok.Data;
import recyclemug.ProjectMug.domain.cup.Cup;

import java.time.LocalDateTime;

@Data
@Builder
public class PartnerCupResponseDTO {

    private Long id;
    private String name;
    private byte[] image;
    private int stockQuantity;
    private int price;
}
