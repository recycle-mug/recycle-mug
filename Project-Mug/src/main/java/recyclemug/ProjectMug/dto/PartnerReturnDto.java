package recyclemug.ProjectMug.dto;

import lombok.Data;

@Data
public class PartnerReturnDto {
    private Long partnerReturnId;
    private String businessName;
    private String cupName;
    private int returnQuantity;

    public PartnerReturnDto(Long partnerReturnId,String businessName,String cupName,int returnQuantity){
        this.partnerReturnId = partnerReturnId;
        this.businessName = businessName;
        this.cupName = cupName;
        this.returnQuantity = returnQuantity;
    }
}
