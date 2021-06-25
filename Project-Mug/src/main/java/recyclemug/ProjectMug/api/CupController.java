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

    @DeleteMapping("/cup/remove")
    @PostAuthorize("hasAnyRole('ADMIN')")
    public void removeCup(@RequestBody @Valid CreateCupRequest request) {
        String cupName = request.getName();
        cupService.removeCup(cupName);
    }

    @GetMapping("/cup/list")
    @PostAuthorize("hasAnyRole('ADMIN')")
    public List<Cup> findAllCups() {
        return cupRepository.findAllCups();
    }

    @GetMapping("/cup/{cupName}")
    @PostAuthorize("hasAnyRole('ADMIN')")
    public Cup findCup(@PathVariable("cupName") String cupName) {
        List<Cup> cups = cupRepository.findByCupName(cupName);
        return cups.get(0);
    }

    @Data
    static class CreateCupRequest {
        private String name;
        private int price;
        private String profilePicture;
    }
}