package io.github.willdomkahari.loanmanagement.user;

import io.github.willdomkahari.usermanagement.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/users")
class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity<Void> registerUsers(@RequestBody UserRegistrationRecord user){
        switch (user.role()){
            case ADMIN -> userService.createAdmin(user.username(), user.email(), user.password());
            case MANAGER -> userService.createManager(user.username(), user.email(), user.password());
            case LOAN_OFFICER -> userService.createLoanOfficer(user.username(), user.email(), user.password());
        }
        return ResponseEntity.ok(null);
    }
}
