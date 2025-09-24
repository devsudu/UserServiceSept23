package dev.sudu.userservicesept23.exceptions;

public class UserAlreadyRegisteredException extends Exception {
    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}
