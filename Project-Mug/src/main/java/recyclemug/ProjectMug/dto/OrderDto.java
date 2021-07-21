package recyclemug.ProjectMug.dto;

import lombok.*;


@Data
public class OrderDto {
    private Long id;
    private String name;
    private int price;

    public OrderDto(Long id,String name,int price){
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
