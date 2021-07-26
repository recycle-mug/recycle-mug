package recyclemug.ProjectMug.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import recyclemug.ProjectMug.domain.cup.PartnerOrder;
import recyclemug.ProjectMug.domain.order.OrderState;

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
        return em.createQuery("select o from PartnerOrder o", PartnerOrder.class).getResultList();
    }

    /**
     * 파트너의 오더 삭제
     * @param partnerOrder
     */
    public void remove(PartnerOrder partnerOrder) {
        em.remove(partnerOrder);
    }

    /**
     * 모든 파트너들의 주문 리스트 중 배달 대기중인 항목들 (삭제 예정)
     * @return
     */
    public List<PartnerOrder> findWaitPartnerOrder() {
        return em.createQuery("select o from PartnerOrder o where o.orderState=:state", PartnerOrder.class)
                .setParameter("state", OrderState.DELIVERY_WAITING)
                .getResultList();
    }

    /**
     * 모든 파트너들의 주문 리스트 중 배달 완료된 항목들 (삭제 예정)
     * @return
     */
    public List<PartnerOrder> findCompletePartnerOrder() {
        return em.createQuery("select o from PartnerOrder o where o.orderState=:state", PartnerOrder.class)
                .setParameter("state", OrderState.COMPLETE)
                .getResultList();
    }

    /**
     * 모든 파트너들의 주문 리스트 중 배달 취소된 항목들 (삭제 예정)
     * @return
     */
    public List<PartnerOrder> findCanceledPartnerOrder() {
        return em.createQuery("select o from PartnerOrder o where o.orderState=:state", PartnerOrder.class)
                .setParameter("state", OrderState.CANCEL)
                .getResultList();
    }

    /**
     * 파트너 주문 리스트를 state 를 기준으로 반환
     * @param state
     * @return
     */
    public List<PartnerOrder> findPartnerOrder(OrderState state) {
        return em.createQuery("select o from PartnerOrder o where o.orderState=:state", PartnerOrder.class)
                .setParameter("state", state)
                .getResultList();
    }
}
