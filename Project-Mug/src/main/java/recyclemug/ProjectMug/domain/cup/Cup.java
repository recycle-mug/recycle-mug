package recyclemug.ProjectMug.domain.cup;

import lombok.Getter;
import lombok.Setter;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.domain.user.Customer;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Cup {

    @Id @GeneratedValue
    @Column(name = "cup_id")
    private Long id;

    @Column(name = "name", length = 10)
    private String name;

    @Column(name = "price", length = 10)
    private int price;

    @Column(name = "stock_quantity", length = 20)
    private int stockQuantity;

    @Column(name = "profile_picture_address", length = 150)
    private String profilePictureAddress;

    protected Cup() {
    }

    public static Cup createCup(String name, int price, int stockQuantity, String profilePictureAddress) {
        Cup cup = new Cup();
        cup.name = name;
        cup.price = price;
        cup.stockQuantity = stockQuantity;
        cup.profilePictureAddress = profilePictureAddress;
        return cup;
    }

}
