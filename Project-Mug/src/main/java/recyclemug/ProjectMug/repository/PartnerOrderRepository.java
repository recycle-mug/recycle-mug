package recyclemug.ProjectMug.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import recyclemug.ProjectMug.domain.cup.PartnerOrder;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PartnerOrderRepository {

    private final EntityManager em;

    public void save(PartnerOrder partnerOrder) {
        em.persist(partnerOrder);
    }

    public PartnerOrder findById(Long id) {
        return em.find(PartnerOrder.class, id);
    }

    /**
     * 모든 파트너들의 주문 리스트 (배달 중, 배달 완료 포함)
     * @return
     */
    public List<PartnerOrder> findAllPartnerOrder() {
        return em.createQuery("select o from PartnerOrder", PartnerOrder.class).getResultList();
    }

    /**
     * 모든 파트너들의 주문 리스트 중 배달 대기중인 항목들
     * @return
     */
    public List<PartnerOrder> findWaitPartnerOrder() {
        return em.createQuery("select o from PartnerOrder o where o.order_state=CANCEL", PartnerOrder.class).getResultList();
    }

    /**
     * 모든 파트너들의 주문 리스트 중 배달 완료된 항목들
     * @return
     */
    public List<PartnerOrder> findCompletePartnerOrder() {
        return em.createQuery("select o from PartnerOrder o where o.order_state=COMPLETE", PartnerOrder.class).getResultList();
    }

    /**
     * 모든 파트너들의 주문 리스트 중 배달 취소된 항목들
     * @return
     */
    public List<PartnerOrder> findCanceledPartnerOrder() {
        return em.createQuery("select o from PartnerOrder o where o.order_state=CANCEL", PartnerOrder.class).getResultList();
    }
}
