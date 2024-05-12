package ba.unsa.etf.ppis.mapper;

import ba.unsa.etf.ppis.Model.Role;
import ba.unsa.etf.ppis.dto.RoleDto;


public class RoleMapper {
    public static RoleDto toRoleDto(Role roleEntity) {
        return new RoleDto(roleEntity.getRoleId(),roleEntity.getName());
    }

    public static Role toRoleEntity(RoleDto roleDto) {
        Role roleEntity = new Role();
        roleEntity.setName(roleDto.getName());
        return roleEntity;
    }
}

