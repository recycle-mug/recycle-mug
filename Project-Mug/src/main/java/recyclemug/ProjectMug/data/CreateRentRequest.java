package recyclemug.ProjectMug.data;

import lombok.Data;

@Data
public class CreateRentRequest {
    private Long customerId;
    private Long partnerCupId;
}
