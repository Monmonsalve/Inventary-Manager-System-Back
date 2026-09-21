package com.api.manager.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.manager.models.RoleModel;
import com.api.manager.services.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    //Get all Rol
    @GetMapping
    public ArrayList<RoleModel> getRol(){
        return this.roleService.getRole();
    }

    //Create Rol
    @PostMapping
    public RoleModel saveRole(@RequestBody RoleModel role){
        if(role != null){
            return this.roleService.saveRole(role);
        }else{
            return null;
        }
    }

    //Delte By id Rol
    @DeleteMapping(path = "/{id}")
    public String deleteRoleById(@PathVariable("id")Long id){
        boolean ok = this.roleService.deleteRole(id);
        if(ok){
            return "Role deleted with id: "+ id;
        }else{
            return "Could not delete Role with id: " + id;
        }
    }

}
