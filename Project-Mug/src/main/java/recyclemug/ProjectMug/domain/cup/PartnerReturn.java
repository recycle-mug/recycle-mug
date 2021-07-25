package recyclemug.ProjectMug.domain.cup;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import recyclemug.ProjectMug.domain.order.ReturnState;
import recyclemug.ProjectMug.domain.user.Partner;

import javax.persistence.*;

@Entity
@Getter @Setter
public class PartnerReturn {

    @Id @GeneratedValue
    @Column(name = "partner_return_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Partner partner;

    @ManyToOne
    @JoinColumn(name = "cup_id")
    private Cup cup;

    private int returnQuantity;

    public PartnerReturn(Partner partner, Cup cup, int returnQuantity) {
        this.partner = partner;
        this.cup = cup;
        this.returnQuantity = returnQuantity;
    }
}
