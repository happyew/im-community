package com.example.imcommunity.service;

import com.example.imcommunity.entity.Role;

public interface RoleService {
    Role findRoleByName(String name);

    Role create(Role role);

    void update(Role role);
}
