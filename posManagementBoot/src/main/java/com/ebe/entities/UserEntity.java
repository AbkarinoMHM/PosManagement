package com.ebe.entities;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;
import java.util.List;

/**
 * Created by saado on 11/16/2016.
 */
@Entity
@Table(name = "user")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name="user_seq", sequenceName="user_user_id_seq", allocationSize=1)
    @Id
    @Column(name="user_id")
    private  Integer userId;
    @Basic(optional = false)
    private String userFullName;
    @Basic(optional = false)
    private String userEmail;
    @Basic(optional = false)
    private String userPassword;
    private String userAddress;
    private String userMobile;
    private Boolean userIsCustomer;

    public UserEntity(){

    }

    public UserEntity(String fullName, String email, String password, boolean isCustomer){
        this.userFullName= fullName;
        this.userEmail = email;
        this.userPassword = password;
        this.userIsCustomer = isCustomer;
    }

    @ManyToMany
    @JoinTable(name="user_role",
            joinColumns=
            @JoinColumn(name="user_role_user_id", referencedColumnName="user_id"),
            inverseJoinColumns=
            @JoinColumn(name="user_role_role_id", referencedColumnName="role_id")
    )
    public List<RoleEntity> roles;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public Boolean getUserIsCustomer() {
        return userIsCustomer;
    }

    public void setUserIsCustomer(Boolean userIsCustomer) {
        this.userIsCustomer = userIsCustomer;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (this.userId != that.getUserId()) return false;
        if (this.userId != null ? !this.userId.equals(that.getUserId()) : that.getUserId() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return this.userId.hashCode();
    }



}
