/*
 * @ (#) PermissionRepository.java  1.0 7/8/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.authservice.repository;


import com.backend.authservice.entity.Permission;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/8/2025
 * @version:    1.0
 */
@RepositoryRestResource(exported = false)
@Hidden
public interface PermissionRepository extends JpaRepository<Permission, String> {
    @NativeQuery("SELECT p.`description` FROM permission p join roles_permissions rp on p.permission_id = rp.permission_id where rp.role_id = :roleId and p.is_deleted = false")
    String findDescriptionByRoleId(@Param("roleId") String roleId);

    Optional<Permission> findByDescription(String description);

    boolean existsByDescription(String description);
    Optional<Permission> deletePermissionByPermissionId(String permissionId);
}
