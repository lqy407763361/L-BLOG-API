package com.lblog.service;

import com.lblog.common.exception.ReturnException;
import com.lblog.common.util.PageResultUtil;
import com.lblog.dao.AdminDao;
import com.lblog.dao.AdminGroupDao;
import com.lblog.domain.Admin;
import com.lblog.domain.AdminGroup;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class AdminGroupService {

    @Autowired
    private AdminGroupDao adminGroupDao;

    @Autowired
    private AdminDao adminDao;

    //添加管理员群组
    @Transactional
    public void addAdminGroup(AdminGroup adminGroup){
        //判断群组名称是否合法和是否已存在
        String name = adminGroup.getName().trim();
        if(StringUtils.isBlank(name)){
            throw new ReturnException("管理员群组名称不能为空！");
        }
        Long existAdminGroupId = adminGroupDao.getAdminGroupId(name);
        if((existAdminGroupId != null) && (existAdminGroupId > 0)){
            throw new ReturnException("管理员群组名称已存在！");
        }

        String description = adminGroup.getDescription().trim();
        Integer status = 1;
        Integer sortOrder = 0;
        String viewPower = adminGroup.getViewPower().trim();
        String editPower = adminGroup.getEditPower().trim();
        Long addTime = Instant.now().toEpochMilli();
        adminGroup.setName(name);
        adminGroup.setDescription(description);
        adminGroup.setStatus(status);
        adminGroup.setSortOrder(sortOrder);
        adminGroup.setViewPower(viewPower);
        adminGroup.setEditPower(editPower);
        adminGroup.setAddTime(addTime);
        adminGroupDao.addAdminGroup(adminGroup);
    }

    //编辑管理员群组
    @Transactional
    public void editAdminGroup(AdminGroup adminGroup){
        //判断管理员群组是否存在
        Long adminGroupId = adminGroup.getId();
        AdminGroup adminGroupDetail = adminGroupDao.getAdminGroupDetail(adminGroupId);
        if(adminGroupDetail == null){
            throw new ReturnException("管理员群组不存在！");
        }

        String name = adminGroup.getName().trim();
        String description = adminGroup.getDescription().trim();
        Integer status = adminGroup.getStatus();
        Integer sortOrder = adminGroup.getSortOrder();
        String viewPower = adminGroup.getViewPower().trim();
        String editPower = adminGroup.getEditPower().trim();
        Long editTime = Instant.now().toEpochMilli();
        adminGroup.setName(name);
        adminGroup.setDescription(description);
        adminGroup.setStatus(status);
        adminGroup.setSortOrder(sortOrder);
        adminGroup.setViewPower(viewPower);
        adminGroup.setEditPower(editPower);
        adminGroup.setEditTime(editTime);
        adminGroupDao.editAdminGroup(adminGroup);
    }

    //删除管理员群组
    @Transactional
    public void deleteAdminGroup(Long adminGroupId){
        //判断管理员群组ID
        if((adminGroupId == null) || (adminGroupId == 0)){
            throw new ReturnException("管理员群组ID不能为空！");
        }

        //判断该群组下属是否存在管理员
        Admin admin = new Admin();
        admin.setGroupId(adminGroupId);
        Integer adminTotal = adminDao.getAdminTotal(admin);
        if((adminTotal != null) && (adminTotal > 0)){
            throw new ReturnException("该群组下存在管理员！");
        }

        adminGroupDao.deleteAdminGroup(adminGroupId);
    }

    //获取管理员群组列表
    public PageResultUtil<AdminGroup> getAdminGroupList(Integer startPage, Integer size, AdminGroup adminGroup){
        //起始位置
        Integer startNum = (startPage-1) * size;
        //获取总数
        Integer total = adminGroupDao.getAdminGroupTotal(adminGroup);
        //查询列表
        List<AdminGroup> adminGroupList = adminGroupDao.getAdminGroupList(startNum, size, adminGroup);

        return new PageResultUtil<>(startPage, size, total, adminGroupList);
    }

    //获取管理员群组详情
    @Transactional
    public AdminGroup getAdminGroupDetail(Long adminGroupId){
        //判断分类ID
        if((adminGroupId == null) || (adminGroupId == 0)){
            throw new ReturnException("管理员群组ID不能为空！");
        }

        return adminGroupDao.getAdminGroupDetail(adminGroupId);
    }

    //获取管理员群组数量
    public Integer getAdminGroupTotal(AdminGroup adminGroup){
        return adminGroupDao.getAdminGroupTotal(adminGroup);
    }
}
