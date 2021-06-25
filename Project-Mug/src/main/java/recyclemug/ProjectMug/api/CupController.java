package recyclemug.ProjectMug.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.repository.CupRepository;
import recyclemug.ProjectMug.service.CupService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CupController {

    private final CupService cupService;
    private final CupRepository cupRepository;

    @PostMapping("/cup/add")
    @PostAuthorize("hasAnyRole('ADMIN')")
    public void saveCup(@RequestBody @Valid CreateCupRequest request) {
        Cup cup = Cup.createCup(request.name, request.price, request.profilePicture);
        cupService.addCup(cup);
    }

    @DeleteMapping("/cup/remove/{cupId}")
    @PostAuthorize("hasAnyRole('ADMIN')")
    public void removeCup(@PathVariable Long cupId) {
        try {
            cupService.removeCup(cupId);
        } catch (IllegalStateException e) {
            log.error("컵이 존재하지 않는 상태에서 삭제하려 합니다.");
        }
    }

    @GetMapping("/cup/list")
    @PostAuthorize("hasAnyRole('ADMIN')")
    public List<Cup> findAllCups() {
        return cupRepository.findAllCups();
    }

    @GetMapping("/cup/{cupId}")
    @PostAuthorize("hasAnyRole('ADMIN')")
    public Cup findCup(@PathVariable("cupName") Long cupId) {
        return cupRepository.findByCupId(cupId);
    }

    @Data
    static class CreateCupRequest {
        private String name;
        private int price;
        private String profilePicture;
    }
}