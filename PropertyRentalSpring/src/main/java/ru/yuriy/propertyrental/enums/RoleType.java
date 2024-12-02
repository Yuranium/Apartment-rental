package ru.yuriy.propertyrental.enums;

import org.springframework.security.core.GrantedAuthority;

public enum RoleType implements GrantedAuthority
{
    ROLE_GUEST, ROLE_USER, ROLE_OWNER, ROLE_ADMIN;

    @Override
    public String getAuthority()
    {
        return name();
    }
}