package dev.sudu.userservicesept23.controllers;

import dev.sudu.userservicesept23.dtos.*;
import dev.sudu.userservicesept23.enums.RoleAction;
import dev.sudu.userservicesept23.exceptions.*;
import dev.sudu.userservicesept23.models.Role;
import dev.sudu.userservicesept23.models.Token;
import dev.sudu.userservicesept23.models.User;
import dev.sudu.userservicesept23.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto signUpRequestDto) throws MandatoryFieldException, UserAlreadyRegisteredException {
        User user = userService.signUp(signUpRequestDto.getName(), signUpRequestDto.getEmail(), signUpRequestDto.getPassword());

        return SignUpResponseDto.from(user);
    }

    @PostMapping("/login")
    public SignUpResponseDto loginIn(@RequestBody LoginRequestDto loginRequestDto) throws MandatoryFieldException, PasswordMismatchException {
        Token token = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        return SignUpResponseDto.fromToken(token);
    }

    @GetMapping("/validate/{token}")
    public SignUpResponseDto validateToken(@PathVariable("token") String token) throws InvalidTokenException {
        Token tokenObj = userService.validateToken(token);

        return SignUpResponseDto.fromToken(tokenObj);
    }

    @PatchMapping("/logout/{token}")
    public SignUpResponseDto logout(@PathVariable String token) throws InvalidTokenException {
        Token tokenObj = userService.logout(token);
        return SignUpResponseDto.fromToken(tokenObj);
    }

    @PostMapping("/role")
    public RoleResponseDto addRole(@RequestBody RoleRequestDto roleRequestDto) throws InvalidTokenException, InvalidRoleException {
        Role role = userService.addRole(roleRequestDto.getName());

        return RoleResponseDto.from(role, RoleAction.ADDED);
    }

    @PatchMapping("/role/{role_id}")
    public RoleResponseDto updateRole(@RequestBody RoleRequestDto roleRequestDto, @PathVariable("role_id") Long role_id) throws InvalidTokenException, InvalidRoleException {
        Role role = userService.updateRole(roleRequestDto.getName(), role_id);

        return RoleResponseDto.from(role, RoleAction.MODIFIED);
    }

    @DeleteMapping("/role/{role_id}")
    public RoleResponseDto deleteRole(@PathVariable("role_id") Long role_id) throws InvalidTokenException, InvalidRoleException {
        Role role = userService.deleteRole(role_id);

        return RoleResponseDto.from(role, RoleAction.REMOVED);
    }
}
