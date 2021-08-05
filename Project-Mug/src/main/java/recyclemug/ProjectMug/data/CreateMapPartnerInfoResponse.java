package recyclemug.ProjectMug.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import recyclemug.ProjectMug.dto.MapPartnerInfoDto;

@Data
@AllArgsConstructor
public class CreateMapPartnerInfoResponse {
    private String result;
    private String message;
    private MapPartnerInfoDto mapPartnerInfoDto;
}
