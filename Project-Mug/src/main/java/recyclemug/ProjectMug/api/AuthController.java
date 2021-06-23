package recyclemug.ProjectMug.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recyclemug.ProjectMug.dto.LoginDto;
import recyclemug.ProjectMug.dto.TokenDto;
import recyclemug.ProjectMug.jwt.JwtFilter;
import recyclemug.ProjectMug.jwt.TokenAuthenticationProvider;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final TokenAuthenticationProvider tokenAuthenticationProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(TokenAuthenticationProvider tokenAuthenticationProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenAuthenticationProvider = tokenAuthenticationProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
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

//    @PostMapping("/login/customer")
//    public ResponseEntity<TokenDto> authorizeCustomer(@Valid @RequestBody LoginDto loginDto) {
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
//
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = tokenAuthenticationProvider.createToken(authentication);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
//
//        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
//    }
//
//    @PostMapping("/login/partner")
//    public ResponseEntity<TokenDto> authorizePartner(@Valid @RequestBody LoginDto loginDto) {
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
//
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = tokenAuthenticationProvider.createToken(authentication);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
//
//        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
//    }
}
