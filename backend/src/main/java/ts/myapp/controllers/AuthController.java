package ts.myapp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

//    @Autowired
//    private AuthenticationManager authenticationManager;

//    @Autowired
//    private JwtProvider jwtProvider;

//    @PostMapping("/login")
//    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getUsername(),
//                        loginRequest.getPassword()
//                )
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = jwtProvider.generateToken(authentication);
//        return ResponseEntity.ok(new JwtResponse(jwt));
//    }
}
