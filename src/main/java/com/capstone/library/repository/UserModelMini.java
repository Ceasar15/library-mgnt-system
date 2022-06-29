package com.capstone.library.repository;

import com.capstone.library.model.RoleType;

import java.util.Set;

public interface UserModelMini {
    String getId();

    String getUsername();

    String getEmail();

    Set<RoleType> getRoleType();


}
