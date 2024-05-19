package ba.unsa.etf.ppis.Service;


import ba.unsa.etf.ppis.Model.Role;
import ba.unsa.etf.ppis.Repository.RoleRepository;
import ba.unsa.etf.ppis.dto.RoleDto;
import ba.unsa.etf.ppis.exceptions.RoleNotFoundException;
import ba.unsa.etf.ppis.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDto addRole(RoleDto roleDto) {
        String roleName = roleDto.getName();
        if (roleRepository.existsByName(roleName)) {
            throw new IllegalArgumentException("Role with name '" + roleName + "' already exists.");
        }

        Role roleEntity = new Role(roleName);
        Role savedRole = roleRepository.save(roleEntity);

        return RoleMapper.toRoleDto(savedRole);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roleEntities = (List<Role>) roleRepository.findAll();
        return roleEntities.stream()
                .map(RoleMapper::toRoleDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto getRoleById(String roleId) {
        var role = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException("Role not found"));
        return RoleMapper.toRoleDto(role);
    }

}
