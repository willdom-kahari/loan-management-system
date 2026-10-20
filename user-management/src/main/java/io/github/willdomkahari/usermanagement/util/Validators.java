package io.github.willdomkahari.usermanagement.util;

import io.github.willdomkahari.usermanagement.exception.BlankStringException;
import io.github.willdomkahari.usermanagement.exception.InvalidEmailException;
import io.github.willdomkahari.usermanagement.exception.ObjectNotNullException;
import io.github.willdomkahari.usermanagement.exception.PasswordMustBeBCryptException;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Validators {
    private static final Pattern STRICT_EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    );

    private static final Pattern BCRYPT =
            Pattern.compile("\\A\\$2[aby]?\\$\\d{2}\\$[./0-9A-Za-z]{53}\\z");

    private Validators(){}

    public static void notNull(Object value, String name){
        if (Objects.isNull(value)) {
            throw new ObjectNotNullException(name);
        }
    }

    public static void notBlank(String value, String name){
        notNull(value, name);
        if (value.isBlank()){
            throw new BlankStringException(name);
        }
    }

    public static void isEmail(String value){
        notBlank(value, "email");
        if (!STRICT_EMAIL_PATTERN.matcher(value).matches()){
            throw new InvalidEmailException();
        }
    }

    public static void isBCrypt(String value){
        notBlank(value, "password");
        if (!BCRYPT.matcher(value).matches()){
            throw new PasswordMustBeBCryptException();
        }
    }
}
