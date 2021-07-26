package recyclemug.ProjectMug.dto;

public class CustomerReturnDto {
    private Long partnerId;
    private String businessName;
    private String cupState;

    public CustomerReturnDto(Long partnerId,String businessName,String cupState){
        this.partnerId = partnerId;
        this.businessName = businessName;
        this.cupState = cupState;
    }
}
