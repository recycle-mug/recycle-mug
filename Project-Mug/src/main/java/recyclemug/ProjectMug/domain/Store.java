package recyclemug.ProjectMug.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Store {

    @Id @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    private String storeName;

    @Embedded
    private Locate locate;

    @OneToMany(mappedBy = "store")
    private List<Cup> cupList = new ArrayList<>();
}
