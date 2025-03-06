package com.aplicaciontec.tecnify.auth;

import com.aplicaciontec.tecnify.jwt.JwtService;
import com.aplicaciontec.tecnify.user.User;
import com.aplicaciontec.tecnify.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// evita el error de  private final UserRepository userRepository; y inicia su constructor automaticamente
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService; // se usa esta variable para el getToken porque no se puede hacer como static
    private final PasswordEncoder passwordEncoder; // se usa para hacer la encroptacion
    //Login
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (Exception e) {
            throw new RuntimeException("Credenciales incorrectas");
        }
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow() ;
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nombre(request.getNombre())
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
