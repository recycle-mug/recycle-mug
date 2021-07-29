package recyclemug.ProjectMug.dto;

import lombok.Data;
import recyclemug.ProjectMug.domain.order.OrderState;

import java.time.LocalDateTime;

@Data
public class CupResponseDto {
    private Long id;
    private String businessName;
    private String cupName;
    private int stockQuantity;
    private int price;
    private LocalDateTime orderDateTime;
    private OrderState state;
    public CupResponseDto(Long id,String businessName, String cupName, int stockQuantity,int price, LocalDateTime orderDateTime, OrderState state){
        this.id = id;
        this.businessName = businessName;
        this.cupName = cupName;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.orderDateTime = orderDateTime;
        this.state = state;
    }
}
