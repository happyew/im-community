package com.example.imcommunity.config;

import com.example.imcommunity.entity.Role;
import com.example.imcommunity.entity.User;
import com.example.imcommunity.repository.RoleRepository;
import com.example.imcommunity.service.RoleService;
import com.example.imcommunity.service.UserService;
import com.example.imcommunity.util.SaltUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BootstrapDataPopulator implements InitializingBean {
    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    private RoleRepository roleRepository;


    public BootstrapDataPopulator(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //管理员
        User root = new User();
        root.setUsername("root");
        root.setPassword("root");
        root.setSalt(SaltUtil.getSalt());
        Role adminRole = new Role("admin");
        adminRole.getPermissions().add("*");
        root.getRoles().add(adminRole);
        userService.create(root);

        //普通用户
        Role userRole = new Role();
        userRole.setName("user");
        userRole.getPermissions().add("question:*:");
        userRole.getPermissions().add("profile:*:");
        roleService.create(userRole);

//        System.out.println("===================someTest========================");
//        System.out.println(roleRepository.findRolesByPermissions("*"));
//        System.out.println(roleRepository.findRolesByPermissionsLike("*"));
    }
}
