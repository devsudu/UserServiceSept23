package dev.sudu.userservicesept23.services;

import dev.sudu.userservicesept23.exceptions.*;
import dev.sudu.userservicesept23.models.Role;
import dev.sudu.userservicesept23.models.Token;
import dev.sudu.userservicesept23.models.User;

public interface UserService {

    User  signUp(String name, String email, String password) throws MandatoryFieldException, UserAlreadyRegisteredException;

    Token login(String email, String password) throws MandatoryFieldException, PasswordMismatchException;

    Token validateToken(String token) throws InvalidTokenException;

    Token logout(String token) throws InvalidTokenException;

    Role addRole(String name) throws InvalidRoleException, InvalidTokenException;

    Role updateRole(String name, Long role_id) throws InvalidRoleException, InvalidTokenException;

    Role deleteRole(Long role_id) throws InvalidRoleException, InvalidTokenException;
}
