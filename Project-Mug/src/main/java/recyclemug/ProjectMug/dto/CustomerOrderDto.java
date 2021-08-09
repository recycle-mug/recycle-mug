package recyclemug.ProjectMug.dto;

import lombok.Data;

@Data
public class CustomerOrderDto {
    private Long customerId;
    private String customerName;
    private Long partnerId;
    private String partnerBusinessName;
    private Long cupId;
    private String cupName;
    private byte[] cupImage;

    public CustomerOrderDto(Long customerId,String customerName,Long partnerId,String partnerBusinessName, Long cupId,String cupName,byte[] cupImage){
        this.customerId = customerId;
        this.customerName = customerName;
        this.partnerId = partnerId;
        this.partnerBusinessName = partnerBusinessName;
        this.cupId = cupId;
        this.cupName = cupName;
        this.cupImage = cupImage;
    }
}
