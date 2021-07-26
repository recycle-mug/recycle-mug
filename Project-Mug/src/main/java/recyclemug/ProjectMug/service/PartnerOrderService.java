package recyclemug.ProjectMug.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.domain.cup.PartnerCup;
import recyclemug.ProjectMug.domain.cup.PartnerOrder;
import recyclemug.ProjectMug.domain.order.OrderState;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.exception.NotEnoughPointException;
import recyclemug.ProjectMug.exception.NotEnoughStockException;
import recyclemug.ProjectMug.repository.PartnerCupRepository;
import recyclemug.ProjectMug.repository.PartnerOrderRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartnerOrderService {

    private final PartnerOrderRepository partnerOrderRepository;
    private final PartnerCupRepository partnerCupRepository;

    /**
     * 파트너가 어드민에게 컵을 주문했을 시
     * @param cup
     * @param partner
     * @param orderQuantity
     */
    @Transactional
    public void cupOrderOfPartner(Cup cup, Partner partner, int orderQuantity) throws NotEnoughPointException{
        if (partner.getPoint() < cup.getPrice() * orderQuantity) {
            throw new NotEnoughPointException();
        } else {
            PartnerOrder partnerOrder = new PartnerOrder(partner, cup, orderQuantity);
            partnerOrderRepository.save(partnerOrder);
            partner.setPoint(partner.getPoint() - orderQuantity * cup.getPrice());
        }
    }

    /**
     * 배송이 완료되었을때 (예 클릭시 발생하는 메서드)
     * PartnerCup 이 이미 존재할 때와 아닐 때로 구분
     * @param partnerOrder
     */
    @Transactional
    public void completeOrder(PartnerOrder partnerOrder) {
        Cup cup = partnerOrder.getCup();
        Partner partner = partnerOrder.getPartner();
        if (!partnerOrder.getOrderState().equals(OrderState.DELIVERY_WAITING)) {
            throw new RuntimeException("partner order state is not DELIVERY_WAITING");
        } else if (cup.getStockQuantity() < partnerOrder.getOrderQuantity()) {
            throw new NotEnoughStockException();
        } else {
            List<PartnerCup> partnerCups = partnerCupRepository.findByPartnerIdAndCupId(partner.getId(), cup.getId());

            // partnerCup 이 없을때
            if (partnerCups.isEmpty()) {
                PartnerCup partnerCup = new PartnerCup(partner, cup, partnerOrder.getOrderQuantity());
                partnerCupRepository.saveCup(partnerCup);
            } else {
                PartnerCup findPartnerCup = partnerCups.get(0);
                findPartnerCup.setStockQuantity(findPartnerCup.getStockQuantity() + partnerOrder.getOrderQuantity());
            }
            cup.setStockQuantity(cup.getStockQuantity() - partnerOrder.getOrderQuantity());
            partnerOrder.setOrderState(OrderState.COMPLETE);
            partnerOrder.setDeliveryCompleteDateTime(LocalDateTime.now());
        }
    }

    /**
     * 배송이 취소되었을때 (아니오 클릭시 발생하는 메서드)
     * @param partnerOrder
     */
    @Transactional
    public void rejectOrder(PartnerOrder partnerOrder) {
        if (partnerOrder.getOrderState().equals(OrderState.DELIVERY_WAITING)) {
            Partner partner = partnerOrder.getPartner();
            partner.setPoint(partner.getPoint() + partnerOrder.getOrderQuantity() * partnerOrder.getCup().getPrice());
            partnerOrder.setOrderState(OrderState.CANCEL);
        } else {
            throw new RuntimeException("partnerOrder State is not DELIVERY_WAITING");
        }
    }

    /**
     * PartnerOrder 삭제
     * @param partnerId
     */
    @Transactional
    public void removeOrder(Long partnerId) {
        PartnerOrder partnerOrder = partnerOrderRepository.findById(partnerId);
        partnerOrderRepository.remove(partnerOrder);
    }


}
