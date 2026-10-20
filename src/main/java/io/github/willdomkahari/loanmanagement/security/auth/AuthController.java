package io.github.willdomkahari.loanmanagement.security.auth;

import io.github.willdomkahari.loanmanagement.api.ApiResponseWrapper;
import io.github.willdomkahari.loanmanagement.user.AppUserDto;
import io.github.willdomkahari.usermanagement.ports.CurrentUser;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authService;
    private final CurrentUser currentUser;

    @PostMapping
    @SecurityRequirements
    public ResponseEntity<ApiResponseWrapper<AuthResponse>> auth(@RequestBody AuthRequest request){
        AuthResponse response = authService.authenticate(request);
        return ResponseEntity.ok(ApiResponseWrapper.success("Authentication successful", response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponseWrapper<AppUserDto>> authenticatedUser(){
        AppUserDto loggedInUser = AppUserDto.of(currentUser.getUser());
        return ResponseEntity.ok(ApiResponseWrapper.success("Successfully retrieved logged in user details", loggedInUser));
    }
}
