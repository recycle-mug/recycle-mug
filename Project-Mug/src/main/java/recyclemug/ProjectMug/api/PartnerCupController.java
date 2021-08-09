package recyclemug.ProjectMug.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import recyclemug.ProjectMug.data.CreateMapPartnerInfoResponse;
import recyclemug.ProjectMug.data.CreateOrderResponse;
import recyclemug.ProjectMug.data.CreatePartnerCupRequest;
import recyclemug.ProjectMug.data.CreateReturnCupsRequest;
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.domain.cup.PartnerCup;
import recyclemug.ProjectMug.domain.cup.PartnerOrder;
import recyclemug.ProjectMug.domain.cup.PartnerReturn;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.dto.MapPartnerInfoDto;
import recyclemug.ProjectMug.dto.PartnerCupResponseDTO;
import recyclemug.ProjectMug.dto.PartnerReturnDto;
import recyclemug.ProjectMug.exception.NotEnoughStockException;
import recyclemug.ProjectMug.repository.PartnerCupRepository;
import recyclemug.ProjectMug.repository.PartnerOrderRepository;
import recyclemug.ProjectMug.repository.PartnerRepository;
import recyclemug.ProjectMug.repository.PartnerReturnRepository;
import recyclemug.ProjectMug.service.PartnerReturnService;
import recyclemug.ProjectMug.service.PartnerOrderService;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PartnerCupController {
    private final PartnerRepository partnerRepository;
    private final PartnerOrderService partnerOrderService;
    private final PartnerCupRepository partnerCupRepository;
    private final PartnerOrderRepository partnerOrderRepository;
    private final PartnerReturnRepository partnerReturnRepository;
    private final PartnerReturnService partnerReturnService;

    @GetMapping("/partner-cup/{partnerId}") // 특정 partner의 컵 목록
    @PreAuthorize("hasAnyRole('ADMIN','PARTNER')")
    @ResponseBody
    public List<PartnerCupResponseDTO> getPartnerCup(@PathVariable Long partnerId) throws IOException {
        List<PartnerCup> cupOfPartner = partnerCupRepository.findCupOfPartner(partnerId);
        ArrayList<PartnerCupResponseDTO> partnerCups = new ArrayList<>();
        for (PartnerCup p : cupOfPartner) {
            Cup cup = p.getCup();
            FileInputStream imageStream = new FileInputStream(cup.getProfilePictureAddress());
            byte[] image = imageStream.readAllBytes();
            imageStream.close();

            PartnerCupResponseDTO partnerCupResponseDTO = PartnerCupResponseDTO.builder()
                    .id(p.getId())
                    .name(cup.getName())
                    .stockQuantity(p.getStockQuantity())
                    .image(image)
                    .price(cup.getPrice())
                    .build();
            partnerCups.add(partnerCupResponseDTO);
        }
        return partnerCups;
    }
    @GetMapping("/partner/map/{partnerId}") // map에서 partner정보 확인
    @PreAuthorize("hasAnyRole('ADMIN','PARTNER','CUSTOMER')")
    @ResponseBody
    public ResponseEntity<CreateMapPartnerInfoResponse> getMapPartnerInfo(@PathVariable Long partnerId){
        try {
            List<PartnerCup> partnerCupsAll = partnerCupRepository.findCupOfPartner(partnerId);
            Partner partner = partnerRepository.findOne(partnerId);
            List<PartnerCupResponseDTO> partnerCups = new ArrayList<>();
            for (PartnerCup partnerCup : partnerCupsAll) {
                FileInputStream imageStream = new FileInputStream(partnerCup.getCup().getProfilePictureAddress());
                byte[] image = imageStream.readAllBytes();
                PartnerCupResponseDTO partnerCupResponseDTO = PartnerCupResponseDTO.builder()
                        .id(partnerCup.getId())
                        .name(partnerCup.getCup().getName())
                        .stockQuantity(partnerCup.getStockQuantity())
                        .image(image)
                        .price(partnerCup.getCup().getPrice())
                        .build();
                partnerCups.add(partnerCupResponseDTO);
            }
            FileInputStream profileImageStream = new FileInputStream(partner.getProfilePictureAddress());
            byte[] profileImage = profileImageStream.readAllBytes();
            MapPartnerInfoDto mapPartnerInfoDto = new MapPartnerInfoDto(partner.getBusinessName(),
                    partner.getAddress(),
                    partner.getNickname(),
                    profileImage,
                    partnerCups);
            return new ResponseEntity<>(new CreateMapPartnerInfoResponse("success","getPartnerInfo",mapPartnerInfoDto),HttpStatus.OK);
        }catch(NullPointerException e){
            log.error("NullPointException in MapPartnerInfo");
            return new ResponseEntity<>(new CreateMapPartnerInfoResponse("fail","NullPointException",null),HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            log.error("Exception in MapPartnerInfo");
            return new ResponseEntity<>(new CreateMapPartnerInfoResponse("fail","Exception",null),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/partner-cup/add") // partner의 cup 대여 신청을 admin이 승인했을 시 partnerCup 등록
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<CreateOrderResponse> addPartnerCup(@RequestBody @Valid CreatePartnerCupRequest request){
        PartnerOrder partnerOrder = partnerOrderRepository.findById(request.getPartnerOrderId());
        try{
            partnerOrderService.completeOrder(partnerOrder);
            log.info("Admin accept partner's order   id : " + partnerOrder.getId());
            return new ResponseEntity<>(new CreateOrderResponse("success","complete"),HttpStatus.OK);
        }catch (NotEnoughStockException e){
            return new ResponseEntity<>(new CreateOrderResponse("fail","Not enough cup"), HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            return new ResponseEntity<>(new CreateOrderResponse("fail","Invalid access"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/partner-cup/reject") // partner의 cup 대여 신청을 admin이 거부했을 시 partnerOrder 거부처리
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<CreateOrderResponse> rejectPartnerCup(@RequestBody @Valid CreatePartnerCupRequest request){
        PartnerOrder partnerOrder = partnerOrderRepository.findById(request.getPartnerOrderId());
        try{
            partnerOrderService.rejectOrder(partnerOrder);
            log.info("Admin reject partner's order   id : " + partnerOrder.getId());
            return new ResponseEntity<>(new CreateOrderResponse("success","reject"),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(new CreateOrderResponse("fail","Invalid access"), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/partner-cup/return/{partnerId}") // ADMIN이 partner에 반납된 사용 컵 회수해가는 컵 목록 UI
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public List<PartnerReturnDto> getPartnerReturnCups(@PathVariable Long partnerId){
        try {
            List<PartnerReturn> partnerReturns = partnerReturnRepository.findByPartnerId(partnerId);
            List<PartnerReturnDto> result = new ArrayList<>();
            for (PartnerReturn partnerReturn : partnerReturns) {
                PartnerReturnDto partnerReturnDto = new PartnerReturnDto(partnerReturn.getPartner().getBusinessName(),
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
    @PostMapping("/partner-cup/return") // ADMIN이 partner에 반납된 사용컵들 회수 로직 수행
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<CreateOrderResponse> returnPartnerCups(@RequestBody @Valid CreateReturnCupsRequest request){
        try {
            partnerReturnService.completeReturn(request.getPartnerId());
            log.info("Return cups for : " + request.getPartnerId());
            return new ResponseEntity<>(new CreateOrderResponse("success","Return Complete"),HttpStatus.OK);
        }catch(Exception e){
            log.error("Invalid PartnerId");
            return new ResponseEntity<>(new CreateOrderResponse("fail","Invalid partnerId"),HttpStatus.BAD_REQUEST);
        }
    }
}
