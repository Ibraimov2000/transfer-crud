package com.example.cbk.service;

import com.example.cbk.entity.Role;
import com.example.cbk.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        Iterable<Role> iterable = roleRepository.findAll();
        List<Role> set = new ArrayList<>();
        iterable.forEach(set::add);
        return set;
    }

    @Override
    public void addRole(Role role) {
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        roleRepository.saveAll(roles);
    }
}
