package equipment_management.auth.service;

import equipment_management.auth.dto.AuthResponse;
import equipment_management.auth.dto.RegisterRequest;
import equipment_management.auth.entity.User;
import equipment_management.auth.repository.UserRepository;
import equipment_management.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    public AuthResponse register (RegisterRequest request)
    {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(u -> { throw new RuntimeException("Email already exists"); });

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .role(request.getRole())
                .build();
        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token, user.getEmail(), user.getRole());
    }
}