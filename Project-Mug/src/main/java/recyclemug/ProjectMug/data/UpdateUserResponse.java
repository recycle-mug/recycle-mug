package recyclemug.ProjectMug.data;

import lombok.Data;

@Data
public class UpdateUserResponse {
    private String result;
    private String message;

    public UpdateUserResponse(String result,String message){
        this.result = result;
        this.message = message;
    }
}
