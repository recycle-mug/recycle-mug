package recyclemug.ProjectMug.data;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private Long cupId;
    private Long partnerId;
    private int stockQuantity;
}
