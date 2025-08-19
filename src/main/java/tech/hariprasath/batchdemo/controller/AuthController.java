package tech.hariprasath.batchdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.hariprasath.batchdemo.dto.AuthRequest;
import tech.hariprasath.batchdemo.dto.AuthResponse;
import tech.hariprasath.batchdemo.entity.User;
import tech.hariprasath.batchdemo.repository.UserRepository;
import tech.hariprasath.batchdemo.roles.Role;
import tech.hariprasath.batchdemo.service.JwtService;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(AuthRequest authRequest) {
        try {
            if(userRepository.existsByEmail(authRequest.getEmail()))
                throw new BadCredentialsException("Email Already Exists");

            User newUser = new User();
            newUser.setEmail(authRequest.getEmail());
            newUser.setPassword(authRequest.getPassword());
            newUser.setName(authRequest.getEmail().split("@")[0]);
            newUser.setRole(Role.USER);

            userRepository.save(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("Registered Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            throw  new BadCredentialsException("Email Already Exists");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest){
        try {
            authenticate(authRequest.getEmail(), authRequest.getPassword());
            final String jwtToken = jwtService.generateToken(authRequest.getEmail());

            ResponseCookie cookie = ResponseCookie
                    .from("jwt", jwtToken)
                    .httpOnly(true)
                    .path("/")
                    .maxAge(Duration.ofDays(1))
                    .sameSite("none")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(AuthResponse.builder().token(jwtToken).success(true).build());
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username and password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        ResponseCookie cookie = ResponseCookie
                .from("jwt", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .sameSite("none")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Logged out successfully.");
    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
    }
}
