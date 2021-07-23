package recyclemug.ProjectMug.data;

import lombok.Data;

@Data
public class CreateAdminResponse {
    private String result;
    private String message;

    public CreateAdminResponse(String result,String message){
        this.result = result;
        this.message = message;
    }
}
