package recyclemug.ProjectMug.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import recyclemug.ProjectMug.data.CreateOrderResponse;
import recyclemug.ProjectMug.data.CreateReturnCupsRequest;
import recyclemug.ProjectMug.domain.cup.PartnerReturn;
import recyclemug.ProjectMug.dto.PartnerReturnDto;
import recyclemug.ProjectMug.repository.PartnerReturnRepository;
import recyclemug.ProjectMug.service.PartnerReturnService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PartnerReturnApiController {

    private final PartnerReturnRepository partnerReturnRepository;
    private final PartnerReturnService partnerReturnService;

    @GetMapping("/partner-cup/return") // 모든 Partner한테 반납된 컵 목록 UI
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public List<PartnerReturnDto> getAllPartnerReturnCups(){
        List<PartnerReturn> partnerReturns = partnerReturnRepository.findAll();
        List<PartnerReturnDto> partnerReturnDtos = new ArrayList<>();
        for(PartnerReturn partnerReturn : partnerReturns){
            PartnerReturnDto partnerReturnDto = new PartnerReturnDto(
                    partnerReturn.getId(),
                    partnerReturn.getPartner().getBusinessName(),
                    partnerReturn.getCup().getName(),
                    partnerReturn.getReturnQuantity());
            partnerReturnDtos.add(partnerReturnDto);
        }
        return partnerReturnDtos;
    }

    @GetMapping("/partner-cup/return/{partnerId}") // ADMIN이 특정 partner한테 반납된 컵 회수해가는 컵 목록 UI
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public List<PartnerReturnDto> getPartnerReturnCups(@PathVariable Long partnerId){
        try {
            List<PartnerReturn> partnerReturns = partnerReturnRepository.findByPartnerId(partnerId);
            List<PartnerReturnDto> result = new ArrayList<>();
            for (PartnerReturn partnerReturn : partnerReturns) {
                PartnerReturnDto partnerReturnDto = new PartnerReturnDto(
                        partnerReturn.getId(),
                        partnerReturn.getPartner().getBusinessName(),
                        partnerReturn.getCup().getName(),
                        partnerReturn.getReturnQuantity());
                result.add(partnerReturnDto);
            }
            return result;
        }catch(Exception e){
            log.error("Invalid PartnerId");
            return null;
        }
    }

    @PostMapping("/partner-cup/return") // ADMIN이 특정 partner에 반납된 사용컵들 회수 로직 수행
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<CreateOrderResponse> returnPartnerCups(@RequestBody @Valid CreateReturnCupsRequest request){
        try {
            partnerReturnService.completeReturn(request.getPartnerReturnId());
            log.info("Return cups for : " + request.getPartnerReturnId());
            return new ResponseEntity<>(new CreateOrderResponse("success","Return Complete"), HttpStatus.OK);
        }catch(NullPointerException e){
            log.error("NullPointException in return PartnerCups");
            return new ResponseEntity<>(new CreateOrderResponse("fail","Invalid partnerId"),HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            log.error("Exception in return PartnerCups");
            return new ResponseEntity<>(new CreateOrderResponse("fail",e.toString()),HttpStatus.BAD_REQUEST);
        }
    }
}
