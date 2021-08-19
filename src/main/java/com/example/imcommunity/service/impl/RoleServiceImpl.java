package com.example.imcommunity.service.impl;

import com.example.imcommunity.entity.Role;
import com.example.imcommunity.repository.RoleRepository;
import com.example.imcommunity.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }

    @Override
    public Role create(Role role) {
        Role roleExisted = findRoleByName(role.getName());
        if (roleExisted == null){
            return roleRepository.save(role);
        }
        return null;
    }
}
