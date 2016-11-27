package com.ebe.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by saado on 11/16/2016.
 */
@Entity
@Table(name="role")
public class RoleEntity {
    @Id
    @Column(name="role_id")
    private  Integer roleId;
    @Column(name="role_name", nullable = false)
    private String ruleName;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleEntity that = (RoleEntity) o;

        if (this.roleId != that.getRoleId()) return false;
        if (this.roleId != null ? !this.roleId.equals(that.getRoleId()) : that.getRoleId() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return this.roleId.hashCode();
    }
}
