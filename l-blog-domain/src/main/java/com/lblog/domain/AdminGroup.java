package com.lblog.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "l_admin_group")
public class AdminGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Integer status;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "view_power")
    private String viewPower;

    @Column(name = "edit_power")
    private String editPower;

    @Column(name = "add_time")
    private Long addTime;

    @Column(name = "edit_time")
    private Long editTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getViewPower() {
        return viewPower;
    }

    public void setViewPower(String viewPower) {
        this.viewPower = viewPower;
    }

    public String getEditPower() {
        return editPower;
    }

    public void setEditPower(String editPower) {
        this.editPower = editPower;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Long getEditTime() {
        return editTime;
    }

    public void setEditTime(Long editTime) {
        this.editTime = editTime;
    }
}
