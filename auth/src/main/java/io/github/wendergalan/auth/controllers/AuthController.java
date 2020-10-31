package io.github.wendergalan.auth.controllers;

import io.github.wendergalan.auth.data.vo.UserVO;
import io.github.wendergalan.auth.jwt.JwtTokenProvider;
import io.github.wendergalan.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @RequestMapping("/testSecurity")
    public String teste() {
        return "Testado!";
    }

    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity login(@RequestBody UserVO userVO) {
        try {
            var username = userVO.getUserName();
            var password = userVO.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            var user = userRepository.findByUserName(username);
            var token = "";

            if (user != null)
                token = jwtTokenProvider.createToken(username, user.getRoles());
            else throw new UsernameNotFoundException("Username not found.");

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);

            return ok(model);
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid username and password.");
        }
    }

}
