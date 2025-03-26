package com.bursaefek.bursaefek.constant;

import lombok.Getter;

@Getter
public enum ERole {
    
    ROLE_ADMIN("Admin");

    private final String description;

    ERole (String description){
        this.description = description;
    }

    public static ERole findByDescription(String description){
        for (ERole role : values()){
            if (role.description.equalsIgnoreCase(description)){
                return role;
            }
        }
        return null;
    }
}
