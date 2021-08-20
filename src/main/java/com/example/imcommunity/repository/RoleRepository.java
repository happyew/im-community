package com.example.imcommunity.repository;

import com.example.imcommunity.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByName(String name);

    List<Role> findRolesByPermissions(String permission);

    @Query("select r from Role r join r.permissions p where LOWER(p) LIKE CONCAT('%', LOWER(:permission), '%')")
    List<Role> findRolesByPermissionsLike(@Param("permission") String permission);
}