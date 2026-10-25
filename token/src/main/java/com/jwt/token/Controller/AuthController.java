package com.jwt.token.Controller;

import com.jwt.token.dto.JwtResponse;
import com.jwt.token.dto.LoginRequest;
import com.jwt.token.dto.SignupRequest;
import com.jwt.token.entity.User;
import com.jwt.token.repository.UserRepository;
import com.jwt.token.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    @PostMapping("/signup")
    public String signup (@RequestBody SignupRequest request){
        if(userRepository.findByUsername(request . getUsername()).isPresent()){
            return "User pehle se hai";

        }
        User user=new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request .getPassword()));
        userRepository.save(user);
        return "Signup ho gaya";
    }
    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
        );
        String token=jwtUtil.generateToken(request.getUsername());
        return new JwtResponse(token);
    }
    @GetMapping("/hello")
    public String hello(){
        return  "token sahi hai, tu andar hai";
    }


}
