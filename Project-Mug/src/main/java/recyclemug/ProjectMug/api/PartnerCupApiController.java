package recyclemug.ProjectMug.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.domain.cup.PartnerCup;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.repository.CupRepository;
import recyclemug.ProjectMug.repository.PartnerCupRepository;
import recyclemug.ProjectMug.repository.PartnerRepository;
import recyclemug.ProjectMug.service.PartnerCupService;
import recyclemug.ProjectMug.service.PartnerService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PartnerCupApiController {

    private final PartnerRepository partnerRepository;
    private final CupRepository cupRepository;
    private final PartnerService partnerService;
    private final PartnerCupService partnerCupService;
    private final PartnerCupRepository partnerCupRepository;

    @GetMapping("/partnerCup/firstOrder")
    @ResponseBody
    public String orderWithoutCup(@RequestParam Long partnerId, @RequestParam Long cupId,
                         @RequestParam Integer orderQuantity) {
        Partner partner = partnerRepository.findOne(partnerId);
        Cup cup = cupRepository.findByCupId(cupId);
        try {
            partnerCupService.firstCupOrderOfPartner(cup, partner, orderQuantity);
            return "ok";
        } catch (Exception e) {
            log.info(e.toString());
        }

        return "err";
    }

    @GetMapping("/partnerCup/order")
    @ResponseBody
    public String orderWithCup(@RequestParam Long partnerCupId, @RequestParam Integer orderQuantity) {
        try {
            PartnerCup partnerCup = partnerCupRepository.findById(partnerCupId);
            partnerCupService.cupOrderOfPartner(partnerCup, orderQuantity);
            return "ok";
        } catch (Exception e) {
            log.info(e.toString());
        }
        return "err";
    }
}
