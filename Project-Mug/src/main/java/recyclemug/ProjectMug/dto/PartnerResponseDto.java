package recyclemug.ProjectMug.dto;

import lombok.Data;

@Data
public class PartnerResponseDto {
    private Long userId;
    private String businessName;
    private String address;
    private String phoneNumber;
    private double latitude;
    private double longitude;
    public PartnerResponseDto(Long userId,String businessName,String address, String phoneNumber, double latitude, double longitude){
        this.userId = userId;
        this.businessName = businessName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
