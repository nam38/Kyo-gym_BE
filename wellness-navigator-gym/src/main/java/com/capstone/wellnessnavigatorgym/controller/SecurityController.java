package com.capstone.wellnessnavigatorgym.controller;

import com.capstone.wellnessnavigatorgym.dto.course.CourseDetail;
import com.capstone.wellnessnavigatorgym.dto.request.LoginRequest;
import com.capstone.wellnessnavigatorgym.dto.request.SignupRequest;
import com.capstone.wellnessnavigatorgym.dto.response.JwtResponse;
import com.capstone.wellnessnavigatorgym.dto.response.MessageResponse;
import com.capstone.wellnessnavigatorgym.dto.response.SocialResponse;
import com.capstone.wellnessnavigatorgym.dto.tree.RecommendationDTO;
import com.capstone.wellnessnavigatorgym.dto.tree.TreeNode;
import com.capstone.wellnessnavigatorgym.dto.tree.UserDataDTO;
import com.capstone.wellnessnavigatorgym.entity.*;
import com.capstone.wellnessnavigatorgym.security.jwt.JwtUtility;
import com.capstone.wellnessnavigatorgym.security.userprinciple.UserPrinciple;
import com.capstone.wellnessnavigatorgym.service.*;
import com.capstone.wellnessnavigatorgym.utils.BuildDecisionTree;
import com.capstone.wellnessnavigatorgym.utils.ConverterMaxCode;
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
import java.util.*;
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
//            Account a
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

        Customer customerLimit = customerService.customerLimit();
        signupRequest.setCustomerCode(ConverterMaxCode.generateNextId(customerLimit.getCustomerCode()));

        customerService.save(new Customer(
                signupRequest.getCustomerCode(),
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


/*    @PostMapping("/signup")
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

        Customer customerLimit = customerService.customerLimit();
        signupRequest.setCustomerCode(ConverterMaxCode.generateNextId(customerLimit.getCustomerCode()));
        Customer customer = new Customer(
                signupRequest.getCustomerCode(),
                signupRequest.getName(),
                signupRequest.getEmail(),
                signupRequest.getPhone(),
                signupRequest.getGender(),
                signupRequest.getDateOfBirth(),
                signupRequest.getIdCard(),
                signupRequest.getAddress(),
                signupRequest.getCustomerImg(),
                true,
                account);
        customerService.save(customer);

        UserDataDTO userDataDTO = new UserDataDTO();
        userDataDTO.setActivity_level(signupRequest.getActivity_level());
        userDataDTO.setAge(signupRequest.getAge());
        userDataDTO.setGender(signupRequest.getGenderUser());
        userDataDTO.setBmi(signupRequest.getBmi());
        userDataDTO.setTraining_goals(signupRequest.getTraining_goals());
        userDataDTO.setTraining_history(signupRequest.getTraining_history());

        // Lấy danh sách thuộc tính và kiểm tra chúng
        List<String> attributeNames = getAttributeNames();
        if (attributeNames == null || attributeNames.isEmpty()) {
            return ResponseEntity.badRequest().body(new RecommendationDTO(null, "Attribute names are missing or invalid"));
        }
        // Lấy dữ liệu và xây dựng cây quyết định
        List<TrackDataAi> trackDataAis = trackDataAiService.getAllTrackDataAi();
        if (trackDataAis.isEmpty()) {
            return ResponseEntity.badRequest().body(new RecommendationDTO(null, "No track data available to build the decision tree"));
        }

        TreeNode decisionTree = buildDecisionTree.buildDecisionTree(trackDataAis, attributeNames);

        // Tạo map dữ liệu người dùng từ TrackDataAi
        Map<String, Object> userData = extractUserDataFromTrackDataAi(userDataDTO);

        // Duyệt qua cây quyết định và tìm đề xuất
        List<Course> recommendations = traverseDecisionTree(decisionTree, userData);

        if (!recommendations.isEmpty()) {
            for (Course course: recommendations) {
                if (course.getCourseId()  != null && customer.getCustomerId() != null) {
                    Boolean recommendedStatus = false;
                    CustomerCourse customerCourse = new CustomerCourse( recommendedStatus,customer,course);
                    customerCourseService.save(customerCourse);
                } else {
                    return ResponseEntity.badRequest().build();
                }
            }
        }else {
            return ResponseEntity.ok(new RecommendationDTO(null, "them customerCourseService thất bại  "));
        }
        return new ResponseEntity<>(new MessageResponse("them customerCourseService thanh cong"), HttpStatus.CREATED);


//        return ResponseEntity.ok(new RecommendationDTO(recommendations, "Course recommendations generated successfully"));


//        return new ResponseEntity<>(new MessageResponse("Account registration successful!"), HttpStatus.OK);
    }*/

//    @PostMapping("/test")
//    public ResponseEntity<?> insertCourse(@RequestBody  List<Course> recommendations){
//
//        Customer customer =customerService.findById(7);
//        if (!recommendations.isEmpty()) {
//            for (Course course: recommendations) {
//                Integer courseId = course.getCourseId() ;
//                Integer customerId = customer.getCustomerId();
//                if (courseId != null && customerId != null) {
//                    Boolean recommendedStatus = true;
//                    customerCourseService.save(
//                            new CustomerCourse(recommendedStatus, courseId,customerId));
//                } else {
//                    return ResponseEntity.badRequest().build();
//                }
//            }
//        }else {
//            return ResponseEntity.ok(new RecommendationDTO(null, "them customerCourseService thất bại  "));
//        }
////        return ResponseEntity.ok(new RecommendationDTO(recommendations, "them customerCourseService thanh cong "));
//        return new ResponseEntity<>(new MessageResponse("them customerCourseService thanh cong"), HttpStatus.CREATED);
//
//    }
}



