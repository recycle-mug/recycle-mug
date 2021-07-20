package recyclemug.ProjectMug.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import recyclemug.ProjectMug.repository.PartnerCupRepository;

@Service
@RequiredArgsConstructor
public class PartnerCupService {

    private final PartnerCupRepository partnerCupRepository;


}
