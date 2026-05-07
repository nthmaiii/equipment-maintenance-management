package equipment_management.auth.controller;

import equipment_management.auth.dto.AuthResponse;
import equipment_management.auth.dto.RegisterRequest;
import equipment_management.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.status(201).body(authService.register(request));
    }
}
