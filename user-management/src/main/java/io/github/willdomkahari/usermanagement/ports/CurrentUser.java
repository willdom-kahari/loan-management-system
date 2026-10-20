package io.github.willdomkahari.usermanagement.ports;

import io.github.willdomkahari.usermanagement.User;
import io.github.willdomkahari.usermanagement.model.Role;
import io.github.willdomkahari.usermanagement.model.Username;

public interface CurrentUser {
    Role getRole();
    User getUser();
    Username getUsername();
}
