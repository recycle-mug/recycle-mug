package recyclemug.ProjectMug.data;

import lombok.Data;

/**
 * Cup 반환 클래스
 */
@Data
public class CreateCupResponse {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private byte[] image;

    public CreateCupResponse(Long id, String name, int price, int stockQuantity, byte[] image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.image = image;
    }
}
