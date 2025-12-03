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
import java.util.Map;

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
        if(StringUtils.isBlank(adminGroup.getName())){
            throw new ReturnException("管理员群组名称不能为空！");
        }
        String name = adminGroup.getName().trim();
        Long existAdminGroupId = adminGroupDao.getAdminGroupId(name);
        if((existAdminGroupId != null) && (existAdminGroupId > 0)){
            throw new ReturnException("管理员群组名称已存在！");
        }

        //内容过滤
        String description = "";
        if(!StringUtils.isBlank(adminGroup.getDescription())){
            description = adminGroup.getDescription().trim();
        }

        String viewPower = "";
        if(!StringUtils.isBlank(adminGroup.getViewPower())){
            viewPower = adminGroup.getViewPower().trim();
        }

        String editPower = "";
        if(!StringUtils.isBlank(adminGroup.getEditPower())){
            editPower = adminGroup.getEditPower().trim();
        }

        Integer status = 1;
        Integer sortOrder = 0;
        Long addTime = Instant.now().getEpochSecond();
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

        //判断群组名称是否合法
        if(StringUtils.isBlank(adminGroup.getName())){
            throw new ReturnException("管理员群组名称不能为空！");
        }
        String name = adminGroup.getName().trim();

        //内容过滤
        String description = "";
        if(!StringUtils.isBlank(adminGroup.getDescription())){
            description = adminGroup.getDescription().trim();
        }

        String viewPower = "";
        if(!StringUtils.isBlank(adminGroup.getViewPower())){
            viewPower = adminGroup.getViewPower().trim();
        }

        String editPower = "";
        if(!StringUtils.isBlank(adminGroup.getEditPower())){
            editPower = adminGroup.getEditPower().trim();
        }

        Integer status = adminGroup.getStatus();
        Integer sortOrder = adminGroup.getSortOrder();
        Long editTime = Instant.now().getEpochSecond();
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
    public void deleteAdminGroup(Map<String, List<Long>> adminGroupId){
        //判断管理员群组ID
        if((adminGroupId == null) || adminGroupId.isEmpty()){
            throw new ReturnException("管理员群组ID不能为空！");
        }

        List<Long> adminGroupIdList = adminGroupId.get("id");

        //判断该群组下属是否存在管理员
        Integer adminTotal = adminDao.getAdminTotalByGroupId(adminGroupIdList);
        if((adminTotal != null) && (adminTotal > 0)){
            throw new ReturnException("该群组下存在管理员！");
        }

        adminGroupDao.deleteAdminGroup(adminGroupIdList);
    }

    //获取管理员群组列表
    public PageResultUtil<AdminGroup> getAdminGroupList(Integer page, Integer size, AdminGroup adminGroup){
        //起始位置
        Integer startNum = (page-1) * size;
        //获取总数
        Integer total = adminGroupDao.getAdminGroupTotal(adminGroup);
        //查询列表
        List<AdminGroup> adminGroupList = adminGroupDao.getAdminGroupList(startNum, size, adminGroup);

        return new PageResultUtil<>(page, size, total, adminGroupList);
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
