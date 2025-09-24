package dev.sudu.userservicesept23.dtos;

import dev.sudu.userservicesept23.enums.RoleAction;
import dev.sudu.userservicesept23.models.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponseDto {
    private Long id;
    private String name;
    private RoleAction roleAction;

    public static RoleResponseDto from(Role role, RoleAction roleAction) {
        if(role == null) return null;
        RoleResponseDto roleResponseDto = new RoleResponseDto();
        roleResponseDto.setId(role.getId());
        roleResponseDto.setName(role.getName());
        roleResponseDto.setRoleAction(roleAction);
        return roleResponseDto;
    }
}
