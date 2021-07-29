package recyclemug.ProjectMug.data;

import lombok.Data;

@Data
public class UpdateProfileImageRequest {
    private Long userId;
    private String pictureAddress;
}
