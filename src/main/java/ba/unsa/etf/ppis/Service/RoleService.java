package ba.unsa.etf.ppis.Service;

import ba.unsa.etf.ppis.dto.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto addRole(RoleDto roleDto);
    List<RoleDto> getAllRoles();
    RoleDto getRoleById(String roleId);

}
