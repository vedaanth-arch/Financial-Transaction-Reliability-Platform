package com.financialplatform.financial_transaction_reliability_platform.api.auth;

import com.financialplatform.financial_transaction_reliability_platform.demo.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final DemoStore store;
    public AuthController(DemoStore store) { this.store = store; }
    public record Registration(@NotBlank @Email @Size(max=320) String email, @NotBlank @Size(min=8,max=128) String password) {}
    public record Login(@NotBlank @Email String email, @NotBlank String password) {}
    public record UserView(java.util.UUID id, String email) {}
    public record TokenView(String accessToken, String tokenType, UserView user) {}
    @PostMapping("/register") @ResponseStatus(HttpStatus.CREATED)
    public UserView register(@Valid @RequestBody Registration request) {
        var user = store.register(request.email().trim(), request.password()); return new UserView(user.id(), user.email());
    }
    @PostMapping("/login")
    public TokenView login(@Valid @RequestBody Login request) {
        var user = store.registeredUser(request.email());
        String token = store.login(request.email(), request.password()); return new TokenView(token,"Bearer",new UserView(user.id(),user.email()));
    }
}
