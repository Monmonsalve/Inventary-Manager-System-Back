package com.api.manager.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.manager.models.RoleModel;
import com.api.manager.repositories.IRoleRepository;

@Service
public class RoleService {
    
    @Autowired
    IRoleRepository roleRepository;

    //Get all Role
    public ArrayList<RoleModel> getRole(){
        return (ArrayList<RoleModel>) roleRepository.findAll();
    }

    //Create Role
    public RoleModel saveRole(RoleModel role){
        return roleRepository.save(role);
    }

    //Update Role
    public RoleModel updateRole(RoleModel request, Long id){
        RoleModel role = roleRepository.findById(id).get();

        role.setName(request.getName());

        return roleRepository.save(role);
    }

    //Delete Role
    public boolean deleteRole (Long id){
        try{
            roleRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
