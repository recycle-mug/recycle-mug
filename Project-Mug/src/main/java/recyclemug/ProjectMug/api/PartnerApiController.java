package recyclemug.ProjectMug.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import recyclemug.ProjectMug.data.*;
import recyclemug.ProjectMug.domain.cup.PartnerCup;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.repository.PartnerCupRepository;
import recyclemug.ProjectMug.repository.PartnerRepository;
import recyclemug.ProjectMug.service.PartnerService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PartnerApiController {

    private final PartnerService partnerService;
    private final PartnerRepository partnerRepository;
    @PostMapping("/join/partner")
    @ResponseBody
    public ResponseEntity<CreateJoinResponse> savePartner(@RequestBody @Valid CreatePartnerRequest request, HttpServletRequest httpServletRequest) {
        String pictureAddress = httpServletRequest.getServletContext().getRealPath("/images/users/default_user.jpg");
        try {
            Partner partner = Partner.createPartner(request.getEmail(), request.getPassword(), request.getPhoneNumber(),
                    request.getBusinessName(), request.getAddress() , request.getLatitude(), request.getAltitude());
            partner.setProfilePictureAddress(pictureAddress);
            partnerService.join(partner);
            return ResponseEntity.ok(new CreateJoinResponse("success", "회원가입에 성공했습니다."));
        } catch (IllegalStateException e) {
            log.error("FAIL TO JOIN PARTNER");
        } catch (Exception e) {
            log.error("FAIL");
        }
        return null;
    }

    @GetMapping("/partner/{partnerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PARTNER')")
    @ResponseBody
    public CreatePartnerResponse getPartner(@PathVariable Long partnerId) {
        try {
            Partner partner = partnerService.findById(partnerId);
            log.info("get Partner success");
            return createPartnerResponse(partner);
        } catch (NullPointerException e) {
            log.error("DB에 없는 Partner id 조회");
        } catch (FileNotFoundException e) {
            log.error("No image for partner id: " + partnerId);
        } catch (IOException e) {
            log.error("IOException");
        }
        return null;
    }
    @PatchMapping("/partner/{partnerId}")
    @PreAuthorize("hasAnyRole('ADMIN','PARTNER')")
    @ResponseBody
    public ResponseEntity<UpdateUserResponse> updatePartner(@PathVariable Long partnerId, @RequestBody @Valid PartnerModifyDTO partnerModifyDTO){
        try {
            Partner partner = partnerRepository.findOne(partnerId);
            partnerService.modifyPartnerInfo(partner,partnerModifyDTO);
            log.info("Partner's info success to update.");
            return new ResponseEntity<>(new UpdateUserResponse("success","Update Partner's info"), HttpStatus.OK);
        }catch(RuntimeException e){
            log.error("Partner's info fail to update (RuntimeException)");
            return new ResponseEntity<>(new UpdateUserResponse("fail","Wrong password type"),HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            log.error("Partner's info fail to update (Invalid data)");
            return new ResponseEntity<>(new UpdateUserResponse("fail","Invalid Data"),HttpStatus.BAD_REQUEST);
        }
    }

    public CreatePartnerResponse createPartnerResponse(Partner partner) throws IOException {
        CreatePartnerResponse response = CreatePartnerResponse.builder()
                .id(partner.getId())
                .email(partner.getEmail())
                .phoneNumber(partner.getPhoneNumber())
                .point(partner.getPoint())
                .nickname(partner.getNickname())
                .businessName(partner.getBusinessName())
                .address(partner.getAddress())
                .latitude(partner.getLatitude())
                .longitude(partner.getLongitude())
                .build();
        FileInputStream imageStream = new FileInputStream(partner.getProfilePictureAddress());
        byte[] image = imageStream.readAllBytes();
        imageStream.close();
        response.setProfilePicture(image);
        return response;
    }
}
