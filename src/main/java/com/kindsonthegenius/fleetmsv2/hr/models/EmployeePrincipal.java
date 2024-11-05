package com.kindsonthegenius.fleetmsv2.hr.models;

import com.kindsonthegenius.fleetmsv2.assetm.models.Asset;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmployeePrincipal implements UserDetails {


    private Employee employee;

    public EmployeePrincipal(Employee employee) {
        this.employee = employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Asset asset : employee.getAssets()) {
            authorities.add(new SimpleGrantedAuthority(asset.getAsset_name()));
        }
        return authorities;

        //return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return "null";
    }

    @Override
    public String getUsername() {
        return "null";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
