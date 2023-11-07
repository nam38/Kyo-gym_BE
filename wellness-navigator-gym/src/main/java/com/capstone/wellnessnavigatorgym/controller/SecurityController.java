package com.capstone.wellnessnavigatorgym.controller;

import com.capstone.wellnessnavigatorgym.dto.request.LoginRequest;
import com.capstone.wellnessnavigatorgym.dto.request.SignupRequest;
import com.capstone.wellnessnavigatorgym.dto.response.JwtResponse;
import com.capstone.wellnessnavigatorgym.dto.response.MessageResponse;
import com.capstone.wellnessnavigatorgym.dto.response.SocialResponse;
import com.capstone.wellnessnavigatorgym.entity.Account;
import com.capstone.wellnessnavigatorgym.entity.Customer;
import com.capstone.wellnessnavigatorgym.entity.Role;
import com.capstone.wellnessnavigatorgym.security.jwt.JwtUtility;
import com.capstone.wellnessnavigatorgym.security.userprinciple.UserPrinciple;
import com.capstone.wellnessnavigatorgym.service.IAccountService;
import com.capstone.wellnessnavigatorgym.service.ICustomerService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/public")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SecurityController {

//    @Value("${google.clientId}")
//    String googleClientId;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtility.generateJwtToken(authentication);
        UserPrinciple userDetails = (UserPrinciple) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                new JwtResponse(
                        jwt,
                        userDetails.getId(),
                        userDetails.getUsername(),
                        roles
                )
        );
    }

//    @PostMapping("/oauth/google")
//    public ResponseEntity<?> loginGoogle(@RequestBody SocialResponse jwtResponseSocial) {
//        final NetHttpTransport netHttpTransport = new NetHttpTransport();
//        final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
//        GoogleIdTokenVerifier.Builder builder =
//                new GoogleIdTokenVerifier.Builder(netHttpTransport, jacksonFactory)
//                        .setAudience(Collections.singletonList(googleClientId));
//        try {
//            final GoogleIdToken googleIdToken = GoogleIdToken.parse(builder.getJsonFactory(), jwtResponseSocial.getToken());
//            final GoogleIdToken.Payload payload = googleIdToken.getPayload();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (accountService.existsByUsername(signupRequest.getUsername())) {
            return new ResponseEntity<>(new MessageResponse("The username existed! Please try again!"), HttpStatus.OK);
        }

        if (accountService.existsByEmail(signupRequest.getEmail())) {
            return new ResponseEntity<>(new MessageResponse("The email existed! Please try again!"), HttpStatus.OK);
        }

        Account account = new Account();
        account.setUserName(signupRequest.getUsername());
        account.setEncryptPassword(passwordEncoder.encode(signupRequest.getPassword()));
        account.setEmail(signupRequest.getEmail());
        account.setIsEnable(true);
        accountService.save(account);

        Role role = new Role(1, "ROLE_USER");
        Set<Role> tempRoles = account.getRoles();
        tempRoles.add(role);
        account.setRoles(tempRoles);

        customerService.save(new Customer(
                signupRequest.getName(),
                signupRequest.getEmail(),
                signupRequest.getPhone(),
                signupRequest.getGender(),
                signupRequest.getDateOfBirth(),
                signupRequest.getIdCard(),
                signupRequest.getAddress(),
                true,
                account
        ));
        return new ResponseEntity<>(new MessageResponse("Account registration successful!"), HttpStatus.OK);
    }


}

