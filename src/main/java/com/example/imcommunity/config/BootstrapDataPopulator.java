package com.example.imcommunity.config;

import com.example.imcommunity.entity.Permission;
import com.example.imcommunity.entity.Role;
import com.example.imcommunity.entity.User;
import com.example.imcommunity.service.UserService;
import com.example.imcommunity.util.SaltUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class BootstrapDataPopulator implements InitializingBean {
    private final UserService userService;

    public BootstrapDataPopulator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        User root = new User();
        root.setUsername("root");
        root.setPassword("root");
        root.setSalt(SaltUtil.getSalt());

        Role adminRole = new Role();
        adminRole.setName("admin");

        Permission adminPermission = new Permission();
        adminPermission.setName("*");

        root.getRoles().add(adminRole);
        adminRole.getPermissions().add(adminPermission);
        adminPermission.getRoles().add(adminRole);

        userService.create(root);
    }
}
