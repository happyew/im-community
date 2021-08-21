package com.example.imcommunity.config;

import com.example.imcommunity.entity.User;
import com.example.imcommunity.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Set;

@Component
public class CustomRealm extends AuthorizingRealm {
    private final UserService userService;

    public CustomRealm(UserService userService) {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(1024);
        setCredentialsMatcher(credentialsMatcher);
        this.userService = userService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String primaryPrincipal = (String) principals.getPrimaryPrincipal();
        User user = userService.findByUsername(primaryPrincipal);
//        System.out.println(user);
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            Long userId = user.getId();
            user.getRoles().forEach(role -> {
//                System.out.println(role);
                simpleAuthorizationInfo.addRole(role.getName());
                Set<String> permissions = role.getPermissions();
                if ("admin".equals(role.getName())) {
                    if (!CollectionUtils.isEmpty(permissions)) {
                        permissions.forEach(permission -> {
//                            System.out.println(permission);
                            simpleAuthorizationInfo.addStringPermission(permission);
                        });
                    }
                } else {
                    if (!CollectionUtils.isEmpty(permissions)) {
                        permissions.forEach(permission -> {
//                            System.out.println(permission);
                            simpleAuthorizationInfo.addStringPermission(permission + userId);
                        });
                    }
                }
            });
            return simpleAuthorizationInfo;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String principal = (String) token.getPrincipal();
        User user = userService.findByUsername(principal);
        if (!ObjectUtils.isEmpty(user)) {
            return new SimpleAuthenticationInfo(principal, user.getPassword(),
                    ByteSource.Util.bytes(user.getSalt()), this.getName());
        }
        return null;
    }
}
