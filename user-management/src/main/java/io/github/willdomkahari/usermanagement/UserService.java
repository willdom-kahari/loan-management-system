package io.github.willdomkahari.usermanagement;


import io.github.willdomkahari.usermanagement.annotation.DomainService;
import io.github.willdomkahari.usermanagement.exception.ActionNotAllowedException;
import io.github.willdomkahari.usermanagement.exception.DuplicateUserDetailsException;
import io.github.willdomkahari.usermanagement.exception.UserNotFoundException;
import io.github.willdomkahari.usermanagement.model.Role;
import io.github.willdomkahari.usermanagement.model.Username;
import io.github.willdomkahari.usermanagement.ports.CurrentUser;
import io.github.willdomkahari.usermanagement.ports.PasswordEncoderPort;
import io.github.willdomkahari.usermanagement.ports.UserRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DomainService
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepositoryPort userRepository;
    private final CurrentUser currentUser;
    private final PasswordEncoderPort passwordEncoder;

    public UserService(UserRepositoryPort userRepository, CurrentUser currentUser, PasswordEncoderPort passwordEncoder) {
        this.userRepository = userRepository;
        this.currentUser = currentUser;
        this.passwordEncoder = passwordEncoder;
    }

    public void seedDefaultUser(String username, String email, String password){
        boolean exists = userRepository.existsAsDefaultUser();
        if (exists){
            log.debug("Default user already exists");
            return;
        }
        log.info("Initialising default user");
        String bcryptEncoded = passwordEncoder.encode(password);
        User admin = User.admin(username, email, bcryptEncoded);
        admin.markDefault();
        userRepository.save(admin);
    }

    public void createAdmin(String username, String email, String password){
        checkAuthorization();
        checkUniqueness(username, email);
        String bcryptEncoded = passwordEncoder.encode(password);
        User admin = User.admin(username, email, bcryptEncoded);
        admin.activate();
        userRepository.save(admin);
    }

    private void checkUniqueness(String username, String email) {
        boolean unique = userRepository.isUserUnique(username, email);
        if (!unique){
            throw new DuplicateUserDetailsException("username or email already exists");
        }
    }

    public void createManager(String username, String email, String password){
        checkAuthorization();
        checkUniqueness(username, email);
        String bcryptEncoded = passwordEncoder.encode(password);
        User manager = User.manager(username, email, bcryptEncoded);
        manager.activate();
        userRepository.save(manager);
    }

    public void createLoanOfficer(String username, String email, String password){
        checkAuthorization();
        checkUniqueness(username, email);
        String bcryptEncoded = passwordEncoder.encode(password);
        User loanOfficer = User.loanOfficer(username, email, bcryptEncoded);
        loanOfficer.activate();
        userRepository.save(loanOfficer);
    }

    private void checkAuthorization() {
        if (!currentUser.getRole().equals(Role.ADMIN)) {
            throw new ActionNotAllowedException("You do not have permission to perform this action");
        }
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(Username.of(username))
                .orElseThrow(()-> new UserNotFoundException(username));
    }
}
