package com.example.cbk.service;

import com.example.cbk.entity.Role;

import java.util.List;

public interface RoleService {
        List<Role> getAllRoles();
        Role addRole(Role role);
}

