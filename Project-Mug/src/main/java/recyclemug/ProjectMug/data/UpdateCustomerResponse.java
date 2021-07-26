package recyclemug.ProjectMug.data;

import lombok.Data;

@Data
public class UpdateCustomerResponse {
    private String result;
    private String message;

    public UpdateCustomerResponse(String result,String message){
        this.result = result;
        this.message = message;
    }
}
