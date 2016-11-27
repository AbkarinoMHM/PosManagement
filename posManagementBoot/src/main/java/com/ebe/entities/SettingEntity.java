package com.ebe.entities;

import javax.persistence.*;

/**
 * Created by saado on 10/17/2016.
 */
@Entity
@Table(name = "setting")
public class SettingEntity {

    @Id
    private String settingName;


    @Basic(optional = false)
    private String settingValue;

    public SettingEntity() {
    }

    public SettingEntity(String name, String value) {

        this.setSettingName(name);
        this.setSettingValue(value);
    }

    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SettingEntity that = (SettingEntity) o;

        if (!this.settingName.equals(that.getSettingName())) return false;
        if (this.settingName != null ? !this.settingName.equals(that.getSettingName()) : that.getSettingName() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return this.settingName.hashCode();
    }

    @Override
    public String toString() {
        return "Setting Name: " + this.getSettingName() + ", Setting Value: " + this.getSettingValue();
    }
}
