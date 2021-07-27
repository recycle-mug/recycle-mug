package recyclemug.ProjectMug.dto;

import lombok.Data;

@Data
public class OrderSummaryDto {
    private int wait;
    private int complete;
    private int cancel;
    private int total;
    public OrderSummaryDto(int wait,int complete,int cancel,int total){
        this.wait = wait;
        this.complete = complete;
        this.cancel = cancel;
        this.total = total;
    }
}
