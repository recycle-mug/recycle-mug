package recyclemug.ProjectMug.dto;

import lombok.Data;

@Data
public class PartnerReturnDto {
    private String businessName;
    private String cupName;
    private int returnQuantity;

    public PartnerReturnDto(String businessName,String cupName,int returnQuantity){
        this.businessName = businessName;
        this.cupName = cupName;
        this.returnQuantity = returnQuantity;
    }
}
