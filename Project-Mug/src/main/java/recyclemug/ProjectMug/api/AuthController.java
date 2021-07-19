package recyclemug.ProjectMug.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import recyclemug.ProjectMug.data.HeaderJwtDTO;
import recyclemug.ProjectMug.data.ResponseProfileDTO;
import recyclemug.ProjectMug.domain.user.Customer;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.domain.user.User;
import recyclemug.ProjectMug.dto.LoginDto;
import recyclemug.ProjectMug.dto.TokenDto;
import recyclemug.ProjectMug.jwt.JwtFilter;
import recyclemug.ProjectMug.jwt.TokenAuthenticationProvider;
import recyclemug.ProjectMug.repository.AdminRepository;
import recyclemug.ProjectMug.repository.CustomerRepository;
import recyclemug.ProjectMug.repository.PartnerRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
public class AuthController {

    private final TokenAuthenticationProvider tokenAuthenticationProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final CustomerRepository customerRepository;
    private final PartnerRepository partnerRepository;
    private final Base64.Decoder decoder;
    private final AdminRepository adminRepository;

    @Autowired
    public AuthController(TokenAuthenticationProvider tokenAuthenticationProvider,
                          AuthenticationManagerBuilder authenticationManagerBuilder,
                          CustomerRepository customerRepository,
                          PartnerRepository partnerRepository,
                          AdminRepository adminRepository) {
        this.tokenAuthenticationProvider = tokenAuthenticationProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.decoder = Base64.getDecoder();
        this.customerRepository = customerRepository;
        this.partnerRepository = partnerRepository;
        this.adminRepository = adminRepository;
    }

    @PostMapping("/login/*")
    public ResponseEntity<TokenDto> authorizeUser(@Valid @RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenAuthenticationProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'PARTNER', 'ADMIN')")
    public ResponseProfileDTO checkUserProfile(@RequestHeader(name = "Authorization") String token,
                                               HttpServletRequest httpServletRequest) throws IOException {
        String jwt = token.substring(7);
        String[] jwtSplit = jwt.split("\\.");
        String payload = new String(decoder.decode(jwtSplit[1]));
        String picturePath = httpServletRequest.getServletContext().getRealPath("/images/users/default_user.jpg");

        // Json -> Object
        ObjectMapper mapper = new ObjectMapper();
        HeaderJwtDTO headerDTO = mapper.readValue(payload, HeaderJwtDTO.class);

        if (headerDTO.getRole().equals("ROLE_CUSTOMER")) {
            List<Customer> findByEmail = customerRepository.findByEmail(headerDTO.getEmail());
            if (!findByEmail.isEmpty()) {
                User user = findByEmail.get(0);
                return new ResponseProfileDTO(user.getId(), user.getEmail(), user.getNickname(), findPicture(user.getProfilePictureAddress()), headerDTO.getRole());
            }
        } else if (headerDTO.getRole().equals("ROLE_PARTNER")) {
            List<Partner> findByEmail = partnerRepository.findByEmail(headerDTO.getEmail());
            if (!findByEmail.isEmpty()) {
                User user = findByEmail.get(0);
                return new ResponseProfileDTO(user.getId(), user.getEmail(), user.getNickname(), findPicture(user.getProfilePictureAddress()), headerDTO.getRole());
            }
        } else {
            User user = adminRepository.findByEmail(headerDTO.getEmail());
            return new ResponseProfileDTO(user.getId(), user.getEmail(), user.getNickname(), findPicture(picturePath), headerDTO.getRole());
        }
        return null;
    }

    public byte[] findPicture(String profilePictureAddress) throws IOException {
        FileInputStream imageStream = new FileInputStream(profilePictureAddress);
        byte[] image = imageStream.readAllBytes();
        imageStream.close();
        return image;
    }
}
