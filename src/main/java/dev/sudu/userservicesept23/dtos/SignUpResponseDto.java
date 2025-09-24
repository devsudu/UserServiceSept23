package dev.sudu.userservicesept23.dtos;

import dev.sudu.userservicesept23.models.Role;
import dev.sudu.userservicesept23.models.Token;
import dev.sudu.userservicesept23.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignUpResponseDto {
    private String name;
    private String email;
    private String token;
    private Long expiryAt;
    private Boolean isActive;
    private List<Role> roles;

    public static SignUpResponseDto from(User user){
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        if(user == null) {
            return signUpResponseDto;
        };

        signUpResponseDto.setName(user.getName());
        signUpResponseDto.setEmail(user.getEmail());
        signUpResponseDto.setToken("");
        signUpResponseDto.setExpiryAt(0L);
        signUpResponseDto.setIsActive(false);
        signUpResponseDto.setRoles(user.getRoles());
        return signUpResponseDto;
    }

    public static SignUpResponseDto fromToken(Token token){
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        if(token == null) {
            return signUpResponseDto;
        };

        signUpResponseDto.setName(token.getUser().getName());
        signUpResponseDto.setEmail(token.getUser().getEmail());
        signUpResponseDto.setToken(token.getToken());
        signUpResponseDto.setExpiryAt(token.getExpiryAt());
        signUpResponseDto.setIsActive(token.getIsActive());
        signUpResponseDto.setRoles(token.getUser().getRoles());
        return signUpResponseDto;
    }
}
