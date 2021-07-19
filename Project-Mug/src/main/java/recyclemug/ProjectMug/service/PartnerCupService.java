package recyclemug.ProjectMug.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.domain.cup.PartnerCup;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.exception.NotEnoughPointException;
import recyclemug.ProjectMug.exception.NotEnoughStockException;
import recyclemug.ProjectMug.repository.PartnerCupRepository;

@Service
@RequiredArgsConstructor
public class PartnerCupService {

    private final PartnerCupRepository partnerCupRepository;

    /**
     * 파트너가 컵이 없는 상태에서 어드민에게 컵을 주문하는 메서드
     *
     * @param cup
     * @param partner
     * @param orderQuantity
     */
    @Transactional
    public void firstCupOrderOfPartner(Cup cup, Partner partner, int orderQuantity) throws NotEnoughPointException, NotEnoughStockException {
        if (cup.getStockQuantity() < orderQuantity) {
            throw new NotEnoughStockException();
        } else if (cup.getPrice() * orderQuantity > partner.getPoint()) {
            throw new NotEnoughPointException();
        } else {
            PartnerCup partnerCup = new PartnerCup(partner, cup, orderQuantity);
            partner.setPoint(partner.getPoint() - cup.getPrice() * orderQuantity);
            cup.setStockQuantity(cup.getStockQuantity() - orderQuantity);
            partnerCupRepository.saveCup(partnerCup);
        }
    }

    /**
     * 파트너가 컵이 존재하는 상태에서 어드민에게 컵을 주문하는 메서드
     * @param partnerCup
     * @param orderQuantity
     * @throws NotEnoughPointException
     * @throws NotEnoughStockException
     */
    @Transactional
    public void cupOrderOfPartner(PartnerCup partnerCup, int orderQuantity) throws NotEnoughPointException, NotEnoughStockException{
        Cup cup = partnerCup.getCup();
        if (cup.getStockQuantity() < orderQuantity) {
            throw new NotEnoughStockException();
        } else if (cup.getPrice() * orderQuantity > partnerCup.getPartner().getPoint()) {
            throw new NotEnoughPointException();
        } else {
            partnerCup.setStockQuantity(partnerCup.getStockQuantity() + orderQuantity);
            cup.setStockQuantity(cup.getStockQuantity() - orderQuantity);
            Partner partner = partnerCup.getPartner();
            partner.setPoint(partner.getPoint() - cup.getPrice() * orderQuantity);
        }
    }
}
