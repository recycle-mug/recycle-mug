package recyclemug.ProjectMug.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Locate {

    /*
    우선은 store 의 위치만을 위한 임베디드 값 타입
     */
    private Long latitude;
    private Long longitude;

    public Locate() { }

    public Locate(Long latitude, Long longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
