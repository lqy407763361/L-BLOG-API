package com.lblog.api;

import com.lblog.common.util.JsonResponseUtil;
import com.lblog.common.util.PageResultUtil;
import com.lblog.domain.AdminGroup;
import com.lblog.service.AdminGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AdminGroupApi {

    @Autowired
    private AdminGroupService adminGroupService;

    //添加管理员群组
    @PostMapping("/addAdminGroup")
    public JsonResponseUtil<String> addAdminGroup(@RequestBody AdminGroup adminGroup){
        adminGroupService.addAdminGroup(adminGroup);

        return JsonResponseUtil.success();
    }

    //编辑管理员群组
    @PostMapping("/editAdminGroup")
    public JsonResponseUtil<String> editAdminGroup(@RequestBody AdminGroup adminGroup){
        adminGroupService.editAdminGroup(adminGroup);

        return JsonResponseUtil.success();
    }

    //删除管理员群组
    @DeleteMapping("/deleteAdminGroup")
    public JsonResponseUtil<String> deleteAdminGroup(@RequestBody Map<String, List<Long>> adminGroupId){
        adminGroupService.deleteAdminGroup(adminGroupId);

        return JsonResponseUtil.success();
    }

    //获取管理员群组列表
    @GetMapping("/getAdminGroupList")
    public JsonResponseUtil<PageResultUtil<AdminGroup>> getAdminGroupList(@RequestParam(defaultValue = "1") Integer page, AdminGroup adminGroup){
        PageResultUtil<AdminGroup> adminGroupList = adminGroupService.getAdminGroupList(page, adminGroup);

        return JsonResponseUtil.success(adminGroupList);
    }

    //获取管理员群组详情
    @GetMapping("/getAdminGroupDetail")
    public JsonResponseUtil<AdminGroup> getAdminGroupDetail(Long adminGroupId){
        AdminGroup adminGroupDetail = adminGroupService.getAdminGroupDetail(adminGroupId);

        return JsonResponseUtil.success(adminGroupDetail);
    }

    //获取文章分类数量
    @GetMapping("getAdminGroupTotal")
    public JsonResponseUtil<Integer> getAdminGroupTotal(AdminGroup adminGroup){
        Integer adminGroupTotal = adminGroupService.getAdminGroupTotal(adminGroup);

        return JsonResponseUtil.success(adminGroupTotal);
    }
}
