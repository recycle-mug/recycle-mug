package recyclemug.ProjectMug.data;

import lombok.Data;

/**
 * /cup/add URI
 * Request json Cup 정보를 ModelAttribute 하기 위한 Data Class
 */
@Data
public class CreateCupRequest {
    private String name;
    private int price;
    private int stockQuantity;
}
