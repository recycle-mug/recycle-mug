package recyclemug.ProjectMug.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.repository.CupRepository;
import recyclemug.ProjectMug.service.CupService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CupController {

    private final CupService cupService;
    private final CupRepository cupRepository;

    @PostMapping("/cup/add")
    @PostAuthorize("hasAnyRole('ADMIN')")
    public void saveCup(@RequestParam("file") MultipartFile file,
                        @ModelAttribute("request") CreateCupRequest request,
                        HttpServletRequest httpServletRequest) {
        // Image File 처리 original File name 을 만들어 새로운 src 주소를 만들어낸다.
        Date date = new Date();
        StringBuilder sb = new StringBuilder();
        String picturePathName = httpServletRequest.getServletContext().getRealPath("/images/cups/");

        // file image 가 없을 경우
        if (file.isEmpty()) {
            sb.append("none");
        } else {
            sb.append(date.getTime());
            sb.append(file.getOriginalFilename());
        }

        picturePathName += sb.toString();

        if (!file.isEmpty()) {
            File dest = new File(picturePathName);
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Cup cup = Cup.createCup(request.name, request.price, request.stockQuantity, picturePathName);
        cupService.addCup(cup);
    }

    @DeleteMapping("/cup/remove/{cupId}")
    @PostAuthorize("hasAnyRole('ADMIN')")
    public void removeCup(@PathVariable Long cupId) {
        try {
            Cup cup = cupRepository.findByCupId(cupId);
            File file = new File(cup.getProfilePictureAddress());
            if (file.delete()) {
                log.info("컵 이미지 파일 지워졌습니다.");
            } else {
                log.warn("지울 컴 이미지 파일이 없습니다.");
            }
            cupService.removeCup(cupId);
        } catch (NullPointerException e) {
            log.error("삭제하려는 컵이 존재하지 않습니다.");
        }
    }

    @GetMapping("/cup/list")
    @PostAuthorize("hasAnyRole('ADMIN')")
    public List<Cup> findAllCups() {
        return cupRepository.findAllCups();
    }

    @GetMapping("/cup/{cupId}")
    @PostAuthorize("hasAnyRole('ADMIN')")
    public CreateCupResponse findCup(@PathVariable Long cupId) throws IOException {
        Cup cup = cupRepository.findByCupId(cupId);
        FileInputStream imageStream = new FileInputStream(cup.getProfilePictureAddress());
        byte[] imageByteArray = imageStream.readAllBytes();
        imageStream.close();

        return new CreateCupResponse(cup.getName(), cup.getPrice(), cup.getStockQuantity(), imageByteArray);
    }

    @Data
    static class CreateCupRequest {
        private String name;
        private int price;
        private int stockQuantity;
    }

    @Data
    static class CreateCupResponse {
        private String name;
        private int price;
        private int stockQuantity;
        private byte[] image;

        public CreateCupResponse(String name, int price, int stockQuantity, byte[] image) {
            this.name = name;
            this.price = price;
            this.stockQuantity = stockQuantity;
            this.image = image;
        }
    }

}